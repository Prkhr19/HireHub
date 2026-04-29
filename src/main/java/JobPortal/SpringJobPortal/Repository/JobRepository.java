package JobPortal.SpringJobPortal.Repository;

import JobPortal.SpringJobPortal.Entity.Company;
import JobPortal.SpringJobPortal.Entity.Job;
import JobPortal.SpringJobPortal.Entity.type.JobStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface JobRepository extends JpaRepository<Job, Long>, JpaSpecificationExecutor<Job> {
public List<Job>findByStatus(JobStatus status);

boolean existsByTitleAndCompanyAndLocation(String title, Company company, String location);





}