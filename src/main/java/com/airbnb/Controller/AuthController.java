package com.airbnb.Controller;

import com.airbnb.DTO.loginDTO;
import com.airbnb.Entity.AppUser;
import com.airbnb.Exception.UserExists;
import com.airbnb.Repository.AppUserRepository;
import com.airbnb.Service.ServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/app/v2/bnb")
public class AuthController {

   private AppUserRepository appUserRepository;
   private ServiceImpl serviceimpl;

    public AuthController(AppUserRepository appUserRepository, ServiceImpl serviceimpl) {
        this.appUserRepository = appUserRepository;
        this.serviceimpl = serviceimpl;
    }

    @PostMapping("/p1")
    public ResponseEntity<AppUser> CreateAppUserPost(
            @RequestBody AppUser appUser
     ){
        Optional<AppUser> email = appUserRepository.findByEmail(appUser.getEmail());
        if(email.isPresent()){
            throw new UserExists("email Already Exists");
        }
        Optional<AppUser> userName = appUserRepository.findByUsername(appUser.getUsername());
        if(userName.isPresent()){
            throw new UserExists("username Already exist");
        }
        AppUser save = serviceimpl.CreateAppUserPost(appUser);

        return new ResponseEntity<>(save, HttpStatus.CREATED);

    }

    @PostMapping("/login")
    public ResponseEntity<String> Signin(@RequestBody loginDTO logindto){
            String token=serviceimpl.verifylogin(logindto);
            if(token!=null){
                return new ResponseEntity< >(token,HttpStatus.OK);
            }else{
                return new ResponseEntity<>("invalid user name",HttpStatus.UNAUTHORIZED);
            }
    }
}
