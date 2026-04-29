package JobPortal.SpringJobPortal.Dto;

import JobPortal.SpringJobPortal.Entity.type.Jobtype;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JobSearchResponseDto {
    private Long id;
    private String title;
    private String location;
    private Double salary;
    private Jobtype jobtype;
    private String companyName;
    private LocalDateTime postedAt;
}
