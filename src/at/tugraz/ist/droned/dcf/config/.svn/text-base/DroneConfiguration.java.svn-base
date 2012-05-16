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
package at.tugraz.ist.droned.dcf.config;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.ArrayList;

import at.tugraz.ist.droned.DroneConsts;
import at.tugraz.ist.droned.dcf.WiFiConnection;

public class DroneConfiguration {

	private ArrayList<String> config;

	// drone configurations
	public static final String RESET_ACKBIT_CMD = "AT*CTRL=#SEQ#,5,0";

	public void setConfig(ArrayList<String> configuration) {
		config = configuration;
	}

	public ArrayList<String> getConfig() {
		return config;
	}

	public ArrayList<String> checkConfiguration() {

		ArrayList<String> cmds = new ArrayList<String>();
		int counter = 0;
		for (int i = 0; i < config.size(); i++)
		{
			try {
				String cfgLine = config.get(i).replaceAll(" ", "");

				String cmd = cfgLine.split("=")[0];
				String value = cfgLine.split("=")[1];

				// check if configuration on drone are the same
				if (DroneConfigSettings.DEFAULT_CONFIG_CMDS.containsKey(cmd)) {
					counter++;
					
					if(DroneConfigSettings.DEFAULT_CONFIG_CMDS.get(cmd) == "TRUE" || DroneConfigSettings.DEFAULT_CONFIG_CMDS.get(cmd) == "FALSE") {
						if(value != DroneConfigSettings.DEFAULT_CONFIG_CMDS.get(cmd)) {
							cmds.add("\"" + cmd + "\",\"" + DroneConfigSettings.DEFAULT_CONFIG_CMDS.get(cmd) + "\"");
						}
					}
					
					else if (Float.parseFloat(DroneConfigSettings.DEFAULT_CONFIG_CMDS.get(cmd)) != Float.parseFloat(value)) {
						cmds.add("\"" + cmd + "\",\"" + DroneConfigSettings.DEFAULT_CONFIG_CMDS.get(cmd) + "\"");
					}
				}

			} catch (Exception e) {
				//Log.d(DroneConsts.DroneLogTag,
				   //     "failing splitting cfgLine @ DroneCongifuration -> checkConfiguration()");
				//Log.d(DroneConsts.DroneLogTag, "bad line: " + config.get(i));
			}
		}

		//Log.d(DroneConsts.DroneLogTag, "#############################################");
		//Log.d(DroneConsts.DroneLogTag,
		 //       "checked " + counter + " commands from total " + DroneConfigSettings.DEFAULT_CONFIG_CMDS.size());
		//Log.d(DroneConsts.DroneLogTag, "commands that must be set: ");
		for (int j = 0; j < cmds.size(); j++) {
			//Log.d(DroneConsts.DroneLogTag, cmds.get(j));
		}
		//Log.d(DroneConsts.DroneLogTag, "#############################################");

		return cmds;
	}

	public boolean readConfiguration(WiFiConnection wifi) {
		
		config = new ArrayList<String>();
		
		Socket socket = null;
		boolean readConfig = true;
		int counter = 0;
		
		while (readConfig) {

			try {
				
				if(socket != null) {
					socket.close();
				}
				
				socket = new Socket(wifi.ip_drone, wifi.CONF_PORT);
				socket.setSoTimeout(3000);

				BufferedReader inStream = new BufferedReader(new InputStreamReader(socket.getInputStream()));

				//Log.d(DroneConsts.DroneLogTag, "Request config from drone");
				wifi.sendAtCommand("AT*CTRL=#SEQ#,4,0");

				String c = "";
				config = new ArrayList<String>();
				while ((c = inStream.readLine()) != null) {
					//Log.d(DroneConsts.DroneLogTag, c);

					config.add(c);
					
					// last line
					if (c.contains("custom:session_desc")) {
						readConfig = false;
						break;
					}
				}

				inStream.close();

				//Log.d(DroneConsts.DroneLogTag, "End of config reached.");

			} catch (SocketTimeoutException e) {
				//Log.e(DroneConsts.DroneLogTag, "Timeout, retry reading config.", e);
				if (counter++ > 3)
					return false;
			} catch (Exception e) {
				//Log.e(DroneConsts.DroneLogTag, "Error retrieving config from drone.", e);
				if (counter++ > 3)
					return false;
			}
		}

		return true;
	}
}
