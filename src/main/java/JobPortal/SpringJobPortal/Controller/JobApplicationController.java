package JobPortal.SpringJobPortal.Controller;

import JobPortal.SpringJobPortal.Dto.JobApplicationRequestDto;
import JobPortal.SpringJobPortal.Dto.JobApplicationResponseDto;
import JobPortal.SpringJobPortal.Repository.CandidateProfileRepository;
import JobPortal.SpringJobPortal.Service.Impl.JobApplicationSevice;
import JobPortal.SpringJobPortal.Service.JobServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class JobApplicationController {

    private final CandidateProfileRepository candidateProfileRepository;
    private final JobServiceImpl jobService;
    private final JobApplicationSevice jobApplicationSevice;


    @PostMapping("/jobs/{jobId}/apply")
    public ResponseEntity<JobApplicationResponseDto> applyJob(@PathVariable Long jobId, @RequestBody @Valid JobApplicationRequestDto jobApplicationRequestDto) {

        JobApplicationResponseDto apply = jobApplicationSevice.applyJob(jobId, jobApplicationRequestDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(apply);
    }

}
