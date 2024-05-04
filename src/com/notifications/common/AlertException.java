package com.notifications.common;


public class AlertException extends Exception
{
	
	public AlertException(Exception exception)
	{
		super(exception);
	}
	
	public AlertException(String message)
	{
		super(message);
	}
	
	public AlertException(String message,Exception exception)
	{
		super(message,exception);
	}


}
