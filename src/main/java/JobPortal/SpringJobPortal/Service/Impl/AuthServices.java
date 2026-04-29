package JobPortal.SpringJobPortal.Service.Impl;

import JobPortal.SpringJobPortal.Dto.AuthResponseDto;
import JobPortal.SpringJobPortal.Dto.LoginRequestDto;
import JobPortal.SpringJobPortal.Dto.SignUpRequestDto;
import JobPortal.SpringJobPortal.Dto.SignupResponseDto;

public interface AuthServices {
    SignupResponseDto signup(SignUpRequestDto signUpRequestDto);
    AuthResponseDto logIn(LoginRequestDto loginRequestDto);
}
