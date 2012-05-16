package at.tugraz.ist.droned.dcf.video;

import at.tugraz.ist.droned.Drone;
import at.tugraz.ist.droned.DroneConsts;

/**
 * Java wrapper class for native C code.
 */
public class NativeVideoWrapper {

	/**
	 * Initialize the Drone video communication
	 */
	public static void init() {
		nativeDroneInitVideo();
	}

	/**
	 * Close the drone video communication port and release all native drone
	 * resources
	 */
	public static void close() {
		nativeDroneClose();
	}

	/**
	 * Set coordinates of tracked objects to display them in the video texture
	 * 
	 * @param xPos
	 *            X pos in video (0 - 320)
	 * @param yPos
	 *            Y pos in video (0 - 240)
	 */
	public static void setTrackingPoint(int xPos, int yPos) {

		if (Drone.getInstance().getCameraOrientation() == DroneConsts.CameraOrientation.VERT) {
			xPos = (int) ((xPos * 176) / 1000);
			yPos = (int) ((yPos * 144) / 1000);
		} else {
			xPos = (int) ((xPos * 320) / 1000);
			yPos = (int) ((yPos * 240) / 1000);
		}

		nativeDroneSetExternaltrackingPoint(xPos, yPos);
	}

	/**
	 * Start video recording to file
	 * 
	 * @param path
	 *            Absolute path to file. Must end with *.mp4 e.g.
	 *            /sdcard/video.mp4
	 */
	public static void startVideoRecorder(String path) {
		if (!path.endsWith(".mp4")) {
		//	Log.d(DroneConsts.DroneLogTag, "Video file not ending with .mp4!");
			return;
		}

		nativeDroneStartVideoRecorder(path);
	}

	/**
	 * Stop video recording and close file handle.
	 */
	public static void stopVideoRecorder() {
		nativeDroneStopVideoRecorder();
	}

	/**
	 * Save actual video image to file
	 * 
	 * @param path
	 *            Absolute path to picture. Must end with *.jpg e.g.
	 *            /sdcard/picture.jpg
	 */
	public static void saveSnapshot(String path) {
		if (!path.endsWith(".jpg")) {
		//	Log.d(DroneConsts.DroneLogTag,
		//	        "Picture file not ending with .jpg!");
			return;
		}
		nativeDroneSaveSnapshot(path);
	}

	/**
	 * Redner actual video image to given OpenGl texture handle
	 * 
	 * @param glHandle
	 *            OpenGL handle to texture
	 */
	public static void renderVideoFrame(int glHandle) {
		nativeDroneRenderVideoFrame(glHandle);
	}

	/**
	 * Set vision parameter in drone. Actually not used
	 * 
	 * @param mode
	 *            the mode
	 */
	public static void setVisionMode(int mode) {

	}

	/**
	 * Show the Frames per second count in the upper left corner of the video.
	 * 
	 * @param enabled
	 *            true = show FPS
	 */
	public static void showFPS(boolean enabled) {
		nativeDroneShowFPS(enabled);
	}

	/**
	 * Is used for debugging purpose when no DCF is used. Simulates the cyclic
	 * drone AT commands and receive simply NavData
	 */
	public static void InitDummyCmds() {
		nativeDroneInitDummyCmds();
	}

	/**
	 * Function to display the battery status in the video texture.
	 * 
	 * @param batteryLoad
	 *            Load level 0 - 100
	 */
	static int oldBatteryImg = 0;

	public static void setBatteryLevel(long batteryLoad) {
		int batteryImg = 0;

		if (batteryLoad == 0) {
			batteryImg = 0;
		} else {
			if (batteryLoad > 0 && batteryLoad <= 25) {
				batteryImg = 1;
			} else if (batteryLoad > 25 && batteryLoad <= 50) {
				batteryImg = 2;
			} else if (batteryLoad > 50 && batteryLoad <= 75) {
				batteryImg = 3;
			} else if (batteryLoad > 75) {
				batteryImg = 4;
			}
		}

		if (oldBatteryImg != batteryImg) {
			oldBatteryImg = batteryImg;
			nativeDroneSetBatteryLevel(batteryImg);
		}
	}

	/**
	 * Function to display the flymode as icon on video texture
	 * 
	 * @param flyMode
	 *            Mode 0 - 2
	 */
	public static void setFlyMode(int flyMode) {
		nativeDroneSetFlyMode(flyMode);
	}

	static {
		System.loadLibrary("ardrone");
	}

	private static native void nativeDroneInitVideo();

	private static native void nativeDroneInitDummyCmds();

	private static native void nativeDroneClose();

	private static native void nativeDroneSetVisionMode(int mode);

	private static native void nativeDroneSetExternaltrackingPoint(int xPos,
	        int yPos);

	private static native void nativeDroneStartVideoRecorder(String path);

	private static native void nativeDroneStopVideoRecorder();

	private static native void nativeDroneSaveSnapshot(String path);

	private static native void nativeDroneRenderVideoFrame(int glHandle);

	private static native void nativeDroneShowFPS(boolean enabled);

	private static native void nativeDroneSetBatteryLevel(int batteryLevel);

	private static native void nativeDroneSetFlyMode(int flyMode);
}
