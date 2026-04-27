package JobPortal.SpringJobPortal.Dto;

import JobPortal.SpringJobPortal.Entity.type.ApplicationStatus;
import JobPortal.SpringJobPortal.Entity.type.JobStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JobApplicationStatusRequestDto {

    private ApplicationStatus status;
}
