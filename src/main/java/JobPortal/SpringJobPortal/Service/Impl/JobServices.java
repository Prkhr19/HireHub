package JobPortal.SpringJobPortal.Service.Impl;

import JobPortal.SpringJobPortal.Dto.JobPatchRequestDto;
import JobPortal.SpringJobPortal.Dto.JobPatchResponseDto;
import JobPortal.SpringJobPortal.Dto.JobRequestDto;
import JobPortal.SpringJobPortal.Dto.JobResponseDto;
import jakarta.validation.Valid;

import java.util.List;

public interface JobServices {
    public JobResponseDto createJob(@Valid JobRequestDto jobRequestDto);

    List<JobResponseDto> getAllJobs();

    JobResponseDto getJobById(Long id);

    JobResponseDto updateJob(Long id, JobRequestDto jobRequestDto);

    JobResponseDto closeJob(Long id);

    JobPatchResponseDto patchJob(Long id, JobPatchRequestDto jobPatchRequestDto);


}
