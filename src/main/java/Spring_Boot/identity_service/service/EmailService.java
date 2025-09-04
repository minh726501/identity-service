package Spring_Boot.identity_service.service;

import Spring_Boot.identity_service.entity.PasswordResetToken;
import Spring_Boot.identity_service.entity.User;
import Spring_Boot.identity_service.repository.PasswordResetTokenRepository;
import Spring_Boot.identity_service.repository.UserRepository;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Random;

@Service
public class EmailService {
    private final JavaMailSender mailSender;
    private final UserRepository userRepository;
    private final PasswordResetTokenRepository passwordResetTokenRepository;
    private final PasswordEncoder passwordEncoder;

    public EmailService(JavaMailSender mailSender, UserRepository userRepository, PasswordResetTokenRepository passwordResetTokenRepository, PasswordEncoder passwordEncoder) {
        this.mailSender = mailSender;
        this.userRepository = userRepository;
        this.passwordResetTokenRepository = passwordResetTokenRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void forgotPassword(String email){
        User user=userRepository.findByEmail(email);
        if (user==null){
            throw new RuntimeException("User not found");
        }
        String otp=String.format("%06d",new Random().nextInt(999999));
        PasswordResetToken resetToken=new PasswordResetToken();
        resetToken.setUser(user);
        resetToken.setToken(otp);
        resetToken.setExpiry(Timestamp.valueOf(LocalDateTime.now().plusMinutes(5)));
        passwordResetTokenRepository.save(resetToken);
        sendMail(email,otp);
    }
    public void sendMail(String email,String otp){
        SimpleMailMessage message=new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Password Reset Request");
        message.setText("Your OTP code is: " + otp + "\nThis code will expire in 5 minutes.");
        mailSender.send(message);
    }
    public void resetPassword(String otp,String newPassword){
        PasswordResetToken token= passwordResetTokenRepository.findByToken(otp);
        if (token==null){
            throw new RuntimeException("Invalid Otp");
        }
        if (token.getExpiry().before(new Timestamp(System.currentTimeMillis()))){
            throw new RuntimeException("Otp Expired");
        }
        User getUser= token.getUser();
        getUser.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(getUser);
        passwordResetTokenRepository.delete(token);
    }
}
