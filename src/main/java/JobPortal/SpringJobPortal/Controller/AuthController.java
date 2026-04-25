package JobPortal.SpringJobPortal.Controller;

import JobPortal.SpringJobPortal.Dto.AuthResponseDto;
import JobPortal.SpringJobPortal.Dto.LoginRequestDto;
import JobPortal.SpringJobPortal.Dto.SignUpRequestDto;
import JobPortal.SpringJobPortal.Service.AuthServiceImpl;
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

    @PostMapping("/signup")
    public ResponseEntity<AuthResponseDto> signup(@Valid @RequestBody SignUpRequestDto signUpRequestDto){
        AuthResponseDto response = authService.signup(signUpRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto>login(@Valid @RequestBody LoginRequestDto loginRequestDto){
        AuthResponseDto response = authService.logIn(loginRequestDto);

        return ResponseEntity.ok(response);
    }


}
