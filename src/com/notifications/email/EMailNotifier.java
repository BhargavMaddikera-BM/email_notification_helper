package com.notifications.email;

import java.io.InputStream;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;


import java.util.*;  

import javax.mail.*;  
import javax.mail.internet.*;  
import javax.activation.*;  
import javax.mail.util.*;


import com.notifications.email.exception.EmailAlertException;
import com.notifications.email.exception.EmailAlertRuntimeException;



 class EMailNotifier {
	private  String localhost = null;
	private  String mailhost = null;
	private String port=null;
	private String smtpAuth=null;
	private String sslFactory=null;
	private static String mailuser = null;
	private static String mailuser_password = null;	
	private static EMailNotifier mailNotifier=null;
	private Properties properties;
	
	static EMailNotifier getInstance()
	{
	
		 if(mailNotifier==null)
		 {
			 mailNotifier=new EMailNotifier();
		 }
		 return mailNotifier;
	}
	
	private EMailNotifier()
	{
		setMailProperties();
	}
	
	
	private Properties getProperties(String sourcePath)throws EmailAlertException
	{
		
		 InputStream is = null;		 
	
		 Properties prop=null;
	        try 
	        {
	        	prop = new Properties();
	            is = EMailNotifier.class.getResourceAsStream(sourcePath);
	            prop.load(is);
	            System.out.println(prop);
	        }
	        catch (Exception e) 
	        {
	           throw new EmailAlertException(e);
	        }
	        
	        return prop;
	        

	}
	
	
	private void setMailProperties()throws EmailAlertRuntimeException
	{
	  try
	  {
		  properties =getProperties("/com/notifications/email/config/config.properties");
		 if (properties != null)
		 {
		 localhost = properties.getProperty("EMAIL_LOCAL_HOST");
		 mailhost = properties.getProperty("SMTP_HOST_NAME");
		 mailuser = properties.getProperty("EMAIL_MAIL_USER");
		 mailuser_password =properties.getProperty("EMAIL_MAIL_USER_PASSWORD");
		 port=properties.getProperty("SMTP_PORT");		 
		 smtpAuth=properties.getProperty("SMTP_AUTH");
		 sslFactory=properties.getProperty("SMTP_SSL_FACTORY");
		
		 }
	  }catch(Exception e)
	  {
		  e.printStackTrace();
		  throw new EmailAlertRuntimeException(e);
	  }
	}

	 Properties getProperties() {
		return properties;
	}

	private Session getMailSession() throws EmailAlertException{
		Session session=null;
		try
		{
	//	setMailProperties();
		Properties p = new Properties();
		p.put("mail.host", mailhost);
		p.put("mail.user", mailuser);
		p.put("mail.smtp.port", port);
		p.put("mail.smtp.auth", smtpAuth);
		p.put("mail.smtp.socketFactory.class", sslFactory);

		 session = javax.mail.Session.getDefaultInstance(p, new Authenticator() 
		 {
			protected PasswordAuthentication getPasswordAuthentication()
			{
				return new PasswordAuthentication(EMailNotifier.mailuser,EMailNotifier.mailuser_password);
			}
		});
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new EmailAlertException(e);
		}
		
		return session;
	}

	private MimeMessage getMimeMessage(String mailBody, String subject,String contentType)throws EmailAlertException 
	{
		MimeMessage msg=null;
	try
	{
		 msg = new MimeMessage(getMailSession());
		try
		{
			msg.setText(mailBody);

			msg.setContent(mailBody, contentType);
			msg.setSubject(subject);

			Address fromAddr = new InternetAddress(mailuser);
			msg.setFrom(fromAddr);
		} catch (Exception e) 
		{
			e.printStackTrace();
		}
	}catch(Exception e)
	{
		throw new EmailAlertException(e);
	}
		return msg;
	}

	 void send(String _toList, String _ccList, String _bccList,String subject, String mailBody) throws EmailAlertException{
		try 
		{
			MimeMessage msg = getMimeMessage(mailBody, subject, "text/html");
			if ((_toList != null) && (!_toList.equals(""))) 
			{
				msg.addRecipients(Message.RecipientType.TO, _toList);
			}
			
			if ((_ccList != null) && (!_ccList.equals(""))) 
			{
				msg.addRecipients(Message.RecipientType.CC, _ccList);
			}
			
			if ((_bccList != null) && (!_bccList.equals("")))
			{
				msg.addRecipients(Message.RecipientType.BCC, _bccList);
			}
			
				Transport.send(msg);
			
		} catch (Exception e) 
		{
			e.printStackTrace();
			throw new EmailAlertException(e);
		}
	}

	 void send(String _toList, String _ccList, String _bccList,String subject, String mailBody,Map<InputStream, String> attachments)throws EmailAlertException {
		try
		{
			MimeMessage msg = new MimeMessage(getMailSession());
				msg.setSubject(subject);
				Address fromAddr = new InternetAddress(mailuser);
				msg.setFrom(fromAddr);
			
			if ((_toList != null) && (!_toList.equals("")))
			{
				msg.addRecipients(Message.RecipientType.TO, _toList);
			}
			
			if ((_ccList != null) && (!_ccList.equals("")))
			{
				msg.addRecipients(Message.RecipientType.CC, _ccList);
			}
			
			if ((_bccList != null) && (!_bccList.equals("")))
			{
				msg.addRecipients(Message.RecipientType.BCC, _bccList);
			}
			
			Multipart mpart = new MimeMultipart();
			BodyPart bodypartforMessage = new MimeBodyPart();
			bodypartforMessage.setText(mailBody);
			bodypartforMessage.setContent(mailBody, "text/html");
			mpart.addBodyPart(bodypartforMessage);
			if ((attachments != null) && (!attachments.isEmpty())) 
			{
				Iterator<Map.Entry<InputStream, String>> iterator = attachments.entrySet().iterator();
				while (iterator.hasNext()) 
				{
					Map.Entry<InputStream, String> entry = (Map.Entry) iterator.next();
					String fileName = (String) entry.getValue();
					InputStream is = (InputStream) entry.getKey();
					BodyPart bodypartforAttachment = new MimeBodyPart();
					DataSource source = new ByteArrayDataSource(is,"application/octet-stream");
					DataHandler dataHandler = new DataHandler(source);
					bodypartforAttachment.setDataHandler(dataHandler);
					bodypartforAttachment.setFileName(fileName);
					mpart.addBodyPart(bodypartforAttachment);
				}
			}
			msg.setContent(mpart);
			Transport.send(msg);

			
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			throw new EmailAlertException(e);
		}
	}
}
