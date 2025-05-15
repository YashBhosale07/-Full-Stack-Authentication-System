package in.yash.VerifyIt.service;

import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender javaMailSender;


    private final String email="yashbhosale833@gmail.com";
    public void sendWelcomeEmail(String toEmail,String name){
        SimpleMailMessage message=new SimpleMailMessage();
        message.setFrom(email);
        message.setTo(toEmail);
        message.setSubject("Welcome to Our Platform");
        message.setText("Hello "+name+",\n\n Thanks for registering with us!!! \n\nRegards, \nVerifyIt Team ");
        javaMailSender.send(message);
    }

    public void sendResetOtpEmail(String toEmail,String otp){
        SimpleMailMessage message=new SimpleMailMessage();
        message.setFrom(email);
        message.setTo(toEmail);
        message.setSubject("Password Reset OTP");
        message.setText("Your otp for resetting the password is "+otp+"\n OTP is valid for 10 minutes");
        javaMailSender.send(message);
    }

    public void sendOtpEmail(String toEmail,String otp){
        SimpleMailMessage message=new SimpleMailMessage();
        message.setFrom(email);
        message.setTo(toEmail);
        message.setSubject("Account Verification OTP");
        message.setText("Your otp for verification is "+otp+"\n OTP is valid for 10 minutes");
        javaMailSender.send(message);
    }

}
