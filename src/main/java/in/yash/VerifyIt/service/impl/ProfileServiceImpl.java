package in.yash.VerifyIt.service.impl;

import in.yash.VerifyIt.Entity.User;
import in.yash.VerifyIt.GlobalExceptionHandler.EmailAlreadyExistsException;
import in.yash.VerifyIt.dto.ProfileRequestDto;
import in.yash.VerifyIt.dto.ProfileResponseDto;
import in.yash.VerifyIt.repository.UserRepository;
import in.yash.VerifyIt.service.EmailService;
import in.yash.VerifyIt.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    public ProfileResponseDto createProfile(ProfileRequestDto request) {
        boolean emailExits = userRepository.existsByEmail(request.getEmail());
        if (emailExits) {
            throw new EmailAlreadyExistsException("Email already registered");
        }
        User user = converToUser(request);
        User savedUser = userRepository.save(user);
        return modelMapper.map(savedUser, ProfileResponseDto.class);
    }

    @Override
    public ProfileResponseDto getProfile(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return modelMapper.map(user, ProfileResponseDto.class);
    }

    @Override
    public void resendOtp(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        //Generate 6 digit otp
        String otp=String.valueOf(new Random().nextInt(900000) + 100000);
        long expiryTime = System.currentTimeMillis() + (10 * 60 * 1000);
        user.setResetOtp(otp);
        user.setResetOtpExpireAt(expiryTime);
        userRepository.save(user);
        try {
            emailService.sendResetOtpEmail(user.getEmail(),otp);
        }catch (Exception e){
            throw new RuntimeException("Unable to send email");
        }

    }

    @Override
    public void resetPassword(String email,String otp,String resetPassword) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        if(user.getResetOtp()==null || !user.getResetOtp().equals(otp)){
            throw new RuntimeException("Otp Invalid");
        }
        if(user.getResetOtpExpireAt()<System.currentTimeMillis()){
            throw new RuntimeException("Otp Expired");
        }
        user.setPassword(passwordEncoder.encode(resetPassword));
        user.setResetOtp(null);
        user.setResetOtpExpireAt(0L);
        userRepository.save(user);

    }

    @Override
    public void sendOtp(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        //Generate 6 digit otp
        if(user.getIsAccountVerified()!=null && user.getIsAccountVerified()){
            return;
        }
        String otp=String.valueOf(new Random().nextInt(900000) + 100000);
        long expiryTime = System.currentTimeMillis() + (10 * 60 * 1000);
        user.setVerifyOtp(otp);
        user.setVerifyOtpExpireAt(expiryTime);
        userRepository.save(user);
        try {
            emailService.sendOtpEmail(user.getEmail(),otp);
        }catch (Exception ex){
            throw new RuntimeException("Unable to send email");
        }
    }

    @Override
    public void verifyOtp(String email, String otp) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        if(user.getVerifyOtp()==null || !user.getVerifyOtp().equals(otp)){
            throw new RuntimeException("Invalid otp");
        }
        if(user.getVerifyOtpExpireAt()<System.currentTimeMillis()){
            throw new RuntimeException("OTP Expired");
        }
        user.setIsAccountVerified(true);
        user.setVerifyOtp(null);
        user.setVerifyOtpExpireAt(0L);
        userRepository.save(user);

    }

    @Override
    public String getLoggedInUserId(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return user.getUserId();
    }

    private User converToUser(ProfileRequestDto request) {
        return User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .isAccountVerified(false)
                .resetOtp(null)
                .verifyOtpExpireAt(0L)
                .resetOtpExpireAt(0L)
                .verifyOtp(null)
                .userId(UUID.randomUUID().toString())
                .build();

    }

}
