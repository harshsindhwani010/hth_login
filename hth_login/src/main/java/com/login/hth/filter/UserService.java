package com.login.hth.filter;

import com.login.hth.beans.UserLogin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    UserLogin userLogin;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        String[] user = userLogin.getUserDetail(userName);
        return new User(user[0].trim(), user[1].trim(), new ArrayList<>());
    }
}
