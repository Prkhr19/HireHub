package JobPortal.SpringJobPortal.Dto;

import JobPortal.SpringJobPortal.Entity.type.RoleType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignUpRequestDto {

    @NotBlank
    private String name;

    @Email @NotBlank
    private String email;

    @NotBlank
    @Size(min = 4)
    private String password;

    @NotNull
    private RoleType role;

}
