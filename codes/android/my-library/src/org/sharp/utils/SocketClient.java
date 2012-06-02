package org.sharp.utils;

import java.net.InetAddress;
import java.net.Socket;
import java.util.List;

import org.apache.commons.io.IOUtils;

public class SocketClient {

	public static void shutdownPC(String ipStr,int socknum) throws Exception{
		try {
			InetAddress address = InetAddress.getByName(ipStr);
			Socket socket = new Socket(address, socknum);
			IOUtils.write("shutdown", socket.getOutputStream());
			List<String> resp = IOUtils.readLines(socket.getInputStream());
			socket.close();

		} catch (Exception e) {
			throw new Exception("Failed to send ShutdownPC packet: + e");
		}

	}

}
