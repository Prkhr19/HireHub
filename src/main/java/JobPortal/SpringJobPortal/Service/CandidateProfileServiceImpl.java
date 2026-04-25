package JobPortal.SpringJobPortal.Service;

import JobPortal.SpringJobPortal.Dto.CandidateProfileReqDto;
import JobPortal.SpringJobPortal.Entity.CandidateProfile;
import JobPortal.SpringJobPortal.Entity.User;
import JobPortal.SpringJobPortal.Repository.CandidateProfileRepository;
import JobPortal.SpringJobPortal.Security.CurrentUserAuth.CurrentUserService;
import JobPortal.SpringJobPortal.Service.Impl.CandidateProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CandidateProfileServiceImpl implements CandidateProfileService {
    private final CandidateProfileRepository candidateProfileRepository;
    private final CurrentUserService currentUserService;


    @Override
    public CandidateProfileReqDto updateProfile(CandidateProfileReqDto candidateProfileReqDto) {



        User user = currentUserService.getCurrentUser();
        CandidateProfile candidateProfile = candidateProfileRepository.findByUserUserId(user.getUserId()).orElseThrow(() -> new UsernameNotFoundException("User not found"));

        candidateProfile.setUser(candidateProfile.getUser());
        candidateProfile.setName(candidateProfileReqDto.getName());
        candidateProfile.setPhoneNo(candidateProfileReqDto.getPhoneNo());
        candidateProfile.setSkills(candidateProfileReqDto.getSkills());
        candidateProfile.setLocation(candidateProfileReqDto.getLocation());
        candidateProfile.setExperience(candidateProfileReqDto.getExperience());
        candidateProfile.setEducation(candidateProfileReqDto.getEducation());
        candidateProfile.setResumeUrl(candidateProfileReqDto.getResumeUrl());

        candidateProfileRepository.save(candidateProfile);


        return CandidateProfileReqDto.builder()
                .name(candidateProfile.getName())
                .phoneNo(candidateProfile.getPhoneNo())
                .resumeUrl(candidateProfile.getResumeUrl())
                .location(candidateProfile.getLocation())
                .experience(candidateProfile.getExperience())
                .skills(candidateProfile.getSkills())
                .education(candidateProfile.getEducation())
                .meessage("Profile Updated Successfully")
                .id(candidateProfile.getId())
                .build();
    }


    }

