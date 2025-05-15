package in.yash.VerifyIt.service;

import in.yash.VerifyIt.dto.ProfileRequestDto;
import in.yash.VerifyIt.dto.ProfileResponseDto;

public interface ProfileService {
    public ProfileResponseDto createProfile(ProfileRequestDto request);

    public ProfileResponseDto getProfile(String email);

    public void resendOtp(String email);

    public void resetPassword(String email,String otp,String resetPassword);

    public void sendOtp(String email);

    public void verifyOtp(String email,String otp);

    public String getLoggedInUserId(String email);
}
