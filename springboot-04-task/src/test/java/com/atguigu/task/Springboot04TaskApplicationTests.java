package com.atguigu.task;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.MailMessage;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.test.context.junit4.SpringRunner;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Springboot04TaskApplicationTests {

    @Autowired
    JavaMailSenderImpl mailSender;

    @Test
    public void contextLoads() {
        SimpleMailMessage message = new SimpleMailMessage();
        //邮件设置
        message.setSubject("通知-今晚开会");
        message.setText("今晚19:30在会议室开会");

        message.setTo("489380134@qq.com");
        message.setFrom("740430795@qq.com");

        mailSender.send(message);
    }

    @Test
    public void test() throws MessagingException {
        //创建复杂邮件
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

        //邮件设置
        helper.setSubject("通知-今晚开会");
        helper.setText("<b style='color:red'>今晚19:30在会议室开会</b>", true);

        helper.setTo("489380134@qq.com");
        helper.setFrom("740430795@qq.com");

        //上传文件
        helper.addAttachment("1.jpg", new File("D:/IdeaProjects/springboot-04-task/src/main/resources/static/瀑布1.jpg"));
        helper.addAttachment("2.jpg", new File("D:/IdeaProjects/springboot-04-task/src/main/resources/static/瀑布2.jpg"));

        mailSender.send(mimeMessage);
    }

    @Test
    public void test1() throws MessagingException {
        //创建复杂邮件
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

        //邮件设置
        helper.setSubject("什么时候来杭州？请你吃大餐");
        helper.setText("<b style='color:red'>吃完大餐看电影，就问瑶瑶行不行？</b><br>附送两张美女照片，是谁家大姑娘，这么可爱？", true);

        helper.setTo("489380134@qq.com");
        helper.setFrom("740430795@qq.com");

        //上传文件
        helper.addAttachment("1.jpg", new File("D:/IdeaProjects/springboot-04-task/src/main/resources/static/1.jpg"));
        helper.addAttachment("2.jpg", new File("D:/IdeaProjects/springboot-04-task/src/main/resources/static/2.jpg"));

        mailSender.send(mimeMessage);
    }

}
