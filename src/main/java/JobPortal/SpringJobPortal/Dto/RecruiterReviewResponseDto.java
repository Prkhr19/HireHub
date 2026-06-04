package JobPortal.SpringJobPortal.Dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RecruiterReviewResponseDto {

    private Long applicationId;

    private String name;

    private String phoneNo;

    private String skills;

    private String resumeUrl;

    private String experience;

    private String education;

    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime appliedAt;



}
