package com.notifications.email.exception;

import com.notifications.common.AlertException;

public class EmailAlertException extends AlertException
{
	
	public EmailAlertException(Exception exception)
	{
		super(exception);
	}
	
	public EmailAlertException(String message)
	{
		super(message);
	}
	
	public EmailAlertException(String message,Exception exception)
	{
		super(message,exception);
	}


}
