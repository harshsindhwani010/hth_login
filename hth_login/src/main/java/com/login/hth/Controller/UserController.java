package com.login.hth.Controller;

import com.login.hth.Security.JWTUtility;
import com.login.hth.beans.*;
import com.login.hth.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class UserController {
    @Autowired
    private UserLogin userLogin;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserVerify userValidation;
    @Autowired
    JWTUtility jwtUtility;

    @Autowired
    private CreateAccount createAccount;

    @PostMapping("/userLogin")
    public ResponseEntity<Object> userLogin(@RequestBody UserDTO userDTO) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            userDTO.getName(),
                            userDTO.getPassword()
                    )
            );
        } catch (BadCredentialsException e) {
            ErrorResponseDTO er = new ErrorResponseDTO();
            er.setMessage("INVALID_CREDENTIALS.");
            //er.setError(String.valueOf(401));
            return new ResponseEntity<>(er, HttpStatus.BAD_REQUEST);
        }
        return userLogin.checkUser(userDTO);
    }

    @GetMapping("/validation")
    public ResponseEntity<Object> userValidation(@RequestBody UserVerifyDTO userValidationDTO){
        return userValidation.checkValidation(userValidationDTO);
    }

    @GetMapping("/forgetPassword/{email}")
    public ResponseEntity<Object> sendEmail(@PathVariable("email") String email) {
        return SendEmail.checkUser(email);
    }

    @PostMapping("/otpValidate")
    public ResponseEntity<Object> otpValidate(@RequestBody OtpValidateDTO otpValidateDTO) {
        return OtpValidate.otpValidate(otpValidateDTO);
    }

    @PostMapping("/createUser")
    public ResponseEntity<Object> CreateUser(@RequestBody CreateAccountDTO createAccountDTO) {
        return CreateAccount.createUser(createAccountDTO);
    }

//    @PostMapping("/securityQuestion")
//    public ResponseEntity<Object> securityQuestion(@RequestBody SecurityQuestionDTO securityQuestion) {
//        return SecurityQuestion.addQuestion(securityQuestion);
//    }

}
