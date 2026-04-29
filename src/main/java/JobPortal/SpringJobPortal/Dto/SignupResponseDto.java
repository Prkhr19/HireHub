package JobPortal.SpringJobPortal.Dto;

import JobPortal.SpringJobPortal.Entity.type.RoleType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SignupResponseDto {

    private String message;
    private RoleType role;

}
