package in.yash.VerifyIt.RestController;

import in.yash.VerifyIt.dto.ProfileRequestDto;
import in.yash.VerifyIt.dto.ProfileResponseDto;
import in.yash.VerifyIt.service.ProfileService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;


    @PostMapping("/register")
    public ResponseEntity<ProfileResponseDto> register(@Valid @RequestBody ProfileRequestDto request) {
        ProfileResponseDto response = profileService.createProfile(request);
        //Todo send welcome email
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/profile")
    public ResponseEntity<ProfileResponseDto> getProfile() {
        ProfileResponseDto profile = profileService.getProfile(SecurityContextHolder.getContext().getAuthentication().getName());
        return new ResponseEntity<>(profile, HttpStatus.OK);
    }
}
