package JobPortal.SpringJobPortal.Service;

import JobPortal.SpringJobPortal.Dto.CompanyRequestDto;
import JobPortal.SpringJobPortal.Dto.CompanyResponseDto;
import JobPortal.SpringJobPortal.Entity.Company;
import JobPortal.SpringJobPortal.Entity.RecruiterProfile;
import JobPortal.SpringJobPortal.Entity.User;
import JobPortal.SpringJobPortal.Entity.type.RoleType;
import JobPortal.SpringJobPortal.Repository.CompanyRepository;
import JobPortal.SpringJobPortal.Repository.RecruiterProfileRepository;
import JobPortal.SpringJobPortal.Security.CurrentUserAuth.CurrentUserService;
import JobPortal.SpringJobPortal.Service.Impl.CompanyService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {
    private final CurrentUserService currentUserService;
    private final RecruiterProfileRepository recruiterProfileRepository;
    private final CompanyRepository companyRepository;

    @Transactional
    @Override
    public CompanyResponseDto createCompany(CompanyRequestDto companyRequestDto) {

        User user = currentUserService.getCurrentUser();
        RoleType role = user.getRole();

       if (role != RoleType.RECRUITER){
           throw new AccessDeniedException("Unauthorized access");
       }

       RecruiterProfile recruiterProfile = recruiterProfileRepository.findByUser(user).orElseThrow(()-> new BadCredentialsException("User not found"));


       if (recruiterProfile.getCompany() != null){
           throw new IllegalArgumentException("Company already present");
       }

        Company company = new Company();
        company.setCompanyName(companyRequestDto.getCompanyName());
        company.setDiscription(companyRequestDto.getDescription());
        company.setLocation(companyRequestDto.getLocation());
        company.setWebSite(companyRequestDto.getWebsite());

       Company savedCompany = companyRepository.save(company);

        recruiterProfile.setCompany(savedCompany);

        recruiterProfileRepository.save(recruiterProfile);


        return CompanyResponseDto.builder()
                .message("Commpany created successfully")
                .companyName(companyRequestDto.getCompanyName())
                .description(companyRequestDto.getDescription())
                .build();
    }
}
