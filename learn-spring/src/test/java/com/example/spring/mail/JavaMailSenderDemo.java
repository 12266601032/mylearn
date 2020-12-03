package com.example.spring.mail;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import org.junit.Test;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

/**
 * Date:     2020年01月15日 9:50 <br/>
 *
 * @author lcc
 * @since 1.3.0
 */
public class JavaMailSenderDemo {

  @Test
  public void name() throws MessagingException {
    JavaMailSenderImpl sender = new JavaMailSenderImpl();
    sender.setPort(25);
    sender.setHost("");
    sender.setUsername("");
    sender.setPassword("");
    MimeMessage message = sender.createMimeMessage();
    MimeMessageHelper helper = new MimeMessageHelper(message);
    helper.setText("test");
    helper.addTo("");
    helper.setFrom("");
    sender.send(message);
  }
}
