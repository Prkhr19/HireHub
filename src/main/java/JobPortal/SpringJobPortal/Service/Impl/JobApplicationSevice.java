package JobPortal.SpringJobPortal.Service.Impl;

import JobPortal.SpringJobPortal.Dto.JobApplicationRequestDto;
import JobPortal.SpringJobPortal.Dto.JobApplicationResponseDto;
import org.springframework.stereotype.Service;

@Service
public interface JobApplicationSevice {

    JobApplicationResponseDto applyJob(Long jobId, JobApplicationRequestDto jobApplicationRequestDto);
}
