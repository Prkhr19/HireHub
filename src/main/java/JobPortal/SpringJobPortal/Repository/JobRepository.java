package JobPortal.SpringJobPortal.Repository;

import JobPortal.SpringJobPortal.Entity.Job;
import JobPortal.SpringJobPortal.Entity.type.JobStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JobRepository extends JpaRepository<Job, Long> {
public List<Job>findByStatus(JobStatus status);

//Boolean findByJobIdAndRercruiterId(Long jobId, Long profileId);



}