package JobPortal.SpringJobPortal.Dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LoginRequestDto {
    @Email
    @NotBlank
    private String email;

    @NotBlank
    @Size(min = 4)
    private String password;



}
