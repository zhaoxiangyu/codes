package org.sharp.intf;


public interface Response {
	public static enum StatusCode {
		EXCEPTION,NORMAL;
	}
	Response.StatusCode statusCode();
	String content();
}
