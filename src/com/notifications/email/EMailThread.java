package com.notifications.email;

import java.io.InputStream;
import java.util.Map;

 class EMailThread implements Runnable {

	private String to;
	private String cc;
	private String bcc;
	private String subject;
	private String body;
	private Map<InputStream, String> attachments;
	private int retry_count;
	private int retry_lc=0;
	
	
	EMailThread(String to,String cc,String bcc,String subject,String body,Map<InputStream, String> attachments)
	{
		this.to=to;
		this.cc=cc;
		this.bcc=cc;
		this.subject=subject;
		this.body=body;
		this.attachments=attachments;
		String retry_str=EMailNotifier.getInstance().getProperties().getProperty("RETRY_COUNT");
		if(retry_str==null)
		{
			this.retry_count=3;
		}
		else
		{
			this.retry_count=Integer.parseInt(retry_str);
		}
	}
	@Override
	public void run() 
	{
		
		try 
		{
    		EMailNotifier mailNotifier=EMailNotifier.getInstance();		
			mailNotifier.send(to,cc,bcc,subject,body,attachments);
		}
		catch (Exception e) 
		{		
			System.out.println(e.getMessage());
			retry_lc++;
			if(retry_lc==retry_count)
			{
				e.printStackTrace();
			}
			else
			{
				run();
			}
			
			
		}
	}
	

}
