package JobPortal.SpringJobPortal.Controller;

import JobPortal.SpringJobPortal.Dto.JobRequestDto;
import JobPortal.SpringJobPortal.Dto.JobResponseDto;
import JobPortal.SpringJobPortal.Repository.JobRepository;
import JobPortal.SpringJobPortal.Service.JobServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor

public class JobController {
    private final JobServiceImpl jobService;

    @PostMapping("/jobs")
    public ResponseEntity<JobResponseDto> createJob(@Valid @RequestBody JobRequestDto jobRequestDto) {
        System.out.println("CREATE JOB API HIT");
        JobResponseDto response = jobService.createJob(jobRequestDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/jobs")
    public ResponseEntity<List<JobResponseDto>> getAllJobs(){
       List <JobResponseDto> response = jobService.getAllJobs();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/jobs/{id}")
    public ResponseEntity<JobResponseDto> getJobById(@PathVariable Long id){
        JobResponseDto response = jobService.getJobById(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/jobs/{id}")
    public ResponseEntity<JobResponseDto> updateJob(@PathVariable Long id, @RequestBody JobRequestDto jobRequestDto){
        JobResponseDto update= jobService.updateJob(id, jobRequestDto);

        return ResponseEntity.ok(update);
    }

    @PatchMapping("/jobs/{id}/close")
    public ResponseEntity<JobResponseDto> closeJob(@PathVariable Long id){
        JobResponseDto close = jobService.closeJob(id);

        return ResponseEntity.ok(close);
    }





}
