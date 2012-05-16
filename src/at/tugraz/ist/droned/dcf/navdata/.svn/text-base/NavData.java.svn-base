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

import at.tugraz.ist.droned.dcf.video.NativeVideoWrapper;

public class NavData {

	public boolean FLY_Bit; /*
							 * !< FLY MASK : (0) ardrone is landed, (1) ardrone
							 * is flying
							 */
	public boolean VIDEO_Bit; /*
							 * !< VIDEO MASK : (0) video disable, (1) video
							 * enable
							 */
	public boolean VISION_Bit; /*
								 * !< VISION MASK : (0) vision disable, (1)
								 * vision enable
								 */
	public boolean CONTROL_Bit; /*
								 * !< CONTROL ALGO : (0) euler angles control,
								 * (1) angular speed control
								 */
	public boolean ALTITUDE_Bit; /*
								 * !< ALTITUDE CONTROL ALGO : (0) altitude
								 * control inactive (1) altitude control active
								 */
	public boolean USER_FEEDBACK_START; /* !< USER feedback : Start button state */
	public boolean COMMAND_Bit; /*
								 * !< Control command ACK : (0) None, (1) one
								 * received
								 */
	public boolean FW_FILE_Bit; /* Firmware file is good (1) */
	public boolean FW_VER_Bit; /* Firmware update is newer (1) */
	public boolean NAVDATA_DEMO_Bit; /*
									 * !< Navdata demo : (0) All navdata, (1)
									 * only navdata demo
									 */
	public boolean NAVDATA_BOOTSTRAP; /*
									 * !< Navdata bootstrap : (0) options sent
									 * in all or demo mode, (1) no navdata
									 * options sent
									 */
	public boolean MOTORS_Bit; /* !< Motors status : (0) Ok, (1) Motors problem */
	public boolean COM_LOST_Bit; /*
								 * !< Communication Lost : (1) com problem, (0)
								 * Com is ok
								 */
	public boolean VBAT_LOW; /* !< VBat low : (1) too low, (0) Ok */
	public boolean USER_EL; /*
							 * !< User Emergency Landing : (1) User EL is ON,
							 * (0) User EL is OFF
							 */
	public boolean TIMER_ELAPSED; /*
								 * !< Timer elapsed : (1) elapsed, (0) not
								 * elapsed
								 */
	public boolean ANGLES_OUT_OF_RANGE; /* !< Angles : (0) Ok, (1) out of range */
	public boolean ULTRASOUND_Bit; /* !< Ultrasonic sensor : (0) Ok, (1) deaf */
	public boolean CUTOUT_Bit; /*
								 * !< Cutout system detection : (0) Not
								 * detected, (1) detected
								 */
	public boolean PIC_VERSION_Bit; /*
									 * !< PIC Version number OK : (0) a bad
									 * version number, (1) version number is OK
									 */
	public boolean ATCODEC_THREAD_ON; /*
									 * !< ATCodec thread ON : (0) thread OFF (1)
									 * thread ON
									 */
	public boolean NAVDATA_THREAD_ON; /*
									 * !< Navdata thread ON : (0) thread OFF (1)
									 * thread ON
									 */
	public boolean VIDEO_THREAD_ON; /*
									 * !< Video thread ON : (0) thread OFF (1)
									 * thread ON
									 */
	public boolean ACQ_THREAD_ON; /*
								 * !< Acquisition thread ON : (0) thread OFF (1)
								 * thread ON
								 */
	public boolean CTRL_WATCHDOG_Bit; /*
									 * !< CTRL watchdog : (1) delay in control
									 * execution (> 5ms), (0) control is well
									 * scheduled
									 */
	public boolean ADC_WATCHDOG_Bit; /*
									 * !< ADC Watchdog : (1) delay in uart2 dsr
									 * (> 5ms), (0) uart2 is good
									 */
	public boolean COM_WATCHDOG_Bit; /*
									 * !< Communication Watchdog : (1) com
									 * problem, (0) Com is ok
									 */
	public boolean EMERGENCY_Bit; /*
								 * !< Emergency landing : (0) no emergency, (1)
								 * emergency
								 */

	public static final int NAVDATA_DEMO_TAG = 0;
	public static final int NAVDATA_TIME_TAG = 1;
	public static final int NAVDATA_RAW_MEASURES_TAG = 2;
	public static final int NAVDATA_PHYS_MEASURES_TAG = 3;
	public static final int NAVDATA_GYROS_OFFSETS_TAG = 4;
	public static final int NAVDATA_EULER_ANGLES_TAG = 5;
	public static final int NAVDATA_REFERENCES_TAG = 6;
	public static final int NAVDATA_TRIMS_TAG = 7;
	public static final int NAVDATA_RC_REFERENCES_TAG = 8;
	public static final int NAVDATA_PWM_TAG = 9;
	public static final int NAVDATA_ALTITUDE_TAG = 10;
	public static final int NAVDATA_VISION_RAW_TAG = 11;
	public static final int NAVDATA_VISION_OF_TAG = 12;
	public static final int NAVDATA_VISION_TAG = 13;
	public static final int NAVDATA_VISION_PERF_TAG = 14;
	public static final int NAVDATA_TRACKERS_SEND_TAG = 15;
	public static final int NAVDATA_VISION_DETECT_TAG = 16;
	public static final int NAVDATA_WATCHDOG_TAG = 17;
	public static final int NAVDATA_ADC_DATA_FRAME_TAG = 18;
	public static final int NAVDATA_VIDEO_STREAM_TAG = 19;
	public static final int NAVDATA_NUM_TAGS = 20;
	public static final int NAVDATA_CKS_TAG = 0xffff;

	public PacketDemo packetDemo;
	public PacketVisionDetect packetVisionDetect;
	public int drone_state = 0;

	public NavData() {
		packetDemo = new PacketDemo();
		packetVisionDetect = new PacketVisionDetect();
	}

	public void loadFromData(byte[] data) {
		drone_state = Tools.get_uint32(data, 4);
		// long sequence = Tools.get_uint32(data, 8);
		// long vision_defined = Tools.get_uint32(data, 12);

		FLY_Bit = getStateBit(drone_state, 0);
		VIDEO_Bit = getStateBit(drone_state, 1);
		VISION_Bit = getStateBit(drone_state, 2);
		CONTROL_Bit = getStateBit(drone_state, 3);
		ALTITUDE_Bit = getStateBit(drone_state, 4);
		USER_FEEDBACK_START = getStateBit(drone_state, 5);
		COMMAND_Bit = getStateBit(drone_state, 6);
		FW_FILE_Bit = getStateBit(drone_state, 7);
		FW_VER_Bit = getStateBit(drone_state, 8);
		NAVDATA_DEMO_Bit = getStateBit(drone_state, 10);
		NAVDATA_BOOTSTRAP = getStateBit(drone_state, 11);
		MOTORS_Bit = getStateBit(drone_state, 12);
		COM_LOST_Bit = getStateBit(drone_state, 13);
		VBAT_LOW = getStateBit(drone_state, 15);
		USER_EL = getStateBit(drone_state, 16);
		TIMER_ELAPSED = getStateBit(drone_state, 17);
		ANGLES_OUT_OF_RANGE = getStateBit(drone_state, 19);
		ULTRASOUND_Bit = getStateBit(drone_state, 21);
		CUTOUT_Bit = getStateBit(drone_state, 22);
		PIC_VERSION_Bit = getStateBit(drone_state, 23);
		ATCODEC_THREAD_ON = getStateBit(drone_state, 24);
		NAVDATA_THREAD_ON = getStateBit(drone_state, 25);
		VIDEO_THREAD_ON = getStateBit(drone_state, 26);
		ACQ_THREAD_ON = getStateBit(drone_state, 27);
		CTRL_WATCHDOG_Bit = getStateBit(drone_state, 28);
		ADC_WATCHDOG_Bit = getStateBit(drone_state, 29);
		COM_WATCHDOG_Bit = getStateBit(drone_state, 30);
		EMERGENCY_Bit = getStateBit(drone_state, 31);

		int optionOffset = 16;

		while (optionOffset < data.length - 5) {
			int tag = Tools.get_uint16(data, optionOffset);
			int size = Tools.get_uint16(data, optionOffset + 2);

			if (tag == NAVDATA_CKS_TAG) {
				// Log.d(DroneConsts.DroneLogTag, "CKS packet: " + a +
				// ", CKS calc: " + b);
				break; // Checksum block reached
			}

			analyzePacket(data, optionOffset);

			optionOffset += size;
		}
		// Log.d(DroneConsts.DroneLogTag, "Size: " + data.length + " ,offset: "
		// + optionOffset);

	}

	void analyzePacket(byte[] data, int offset) {
		int tag;
		int size;
		byte[] packetData;

		tag = Tools.get_uint16(data, offset);
		size = Tools.get_uint16(data, offset + 2);

		packetData = new byte[size];
		System.arraycopy(data, offset + 4, packetData, 0, size);
		// Log.d(DroneConsts.DroneLogTag, "option tag: " + tag + " , size: " +
		// size);

		switch (tag) {

		case NAVDATA_DEMO_TAG:
			packetDemo.loadFromData(packetData);
			NativeVideoWrapper
					.setBatteryLevel(packetDemo.vbat_flying_percentage);
			break;

		case NAVDATA_VISION_DETECT_TAG:
			packetVisionDetect.loadFromData(packetData);
			break;

		// default:
		// Log.d(DroneConsts.DroneLogTag, "unknown tag: " + tag);

		}
	}

	boolean getStateBit(int state, int bit) {
		if ((((long) 1 << bit) & state) > 0) {
			return true;
		} else {
			return false;
		}
	}

	// Define masks for Drone state
	// 31 0
	// x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x x -> state
	// | | | | | | | | | | | | | | | | | | | | | | | | | | | | | | | |
	// | | | | | | | | | | | | | | | | | | | | | | | | | | | | | | | FLY MASK :
	// (0) ardrone is landed, (1) ardrone is
	// flying
	// | | | | | | | | | | | | | | | | | | | | | | | | | | | | | | VIDEO MASK :
	// (0) video disable, (1) video enable
	// | | | | | | | | | | | | | | | | | | | | | | | | | | | | | VISION MASK :
	// (0) vision disable, (1) vision enable
	// | | | | | | | | | | | | | | | | | | | | | | | | | | | | CONTROL ALGO :
	// (0) euler angles control, (1) angular
	// speed control
	// | | | | | | | | | | | | | | | | | | | | | | | | | | | ALTITUDE CONTROL
	// ALGO : (0) altitude control inactive (1)
	// altitude control active
	// | | | | | | | | | | | | | | | | | | | | | | | | | | USER feedback : Start
	// button state
	// | | | | | | | | | | | | | | | | | | | | | | | | | Control command ACK :
	// (0) None, (1) one received
	// | | | | | | | | | | | | | | | | | | | | | | | | Trim command ACK : (0)
	// None, (1) one received
	// | | | | | | | | | | | | | | | | | | | | | | | Trim running : (0) none,
	// (1) running
	// | | | | | | | | | | | | | | | | | | | | | | Trim result : (0) failed, (1)
	// succeeded
	// | | | | | | | | | | | | | | | | | | | | | Navdata demo : (0) All navdata,
	// (1) only navdata demo
	// | | | | | | | | | | | | | | | | | | | | Navdata bootstrap : (0) options
	// sent in all or demo mode, (1) no navdata
	// options sent
	// | | | | | | | | | | | | | | | | | | | | Motors status : (0) Ok, (1)
	// Motors Com is down
	// | | | | | | | | | | | | | | | | | |
	// | | | | | | | | | | | | | | | | | Bit means that there's an hardware
	// problem with gyrometers
	// | | | | | | | | | | | | | | | | VBat low : (1) too low, (0) Ok
	// | | | | | | | | | | | | | | | VBat high (US mad) : (1) too high, (0) Ok
	// | | | | | | | | | | | | | | Timer elapsed : (1) elapsed, (0) not elapsed
	// | | | | | | | | | | | | | Power : (0) Ok, (1) not enough to fly
	// | | | | | | | | | | | | Angles : (0) Ok, (1) out of range
	// | | | | | | | | | | | Wind : (0) Ok, (1) too much to fly
	// | | | | | | | | | | Ultrasonic sensor : (0) Ok, (1) deaf
	// | | | | | | | | | Cutout system detection : (0) Not detected, (1)
	// detected
	// | | | | | | | | PIC Version number OK : (0) a bad version number, (1)
	// version number is OK
	// | | | | | | | ATCodec thread ON : (0) thread OFF (1) thread ON
	// | | | | | | Navdata thread ON : (0) thread OFF (1) thread ON
	// | | | | | Video thread ON : (0) thread OFF (1) thread ON
	// | | | | Acquisition thread ON : (0) thread OFF (1) thread ON
	// | | | CTRL watchdog : (1) delay in control execution (> 5ms), (0) control
	// is well scheduled // Check frequency of
	// control loop
	// | | ADC Watchdog : (1) delay in uart2 dsr (> 5ms), (0) uart2 is good //
	// Check frequency of uart2 dsr (com with
	// adc)
	// | Communication Watchdog : (1) com problem, (0) Com is ok // Check if we
	// have an active connection with a client
	// Emergency landing : (0) no emergency, (1) emergency

	// typedef enum _navdata_tag_t {
	// NAVDATA_DEMO_TAG = 0,
	// NAVDATA_TIME_TAG,
	// NAVDATA_RAW_MEASURES_TAG,
	// NAVDATA_PHYS_MEASURES_TAG,
	// NAVDATA_GYROS_OFFSETS_TAG,
	// NAVDATA_EULER_ANGLES_TAG,
	// NAVDATA_REFERENCES_TAG,
	// NAVDATA_TRIMS_TAG,
	// NAVDATA_RC_REFERENCES_TAG,
	// NAVDATA_PWM_TAG,
	// NAVDATA_ALTITUDE_TAG,
	// NAVDATA_VISION_RAW_TAG,
	// NAVDATA_VISION_OF_TAG,
	// NAVDATA_VISION_TAG,
	// NAVDATA_VISION_PERF_TAG,
	// NAVDATA_TRACKERS_SEND_TAG,
	// NAVDATA_VISION_DETECT_TAG,
	// NAVDATA_WATCHDOG_TAG,
	// NAVDATA_ADC_DATA_FRAME_TAG,
	// NAVDATA_VIDEO_STREAM_TAG,
	// /*Insert new tags above this line */
	// NAVDATA_NUM_TAGS,
	// NAVDATA_CKS_TAG = 0xFFFF
	// } navdata_tag_t;

}
