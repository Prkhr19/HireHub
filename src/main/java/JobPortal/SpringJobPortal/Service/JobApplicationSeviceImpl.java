package JobPortal.SpringJobPortal.Service;

import JobPortal.SpringJobPortal.Dto.CandidateProfileReqDto;
import JobPortal.SpringJobPortal.Entity.CandidateProfile;
import JobPortal.SpringJobPortal.Entity.Job;
import JobPortal.SpringJobPortal.Entity.JobApplication;
import JobPortal.SpringJobPortal.Entity.type.ApplicationStatus;
import JobPortal.SpringJobPortal.Entity.type.JobStatus;
import JobPortal.SpringJobPortal.Repository.JobRepository;
import JobPortal.SpringJobPortal.Security.CurrentUserAuth.CurrentUserService;
import JobPortal.SpringJobPortal.Dto.JobApplicationRequestDto;
import JobPortal.SpringJobPortal.Dto.JobApplicationResponseDto;
import JobPortal.SpringJobPortal.Entity.User;
import JobPortal.SpringJobPortal.Repository.CandidateProfileRepository;
import JobPortal.SpringJobPortal.Repository.JobApplicationRepository;
import JobPortal.SpringJobPortal.Service.Impl.JobApplicationSevice;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JobApplicationSeviceImpl implements JobApplicationSevice {
    private final CandidateProfileRepository candidateProfileRepository;
    private final JobApplicationRepository jobApplicationRepository;
    private final CurrentUserService currentUserService;
    private final JobRepository jobRepository;

    @Override
    public JobApplicationResponseDto applyJob(Long jobId) {


        User user = currentUserService.getCurrentUser();


//        if (user.getRole() != RoleType.CANDIDATE) {
//            throw new AccessDeniedException("Only candidates can apply for jobs");
//        }

        Job job = jobRepository.findById(jobId).orElseThrow(() -> new EntityNotFoundException("Job not found with this id"));


        if (job.getStatus() != JobStatus.OPEN) {
            throw new IllegalStateException("This job is now closed");
        }

        CandidateProfile candidate = candidateProfileRepository.findByUserUserId(user.getUserId()).orElseThrow(() -> new UsernameNotFoundException("User not found"));

        if (jobApplicationRepository.existsByCandidateAndJob(candidate, job)) {
            throw new AccessDeniedException("User already applied for this job");
        }

        JobApplication application = new JobApplication();
        application.setJob(job);
        application.setCandidate(candidate);
        application.setStatus(ApplicationStatus.APPLIED);

        jobApplicationRepository.save(application);
        System.out.println("before save");

        return JobApplicationResponseDto.builder()
                .name(candidate.getName())
                .message("Applied Successfully")
                .status(ApplicationStatus.APPLIED)
                .build();


    }
}

