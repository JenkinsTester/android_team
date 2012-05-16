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
package at.tugraz.ist.droned.dcf.firmware;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

import at.tugraz.ist.droned.DroneConsts;

//import com.enterprisedt.net.ftp.FTPException;
//import com.enterprisedt.net.ftp.FileTransferClient;

public class FirmwareUpdate {

	// TODO Move to Consts
	private static final String DL_PATH = "/mnt/sdcard/download/";
	private static final String DL_FILENAME = "ardrone_update.plf";
	private static final String DL_VERSIONFILE = "version.txt";
	private static final int REMOTE_FTP_PORT = 5551;
	private static final String REMOTE_FTP_IP = "192.168.1.1";
	private static final int TELNET_PORT = 23;

	//private FileTransferClient ftp;
	private Socket socket;
	private OutputStream socket_output;

	public FirmwareUpdate() {
//		super();
	}

	public void applyDowngradeTrick() {
		// TODO open Telnet and modify files accordingly

	}

	public String getFirmwareVersion() {
/*
		connectFTP();

		String version = null;

		try {
			ftp.downloadFile(DL_PATH + DL_VERSIONFILE, DL_VERSIONFILE);

			FileInputStream fstream = new FileInputStream(DL_PATH
			        + DL_VERSIONFILE);
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));

			version = br.readLine();
			//Log.d(DroneConsts.DroneLogTag, version);

			in.close();

		} catch (Exception e) {
			//Log.d(DroneConsts.DroneLogTag,
			        "Something went wrong while reading the Drone Version");
			e.printStackTrace();
			return version;
		}
		disconnectFTP();
		return version;*/
		return "";
	}

	private int disconnectFTP() {
		/*
		try {
			ftp.disconnect();
			//Log.d(DroneConsts.DroneLogTag, "FTP Disconnected");
		} catch (Exception e) {
			e.printStackTrace();
			//Log.d(DroneConsts.DroneLogTag, "FTP Disconnection Failed");
			return -1;
		}*/
		return 0;
	}

	/** Asset Manager Causes Reflection Error */
	private void uploadFile2Drone() {
		// //Log.d(DroneConsts.DroneLogTag, "Error getting  Assets!");
		// AssetManager assetManager = null /** context.getAssets() */;

		// InputStream stream = null;
		// try {
		// stream = assetManager.open("ardrone_firmware.plf");
		// } catch (IOException e) {
		// //Log.d(DroneConsts.DroneLogTag, "Error opening Assets!");
		// e.printStackTrace();
		// }
/*
		OutputStream out = null;
		try {
			out = ftp.uploadStream(DL_FILENAME);
		} catch (FTPException e) {
			//Log.d(DroneConsts.DroneLogTag, "FTP-Error opening FTP up stream!");
			e.printStackTrace();
		} catch (IOException e) {
			//Log.d(DroneConsts.DroneLogTag, "IO-Error opening FTP up stream!");
			e.printStackTrace();
		}

		//Log.d(DroneConsts.DroneLogTag, "uploadFile()  Opend Input Streams ");

		BufferedInputStream buffInReader = null;
		BufferedOutputStream buffOutWriter = null;

		buffOutWriter = new BufferedOutputStream(out);
		// buffInReader = new BufferedInputStream(stream);
		int buff;

		//Log.d(DroneConsts.DroneLogTag, "uploadFile() Created Buffers");

		try {
			while ((buff = buffInReader.read()) != -1) {
				buffOutWriter.write(buff);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			buffInReader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			buffOutWriter.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			out.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			buffOutWriter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
*/
		//Log.d(DroneConsts.DroneLogTag, "Firmware Upload to Drone Successful!");
	}

	public Boolean uploadFile() {/*
		connectFTP();

		disconnectFTP();*/
		return true;
	}

	private int connectFTP() {
		// create client
		/*
		try {
			ftp = new FileTransferClient();

			ftp.setRemoteHost(REMOTE_FTP_IP);
			ftp.setRemotePort(REMOTE_FTP_PORT);

			ftp.connect();

			if (ftp.isConnected()) {
				//Log.d(DroneConsts.DroneLogTag, "FTP Connected");
			}

			if (ftp.exists("version.txt")) {
				//Log.d(DroneConsts.DroneLogTag,
				        "version.txt exists => right directory!");
			}
		} catch (Exception e) {
			//Log.d(DroneConsts.DroneLogTag, "Error: FTP NOT Connected!");
			e.printStackTrace();
			return -1;
		}*/
		return 0;

	}

	private boolean openTelnetConnection() {
		/*try {
			socket = new Socket(REMOTE_FTP_IP, TELNET_PORT);
			socket_output = socket.getOutputStream();
			//Log.d(DroneConsts.DroneLogTag, "Telnet connection successful!");
		} catch (Exception e) {
			e.printStackTrace();
			//Log.d(DroneConsts.DroneLogTag, "Telnet connection failed!");
			return false;
		}*/
		return true;
	}

	private boolean sendRestartCommand() {
/*
		if (sendTelnetCommand("reboot\n")) {
			//Log.d(DroneConsts.DroneLogTag, "Restart Command Sucessfully Sent!");
		} else {
			//Log.d(DroneConsts.DroneLogTag, "Restart Command Sucessfully Sent!");
			return false;
		}
*/
		return true;
	}

	private boolean sendTelnetCommand(String command) {
	/*	try {
			socket_output.write(command.getBytes());
			socket_output.flush();
			//Log.d(DroneConsts.DroneLogTag, "Sucessfully sent command: "
			 //       + command + " signal to Drone!");
		} catch (IOException e) {
			//Log.d(DroneConsts.DroneLogTag, "Sending command: " + command
			  //      + " command to drone Failed!");
			e.printStackTrace();
			return false;
		}*/
		return true;
	}

	private boolean killTelnetConnection() {
/*
		try {
			socket_output.close();
			socket.close();
			//Log.d(DroneConsts.DroneLogTag, "Telnet connection closed!");
		} catch (Exception e) {
			e.printStackTrace();
			//Log.d(DroneConsts.DroneLogTag, "Telnet connection closing failed!");
			return false;
		}*/
		return true;
	}

	public boolean restartDrone() {
/*
		if (openTelnetConnection() && sendRestartCommand()
		        && killTelnetConnection()) {
			return true;
		} else {
			return false;
		}*/
		return true;
	}

}
