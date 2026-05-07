package JobPortal.SpringJobPortal.Service;

import JobPortal.SpringJobPortal.Dto.AuthResponseDto;
import JobPortal.SpringJobPortal.Dto.LoginRequestDto;
import JobPortal.SpringJobPortal.Dto.SignUpRequestDto;
import JobPortal.SpringJobPortal.Dto.SignupResponseDto;
import JobPortal.SpringJobPortal.Entity.CandidateProfile;
import JobPortal.SpringJobPortal.Entity.RecruiterProfile;
import JobPortal.SpringJobPortal.Entity.User;
import JobPortal.SpringJobPortal.Entity.type.RoleType;
import JobPortal.SpringJobPortal.Repository.CandidateProfileRepository;
import JobPortal.SpringJobPortal.Repository.RecruiterProfileRepository;
import JobPortal.SpringJobPortal.Repository.UserRepository;
import JobPortal.SpringJobPortal.Security.JwtService;
import JobPortal.SpringJobPortal.Service.Impl.AuthServices;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthServices {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;
    private final JwtService jwtService;
    private final RecruiterProfileRepository recruiterProfileRepository;
    private final CandidateProfileRepository candidateProfileRepository;


    @Override
    public SignupResponseDto signup(SignUpRequestDto signUpRequestDto) {

        if (userRepository.existsByEmail(signUpRequestDto.getEmail())) {
            throw new IllegalArgumentException("User with this email already exists.");
        }

        User user = User.builder()
                .name(signUpRequestDto.getName())
                .email(signUpRequestDto.getEmail())
                .password(passwordEncoder.encode(signUpRequestDto.getPassword()))
                .role(signUpRequestDto.getRole())
                .isActive(true)
                .build();

        User savedUser = userRepository.save(user);

        if (savedUser.getRole() == RoleType.RECRUITER) {
            RecruiterProfile recruiterProfile = new RecruiterProfile();

            recruiterProfile.setFullName(savedUser.getName());
            recruiterProfile.setDesignation("Recruiter");
            recruiterProfile.setUser(savedUser);

            recruiterProfileRepository.save(recruiterProfile);

        }
        if (savedUser.getRole() == RoleType.CANDIDATE) {
            CandidateProfile candidateProfile = new CandidateProfile();
            candidateProfile.setUser(savedUser);
            savedUser.setCandidateProfile(candidateProfile);
            candidateProfile.setName(savedUser.getName());

            candidateProfileRepository.save(candidateProfile);

        }


        return SignupResponseDto.builder()
                .message("User registered successfully")
                .role(savedUser.getRole())
                .build();
    }

    @Override
    public AuthResponseDto logIn(LoginRequestDto loginRequestDto) {
        User user = userRepository.findByEmail(loginRequestDto.getEmail()).orElseThrow(() -> new BadCredentialsException("Invalid Credentials"));

        if (!user.getIsActive()) {
            throw new DisabledException("Invalid Credentials");
        }

        if (!passwordEncoder.matches(loginRequestDto.getPassword(), user.getPassword())) {
            throw new BadCredentialsException("Invalid Credentials");
        }

        String token = jwtService.generateToken(user);

        return AuthResponseDto.builder()
                .message("Login successfull")
                .token(token)
                .role(user.getRole().name())
                .build();
    }
}
