package org.apache.commons.mail;

import static org.junit.Assert.assertEquals;

import java.util.Calendar;
import java.util.Date;

import javax.mail.Session;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class EmailTest {
	private static final String[] TEST_EMAILS = {"ab@bc.com", "a.b@c.org", "abcdefghijklmnopqrst@abcdefghijklmnopqrst.com.bd"};

	private static final String[] TEST_EMAILS1 = {}; //empty array for triggering exceptions
	
	private EmailConcrete email;
	
	@Before
	public void setUpEmailTest() throws Exception {
		email = new EmailConcrete();
	}
	
	@After
	public void tearDownEmailTest() throws Exception {
		
	}
	
	@Test
	public void testAddBcc() throws Exception {
		email.addBcc(TEST_EMAILS);
		assertEquals(3, email.getBccAddresses().size());
		
		email.addBcc(TEST_EMAILS1); //test with an empty array to trigger the exception
		assertEquals(0, email.getBccAddresses().size());
	}
	
	@Test
	public void testAddCc() throws Exception {
		email.addCc("ab@bc.com");
	}
	
	@Test
	public void testAddHeader() throws Exception {
		email.addHeader("Name", "Value");
		
		email.addHeader(null, null); //test with no values to trigger an exception
	}
	
	@Test
	public void testAddReplyTo() throws Exception {
		email.addReplyTo("ab@bc.com", "Name");
	}
	
	@Test
	public void testBuildMimeMessage() throws Exception {
		//add some things to build the message with first
		email.setHostName("Name");
		email.setFrom("ab@bc.com");
		email.addTo("bc@cd.com");
		email.setSubject("Subject");
		email.addCc("ab@bc.com");
		email.addBcc(TEST_EMAILS);
		assertEquals(3, email.getBccAddresses().size());
		final String headerValue = "1234567890 1234567890 123456789 01234567890 123456789 0123456789 01234567890 01234567890";
		email.addHeader("X-LongHeader", headerValue);
		email.addReplyTo("ab@bc.com", "Name");
		email.setCharset("ISO-8859-1");
		email.setContent("test content", "text/plain; charset=US-ASCII");
		email.setPopBeforeSmtp(true, headerValue, headerValue, headerValue);
		email.buildMimeMessage();
	}
	
	@Test
	public void testGetHostName() throws Exception {
		email.getHostName(); //first try with no host name
		//set a host name and try again
		email.setHostName("Name");
		assertEquals("Name", email.getHostName());
	}

	@Test
	public void testGetMailSession() throws Exception {
		email.getMailSession();
	}
	
	@Test
	public void testGetSentDate()
	{
		//set a date and test
		final Date dtTest = Calendar.getInstance().getTime();
		email.setSentDate(dtTest);
		assertEquals(dtTest, email.getSentDate());
	}
	
	@Test
	public void testGetSocketConnectionTimeout() throws Exception {
		email.getSocketConnectionTimeout();
	}
	
	@Test
	public void testSetFrom() throws Exception {
		email.setFrom("ab@bc.com");
	}
}
