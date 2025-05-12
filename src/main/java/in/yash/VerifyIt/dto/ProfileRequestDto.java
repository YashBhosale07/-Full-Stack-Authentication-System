package in.yash.VerifyIt.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProfileRequestDto {
    @NotBlank(message = "Name should not be empty")
    private String name;
    @NotBlank(message = "Email Should not be blank")
    @Email(message = "Invalid Email")
    private String email;
    @Size(min = 6,max = 18,message = "Password should be min 6 character and less than 18 character")
    private String password;
}
