package JobPortal.SpringJobPortal.Controller;

import JobPortal.SpringJobPortal.Dto.AuthResponseDto;
import JobPortal.SpringJobPortal.Dto.LoginRequestDto;
import JobPortal.SpringJobPortal.Dto.SignUpRequestDto;
import JobPortal.SpringJobPortal.Dto.SignupResponseDto;
import JobPortal.SpringJobPortal.Service.AuthServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final AuthServiceImpl authService;


    @Operation(summary = "SignUp authentication")
    @PostMapping("/signup")
    public ResponseEntity<SignupResponseDto> signup(@Valid @RequestBody SignUpRequestDto signUpRequestDto){
        SignupResponseDto response = authService.signup(signUpRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "Login Authentication")
    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto>login(@Valid @RequestBody LoginRequestDto loginRequestDto){
        AuthResponseDto response = authService.logIn(loginRequestDto);

        return ResponseEntity.ok(response);
    }


}
