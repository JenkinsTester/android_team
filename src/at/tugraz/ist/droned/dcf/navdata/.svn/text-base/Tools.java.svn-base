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


public class Tools {
	public static int get_uint32(byte[] data, int offset) {
		int tmp = 0, n = 0;

		for (int i = 3; i >= 0; i--) {
			n <<= 8;
			tmp = data[offset + i] & 0xFF;
			n |= tmp;
		}

		return n;
	}

	public static int get_uint16(byte[] data, int offset) {
		int tmp = 0, n = 0;

		for (int i = 1; i >= 0; i--) {
			n <<= 8;
			tmp = data[offset + i] & 0xFF;
			n |= tmp;
		}

		return n;
	}

	public static float get_float(byte[] data, int offset) {
		return Float.intBitsToFloat(get_uint32(data, offset));
	}

	public static boolean get_state_bit(int state, int bit) {
		if ((((long) 1 << bit) & state) > 0) {
			return true;
		} else {
			return false;
		}
	}

}
