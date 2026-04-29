package JobPortal.SpringJobPortal.Service.Impl;

import JobPortal.SpringJobPortal.Dto.*;
import jakarta.validation.Valid;
import org.jspecify.annotations.Nullable;
import org.springframework.data.domain.Page;


import java.util.List;

public interface JobServices {
    public JobResponseDto createJob(@Valid JobRequestDto jobRequestDto);

    List<JobResponseDto> getAllJobs();

    JobResponseDto getJobById(Long id);

    JobResponseDto updateJob(Long id, JobRequestDto jobRequestDto);

    JobStatusResponseDto updateJobStatus(Long id, JobStatusRequestDto jobStatusRequestDto);

    JobPatchResponseDto patchJob(Long id, JobPatchRequestDto jobPatchRequestDto);

    Page<JobSearchResponseDto> searchJobs(JobSearchRequestDto jobSearchRequestDto);


     public String saveJob(Long jobId);
}
