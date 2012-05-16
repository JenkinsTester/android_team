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

import at.tugraz.ist.droned.Drone;
import at.tugraz.ist.droned.DroneConsts;

public class MoveSecurityThread extends Thread {
	
	private DroneSecurityLayer dsl;
	
	public MoveSecurityThread(DroneSecurityLayer dsl) {
		this.dsl = dsl;		
	}
	
	@Override
	public void run() {

		try {

			while (dsl.hovering) {

				while (!dsl.move && dsl.hovering) {
					sleep(100);
					continue;
				}

				if (dsl.move && dsl.hovering) {
					int old_reset = dsl.reset;

					int counter = 0;
					while (counter++ < dsl.moveTimeout && dsl.hovering && dsl.move) {
						Thread.sleep(1000);
						if (old_reset < dsl.reset) {
							break;
						}
					}

					// timeout
					if (counter >= dsl.moveTimeout) {
						Drone.getInstance().move(0, 0, 0, 0);
					}

					dsl.reset = 0;

				} else {
					break;
				}

			}

		} catch (Exception e) {
		//	Log.e(DroneConsts.DroneLogTag, "Exception MoveSecurityThread", e);
		}
	}
};