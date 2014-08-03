package org.sharp.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.apache.commons.io.IOUtils;
import org.sharp.jdkex.JdkUtils;

public class SocketSer {

	public static void startShutdownListener(final int port){
		
		new Thread(new Runnable() {
			public void run() {
				ServerSocket ss;
				try {
					ss = new ServerSocket(port);
					while(true){
						Socket socket = ss.accept();
						Executor executor = Executors.newCachedThreadPool();
						executor.execute(new ShutdownPCProcessorFac().runnable(socket));
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}
	
	public static interface SocketProcessorFac{
		
		Runnable runnable(Socket socket);
	}
	
	public static class ShutdownPCProcessorFac implements SocketProcessorFac {
		
		public Runnable runnable(final Socket socket) {
		
			return new Runnable(){
				
				public void run() {
					
					InputStream input = null;
					OutputStream output = null;
					try {
						input = socket.getInputStream();
						output = socket.getOutputStream();
						String cmd = IOUtils.toString(input);
						if("shutdown".equalsIgnoreCase(cmd)){
							JdkUtils.shutdownPC();
							IOUtils.write("OK", output);
						}
					} catch (IOException e) {
						e.printStackTrace();
					} finally{
						IOUtils.closeQuietly(input);
						IOUtils.closeQuietly(output);
						try {
							socket.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
				
			};
		}
		
	}
}
