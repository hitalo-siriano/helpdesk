package com.api.sistema.helpdesk.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import com.api.sistema.helpdesk.models.UserModel;
import java.util.List;


public interface UserRepository extends JpaRepository<UserModel, UUID> {

   UserDetails findByLogin(String login);


}
