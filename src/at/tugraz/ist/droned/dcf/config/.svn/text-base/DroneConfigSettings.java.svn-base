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
package at.tugraz.ist.droned.dcf.config;

import java.util.HashMap;
import java.util.Map;

import at.tugraz.ist.droned.DroneConsts;

public final class DroneConfigSettings {

	public static final String id_session = "ca0000d1";
	public static final String id_application = "ca0000d2";
	public static final String id_profile = "ca0000d3";

	public static final String session = "Session 1";
	public static final String application = "at.tugraz.ist.catroid:"
			+ DroneConsts.SupportedDroneFirmwareVersion;
	public static final String profile = "usr.catroid";

	// @formatter:off

	public static final String CONFIG_IDS_CMD = "AT*CONFIG_IDS=#SEQ#,\""
			+ id_session + "\",\"" + id_profile + "\",\"" + id_application
			+ "\"";

	// see DeveloperGuide SDK 1.7 page 68ff
	// most settings can be found in ardrone_api.h
	public static final String[] CONFIG_INIT_CMDS = {
			"\"custom:session_id\",\"" + "-all" + "\"", // deletes all session
														// configs (good
														// practice to do this)
			"\"custom:session_id\",\"" + id_session + "\"", // create and/or
															// switch to config
			"\"custom:application_id\",\"" + id_application + "\"",
			"\"custom:profile_id\",\"" + id_profile + "\"" };

	public static final String[] CONFIG_DESC_CMDS = {
			"\"custom:session_desc\",\"" + session + "\"", // sets the current
															// application
															// description
															// string
			"\"custom:application_desc\",\"" + application + "\"",
			"\"custom:profile_desc\",\"" + profile + "\"" };

	public static final Map<String, String> DEFAULT_CONFIG_CMDS = new HashMap<String, String>() {
		private static final long serialVersionUID = 1L;
		{
			// -----------------------------------------------------------------
			// <! General configuration !>

			// -----------------------------------------------------------------
			// <! Control configuration !>
			// This settings tells the control loop that the Drone is flying
			// outside.
			// Setting the indoor/outdoor flight will load the corresponding
			// indoor/outdoor_control_yaw,
			// indoor/outdoor_euler_angle_max and indoor/outdoor_control_vz_max.
			put("control:outdoor", "TRUE");

			// combined-yaw
			put("control:control_level", "1");

			// Minimum drone altitude in millimeters.
			// Should be left to default value (50), for control stabilities
			// issues.
			put("control:altitude_min", "50");

			// Maximum drone altitude in millimeters. Give an integer value
			// between 500 and 5000 to prevent the drone
			// from flying above this limit,
			// or set it to 10000 to let the drone fly as high as desired.
			put("control:altitude_max", "3000");

			// Maximum bending angle for the drone in radians, for both pitch
			// and roll angles.
			// This parameter is a positive floating-point value between 0 and
			// 0.52 (ie. 30 deg).
			put("control:euler_angle_max", "0.18");

			// Maximum vertical speed of the Drone, in milimeters per second.
			// Recommanded values goes from 200 to 2000. Others values may cause
			// instability.
			put("control:control_vz_max", "800");

			// Maximum yaw speed of the Drone, in radians per second.
			// Recommanded values goes from 40=s to 350=s (approx 0.7rad=s to
			// 6.11rad=s). Others values may cause
			// instability.
			put("control:control_yaw", "2.05");

			// This settings tells the control loop that the Drone is
			// currently using the outdoor hull.
			// This setting is NOT linked with the CONTROL:outdoor setting. They
			// have different effects on the control
			// loop.
			put("control:flight_without_shell", "FALSE");

			// 0 -> FREE_FLIGHT, 1 -> HOVER_ON_TOP_OF_ROUNDEL
			put("control:flying_mode", "0");

			// -----------------------------------------------------------------
			// <! Network configuration !>
			// 0 -> acces-point-mode, 1 -> adhoc-mode
			put("network:wifi_mode", "0");

			// -----------------------------------------------------------------
			// <! Nav-board configuration !>

			// -----------------------------------------------------------------
			// <! Video configuration !>
			// Used video-codec. (P264 (64), VLIB (32))
			put("video:video_codec", "64");

			// Sets the bitrate of the video transmission.
			// Recommended values are 20000 for VLIB codec, and 15000 for P264
			// codec.
			put("video:bitrate", "15000");

			// Enables the automatic bitrate control of the video stream.
			// Enabling this configuration will reduce the bandwith used by the
			// video
			// stream under bad Wi-Fi conditions, reducing the commands latency.
			put("video:bitrate_control_mode", "1");

			// The video channel that will be sent to the controller.
			put("video:video_channel", "0");

			// -----------------------------------------------------------------
			// <! Leds configuration !>

			// -----------------------------------------------------------------
			// <! Detection configuration !>
			put("detect:detect_type", "0");

			// Bitfields to select detections that should be enabled on
			// horizontal camera.
			put("detect:detections_select_h", "0");

			// Bitfileds to select the detections that should be enabled on
			// vertical camera.
			put("detect:detections_select_v_hsync", "0");

			// Bitfileds to select the detections that should be active on
			// vertical camera.
			// These detections will be run at 60Hz. If you don’t need that
			// speed, using detections_select_v_hsync
			// instead will reduce the CPU load.
			put("detect:detections_select_v", "0");

			put("detect:enemy_colors", "0");

			put("detect:groundstripe_colors", "0");

			put("detect:enemy_without_shell", "FALSE");
		}
	};

	public static final class VideoChannelSetting {
		public static final int HORI = 0; // horizontal camera
		public static final int VERT = 1; // vertical camera
		public static final int HORI_SMALL_VERT = 2; // horizontal camera with
														// vertical camera
														// picture inserted in
														// the
														// left-top corner
		public static final int LARGE_VERT_SMALL_HORI = 3; // vertical camera
															// with horizontal
															// camera picture
															// inserted in
															// the left-top
															// corner
		public static final int NEXT = 6;
	}

	public static final class DetectionTypeSetting {
		public static final int VISION = 2; // Detection of 2D horizontal tags
											// on drone shells
		public static final int NONE = 3; // Detection disabled
		public static final int COCARDE = 4; // Detects a roundel under the
												// drone
		public static final int ORIENTED_COCARDE = 5; // Detects an oriented
														// roundel under the
														// drone
		public static final int STRIPE = 6; // Detects a uniform stripe on the
											// ground
		public static final int H_COCARDE = 7; // Detects a roundel in front of
												// the drone
		public static final int H_ORIENTED_COCARDE = 8; // Detects an oriented
														// roundel in front of
														// the drone
		public static final int STRIPE_V = 9;
		public static final int MULTIPLE_DETECTION_MODE = 10; // The drone uses
																// several
																// detections at
																// the same time
		public static final int CAP = 11; // Detects a Cap orange and green in
											// front of the drone
	}

	// TAG TYPES
	public final class DetectionSelectSetting {
		public static final int NONE = 0;
		public static final int SHELL_TAG = 1;
		public static final int ROUNDEL = 2;
		public static final int ORIENTED_ROUNDEL = 3;
		public static final int STRIPE = 4;
		public static final int CAP = 5;
	}

	public static final class DetectionEnemyColorSetting {
		public static final int ORANGE_GREEN = 1; // orange-green-orange tags
		public static final int ORANGE_YELLOW = 2; // orange-yellow-orange tags
		public static final int ORANGE_BLUE = 3; // orange-blue-orange tags
	}

	// FlyingModeConfigurations
	public static final class FLyingModeConfigurations {

		// NORMAL
		public static final String[] NORMAL = {
				"\"detect:detect_type\",\"" + DetectionTypeSetting.NONE + "\"",
				"\"detect:detections_select_h\",\""
						+ DetectionSelectSetting.NONE + "\"",
				"\"detect:detections_select_v_hsync\",\""
						+ DetectionSelectSetting.NONE + "\"",
				"\"detect:detections_select_v\",\""
						+ DetectionSelectSetting.NONE + "\"",
				"\"detect:enemy_colors\",\"0\"",
				"\"detect:groundstripe_colors\",\"0\"",

				// reset
				"\"video:video_channel\",\"" + VideoChannelSetting.HORI + "\"",
				"\"control:altitude_max\",\"3000\"",
				"\"control:euler_angle_max\",\"0.18\"",
				"\"control:control_vz_max\",\"800\"",
				"\"control:control_yaw\",\"2.05\"",
				"\"control:altitude_min\",\"50\"" };

		// HOVER_ON_TOP_OF_ROUNDEL
		public static final String[] HOVER_ON_TOP_OF_ROUNDEL = {
				"\"detect:detect_type\",\"" + DetectionTypeSetting.COCARDE
						+ "\"",
				"\"detect:detections_select_v_hsync\",\""
						+ DetectionSelectSetting.ROUNDEL + "\"",
				"\"video:video_channel\",\"" + VideoChannelSetting.VERT + "\"",

				// reset
				"\"detect:detections_select_h\",\""
						+ DetectionSelectSetting.NONE + "\"",
				"\"detect:detections_select_v\",\""
						+ DetectionSelectSetting.NONE + "\"",
				"\"detect:enemy_colors\",\"0\"",
				"\"detect:groundstripe_colors\",\"0\"" };

		// FOLLOW_SHELL_TAG_YAW
		public static final String[] FOLLOW_SHELL_TAG_YAW = {
				"\"detect:detect_type\",\"" + DetectionTypeSetting.VISION
						+ "\"",
				"\"detect:detections_select_h\",\""
						+ DetectionSelectSetting.SHELL_TAG + "\"",
				"\"detect:enemy_colors\",\""
						+ DetectionEnemyColorSetting.ORANGE_YELLOW + "\"",
				"\"video:video_channel\",\"" + VideoChannelSetting.HORI + "\"",

				// reset
				"\"detect:detections_select_v_hsync\",\""
						+ DetectionSelectSetting.NONE + "\"",
				"\"detect:detections_select_v\",\""
						+ DetectionSelectSetting.NONE + "\"",
				"\"detect:groundstripe_colors\",\"0\"",

				// movement settings
				"\"control:control_yaw\",\"1.00\"",
				"\"control:euler_angle_max\",\"0.15\"",
				"\"control:control_vz_max\",\"200\"" };
	}

	// @formatter:on
}

// general:num_version_config = 1
// general:num_version_config = 1
// general:num_version_mb = 17
// general:num_version_soft = 1.7.4
// general:soft_build_date = 2011-07-13 09:52
// general:motor1_soft = 1.20
// general:motor1_hard = 3.0
// general:motor1_supplier = 1.1
// general:motor2_soft = 1.20
// general:motor2_hard = 3.0
// general:motor2_supplier = 1.1
// general:motor3_soft = 1.20
// general:motor3_hard = 3.0
// general:motor3_supplier = 1.1
// general:motor4_soft = 1.20
// general:motor4_hard = 3.0
// general:motor4_supplier = 1.1
// general:ardrone_name = My Drone
// general:flying_time = 2217
// general:navdata_demo = TRUE
// general:com_watchdog = 2
// general:video_enable = TRUE
// general:vision_enable = TRUE
// general:vbat_min = 9000
// control:accs_offset = { -2.2353198e+03 1.9247540e+03 2.0844270e+03 }
// control:accs_gains = { 1.0109735e+00 2.3000900e-02 4.1132037e-02
// -4.2992691e-03 -9.9152333e-01 2.8609591e-02
// 1.2049361e-02 -6.9085114e-02 -9.8430282e-01 }
// control:gyros_offset = { 1.6490200e+03 1.6510120e+03 1.6575740e+03 }
// control:gyros_gains = { 6.9980328e-03 -7.0105256e-03 -3.7592391e-03 }
// control:gyros110_offset = { 1.6447460e+03 1.6526660e+03 }
// control:gyros110_gains = { 1.5487089e-03 -1.5530029e-03 }
// control:gyro_offset_thr_x = 4.0000000e+00
// control:gyro_offset_thr_y = 4.0000000e+00
// control:gyro_offset_thr_z = 5.0000000e-01
// control:pwm_ref_gyros = 470
// control:shield_enable = 1
// control:altitude_max = 5000
// control:altitude_min = 50
// control:outdoor = FALSE
// control:flight_without_shell = FALSE
// control:brushless = TRUE
// control:autonomous_flight = FALSE
// control:flight_anim = 0
// network:ssid_single_player = ardrone_160717
// network:ssid_multi_player = ardrone_v1.7.4
// network:wifi_mode = 0
// network:secure = FALSE
// network:passkey =
// network:navdata_port = 5554
// network:video_port = 5555
// network:at_port = 5556
// network:cmd_port = 5559
// network:owner_mac = 00:00:00:00:00:00
// network:owner_ip_address = 0
// network:local_ip_address = 0
// network:broadcast_address = 0
// pic:ultrasound_freq = 8
// pic:ultrasound_watchdog = 3
// pic:pic_version = 100925505
// video:camif_fps = 15
// video:camif_buffers = 2
// video:num_trackers = 12
// leds:leds_anim = 0
// detect:enemy_colors = 1
// detect:enemy_without_shell = 0
// syslog:output = 7
// syslog:max_size = 102400
// syslog:nb_files = 5
// general:navdata_options = 65537
// control:control_level = 1
// video:bitrate = 6172
// video:bitrate_ctrl_mode = 1
// video:video_codec = 64
// custom:application_desc = at.tugraz.ist.catroid:1.7.4
// control:euler_angle_max = 1.5000001e-01
// control:control_trim_z = 0.0000000e+00
// control:control_iphone_tilt = 3.4906584e-01
// control:control_vz_max = 5.0000000e+02
// control:control_yaw = 1.0000000e+00
// control:manual_trim = FALSE
// control:indoor_euler_angle_max = 1.0000000e+00
// control:indoor_control_vz_max = 1.0000000e+00
// control:indoor_control_yaw = 1.0000000e+00
// control:outdoor_euler_angle_max = 1.0000000e+00
// control:outdoor_control_vz_max = 1.0000000e+00
// control:outdoor_control_yaw = 1.0000000e+00
// custom:profile_desc = usr.catroid
// control:flying_mode = 0
// video:video_channel = 0
// detect:groundstripe_colors = 16
// detect:detect_type = 3
// detect:detections_select_h = 0
// detect:detections_select_v_hsync = 0
// detect:detections_select_v = 0
// custom:application_id = ca0000d2
// custom:profile_id = ca0000d3
// custom:session_id = ca0000d1
// custom:session_desc = Session 1
// general:num_version_mb = 17
// general:num_version_soft = 1.7.4
// general:soft_build_date = 2011-07-13 09:52
// general:motor1_soft = 1.20
// general:motor1_hard = 3.0
// general:motor1_supplier = 1.1
// general:motor2_soft = 1.20
// general:motor2_hard = 3.0
// general:motor2_supplier = 1.1
// general:motor3_soft = 1.20
// general:motor3_hard = 3.0
// general:motor3_supplier = 1.1
// general:motor4_soft = 1.20
// general:motor4_hard = 3.0
// general:motor4_supplier = 1.1
// general:ardrone_name = My Drone
// general:flying_time = 2217
// general:navdata_demo = TRUE
// general:com_watchdog = 2
// general:video_enable = TRUE
// general:vision_enable = TRUE
// general:vbat_min = 9000
// control:accs_offset = { -2.2353198e+03 1.9247540e+03 2.0844270e+03 }
// control:accs_gains = { 1.0109735e+00 2.3000900e-02 4.1132037e-02
// -4.2992691e-03 -9.9152333e-01 2.8609591e-02
// 1.2049361e-02 -6.9085114e-02 -9.8430282e-01 }
// control:gyros_offset = { 1.6490200e+03 1.6510120e+03 1.6575740e+03 }
// control:gyros_gains = { 6.9980328e-03 -7.0105256e-03 -3.7592391e-03 }
// control:gyros110_offset = { 1.6447460e+03 1.6526660e+03 }
// control:gyros110_gains = { 1.5487089e-03 -1.5530029e-03 }
// control:gyro_offset_thr_x = 4.0000000e+00
// control:gyro_offset_thr_y = 4.0000000e+00
// control:gyro_offset_thr_z = 5.0000000e-01
// control:pwm_ref_gyros = 470
// control:shield_enable = 1
// control:altitude_max = 5000
// control:altitude_min = 50
// control:outdoor = FALSE
// control:flight_without_shell = FALSE
// control:brushless = TRUE
// control:autonomous_flight = FALSE
// control:flight_anim = 0
// network:ssid_single_player = ardrone_160717
// network:ssid_multi_player = ardrone_v1.7.4
// network:wifi_mode = 0
// network:secure = FALSE
// network:passkey =
// network:navdata_port = 5554
// network:video_port = 5555
// network:at_port = 5556
// network:cmd_port = 5559
// network:owner_mac = 00:00:00:00:00:00
// network:owner_ip_address = 0
// network:local_ip_address = 0
// network:broadcast_address = 0
// pic:ultrasound_freq = 8
// pic:ultrasound_watchdog = 3
// pic:pic_version = 100925505
// video:camif_fps = 15
// video:camif_buffers = 2
// video:num_trackers = 12
// leds:leds_anim = 0
// detect:enemy_colors = 1
// detect:enemy_without_shell = 0
// syslog:output = 7
// syslog:max_size = 102400
// syslog:nb_files = 5
// general:navdata_options = 65537
// control:control_level = 1
// video:bitrate = 6172
// video:bitrate_ctrl_mode = 1
// video:video_codec = 64
// custom:application_desc = at.tugraz.ist.catroid:1.7.4
// control:euler_angle_max = 1.5000001e-01
// control:control_trim_z = 0.0000000e+00
// control:control_iphone_tilt = 3.4906584e-01
// control:control_vz_max = 5.0000000e+02
// control:control_yaw = 1.0000000e+00
// control:manual_trim = FALSE
// control:indoor_euler_angle_max = 1.0000000e+00
// control:indoor_control_vz_max = 1.0000000e+00
// control:indoor_control_yaw = 1.0000000e+00
// control:outdoor_euler_angle_max = 1.0000000e+00
// control:outdoor_control_vz_max = 1.0000000e+00
// control:outdoor_control_yaw = 1.0000000e+00
// custom:profile_desc = usr.catroid
// control:flying_mode = 0
// video:video_channel = 0
// detect:groundstripe_colors = 16
// detect:detect_type = 3
// detect:detections_select_h = 0
// detect:detections_select_v_hsync = 0
// detect:detections_select_v = 0
// custom:application_id = ca0000d2
// custom:profile_id = ca0000d3
// custom:session_id = ca0000d1
// custom:session_desc = Session 1
