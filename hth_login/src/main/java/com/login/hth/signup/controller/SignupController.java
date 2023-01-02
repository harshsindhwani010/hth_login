package com.login.hth.signup.controller;

import com.login.hth.beans.SignupUser;
import com.login.hth.dto.SignupRequestDTO;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class SignupController {
    @Autowired
    SignupUser signupUser;

    @GetMapping("/test")
    public void getUser() {
        String[] data = signupUser.getAllProfile();
        Object obj= JSONValue.parse(data[1].trim());
        JSONObject jsonObject = (JSONObject) obj;
        String first = jsonObject.get("firstName").toString();
        String last = jsonObject.get("lastName").toString();
        System.out.println(first+":");
        System.out.println(last+":");
    }

    @PostMapping("/signup")
    public ResponseEntity<Object> signupUser(@RequestBody SignupRequestDTO signupRequestDTO) {
        String[] userExist = signupUser.checkUserName(signupRequestDTO.getUserName());
        if (userExist != null) {
            return new ResponseEntity<>("Invalid Username", HttpStatus.BAD_REQUEST);
        } else if (!signupRequestDTO.getPassword().equals(signupRequestDTO.getConfirmPassword())) {
            return new ResponseEntity<>("Password not matched.", HttpStatus.BAD_REQUEST);
        } else {
            signupUser.insertUserDetails(signupRequestDTO);
            return new ResponseEntity<>("", HttpStatus.BAD_REQUEST);
        }
    }
}
