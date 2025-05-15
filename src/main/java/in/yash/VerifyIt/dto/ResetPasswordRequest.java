package in.yash.VerifyIt.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResetPasswordRequest {
    @NotBlank(message = "new password is required")
    @Size(min = 6, max = 18, message = "Password should be min 6 character and less than 18 character")
    private String newPassword;
    @NotBlank(message = "Otp is required")
    private String otp;
    @NotBlank(message = "Email Should not be blank")
    @Email(message = "Invalid Email")
    private String email;
}
