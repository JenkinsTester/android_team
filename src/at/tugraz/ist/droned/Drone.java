/**
 *  Catroid: An on-device graphical programming language for Android devices
 *  Copyright (C) 2010  Catroid development team
 *  (<http://code.google.com/p/catroid/wiki/Credits>)
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package at.tugraz.ist.droned;

import java.util.ArrayList;
import at.tugraz.ist.droned.dcf.ThreadCmd;
import at.tugraz.ist.droned.dcf.ThreadNavData;
import at.tugraz.ist.droned.dcf.WiFiConnection;
import at.tugraz.ist.droned.dcf.config.DroneConfigSettings;
import at.tugraz.ist.droned.dcf.config.DroneConfiguration;
import at.tugraz.ist.droned.dcf.firmware.FirmwareUpdate;
import at.tugraz.ist.droned.dcf.navdata.NavData;
import at.tugraz.ist.droned.dcf.navdata.Tools;
import at.tugraz.ist.droned.dcf.security.DroneSecurityLayer;
import at.tugraz.ist.droned.dcf.video.NativeVideoWrapper;

public class Drone implements IDrone {

	private static Drone drone;

	public final int INTERVAL = 50;

	private WiFiConnection wifi;
	private NavData navData;
	private ThreadNavData threadNavData;
	private ThreadCmd threadCmd;

	private DroneConfiguration config;
	private DroneSecurityLayer dsl;
	private FirmwareUpdate fwUp;

	private int flyingMode;
	private boolean connected;
	private boolean video;
	private boolean videoRecording;
	private int cameraOrientation;

	public static Drone getInstance() {
		if (drone == null)
			drone = new Drone();
		return drone;
	}

	private Drone() {
		wifi = new WiFiConnection();
		config = new DroneConfiguration();
		dsl = new DroneSecurityLayer();
//		fwUp = new FirmwareUpdate();
		flyingMode = DroneConsts.FlyingMode.NORMAL;
	}

	public synchronized boolean connect() {
		if (DroneConsts.debug) {
			//Log.d(DroneConsts.DroneLogTag, "connect()");
		}

		if (connected) {
			return true;
		}

		try {
			navData = new NavData();

			threadNavData = new ThreadNavData(wifi, navData);
			threadCmd = new ThreadCmd(wifi);
			wifi.connect();

			threadNavData.start();
			threadCmd.start();

			if (!setConfiguration("AT*CONFIG=#SEQ#,"
					+ "\"custom:session_id\",\""
					+ DroneConfigSettings.id_session + "\"", false)) {
				disconnect();
				return false;
			}

			connected = true;

		} catch (Exception e) {
			//Log.e(DroneConsts.DroneLogTag, "Exception Drone -> connect()", e);
			return false;
		}

		return true;
	}

	public synchronized void disconnect() {
		if (DroneConsts.debug) {
			//Log.d(DroneConsts.DroneLogTag, "disconnect()");
		}

		try {
			connected = false;

			if (navData.packetDemo.flying_state != DroneConsts.FlyingState.LANDED) {
				emergencyLand();
			}

			if (videoRecording) {
				NativeVideoWrapper.stopVideoRecorder();
			}
			if (video) {
				NativeVideoWrapper.close();
				video = false;
			}

			wifi.setRunning(false);
			Thread.sleep(INTERVAL);

			wifi.disconnect();

			threadNavData = null;
			threadCmd = null;

		} catch (Exception e) {
			//Log.e(DroneConsts.DroneLogTag, "Exception Drone -> disconnect()", e);
		}
	}

	public void takeoff() {
		if (DroneConsts.debug) {
			//Log.d(DroneConsts.DroneLogTag, "takeoff()");
		}

		try {

			// reset drone after failure
			while (navData.packetDemo.flying_state == DroneConsts.FlyingState.EMERGENCY) {
				//Log.d(DroneConsts.DroneLogTag,
				//		"found takeoff avoiding error -> send emergency");
				emergency();
				Thread.sleep(2000);
			}

			wifi.sendAtCommand("AT*FTRIM=#SEQ#");
			Thread.sleep(INTERVAL);

			dsl.parseCommand("takeoff");
			threadCmd.setAxisMove(0, 0, 0, 0);
			threadCmd.setHovering(true);

			// wait until drone is flying
			while (navData.packetDemo.flying_state == DroneConsts.FlyingState.LANDED
					|| navData.packetDemo.flying_state == DroneConsts.FlyingState.TRANS_TAKEOFF) {
				Thread.sleep(200);
			}
		} catch (Exception e) {
			//Log.e(DroneConsts.DroneLogTag, "Exception Drone -> takeoff()", e);
		}
	}

	public void land() {
		if (DroneConsts.debug) {
			//Log.d(DroneConsts.DroneLogTag, "land()");
		}

		try {

			dsl.parseCommand("land");
			threadCmd.setAxisMove(0, 0, 0, 0);
			threadCmd.setHovering(false);

			// wait until drone landed
			int counter = 0;
			while (navData.packetDemo.flying_state != DroneConsts.FlyingState.LANDED) {
				Thread.sleep(200);
				if (counter++ > 15)
					break;
			}
		} catch (Exception e) {
			//Log.e(DroneConsts.DroneLogTag, "Exception DroneState -> land()", e);
		}
	}

	public void emergency() {
		if (DroneConsts.debug) {
			//Log.d(DroneConsts.DroneLogTag, "emergency()");
		}

		try {
			threadCmd.setHovering(false);

			wifi.sendAtCommand("AT*REF=#SEQ#,290717696");
			Thread.sleep(INTERVAL);

			dsl.parseCommand("emergency");

			wifi.sendAtCommand("AT*REF=#SEQ#,290717952");
			Thread.sleep(INTERVAL);

			wifi.sendAtCommand("AT*REF=#SEQ#,290717696");

		} catch (Exception e) {
			//Log.e(DroneConsts.DroneLogTag, "Exception Drone -> emergency()");
		}
	}

	public void emergencyLand() {
		if (DroneConsts.debug) {
			//Log.d(DroneConsts.DroneLogTag, "emergencyLand()");
		}
		if (!connected) {
			return;
		}

		dsl.parseCommand("land");
		threadCmd.setAxisMove(0, 0, 0, 0);
		threadCmd.setHovering(false);
	}

	public void move(double throttle, double roll, double pitch, double yaw) {
		if (DroneConsts.debug) {
			//Log.d(DroneConsts.DroneLogTag, "move()");
		}

		if (navData.packetDemo.flying_state == DroneConsts.FlyingState.LANDING
				|| navData.packetDemo.flying_state == DroneConsts.FlyingState.TRANS_TAKEOFF) {
			return;
		}

		dsl.parseCommand("move," + throttle + "," + roll + "," + pitch + ","
				+ yaw);
		threadCmd.setAxisMove((float) roll, (float) pitch, (float) throttle,
				(float) yaw);
	}

	public void playLedAnimation(int animation, float frequency,
			int durationSeconds) {
		if (DroneConsts.debug) {
			//Log.d(DroneConsts.DroneLogTag, "playLedAnimation()");
		}

		try {
			String command = "AT*LED=#SEQ#," + animation + ","
					+ Float.floatToRawIntBits(frequency) + ","
					+ durationSeconds;
			dsl.parseCommand("led");
			wifi.sendAtCommand(command);
		} catch (Exception e) {
			//Log.e(DroneConsts.DroneLogTag,
		//			"Exception Drone -> playLedAnimation()", e);
		}
	}

	public void playMoveAnimation(int animation, int durationSeconds) {
		if (DroneConsts.debug) {
			//Log.d(DroneConsts.DroneLogTag, "playMoveAnimation()");
		}

		try {
			if (navData.packetDemo.flying_state == DroneConsts.FlyingState.LANDING
					|| navData.packetDemo.flying_state == DroneConsts.FlyingState.TRANS_TAKEOFF) {
				return;
			}

			String command = "AT*ANIM=#SEQ#," + animation + ","
					+ durationSeconds * 1000;
			move(0, 0, 0, 0);
			dsl.parseCommand("anim");
			wifi.sendAtCommand(command);
		} catch (Exception e) {
			//Log.e(DroneConsts.DroneLogTag,
		//			"Exception Drone -> playMoveAnimation()", e);
		}
	}

	public boolean changeFlyingMode(int mode) {
		if (DroneConsts.debug) {
			//Log.d(DroneConsts.DroneLogTag, "changeFlyingMode() " + mode);
		}

		String command = "AT*CONFIG=#SEQ#,\"control:flying_mode\",\""
				+ DroneConsts.FlyingMode.NORMAL + "\"";

		// NORMAL
		if (mode == DroneConsts.FlyingMode.NORMAL) {
			for (int i = 0; i < DroneConfigSettings.FLyingModeConfigurations.NORMAL.length; i++) {
				if (!setConfiguration(
						"AT*CONFIG=#SEQ#,"
								+ DroneConfigSettings.FLyingModeConfigurations.NORMAL[i],
						true)) {
					return false;
				}
			}
			cameraOrientation = DroneConsts.CameraOrientation.HORI;
		}

		// HOVER_ON_TOP_OF_ROUNDEL
		else if (mode == DroneConsts.FlyingMode.HOVER_ON_TOP_OF_ROUNDEL) {
			for (int i = 0; i < DroneConfigSettings.FLyingModeConfigurations.HOVER_ON_TOP_OF_ROUNDEL.length; i++) {
				if (!setConfiguration(
						"AT*CONFIG=#SEQ#,"
								+ DroneConfigSettings.FLyingModeConfigurations.HOVER_ON_TOP_OF_ROUNDEL[i],
						true)) {
					return false;
				}
			}
			command = "AT*CONFIG=#SEQ#,\"control:flying_mode\",\"" + mode
					+ "\"";
			cameraOrientation = DroneConsts.CameraOrientation.VERT;
		}

		// FOLLOW_SHELL_TAG_YAW - own implemented flying mode
		else if (mode == DroneConsts.FlyingMode.FOLLOW_SHELL_TAG_YAW) {
			for (int i = 0; i < DroneConfigSettings.FLyingModeConfigurations.FOLLOW_SHELL_TAG_YAW.length; i++) {
				if (!setConfiguration(
						"AT*CONFIG=#SEQ#,"
								+ DroneConfigSettings.FLyingModeConfigurations.FOLLOW_SHELL_TAG_YAW[i],
						true)) {
					return false;
				}
			}
			cameraOrientation = DroneConsts.CameraOrientation.HORI;
		}

		if (!setConfiguration(command, true)) {
			return false;
		}

		move(0, 0, 0, 0);
		flyingMode = mode;
		NativeVideoWrapper.setFlyMode(mode);
		return true;
	}

	public boolean doStartUpConfiguration() {
		if (DroneConsts.debug) {
			//Log.d(DroneConsts.DroneLogTag, "doStartUpConfiguration()");
		}

		// 1. init commands: create or switch to configs
		for (int i = 0; i < DroneConfigSettings.CONFIG_INIT_CMDS.length; i++) {
			if (!setConfiguration("AT*CONFIG=#SEQ#,"
					+ DroneConfigSettings.CONFIG_INIT_CMDS[i], false)) {
				return false;
			}
		}

		// 2. describe configs with name
		for (int i = 0; i < DroneConfigSettings.CONFIG_DESC_CMDS.length; i++) {
			if (!setConfiguration("AT*CONFIG=#SEQ#,"
					+ DroneConfigSettings.CONFIG_DESC_CMDS[i], true)) {
				return false;
			}
		}

		// 3. read configuration
		if (!config.readConfiguration(wifi)) {
			return false;
		}

		// 4. compare read-in config to default config cmds
		ArrayList<String> cmds = config.checkConfiguration();

		// 5. write configurations
		if (cmds.size() != 0) {
			for (int i = 0; i < cmds.size(); i++) {
				if (!setConfiguration("AT*CONFIG=#SEQ#," + cmds.get(i), true)) {
					return false;
				}
			}
		}

		return true;
	}

	public synchronized boolean setConfiguration(String configuration,
			boolean isIDScmd) {
		if (DroneConsts.debug) {
			//Log.d(DroneConsts.DroneLogTag, "----------------------------------");
			//Log.d(DroneConsts.DroneLogTag, "setConfiguration()");
			//Log.d(DroneConsts.DroneLogTag, configuration);
		}

		try {
			// https://projects.ardrone.org/boards/1/topics/show/2364
			final int WriteConfigTimeout = 3000;
			final int interval = 100;
			int counter = 0;

			// 1. check if reseted or reset AckBit for configuration
			while (Tools.get_state_bit(navData.drone_state, 6)) {
				wifi.sendAtCommand(DroneConfiguration.RESET_ACKBIT_CMD);
				Thread.sleep(interval * 2);
				if (counter++ > WriteConfigTimeout) {
					return false;
				}
			}

			// 2. send command
			// https://projects.ardrone.org/boards/1/topics/show/3383
			if (isIDScmd) {
				configuration = "#IDS#\r" + configuration;
			}

			dsl.parseCommand("config");
			wifi.sendAtCommand(configuration);
			counter = 0;
			// 3. wait until AckBit is 1
			while (!Tools.get_state_bit(navData.drone_state, 6)) {
				Thread.sleep(interval);
				if (counter++ > WriteConfigTimeout) {
					return false;
				}
			}

			// 4. send reset AckBit cmd
			// TODO spam drone with cmd
			wifi.sendAtCommand(DroneConfiguration.RESET_ACKBIT_CMD);

			if (DroneConsts.debug) {
				//Log.d(DroneConsts.DroneLogTag, "OK!");
			}

			if (configuration.contains("video:video_channel")) {
				if (configuration.contains("0")) {
					cameraOrientation = DroneConsts.CameraOrientation.HORI;
				} else if (configuration.contains("1")) {
					cameraOrientation = DroneConsts.CameraOrientation.VERT;
				} else if (configuration.contains("2")) {
					cameraOrientation = DroneConsts.CameraOrientation.HORI_SMALL_VERT;
				} else if (configuration.contains("3")) {
					cameraOrientation = DroneConsts.CameraOrientation.LARGE_VERT_SMALL_HORI;
				} else {
					//Log.d(DroneConsts.DroneLogTag,
			//				" setConfiguration(): unknown video_channel!");
				}
			}

		} catch (Exception e) {
			//Log.e(DroneConsts.DroneLogTag,
			//		"Exception Drone -> setConfiguration()", e);
			return false;
		}
		return true;
	}

	public void setDslTimeout(int seconds) {
		if (DroneConsts.debug) {
			//Log.d(DroneConsts.DroneLogTag, "setDslTimeout()");
		}
		dsl.setTimeout(seconds);
	}

	public void startVideo() {
		if (DroneConsts.debug) {
			//Log.d(DroneConsts.DroneLogTag, "startVideo()");
		}

		video = true;
		NativeVideoWrapper.init();

		NativeVideoWrapper.setFlyMode(0);
		NativeVideoWrapper
				.setBatteryLevel(navData.packetDemo.vbat_flying_percentage);

		if (DroneConsts.debug) {
			NativeVideoWrapper.showFPS(true);
		}
	}

	public void renderVideoFrame(int glHandle) {
		NativeVideoWrapper.renderVideoFrame(glHandle);
	}

	public void stopVideo() {
		if (DroneConsts.debug) {
			//Log.d(DroneConsts.DroneLogTag, "stopVideo()");
		}

		if (video) {
			NativeVideoWrapper.close();
			video = false;
		}
	}

	public void startVideoRecorder(String path) {
		if (DroneConsts.debug) {
			//Log.d(DroneConsts.DroneLogTag, "startVideoRecorder()");
		}

		if (!videoRecording) {
			videoRecording = true;
			NativeVideoWrapper.startVideoRecorder(path);
		}
	}

	public void stopVideoRecorder() {
		if (DroneConsts.debug) {
			//Log.d(DroneConsts.DroneLogTag, "stopVideoRecorder()");
		}

		if (videoRecording) {
			videoRecording = false;
			NativeVideoWrapper.stopVideoRecorder();
		}
	}

	public void saveVideoSnapshot(String path) {
		if (DroneConsts.debug) {
			//Log.d(DroneConsts.DroneLogTag, "saveVideoSnapshot()");
		}

		NativeVideoWrapper.saveSnapshot(path);
	}

	public int getFlyingMode() {
		return flyingMode;
	}

	public int getCameraOrientation() {
		return cameraOrientation;
	}

	public int getBatteryLoad() {
		return (int) navData.packetDemo.vbat_flying_percentage;
	}

	public boolean isConnected() {
		return connected;
	}

	public String getFirmwareVersion() {
		return fwUp.getFirmwareVersion();
	}

	public boolean uploadFirmwareFile() {
		return fwUp.uploadFile();
	}

	public boolean restartDrone() {
		return fwUp.restartDrone();
	}

}
