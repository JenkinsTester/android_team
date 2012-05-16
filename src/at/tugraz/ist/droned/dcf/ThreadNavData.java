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

import java.net.DatagramPacket;
import java.net.SocketTimeoutException;

import at.tugraz.ist.droned.DroneConsts;
import at.tugraz.ist.droned.dcf.navdata.NavData;
import at.tugraz.ist.droned.dcf.navdata.Tools;

public class ThreadNavData extends Thread {

	private WiFiConnection wifi = null;
	private NavData navdata = null;

	private byte[] buffer = new byte[10240];
	private DatagramPacket dg = null;
	private int firsttestcase = 0;
	public int getFirsttestcase() {
		return firsttestcase;
	}

	public void setFirsttestcase(int firsttestcase) {
		this.firsttestcase = firsttestcase;
	}

	public ThreadNavData(WiFiConnection con, NavData data) {
		wifi = con;
		navdata = data;
	}
	public void setBuffer(byte[] buffer)
	{
		this.buffer = buffer; 
	}
	@Override
	public void run() {
		int restartCounter = 0;
		int len;
		int errorCounter = 0;

		//Log.d(DroneConsts.DroneLogTag, "NavData thread started");

		while (true) {
			try {
				firsttestcase=1;

				dg = new DatagramPacket(buffer, buffer.length);

				wifi.socketNav.receive(dg);
				errorCounter = 0;
				len = dg.getLength();
				
				if (len == wifi.trigger_bytes.length) {
					// //Log.d(DroneConsts.DroneLogTag,
					// "Interrupt bytes received, ignore it");

					if (++restartCounter <= 2) {
						continue;
					}
					restartCounter = 0;
					wifi.connect();
					Thread.sleep(500);
				}

				if (len == 24) {
					//Log.d(DroneConsts.DroneLogTag,
					//		"BOOTSTRAP mode, reconnect to enter DEMO mode");
					wifi.connectNAV();
					Thread.sleep(500);
					continue;
				}

				// //Log.d(DroneConsts.DroneLogTag, "NavData received");

				buffer = dg.getData();
				int header = Tools.get_uint32(buffer, 0);

				if (header != 0x55667788) {
					//Log.d(DroneConsts.DroneLogTag, "Wrong navdata header");
					continue;
				}

				navdata.loadFromData(buffer);

				if (navdata.COM_WATCHDOG_Bit) {
					//Log.d(DroneConsts.DroneLogTag, "MYKONOS_COM_WATCHDOG_MASK");
					wifi.sendAtCommand("AT*COMWDG=1");
					wifi.resetSeqNumber();
					Thread.sleep(wifi.INTERVAL);
				}

			} catch (SocketTimeoutException e) {
				if (++errorCounter >= 3) {
					errorCounter = 0;
					wifi.running = false;
					//Log.e(DroneConsts.DroneLogTag,
					//		"Timeout getting navdata from drone.", e);
					if (wifi.socketNav != null) {
						wifi.socketNav.close();
					}
				}

			} catch (Exception e) {
				//Log.e(DroneConsts.DroneLogTag,
				//		"Exception ThreadNavData -> run()", e);
				wifi.running = false;
				if (wifi.socketNav != null) {
					wifi.socketNav.close();
				}
			}
			wifi.setRunning(false);
		}

		//Log.d(DroneConsts.DroneLogTag, "Navdata Thread stopped");
	}

}
