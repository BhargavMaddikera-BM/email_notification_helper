package com.notifications.test;

import java.util.Map;

import com.notifications.email.EmailHelper;
import com.notifications.email.exception.EmailAlertException;


public class Test
{

	public static void main(String[] args) throws Exception
	{
		testEmail("Bhargav.Maddikera@gmail.com",null,null,"Hai", "Test Message", null);
	}
	
	
	private static void testEmail(String to, String cc, String bcc, String subject, String body , Map attachments)throws EmailAlertException
	{
		EmailHelper.getInstance().send(to,cc,bcc,subject,body,attachments);

	}

}
