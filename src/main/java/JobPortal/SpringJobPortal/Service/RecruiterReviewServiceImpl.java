package JobPortal.SpringJobPortal.Service;

import JobPortal.SpringJobPortal.Dto.JobApplicationStatusRequestDto;
import JobPortal.SpringJobPortal.Dto.JobApplicationStatusResponseDto;
import JobPortal.SpringJobPortal.Dto.RecruiterReviewResponseDto;
import JobPortal.SpringJobPortal.Entity.*;
import JobPortal.SpringJobPortal.Entity.type.RoleType;
import JobPortal.SpringJobPortal.Repository.*;
import JobPortal.SpringJobPortal.Security.CurrentUserAuth.CurrentUserService;
import JobPortal.SpringJobPortal.Service.Impl.RecruiterReviewService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.AccessDeniedException;
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

        Job job = jobRepository.findById(jobId).orElseThrow(() -> new EntityNotFoundException("Job not found"));

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
        System.out.println("User id: "+ user.getUserId());
       // CandidateProfile profile = userRepository.findById(user.getUserId()).orElseThrow(()-> new EntityNotFoundException("Candidate not found with Id")).getCandidateProfile();



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

    @Override
    public JobApplicationStatusResponseDto applicantStatus(Long applicationId, JobApplicationStatusRequestDto jobApplicationStatusRequestDto) {

        User user = currentUserService.getCurrentUser();

        JobApplication application = jobApplicationRepository.findById(applicationId).orElseThrow(()-> new EntityNotFoundException("Job application not found"));

        Job job = jobRepository.findById(user.getUserId()).orElseThrow(()-> new EntityNotFoundException("Job not found with this ID"));

        RecruiterProfile profile = recruiterProfileRepository.findByUserUserId(user.getUserId()).orElseThrow(()-> new UsernameNotFoundException("User name not found"));

//        if (! jobRepository.findByJobIdAndRercruiterId(job.getId(), profile.getId() )){
//            throw new AccessDeniedException("Unauthorized");
//        }

            application.setStatus(jobApplicationStatusRequestDto.getStatus());


            jobApplicationRepository.save(application);

            return JobApplicationStatusResponseDto.builder()
                    .status(jobApplicationStatusRequestDto.getStatus())
                    .message("Jobapplication updated successfully")
                    .build();


    }
}
