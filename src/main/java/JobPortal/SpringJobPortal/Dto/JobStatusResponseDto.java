package JobPortal.SpringJobPortal.Dto;

import JobPortal.SpringJobPortal.Entity.type.JobStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JobStatusResponseDto {
    private String message;
    private JobStatus status;
}
