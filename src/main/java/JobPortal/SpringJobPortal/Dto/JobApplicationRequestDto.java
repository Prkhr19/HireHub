package JobPortal.SpringJobPortal.Dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class JobApplicationRequestDto {

    @NotBlank
    private String name;

    @NotBlank @Size(max = 10)
    private String phoneNo;

    @NotBlank
    private String skills;

    @NotBlank
    private String resumeUrl;

    @NotNull @Positive
    private Double experience;

    @NotBlank
    private String education;

    @NotBlank
    private String location;


}
