package JobPortal.SpringJobPortal.Service;

import JobPortal.SpringJobPortal.Dto.JobApplicationStatusRequestDto;
import JobPortal.SpringJobPortal.Dto.JobApplicationStatusResponseDto;
import JobPortal.SpringJobPortal.Dto.RecruiterReviewResponseDto;
import JobPortal.SpringJobPortal.Dto.RecruiterViewJobDTO;
import JobPortal.SpringJobPortal.Entity.*;
import JobPortal.SpringJobPortal.Entity.type.RoleType;
import JobPortal.SpringJobPortal.Repository.*;
import JobPortal.SpringJobPortal.Security.CurrentUserAuth.CurrentUserService;
import JobPortal.SpringJobPortal.Service.Impl.RecruiterReviewService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RecruiterReviewServiceImpl implements RecruiterReviewService {
    private final CurrentUserService currentUserService;
    private final JobRepository jobRepository;
    private final RecruiterProfileRepository recruiterProfileRepository;
    private final CandidateProfileRepository candidateProfileRepository;
    private final UserRepository userRepository;
    private final JobApplicationRepository jobApplicationRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<RecruiterReviewResponseDto> getJobApplication(Long jobId) {

        User user = currentUserService.getCurrentUser();

        Job job = jobRepository.findById(jobId).orElseThrow(() -> new BadCredentialsException("Job not found"));

        RoleType role = user.getRole();
        if (!(role == RoleType.ADMIN || role == RoleType.RECRUITER)){

            throw new AccessDeniedException("Unauthorized access");
        }

        else if (role == RoleType.RECRUITER) {

            RecruiterProfile currentUser = recruiterProfileRepository.findByUserUserId(user.getUserId()).orElseThrow(() -> new UsernameNotFoundException("User name not found"));

            if (!job.getRecruiter().getId().equals(currentUser.getId())) {
                throw new AccessDeniedException("You can only view your own job applications");
            }
        }



        List<JobApplication> applications = jobApplicationRepository.findByJobId(jobId);


        List<RecruiterReviewResponseDto> response = applications.stream().map(app -> RecruiterReviewResponseDto.builder()

                .applicationId(app.getId())
                .name(app.getCandidate().getName())
                .skills(app.getCandidate().getSkills())
                .resumeUrl(app.getCandidate().getResumeUrl())
                .appliedAt(app.getAppliedAt())
                .phoneNo(app.getCandidate().getPhoneNo())
                .education(app.getCandidate().getEducation())
                .experience(app.getCandidate().getExperience())
                .build()).toList();


            return response ;
    }

    @Transactional
    @Override
    public JobApplicationStatusResponseDto applicantStatus(Long applicationId, JobApplicationStatusRequestDto jobApplicationStatusRequestDto) {

        User user = currentUserService.getCurrentUser();

        JobApplication application = jobApplicationRepository.findById(applicationId).orElseThrow(()-> new BadCredentialsException("Job application not found"));

        Job job = application.getJob();

        RoleType role = user.getRole();

        if (role == RoleType.ADMIN){

        } else if (role == RoleType.RECRUITER) {

            RecruiterProfile recruiter = recruiterProfileRepository.findByUserUserId(user.getRecruiterProfile().getId()).orElseThrow(()-> new UsernameNotFoundException("Recruiter not found"));

            if (! job.getRecruiter().getId().equals(recruiter.getId())){
                throw new AccessDeniedException("Unauthorized");

            }

        }else {
            throw new AccessDeniedException("Unauthorized");
        }


        application.setStatus(jobApplicationStatusRequestDto.getStatus());


            jobApplicationRepository.save(application);

            return JobApplicationStatusResponseDto.builder()
                    .status(jobApplicationStatusRequestDto.getStatus())
                    .message("Job application updated successfully")
                    .build();


    }

    @Override
    public List<RecruiterViewJobDTO> viewMyJob() {
        User user = currentUserService.getCurrentUser();

        RoleType roles = user.getRole();

        if (roles != RoleType.RECRUITER){
            throw new AccessDeniedException("Unauthorized access");
        }

        RecruiterProfile recruiter = recruiterProfileRepository.findByUserUserId(user.getUserId()).orElseThrow(()-> new BadCredentialsException("No user found"));

        List<Job> jobs = jobRepository.findByRecruiter_Id(recruiter.getId());




        List<RecruiterViewJobDTO> response = jobs.stream().map(job ->RecruiterViewJobDTO.builder()
                .id(job.getId().toString())
                .title(job.getTitle())
                .status(job.getStatus())
                .location(job.getLocation())
                .salary(job.getSalary().toString())
                .build()).toList();

        return response;
    }
}
