package JobPortal.SpringJobPortal.Service.Impl;

import JobPortal.SpringJobPortal.Dto.CompanyRequestDto;
import JobPortal.SpringJobPortal.Dto.CompanyResponseDto;
import org.springframework.stereotype.Service;

@Service
public interface CompanyService {
    CompanyResponseDto createCompany(CompanyRequestDto companyRequestDto);

}
