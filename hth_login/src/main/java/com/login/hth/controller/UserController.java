package com.login.hth.controller;

import ch.qos.logback.core.encoder.EchoEncoder;
import com.login.hth.security.JWTUtility;
import com.login.hth.beans.*;
import com.login.hth.dto.*;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class UserController {
    @Autowired
    UserLogin userLogin;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    UserVerify userValidation;
    @Autowired
    JWTUtility jwtUtility;
    @Autowired
    SendEmail sendEmail;
    @Autowired
    OtpValidate otpValidate;
    @Autowired
    SignupUser signupUser;

    @PostMapping("/userLogin")
    public ResponseEntity<Object> userLogin(@RequestBody UserDTO userDTO) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            userDTO.getEmail(),
                            userDTO.getPassword()
                    )
            );
        } catch (BadCredentialsException e) {
            MessageDTO er = new MessageDTO();
            er.setMessage("INVALID_CREDENTIALS.");
            return new ResponseEntity<>(er, HttpStatus.BAD_REQUEST);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return userLogin.checkUser(userDTO);
    }

    @PostMapping("/otpValidate")
    public ResponseEntity<Object> otpValidate(@RequestBody OtpValidateDTO otpValidateDTO) {
        return otpValidate.otpValidate(otpValidateDTO);
    }

    @PostMapping("/validation")
    public ResponseEntity<Object> userValidation(@RequestBody UserVerifyDTO userValidationDTO) {
        return userValidation.checkValidation(userValidationDTO);
    }

    @PostMapping("/signup")
    public ResponseEntity<Object> signupUser(@RequestBody SignupRequestDTO signupRequestDTO) {
        List<String[]> userExist = signupUser.checkUserName(signupRequestDTO);
        if (userExist.size() > 0) {
            return new ResponseEntity<>("User Already Registered", HttpStatus.BAD_REQUEST);
        } else if (!signupRequestDTO.getPassword().equals(signupRequestDTO.getConfirmPassword())) {
            return new ResponseEntity<>("Password Mismatched.", HttpStatus.BAD_REQUEST);
        } else {
            signupUser.insertUserDetails(signupRequestDTO);
            return new ResponseEntity<>("Success", HttpStatus.OK);
        }
    }

    @GetMapping("/forgetPassword/{email}")
    public ResponseEntity<Object> sendEmail(@PathVariable("email") String email) {
        return sendEmail.checkUser(email);
    }

    @PostMapping("/forgetPassword")
    public ResponseEntity<Object> forgetPassword(@RequestBody ForgetPasswordDTO forgetPasswordDTO, @RequestHeader("Authorization") String bearerToken) {
        bearerToken = bearerToken.substring(7, bearerToken.length());
        Claims claims = jwtUtility.getAllClaimsFromToken(bearerToken);
        String email = claims.get("email").toString();
        if (userLogin.getUserDetail(email).length > 0) {
            if (!forgetPasswordDTO.getNewPassword().equals(forgetPasswordDTO.getConfirmPassword())) {
                return new ResponseEntity<>("Password not Matched", HttpStatus.BAD_REQUEST);
            } else {
                return sendEmail.updatePassword(forgetPasswordDTO.getNewPassword(), email);
            }
        } else {
            return new ResponseEntity<>("Bad Request", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/changePassword")
    public ResponseEntity<Object> changePassword(@RequestBody ChangePasswordDTO changePasswordDTO, @RequestHeader("Authorization") String bearerToken) {
        MessageDTO er = new MessageDTO();
        bearerToken = bearerToken.substring(7, bearerToken.length());
        Claims claims = jwtUtility.getAllClaimsFromToken(bearerToken);
        String email = claims.get("email").toString();
        if (userLogin.getUserDetail(email).length > 0) {
            if (!changePasswordDTO.getNewPassword().equals(changePasswordDTO.getConfirmPassword())) {
                er.setMessage("Password not Matched");
                return new ResponseEntity<>(er, HttpStatus.BAD_REQUEST);
            } else {
                return sendEmail.changePassword(changePasswordDTO, email);
            }
        } else {
            return new ResponseEntity<>("Bad Request", HttpStatus.BAD_REQUEST);
        }
    }
}
