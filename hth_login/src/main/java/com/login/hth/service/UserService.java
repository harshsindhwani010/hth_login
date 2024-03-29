package com.login.hth.service;

import com.login.hth.beans.UserLogin;
import com.login.hth.iservice.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserService implements IUserService {
    @Autowired
    UserLogin userLogin;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        String[] user = userLogin.getUserDetailUser(userName);
        if(user==null){
            throw new UsernameNotFoundException("User not found:"+ userName);
        }
        return new User(user[0].trim(), user[1].trim(), new ArrayList<>());
    }
}
