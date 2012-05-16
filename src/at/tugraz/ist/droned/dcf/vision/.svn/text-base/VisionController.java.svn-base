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
package at.tugraz.ist.droned.dcf.vision;

import at.tugraz.ist.droned.Drone;
import at.tugraz.ist.droned.DroneConsts;
import at.tugraz.ist.droned.dcf.config.DroneConfigSettings;
import at.tugraz.ist.droned.dcf.video.NativeVideoWrapper;

public class VisionController {

	public void control(int flyingMode, int tagType, VisionDetectPacket vdp) {

		if (tagType == DroneConfigSettings.DetectionSelectSetting.NONE) {

			if (DroneConsts.debug) {
			//	//Log.d(DroneConsts.DroneLogTag, "##########################");
			//	//Log.d(DroneConsts.DroneLogTag, "tag_type: NONE");
			//	//Log.d(DroneConsts.DroneLogTag, "##########################");
			}
			if (Drone.getInstance().getFlyingMode() > 1) {
				Drone.getInstance().move(0, 0, 0, 0);
			}
			NativeVideoWrapper.setTrackingPoint(0, 0);
			return;
		}

		NativeVideoWrapper.setTrackingPoint(vdp.xc, vdp.yc);

		if (DroneConsts.debug) {
			//Log.d(DroneConsts.DroneLogTag, "--------------------------------");
			//Log.d(DroneConsts.DroneLogTag, "VisonController.control()");
			//Log.d(DroneConsts.DroneLogTag, "flyingMode: " + flyingMode);

			if (tagType == DroneConfigSettings.DetectionSelectSetting.SHELL_TAG) {
				//Log.d(DroneConsts.DroneLogTag, "tag_type: SHELL_TAG");
			} else if (tagType == DroneConfigSettings.DetectionSelectSetting.ROUNDEL) {
				//Log.d(DroneConsts.DroneLogTag, "tag_type: ROUNDEL");
			} else if (tagType == DroneConfigSettings.DetectionSelectSetting.ORIENTED_ROUNDEL) {
				//Log.d(DroneConsts.DroneLogTag, "tag_type: ORIENTED_ROUNDEL");
			} else if (tagType == DroneConfigSettings.DetectionSelectSetting.STRIPE) {
				//Log.d(DroneConsts.DroneLogTag, "tag_type: STRIPE");
			} else {
				//Log.d(DroneConsts.DroneLogTag, "tag_type: unknown tag");
			}

			//Log.d(DroneConsts.DroneLogTag, "xc: " + vdp.xc);
			//Log.d(DroneConsts.DroneLogTag, "yc: " + vdp.yc);
			//Log.d(DroneConsts.DroneLogTag, "width: " + vdp.width);
			//Log.d(DroneConsts.DroneLogTag, "height: " + vdp.height);
			//Log.d(DroneConsts.DroneLogTag, "dist: " + vdp.dist);
			//Log.d(DroneConsts.DroneLogTag, "orientation_angle: "
				//	+ vdp.orientation_angle);
		}

		switch (flyingMode) {

		case DroneConsts.FlyingMode.FOLLOW_SHELL_TAG_YAW:
			if (tagType == DroneConfigSettings.DetectionSelectSetting.SHELL_TAG) {
				handleFlyingModeFollowShellTagYaw(vdp);
			}
			break;

		default:
			//Log.d(DroneConsts.DroneLogTag,
			//		"unknown flying mode! (VisionController)");
			break;
		}

	}

	// !! do not change configurations during handling -> ~3 seconds navdata
	// timeout
	// instead use movement between -1.0 and 1.0
	float lastYaw = 0.0f;

	private void handleFlyingModeFollowShellTagYaw(VisionDetectPacket vdp) {

		// throttle
//		float throttle = 0.0f;
//		if (vdp.yc < 300) {
//			throttle = 0.3f;
//		} else if (vdp.yc >= 300 && vdp.yc <= 700) {
//			throttle = 0.0f;
//		} else if (vdp.yc > 700) {
//			throttle = -0.3f;
//		}
//
//		// pitch
//		float pitch = 0.0f;
//		if (vdp.dist < 50) {
//			pitch = 0.1f;
//		} else if (vdp.dist >= 50 && vdp.dist <= 100) {
//			pitch = 0.0f;
//		} else if (vdp.dist > 100) {
//			pitch = -0.1f;
//		}
//
		// yaw
		float yaw = 0.0f;
		if (vdp.xc < 400) {
			yaw = -0.5f;
		} else if (vdp.xc >= 400 && vdp.xc <= 600) {
			yaw = 0.0f;
		} else if (vdp.xc > 600) {
			yaw = 0.5f;
		}


		Drone.getInstance().move(0, 0, 0, yaw);
	}
}
