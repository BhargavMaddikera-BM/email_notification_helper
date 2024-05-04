package com.notifications.common;


public class AlertRuntimeException extends RuntimeException
{
	
	public AlertRuntimeException(Exception exception)
	{
		super(exception);
	}
	
	public AlertRuntimeException(String message)
	{
		super(message);
	}
	
	public AlertRuntimeException(String message,Exception exception)
	{
		super(message,exception);
	}


}
