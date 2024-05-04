package com.notifications.email;

import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

 class EmailThreadPoolExecutor {

	private static EmailThreadPoolExecutor emailThreadPoolExecutor;
	private ExecutorService executor;
	
	
	
	private EmailThreadPoolExecutor()
	{

		
        Properties p=EMailNotifier.getInstance().getProperties();
        String threadPoolSize = p.getProperty("EMAIL_THREAD_POOL_SIZE");
        int poolSize=25;
        if(threadPoolSize!=null)
        {
        	poolSize=Integer.parseInt(threadPoolSize);
        }
		
		executor = Executors.newFixedThreadPool(poolSize);	
	}
	
	
	 ExecutorService getExecutor() 
	 {		
		return executor;
     }


	 static EmailThreadPoolExecutor getInstance()
	{
	
		 if(emailThreadPoolExecutor==null)
		 {
			 emailThreadPoolExecutor=new EmailThreadPoolExecutor();
		 }
		
		 return emailThreadPoolExecutor;
	}
	

	
	 

}
