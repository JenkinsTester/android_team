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
package at.tugraz.ist.droned.dcf.security;

import at.tugraz.ist.droned.DroneConsts;

public class DroneSecurityLayer {

	boolean hovering = false;
	boolean move = false;
	int reset = 0;
	int moveTimeout = 5;

	private MoveSecurityThread moveSecurityThread = null;

	public void parseCommand(String cmd) {

		if (DroneConsts.debug) {
			//Log.d(DroneConsts.DroneLogTag, "DSL: " + cmd);
		}

		// land
		if (cmd.contains("land")) {
			registerLand();
		}
		// takeoff
		else if (cmd.contains("takeoff")) {
			registerTakeOff();
		}
		// emergency
		else if (cmd.contains("emergency")) {
			registerEmergency();
		}
		// move
		else if (cmd.contains("move")) {

			float roll = Float.parseFloat(cmd.split(",")[1]);
			float pitch = Float.parseFloat(cmd.split(",")[2]);
			float alt = Float.parseFloat(cmd.split(",")[3]);
			float yaw = Float.parseFloat(cmd.split(",")[4]);

			if (roll == 0 && pitch == 0 && alt == 0 && yaw == 0) {
				move = false;
				reset = 0;
			} else {
				move = true;
				reset++;
			}

		}
		// led animation
		else if (cmd.contains("led")) {
			if (move)
				reset++;
		}
		// move animation
		else if (cmd.contains("anim")) {
			if (move)
				reset++;
		}
		// config setting
		else if (cmd.contains("config")) {
			if (move)
				reset++;
		}
		// unknown
		else {
			//Log.d(DroneConsts.DroneLogTag, "unknown cmd");
		}
	}

	private void registerTakeOff() {
		hovering = true;
		move = false;
		reset = 0;
		moveSecurityThread = new MoveSecurityThread(this);
		moveSecurityThread.start();

	}

	private void registerLand() {
		hovering = false;
		moveSecurityThread = null;
	}

	private void registerEmergency() {
		hovering = false;
		moveSecurityThread = null;
	}

	public void setTimeout(int seconds) {
	    moveTimeout = seconds;	    
    }

}
