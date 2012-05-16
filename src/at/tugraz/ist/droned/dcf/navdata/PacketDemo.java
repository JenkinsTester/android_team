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
package at.tugraz.ist.droned.dcf.navdata;

public class PacketDemo {
	public long ctrl_state; /* !< Flying state (landed, flying, hovering, etc.) defined in CTRL_STATES enum. */
	public long vbat_flying_percentage; /* !< battery voltage filtered (mV) */
	public int flying_state;

	public float theta; /* !< UAV's pitch in milli-degrees */
	public float phi; /* !< UAV's roll in milli-degrees */
	public float psi; /* !< UAV's yaw in milli-degrees */

	public long altitude; /* !< UAV's altitude in centimeters */

	public float vx; /* !< UAV's estimated linear velocity */
	public float vy; /* !< UAV's estimated linear velocity */
	public float vz; /* !< UAV's estimated linear velocity */

	public long num_frames; /* !< streamed frame index */// Not used -> To integrate in video stage.
	public long detection_camera_type; /* !< Type of tag searched in detection */

	public void loadFromData(byte[] data) {
		ctrl_state = Tools.get_uint32(data, 0);
		flying_state = (int) ctrl_state >> 16;
		vbat_flying_percentage = Tools.get_uint32(data, 4);

		theta = Tools.get_float(data, 8);
		phi = Tools.get_float(data, 12);
		psi = Tools.get_float(data, 16);

		altitude = Tools.get_uint32(data, 20);

		vx = Tools.get_float(data, 24);
		vy = Tools.get_float(data, 28);
		vz = Tools.get_float(data, 32);

		num_frames = Tools.get_uint32(data, 36);

		detection_camera_type = Tools.get_uint32(data, 52);
	}

}
