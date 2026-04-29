package JobPortal.SpringJobPortal.Controller;

import JobPortal.SpringJobPortal.Dto.JobApplicationStatusRequestDto;
import JobPortal.SpringJobPortal.Dto.JobApplicationStatusResponseDto;
import JobPortal.SpringJobPortal.Dto.RecruiterReviewResponseDto;
import JobPortal.SpringJobPortal.Service.Impl.RecruiterReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/recruiter")
public class RecruiterReviewController {
    private final RecruiterReviewService recruiterReviewService;

    @GetMapping("/{jobId}/applications")
    public ResponseEntity<List<RecruiterReviewResponseDto>> getJobApplication(@PathVariable Long jobId){
        List<RecruiterReviewResponseDto> response = recruiterReviewService.getJobApplication(jobId);

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{applicationId}/status")
    public ResponseEntity<JobApplicationStatusResponseDto> statusUpdate(@PathVariable Long applicationId, @RequestBody JobApplicationStatusRequestDto jobApplicationStatusRequestDto){
        JobApplicationStatusResponseDto response = recruiterReviewService.applicantStatus(applicationId, jobApplicationStatusRequestDto);

        return ResponseEntity.ok(response);

    }

}
