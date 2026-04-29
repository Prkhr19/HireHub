package JobPortal.SpringJobPortal.Repository;

import JobPortal.SpringJobPortal.Entity.CandidateProfile;
import JobPortal.SpringJobPortal.Entity.Job;
import JobPortal.SpringJobPortal.Entity.SavedJob;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SavedJobRepository extends JpaRepository<SavedJob, Long> {

    public boolean existsByCandidatesAndJob(CandidateProfile candidate, Job Job);
}