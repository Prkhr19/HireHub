package JobPortal.SpringJobPortal.Dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompanyRequestDto {

    @NotBlank
    private String companyName;

    @NotBlank
    private String location;

    @NotBlank
    private String website;

    @NotBlank
    private String description;

}
