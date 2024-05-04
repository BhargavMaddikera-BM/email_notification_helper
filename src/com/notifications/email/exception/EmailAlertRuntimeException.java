package com.notifications.email.exception;

import com.notifications.common.AlertRuntimeException;


public class EmailAlertRuntimeException extends AlertRuntimeException
{
	
	public EmailAlertRuntimeException(Exception exception)
	{
		super(exception);
	}
	
	public EmailAlertRuntimeException(String message)
	{
		super(message);
	}
	
	public EmailAlertRuntimeException(String message,Exception exception)
	{
		super(message,exception);
	}


}
