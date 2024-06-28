package com.indusnet.ums.service.serviceImpl;


import com.indusnet.ums.exception.CustomNotFoundException;
import com.indusnet.ums.model.UserModel;
import com.indusnet.ums.repository.IUserServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    IUserServiceRepository repository;

    @Override
    public UserDetails loadUserByUsername(String email) throws CustomNotFoundException {
        Optional<UserModel> userModelOptional = repository.findByEmail(email);
        UserModel userModel = userModelOptional.orElseThrow(() -> new CustomNotFoundException("User not found with email: " + email));
        return userModel;
    }
}


