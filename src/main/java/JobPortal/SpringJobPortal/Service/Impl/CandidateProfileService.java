package JobPortal.SpringJobPortal.Service.Impl;

import JobPortal.SpringJobPortal.Dto.CandidateProfileReqDto;
import JobPortal.SpringJobPortal.Dto.JobApplicationRequestDto;
import org.springframework.stereotype.Service;

@Service
public interface CandidateProfileService {

    public CandidateProfileReqDto updateProfile(CandidateProfileReqDto candidateProfileReqDto);
}