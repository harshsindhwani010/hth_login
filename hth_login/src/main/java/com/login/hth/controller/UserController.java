package com.login.hth.controller;

import com.login.hth.security.JWTUtility;
import com.login.hth.beans.*;
import com.login.hth.dto.*;
import io.jsonwebtoken.Claims;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
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
    ChangePassword changePassword;
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
        return SendEmail.checkUser(email);
    }

    @PostMapping("/forgetPassword")
    public ResponseEntity<Object> forgetPassword(@RequestBody ForgetPasswordDTO forgetPasswordDTO, @RequestHeader("Authorization") String bearerToken) {
        bearerToken = bearerToken.substring(7, bearerToken.length());
        Claims claims = jwtUtility.getAllClaimsFromToken(bearerToken);
        String email = claims.get("email").toString();
        if (changePassword.checkUser(email).equalsIgnoreCase("success")) {
            if (!forgetPasswordDTO.getNewPassword().equals(forgetPasswordDTO.getConfirmPassword())) {
                return new ResponseEntity<>("Password not Matched", HttpStatus.BAD_REQUEST);
            } else {
                return sendEmail.updatePassword(forgetPasswordDTO.getNewPassword(), email);
            }
        } else {
            return new ResponseEntity<>("Bad Request", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/changePassword")
    public ResponseEntity<Object> changePassword(@RequestBody ChangePasswordDTO changePasswordDTO, @RequestHeader("Authorization") String bearerToken) {
        MessageDTO er = new MessageDTO();
        bearerToken = bearerToken.substring(7, bearerToken.length());
        Claims claims = jwtUtility.getAllClaimsFromToken(bearerToken);
        String email = claims.get("email").toString();
        if (changePassword.checkUser(email).equalsIgnoreCase("success")) {
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

    @PostMapping("/createUser")
    public ResponseEntity<Object> CreateUser(@RequestBody CreateAccountDTO createAccountDTO) {
        return CreateAccount.createUser(createAccountDTO);
    }

    @GetMapping("/test")
    public void getUser() {
        String[] data = signupUser.getAllProfile();
        Object obj = JSONValue.parse(data[1].trim());
        JSONObject jsonObject = (JSONObject) obj;
        String first = jsonObject.get("firstName").toString();
        String last = jsonObject.get("lastName").toString();
        System.out.println(first + ":");
        System.out.println(last + ":");
    }

}
