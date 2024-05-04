package com.notifications.email;

import java.io.InputStream;
import java.util.Map;
import java.util.Properties;

import com.notifications.email.exception.EmailAlertException;

public class EmailHelper
{
	private  String isThreadPoolNeeded;
	private static EmailHelper emailHelper;
	
	public static EmailHelper getInstance()
		{
		
			 if(emailHelper==null)
			 {
				 emailHelper=new EmailHelper();
			 }
			
			 return emailHelper;
		}
		 
	private EmailHelper()
	{
		 Properties p=EMailNotifier.getInstance().getProperties();
         isThreadPoolNeeded = p.getProperty("EMAIL_THREAD_POOL_REQUIRED");
         if(isThreadPoolNeeded==null)
         {
        	 isThreadPoolNeeded="N";
         }
	}	
public  void send(String to,String cc,String bcc,String subject,String body,Map<InputStream, String> attachments)throws EmailAlertException
{

	try
	{
		EMailThread mailThread=new EMailThread(to,cc,bcc,subject,body,attachments);
			
		if(isThreadPoolNeeded.equals("Y"))
		{
			EmailThreadPoolExecutor.getInstance().getExecutor().execute(mailThread);
		}
		else
		{
			mailThread.run();
		}
	}catch(Exception e)
	{
		throw new EmailAlertException(e);
	}
	
	
	
	
}

}
