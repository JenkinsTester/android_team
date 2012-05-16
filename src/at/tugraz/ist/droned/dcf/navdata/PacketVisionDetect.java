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

import at.tugraz.ist.droned.Drone;
import at.tugraz.ist.droned.DroneConsts;
import at.tugraz.ist.droned.dcf.config.DroneConfigSettings;
import at.tugraz.ist.droned.dcf.vision.VisionController;
import at.tugraz.ist.droned.dcf.vision.VisionDetectPacket;

public class PacketVisionDetect {

	static final int NB_NAVDATA_DETECTION_RESULTS = 4;

	private VisionController visionController = new VisionController();

	public int nb_detected;
	public int[] type = new int[NB_NAVDATA_DETECTION_RESULTS];
	public int[] xc = new int[NB_NAVDATA_DETECTION_RESULTS];
	public int[] yc = new int[NB_NAVDATA_DETECTION_RESULTS];
	public int[] width = new int[NB_NAVDATA_DETECTION_RESULTS];
	public int[] height = new int[NB_NAVDATA_DETECTION_RESULTS];
	public int[] dist = new int[NB_NAVDATA_DETECTION_RESULTS];
	public int[] orientation_angle = new int[NB_NAVDATA_DETECTION_RESULTS];

	boolean lastDetected = true;

	public void loadFromData(byte[] data)
	{
		nb_detected = Tools.get_uint32(data, 0);

		if (nb_detected == 0) {
			if (lastDetected) {
				lastDetected = false;
				visionController.control(Drone.getInstance().getFlyingMode(),
				        DroneConfigSettings.DetectionSelectSetting.NONE, null);
			}
			return;
		}

		if (DroneConsts.debug) {
			//Log.d(DroneConsts.DroneLogTag, "Tags detected! #: " + nb_detected);
		}
		
		lastDetected = true;

		for (int i = 1; i <= nb_detected; i++) {

			VisionDetectPacket vdp = new VisionDetectPacket();

			vdp.tag_type = getTagType(Tools.get_uint32(data, 0 + 4 * i));
			vdp.xc = Tools.get_uint32(data, 16 + 4 * i);
			vdp.yc = Tools.get_uint32(data, 32 + 4 * i);
			vdp.width = Tools.get_uint32(data, 48 + 4 * i);
			vdp.height = Tools.get_uint32(data, 64 + 4 * i);
			vdp.dist = Tools.get_uint32(data, 80 + 4 * i);
			vdp.orientation_angle = Tools.get_uint32(data, 96 + 4 * i);

			visionController.control(Drone.getInstance().getFlyingMode(), vdp.tag_type, vdp);
		}
	}

	private int getTagType(int type) {
		return (int) ((long) type & 0x7FFF);
	}
}