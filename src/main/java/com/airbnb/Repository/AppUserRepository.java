package com.airbnb.Repository;

import com.airbnb.Entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {

  Optional<AppUser> findByEmail(String email);
  Optional<AppUser> findByUsername(String username);


}