package com.airbnb.Service;

import com.airbnb.DTO.loginDTO;
import com.airbnb.Entity.AppUser;
import com.airbnb.Repository.AppUserRepository;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;

@Service
public class ServiceImpl {

    private AppUserRepository appUserRepository;

    private JWTService jwtService;



    public ServiceImpl(AppUserRepository appUserRepository,JWTService jwtService) {
        this.jwtService = jwtService;
        this.appUserRepository = appUserRepository;
    }
    public AppUser CreateAppUserPost( AppUser appUser){

        String hashpw = BCrypt.hashpw(appUser.getPassword(), BCrypt.gensalt(10));
        appUser.setPassword(hashpw);
        AppUser save = appUserRepository.save(appUser);
        return save;


    }


    public String verifylogin(loginDTO logindto) {
        Optional<AppUser> Username = appUserRepository.findByUsername(logindto.getUsername());
        if (Username.isPresent()){
            AppUser appUser=Username.get();
            if ( BCrypt.checkpw(logindto.getPassword(), appUser.getPassword())) {


                return   jwtService.generateToken(appUser);
            }
        }
        return null;

    }
}

