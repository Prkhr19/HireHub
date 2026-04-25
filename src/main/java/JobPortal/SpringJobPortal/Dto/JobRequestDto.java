package JobPortal.SpringJobPortal.Dto;

import JobPortal.SpringJobPortal.Entity.RecruiterProfile;
import JobPortal.SpringJobPortal.Entity.type.JobStatus;
import JobPortal.SpringJobPortal.Entity.type.Jobtype;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JobRequestDto {

    @NotBlank
    private String title;

    @NotBlank
    private String description;

    @NotBlank
    private String location;

    @NotNull @Positive
    private Double salary;

    @NotBlank
    private String experienceRequired;

    @NotNull
    private Jobtype jobType;

}
