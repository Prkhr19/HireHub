package JobPortal.SpringJobPortal.Controller;

import JobPortal.SpringJobPortal.Dto.*;
import JobPortal.SpringJobPortal.Service.Impl.CompanyService;
import JobPortal.SpringJobPortal.Service.JobServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/recruiter")


public class JobController {
    private final JobServiceImpl jobService;
    private final CompanyService companyService;

    @Operation(summary = "Create Job")
    @PostMapping("/jobs")
    public ResponseEntity<JobResponseDto> createJob(@Valid @RequestBody JobRequestDto jobRequestDto) {
        System.out.println("CREATE JOB API HIT");
        JobResponseDto response = jobService.createJob(jobRequestDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "Create company")
    @PostMapping("/company")
    public ResponseEntity<CompanyResponseDto> createCompany(@RequestBody CompanyRequestDto companyRequestDto){
        CompanyResponseDto response = companyService.createCompany(companyRequestDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    @Operation(summary = "Get All jobs")
    @GetMapping("/jobs")
    public ResponseEntity<List<JobResponseDto>> getAllJobs(){
       List <JobResponseDto> response = jobService.getAllJobs();
        return ResponseEntity.ok(response);
    }



    @Operation(summary = "Get jobs by id")
    @GetMapping("/jobs/{id}")
    public ResponseEntity<JobResponseDto> getJobById(@PathVariable Long id){
        JobResponseDto response = jobService.getJobById(id);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Update job")
    @PutMapping("/{id}/update")
    public ResponseEntity<JobResponseDto> updateJob(@PathVariable Long id, @RequestBody JobRequestDto jobRequestDto){
        JobResponseDto update= jobService.updateJob(id, jobRequestDto);

        return ResponseEntity.ok(update);
    }

    @Operation(summary = "Update job status")
    @PatchMapping("/{id}/status")
    public ResponseEntity<JobStatusResponseDto> updateJobStatus(@PathVariable Long id , @RequestBody JobStatusRequestDto jobStatusRequestDto){
        JobStatusResponseDto response = jobService.updateJobStatus(id, jobStatusRequestDto);

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Update salary")
    @PatchMapping("/{id}/updateSal")
    public ResponseEntity<JobPatchResponseDto> updatesal(@PathVariable Long id , @RequestBody JobPatchRequestDto jobPatchRequestDto){
        JobPatchResponseDto response = jobService.patchJob(id, jobPatchRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "search job filter")
    @PostMapping("/jobs/search/filter")
    public ResponseEntity<Page<JobSearchResponseDto>> searchJobFilter(@RequestBody JobSearchRequestDto jobSearchRequestDto){
        Page<JobSearchResponseDto> response = jobService.searchJobs((jobSearchRequestDto));
        return ResponseEntity.ok(response);
    }





}
