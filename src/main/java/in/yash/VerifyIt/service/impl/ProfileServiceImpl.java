package in.yash.VerifyIt.service.impl;

import in.yash.VerifyIt.Entity.User;
import in.yash.VerifyIt.GlobalExceptionHandler.EmailAlreadyExistsException;
import in.yash.VerifyIt.dto.ProfileRequestDto;
import in.yash.VerifyIt.dto.ProfileResponseDto;
import in.yash.VerifyIt.repository.UserRepository;
import in.yash.VerifyIt.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

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
