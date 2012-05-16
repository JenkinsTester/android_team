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
package at.tugraz.ist.droned.dcf;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import at.tugraz.ist.droned.DroneConsts;
import at.tugraz.ist.droned.dcf.config.DroneConfigSettings;

public class WiFiConnection {

	public final int AT_PORT = 5556;
	public final int NAV_PORT = 5554;
	public final int CONF_PORT = 5559;
	public final int VIDEO_PORT = 5555;

	public final int INTERVAL = 30;

	public final byte[] trigger_bytes = { 0x01, 0x00, 0x00, 0x00 };

	public InetAddress ip_drone = null;
	protected DatagramSocket socketCmd = null;
	protected DatagramSocket socketNav = null;

	private int seqNumber = 0;
	protected boolean running = false;

	public WiFiConnection() {
		setIp("192.168.1.1");
	}

	public void setIp(String ip) {
		try {
			ip_drone = InetAddress.getByName(ip);
		} catch (Exception e) {
			//Log.e(DroneConsts.DroneLogTag, "Exception Wificonnection -> setIp()", e);
		}
	}

	public void connect() throws IOException, InterruptedException {
		seqNumber = 1;

		running = true;

		connectCMD();
		connectNAV();
	}

	public void disconnect() throws IOException {
		try {
			if (socketNav != null) {
				socketNav.close();
			}
			if (socketCmd != null) {
				socketCmd.close();
			}
		} catch (Exception e) {
			//Log.e(DroneConsts.DroneLogTag, "Exception WifiConnection -> disconnect()", e);
		}

	}

	void connectCMD() throws IOException, InterruptedException {
		if (socketCmd != null) {
			socketCmd.close();
		}

		socketCmd = new DatagramSocket();
		socketCmd.setSoTimeout(3000);

		sendAtCommand("AT*COMWDG=1");
		Thread.sleep(INTERVAL);

		sendAtCommand("AT*CTRL=#SEQ#,5,0");
		Thread.sleep(INTERVAL);

		sendAtCommand("AT*PCMD=#SEQ#,0,0,0,0,0");
		Thread.sleep(INTERVAL);

		//Log.d(DroneConsts.DroneLogTag, "Socket CMD connected.");
	}

	public void setSocketNAV(DatagramSocket socketNAV)
	{
		this.socketNav = socketNAV;
	}
	public void connectNAV() throws IOException, InterruptedException {
		if (socketNav != null) {
			socketNav.close();
		}
		socketNav = new DatagramSocket(NAV_PORT); 
		socketNav.setSoTimeout(1000);

		DatagramPacket dg = new DatagramPacket(trigger_bytes, trigger_bytes.length, ip_drone, NAV_PORT);
		socketNav.send(dg);

		//Log.d(DroneConsts.DroneLogTag, "Sent NavData trigger bytes to " + ip_drone.toString() + ":" + NAV_PORT);

		// SDK 1.7 Developer Guide Seite 19
		sendAtCommand("AT*CONFIG=#SEQ#,\"general:navdata_demo\",\"TRUE\"");
	}

	public  void sendAtCommand(String msg) throws IOException {
		if (msg.contains("#IDS#")) {
			msg = msg.replace("#IDS#", DroneConfigSettings.CONFIG_IDS_CMD);
			msg = msg.replaceFirst("#SEQ#", Integer.toString(seqNumber));
			seqNumber++;
		}
		if (msg.contains("#SEQ#")) {
			msg = msg.replace("#SEQ#", Integer.toString(seqNumber));
			seqNumber++;
		}

		byte[] bytes = (msg + "\r").getBytes();
		DatagramPacket dg = new DatagramPacket(bytes, bytes.length, ip_drone, AT_PORT);

		synchronized (socketCmd) {
			socketCmd.send(dg);
		}	
	}

	public void resetSeqNumber() {
		seqNumber = 0;
	}

	public void setRunning(boolean toSet) {
		running = toSet;
	}

	public boolean isRunning() {
	    return running;
    }

}
