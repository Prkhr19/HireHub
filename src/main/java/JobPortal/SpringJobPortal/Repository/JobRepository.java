package JobPortal.SpringJobPortal.Repository;

import JobPortal.SpringJobPortal.Dto.JobRequestDto;
import JobPortal.SpringJobPortal.Dto.JobResponseDto;
import JobPortal.SpringJobPortal.Entity.Job;
import JobPortal.SpringJobPortal.Entity.type.JobStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface JobRepository extends JpaRepository<Job, Long> {
public List<Job>findByStatus(JobStatus status);



}