package JobPortal.SpringJobPortal.Service.Impl;

import JobPortal.SpringJobPortal.Dto.CandidateApplicationResposeDto;
import JobPortal.SpringJobPortal.Dto.CandidateProfileReqDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CandidateProfileService {

    public CandidateProfileReqDto updateProfile(CandidateProfileReqDto candidateProfileReqDto);

    List<CandidateApplicationResposeDto> myApplication();
}