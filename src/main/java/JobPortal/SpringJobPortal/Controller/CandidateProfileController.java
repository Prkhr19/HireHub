package JobPortal.SpringJobPortal.Controller;

import JobPortal.SpringJobPortal.Dto.CandidateApplicationResposeDto;
import JobPortal.SpringJobPortal.Dto.CandidateProfileReqDto;
import JobPortal.SpringJobPortal.Service.Impl.CandidateProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/candidate")
public class CandidateProfileController {
    private final CandidateProfileService candidateProfileService;

    @PutMapping("/profile")
    public ResponseEntity<CandidateProfileReqDto> updateProfile(@RequestBody CandidateProfileReqDto candidateProfileReqDto){


        return ResponseEntity.ok(candidateProfileService.updateProfile(candidateProfileReqDto));
    }

    @GetMapping("/applicationStatus")
    public ResponseEntity<List<CandidateApplicationResposeDto>> myApplications(){

        List<CandidateApplicationResposeDto> response = candidateProfileService.myApplication();

        return ResponseEntity.ok(response);

    }

}
