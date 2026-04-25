package JobPortal.SpringJobPortal.Service;

import JobPortal.SpringJobPortal.Dto.JobRequestDto;
import JobPortal.SpringJobPortal.Dto.JobResponseDto;
import JobPortal.SpringJobPortal.Entity.Company;
import JobPortal.SpringJobPortal.Entity.Job;
import JobPortal.SpringJobPortal.Entity.RecruiterProfile;
import JobPortal.SpringJobPortal.Entity.User;
import JobPortal.SpringJobPortal.Entity.type.JobStatus;
import JobPortal.SpringJobPortal.Entity.type.RoleType;
import JobPortal.SpringJobPortal.Repository.JobRepository;
import JobPortal.SpringJobPortal.Repository.RecruiterProfileRepository;
import JobPortal.SpringJobPortal.Repository.UserRepository;
import JobPortal.SpringJobPortal.Service.Impl.JobServices;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.access.AccessDeniedException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class JobServiceImpl implements JobServices {

    private final UserRepository userRepository;
    private final RecruiterProfileRepository recruiterProfileRepository;
    private final JobRepository jobRepository;
    //private final Company company;
   // private final RecruiterProfile recruiterProfile;
    private final ModelMapper modelMapper;

    @Override
    public JobResponseDto createJob(@Valid JobRequestDto jobRequestDto) {
        User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        System.out.println("EMAIL: " + user.getEmail());
        System.out.println("ROLE: " + user.getRole());



        if (user.getRole() != RoleType.RECRUITER) throw new AccessDeniedException("Only Recruiters can create jobs");

        RecruiterProfile recruiterProfile =
                recruiterProfileRepository.findByUser(user)
                        .orElseThrow(() -> new RuntimeException("Recruiter profile not found"));
        Company company = recruiterProfile.getCompany();

        Job job = Job.builder()
                .title(jobRequestDto.getTitle())
                .jobType(jobRequestDto.getJobType().name())
                .description(jobRequestDto.getDescription())
                .company(company)
                .recruiter(recruiterProfile)
                .salary(jobRequestDto.getSalary())
                .location(jobRequestDto.getLocation())
                .experienceRequired(jobRequestDto.getExperienceRequired())
                .status(JobStatus.OPEN)
                .build();


        Job savedJob = jobRepository.save(job);


        //return modelMapper.map(savedJob, JobResponseDto.class);
        return JobResponseDto.builder()
                .message("Job created Successfully")
                .status(savedJob.getStatus().OPEN.name())
                .id(savedJob.getId())
                .title(savedJob.getTitle())
                .build();

    }

    @Override
    public List<JobResponseDto> getAllJobs() {
        List<Job> jobs = jobRepository.findByStatus(JobStatus.OPEN);
        return jobs.stream().map(job -> modelMapper.map(job, JobResponseDto.class)).toList();
    }

    @Override
    public JobResponseDto getJobById(Long id) {
        Job job = jobRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Job not found"));
        if (job.getStatus() == JobStatus.CLOSED || job.getStatus() == JobStatus.PAUSED) {
            throw new IllegalStateException("Not Currently open for hiring");
        }

        return modelMapper.map(job, JobResponseDto.class);
    }

    @Override
    public JobResponseDto updateJob(Long id, JobRequestDto jobRequestDto) {

        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));


        Job job = jobRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Job not found"));

        RoleType role = user.getRole();
        if (role == RoleType.ADMIN) {
            //admin can update any job
        } else if (role == RoleType.RECRUITER) {
            RecruiterProfile currentRecruiter = user.getRecruiterProfile();
            if (!job.getRecruiter().getId().equals(currentRecruiter.getId()))
                throw new DisabledException("You can update only your jobs");


        } else {
            throw new AccessDeniedException("Unauthorized access");
        }


        if (job.getStatus() != JobStatus.OPEN) throw new IllegalStateException("Cannot update this job");

//        RecruiterProfile recruiterProfile = user.getRecruiterProfile();
//
//        Company company = recruiterProfile.getCompany();

        job.setTitle(jobRequestDto.getTitle());
        job.setJobType(jobRequestDto.getJobType().name());
        job.setSalary(jobRequestDto.getSalary());
        job.setDescription((jobRequestDto.getDescription()));
        job.setLocation(jobRequestDto.getLocation());
        job.setExperienceRequired(jobRequestDto.getExperienceRequired());

        Job updated = jobRepository.save(job);


        return modelMapper.map(updated, JobResponseDto.class);
    }

    @Override
    public JobResponseDto closeJob(Long id) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User name not found"));
        Job job = jobRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Id not found"));

        RoleType role = user.getRole();
        if (role == RoleType.ADMIN) {

        } else if (role == RoleType.RECRUITER) {
            if (!job.getRecruiter().getId().equals(user.getRecruiterProfile().getId())) {
                throw new AccessDeniedException("You can only close your job openings ");
            }

        } else {
            throw new AccessDeniedException("Unauthorized access");
        }

        if (job.getStatus() != JobStatus.OPEN) {
            throw new IllegalStateException("Job is already closed");

        }


        job.setStatus(JobStatus.CLOSED);
        Job patched = jobRepository.save(job);

        return modelMapper.map(patched, JobResponseDto.class);


    }


}
