package JobPortal.SpringJobPortal.Controller;

import JobPortal.SpringJobPortal.Dto.JobSearchRequestDto;
import JobPortal.SpringJobPortal.Dto.JobSearchResponseDto;
import JobPortal.SpringJobPortal.Service.Impl.JobServices;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SearchJobsController {

    private final JobServices jobService;

    @Operation(summary = "search job filter")
    @PostMapping("/jobs/search/filter")
    public ResponseEntity<Page<JobSearchResponseDto>> searchJobFilter(@RequestBody JobSearchRequestDto jobSearchRequestDto){
        Page<JobSearchResponseDto> response = jobService.searchJobs((jobSearchRequestDto));
        return ResponseEntity.ok(response);
    }
}
