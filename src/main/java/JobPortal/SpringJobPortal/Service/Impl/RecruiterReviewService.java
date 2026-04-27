package JobPortal.SpringJobPortal.Service.Impl;

import JobPortal.SpringJobPortal.Dto.JobApplicationStatusRequestDto;
import JobPortal.SpringJobPortal.Dto.JobApplicationStatusResponseDto;
import JobPortal.SpringJobPortal.Dto.RecruiterReviewResponseDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RecruiterReviewService {
    List<RecruiterReviewResponseDto> getJobApplication(Long jobId);

    JobApplicationStatusResponseDto applicantStatus(Long applicationId, JobApplicationStatusRequestDto jobApplicationStatusRequestDto);
}
