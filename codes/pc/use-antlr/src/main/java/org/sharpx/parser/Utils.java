package org.sharpx.parser;

public class Utils {
	public static int runAntTarget(String antBatPath, String buildXmlPath,
			String targetName) {
		try {
			Process process = Runtime.getRuntime().exec(
					"cmd.exe /c antBatPath -f buildXmlPath targetName");
			return process.waitFor();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}

	public static String convert(String str) {
		StringBuffer ostr = new StringBuffer();

		for (int i = 0; i < str.length(); i++) {
			char ch = str.charAt(i);
			 // Does the char need to be
			// converted to unicode?
			if ((ch >= 0x0020) && (ch <= 0x007e)){
				ostr.append(ch); // No.
			} else { // Yes.
				ostr.append("\\u"); // standard unicode format.
				String hex = Integer.toHexString(str.charAt(i) & 0xFFFF); // Get hex value of the char.
				for (int j = 0; j < 4 - hex.length(); j++) // Prepend zeros because unicode requires 4 digits
					ostr.append("0");
				ostr.append(hex.toLowerCase()); // standard unicode format.
			}
		}
		return (new String(ostr)); // Return the stringbuffer cast as a string.

	}
}
