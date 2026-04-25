package JobPortal.SpringJobPortal.Service.Impl;

import JobPortal.SpringJobPortal.Dto.AuthResponseDto;
import JobPortal.SpringJobPortal.Dto.LoginRequestDto;
import JobPortal.SpringJobPortal.Dto.SignUpRequestDto;

public interface AuthServices {
    AuthResponseDto signup(SignUpRequestDto signUpRequestDto);
    AuthResponseDto logIn(LoginRequestDto loginRequestDto);
}
