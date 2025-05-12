package in.yash.VerifyIt.RestController;

import in.yash.VerifyIt.dto.ProfileRequestDto;
import in.yash.VerifyIt.dto.ProfileResponseDto;
import in.yash.VerifyIt.service.ProfileService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1.0")
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;

    @PostMapping("/register")
    public ResponseEntity<ProfileResponseDto>register(@Valid @RequestBody ProfileRequestDto request){
        ProfileResponseDto response=profileService.createProfile(request);
        //Todo send welcome email
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

}
