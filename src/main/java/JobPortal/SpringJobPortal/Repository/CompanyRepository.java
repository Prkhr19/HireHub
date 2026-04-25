package JobPortal.SpringJobPortal.Repository;

import JobPortal.SpringJobPortal.Entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, Long> {
}