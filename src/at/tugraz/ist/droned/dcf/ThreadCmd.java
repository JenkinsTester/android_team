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


import at.tugraz.ist.droned.DroneConsts;

public class ThreadCmd extends Thread {

	private WiFiConnection wifi = null;

	private int move_armed, old_move_armed;
	private float move_roll, move_pitch, move_altitude, move_yaw;

	private int uiBits = 290717696;

	public ThreadCmd(WiFiConnection con) {
		wifi = con;
	}
	
	@Override
	public void run() {
		try {
			//Log.d(DroneConsts.DroneLogTag, "CMD Thread started");

			while (wifi.running) {
				if (old_move_armed != move_armed) {
					wifi.sendAtCommand(createMoveCmd());
					old_move_armed = move_armed;
				}

				if (move_armed == 0) {
					wifi.sendAtCommand(createTakeOffLandCmd());
				} else {
					wifi.sendAtCommand(createMoveCmd());
				}

				Thread.sleep(wifi.INTERVAL);
			}
			//Log.d(DroneConsts.DroneLogTag, "CMD Thread stopped");

		} catch (Exception e) {
			wifi.running = false;
			//Log.e(DroneConsts.DroneLogTag, "Error in CMD Thread loop", e);
			if (wifi.socketCmd != null) {
				wifi.socketCmd.close();
			}
		}

	}

	public void setAxisMove(float roll, float pitch, float altitude, float yaw) {
		int enabled = 0;

		if (roll == 0 && pitch == 0 && altitude == 0 && yaw == 0) {
			enabled = 0;
		} else {
			enabled = 1;
		}

		synchronized (this) {
			move_armed = enabled;
			move_roll = roll;
			move_pitch = pitch;
			move_altitude = altitude;
			move_yaw = yaw;
		}
	}

	public void setHovering(boolean hovering) {
		if (hovering) {
			uiBits = 290718208;
		} else {
			uiBits = 290717696;
		}
	}

	public String createTakeOffLandCmd() {
		return "AT*REF=#SEQ#," + Integer.toString(uiBits);
	}

	public String createMoveCmd() {
		String cmd = "";
		synchronized (this) {
			cmd = "AT*PCMD=#SEQ#," + (move_armed) + "," + Float.floatToIntBits(move_roll) + ","
			        + Float.floatToIntBits(move_pitch) + "," + Float.floatToIntBits(move_altitude) + ","
			        + Float.floatToIntBits(move_yaw);
		}
		return cmd;
	}

}
