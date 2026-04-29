package JobPortal.SpringJobPortal.Service;

import JobPortal.SpringJobPortal.Dto.*;
import JobPortal.SpringJobPortal.Entity.*;
import JobPortal.SpringJobPortal.Entity.type.ApplicationStatus;
import JobPortal.SpringJobPortal.Entity.type.JobStatus;
import JobPortal.SpringJobPortal.Entity.type.RoleType;
import JobPortal.SpringJobPortal.Repository.*;
import JobPortal.SpringJobPortal.Security.CurrentUserAuth.CurrentUserService;
import JobPortal.SpringJobPortal.Service.Impl.JobServices;
import JobPortal.SpringJobPortal.Service.Specifacations.JobSpecification;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.authentication.BadCredentialsException;
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
    private final CurrentUserService currentUserService;
    //private final Company company;
    // private final RecruiterProfile recruiterProfile;
    private final ModelMapper modelMapper;
    private final CompanyRepository companyRepository;
    private final SavedJobRepository savedJobRepository;
    private final CandidateProfileRepository candidateProfileRepository;

    @Transactional
    @Override
    public JobResponseDto createJob(@Valid JobRequestDto jobRequestDto) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        System.out.println("EMAIL: " + user.getEmail());
        System.out.println("ROLE: " + user.getRole());


        if (user.getRole() != RoleType.RECRUITER) throw new AccessDeniedException("Only Recruiters can create jobs");

        RecruiterProfile recruiterProfile =
                recruiterProfileRepository.findByUser(user)
                        .orElseThrow(() -> new IllegalArgumentException("Recruiter profile not found"));
        Company company = recruiterProfile.getCompany();

        if (company == null){
            throw new IllegalArgumentException("Company cannot be null");
        }



        Job job = Job.builder()
                .title(jobRequestDto.getTitle())
                .jobType(jobRequestDto.getJobType())
                .description(jobRequestDto.getDescription())
                .company(company)
                .recruiter(recruiterProfile)
                .salary(jobRequestDto.getSalary())
                .location(jobRequestDto.getLocation())
                .experienceRequired(jobRequestDto.getExperienceRequired())
                .status(JobStatus.OPEN)
                .build();


        job.setCompany(company);

        boolean exists = jobRepository.existsByTitleAndCompanyAndLocation(jobRequestDto.getTitle(),recruiterProfile.getCompany(), jobRequestDto.getLocation());

        if (exists){
            throw new IllegalArgumentException("Job already exists");
        }
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
        Job job = jobRepository.findById(id).orElseThrow(() -> new BadCredentialsException("Job not found"));
        if (job.getStatus() == JobStatus.CLOSED || job.getStatus() == JobStatus.PAUSED) {
            throw new DisabledException("Not Currently open for hiring");
        }

        return modelMapper.map(job, JobResponseDto.class);
    }

    @Transactional
    @Override
    public JobResponseDto updateJob(Long id, @Valid JobRequestDto jobRequestDto) {
        System.out.println("Entered updateJob");

        // User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = currentUserService.getCurrentUser();
        System.out.println("2 Auth fetched");

//        User user = userRepository.findByEmail(auth.getName()).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        //User user = userRepository.findByEmail(email).orElseThrow(()->new EntityNotFoundException("user name not found"));
        System.out.println("User fetched");
        Job job = jobRepository.findById(id).orElseThrow(() -> new BadCredentialsException("not found"));

        //Job job = jobRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Job not found"));
//        System.out.println("Job fetched");


        RoleType role = user.getRole();
        System.out.println(user.getRole());

        if (role == RoleType.ADMIN) {
            //admin can update any job
        } else if (role == RoleType.RECRUITER) {
            RecruiterProfile currentRecruiter = recruiterProfileRepository.findByUserUserId(user.getUserId()).orElseThrow(() -> new BadCredentialsException("User not exist"));
            System.out.println(currentRecruiter.getId());

            System.out.println(currentRecruiter.getId());

            System.out.println(job.getRecruiter().getId());

            if (!job.getRecruiter().getId().equals(currentRecruiter.getId()))
                throw new DisabledException("You can update only your jobs");


        } else {
            throw new AccessDeniedException("Unauthorized access");
        }

        System.out.println(job.getStatus());

        if (job.getStatus() != JobStatus.OPEN) throw new IllegalArgumentException("Cannot update this job");

//        RecruiterProfile recruiterProfile = user.getRecruiterProfile();
//
//        Company company = recruiterProfile.getCompany();

        job.setTitle(jobRequestDto.getTitle());
        job.setJobType(jobRequestDto.getJobType());
        job.setSalary(jobRequestDto.getSalary());
        job.setDescription((jobRequestDto.getDescription()));
        job.setLocation(jobRequestDto.getLocation());
        job.setExperienceRequired(jobRequestDto.getExperienceRequired());

        Job updated = jobRepository.save(job);


        return JobResponseDto.builder()
                .message("Job updated successfully")
                .status(JobStatus.OPEN.name())
                .id(updated.getId())
                .title(updated.getTitle())
                .build();
    }

    @Transactional
    @Override
    public JobStatusResponseDto updateJobStatus(Long id, JobStatusRequestDto jobStatusRequestDto) {

        User user = currentUserService.getCurrentUser();

        Job job = jobRepository.findById(id).orElseThrow(() -> new BadCredentialsException("Id not found"));

        RoleType role = user.getRole();
        if (role == RoleType.ADMIN) {

        } else if (role == RoleType.RECRUITER) {
            if (!job.getRecruiter().getId().equals(user.getRecruiterProfile().getId())) {
                throw new AccessDeniedException("You can only close your job openings ");
            }

        } else {
            throw new AccessDeniedException("Unauthorized access");
        }


        job.setStatus(jobStatusRequestDto.getStatus());

        jobRepository.save(job);

        return JobStatusResponseDto.builder()
                .message("Update Successfully")
                .status(jobStatusRequestDto.getStatus())
                .build();


    }

    @Transactional
    @Override
    public JobPatchResponseDto patchJob(Long id, JobPatchRequestDto jobPatchRequestDto) {

        User user = currentUserService.getCurrentUser();

        Job job = jobRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("User name not found"));

        ApplicationStatus status1 = ApplicationStatus.APPLIED;

//        if (status1 == ApplicationStatus.APPLIED){
//            throw new AccessDeniedException("Candidated already applied for this job contact admin");
//        }

        RoleType role = user.getRole();

        if (!(role == RoleType.ADMIN || role == RoleType.RECRUITER)) {

            throw new AccessDeniedException("Unauthorized");

        } else if (role == RoleType.RECRUITER || role == RoleType.ADMIN) {

            if (!user.getRecruiterProfile().getId().equals(job.getRecruiter().getId())) {
                throw new BadCredentialsException("You can update your own job");
            }


        } else {
            throw new AccessDeniedException("Unauthorized access");
        }

        JobStatus status = job.getStatus();

        if (status != JobStatus.OPEN) {
            throw new DisabledException("Job is currently not open");
        }

        job.setSalary(jobPatchRequestDto.getSalary());

        Job patched = jobRepository.save(job);

        return JobPatchResponseDto.builder()
                .updatedSalary(patched.getSalary())
                .message("Salary updated")
                .build();
    }

    @Override
    public Page<JobSearchResponseDto> searchJobs(JobSearchRequestDto jobSearchRequestDto) {
        int page = jobSearchRequestDto.getPage() > 0 ? jobSearchRequestDto.getPage() : 0;
        int size = jobSearchRequestDto.getSize()> 0 ? jobSearchRequestDto.getSize() : 10;

        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by("postedAt").descending()
        );

        Specification<Job> spec = (root, query, criteriaBuilder) -> criteriaBuilder.conjunction();

        spec = spec.and(JobSpecification.hasStatus(JobStatus.OPEN));

        if (jobSearchRequestDto.getKeyword() != null && !jobSearchRequestDto.getKeyword().isBlank()){
            spec = spec.and(JobSpecification.hasKeyword(jobSearchRequestDto.getKeyword()));
        }

        if (jobSearchRequestDto.getJobType() != null ){
            spec = spec.and((JobSpecification.hasJobType(jobSearchRequestDto.getJobType())));
        }

        if ( jobSearchRequestDto.getCompany() != null &&  ! jobSearchRequestDto.getCompany().isBlank()){
            spec = spec.and(JobSpecification.hasCompany(jobSearchRequestDto.getCompany()));

        }

        if (jobSearchRequestDto.getLocation() != null && !jobSearchRequestDto.getLocation().isBlank()){
            spec = spec.and(JobSpecification.hasLocation(jobSearchRequestDto.getLocation()));
        }

        Page<Job> jobPage = jobRepository.findAll(spec, pageable);

       return jobPage.map(job -> JobSearchResponseDto.builder()
                .id(job.getId())
                .title(job.getTitle())
                .companyName(job.getCompany().getCompanyName())
                .postedAt(job.getPostedAt())
                .salary(job.getSalary())
                .jobtype(job.getJobType())
                .build());

    }

    @Transactional
    @Override
    public String saveJob(Long jobId) {

        User user = currentUserService.getCurrentUser();

        RoleType role = user.getRole();

        CandidateProfile candidate = candidateProfileRepository.findByUserUserId(user.getUserId()).orElseThrow(()-> new BadCredentialsException("User not found"));

        Job job = jobRepository.findById(jobId).orElseThrow(()-> new IllegalArgumentException("Job not found"));
        boolean exists = savedJobRepository.existsByCandidatesAndJob(candidate, job );

        if (exists){
            throw new IllegalArgumentException("Job already saved");
        }

        SavedJob savedJob = SavedJob.builder()
                .candidates(candidate)
                .job(job)
                .build();

        savedJobRepository.save(savedJob);

        return "Job successfully saved";
    }


}
