package in.yash.VerifyIt.service;

import in.yash.VerifyIt.dto.ProfileRequestDto;
import in.yash.VerifyIt.dto.ProfileResponseDto;

public interface ProfileService {
    public ProfileResponseDto createProfile(ProfileRequestDto request);

    public ProfileResponseDto getProfile(String email);
}
