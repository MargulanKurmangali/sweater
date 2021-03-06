package com.example.sweater.service;

import com.example.sweater.domain.Role;
import com.example.sweater.domain.User;
import com.example.sweater.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service

public class UserService implements UserDetailsService {
    @Autowired
    private UserRepo userRepo;

    @Autowired
    RegistrationService registrationService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByUsername(username);
        if(Objects.isNull(user)) {
            throw new UsernameNotFoundException("User not found");
        }

        return user;
    }

    public void saveUser(String username, Map<String, String> form, User user) {
        user.setUsername(username);
        Set<String> roles = Arrays.stream(Role.values()).map(Role::name).collect(Collectors.toSet());
        user.getRoles().clear();

        for (String key : form.keySet()) {
            if (roles.contains(key)) {
                user.getRoles().add(Role.valueOf(key));
            }
        }

        userRepo.save(user);
    }

    public void editUserForm(User user, Model model) {
        model.addAttribute("user", user);
        model.addAttribute("roles", Role.values());
    }

    public void getUserList(Model model) {
        model.addAttribute("users", userRepo.findAll());
    }

    public boolean activateUser(String code) {
        User user = userRepo.findByActivationCode(code);
        if (Objects.nonNull(user)) {
            user.setActivationCode(null);
            user.setActive(true);
            userRepo.save(user);

            return true;
        }

        return false;
    }

    public void editProfile(User user, String password, String email) {
        String userEmail = user.getEmail();
        String currentPassword = user.getPassword();
        boolean isProfileChanged = (userEmail != null && !userEmail.equals(email)
                || currentPassword != null && !currentPassword.equals(password));
        if (isProfileChanged) {
            user.setEmail(email);
            if (!StringUtils.isEmpty(email)) {
                user.setActivationCode(UUID.randomUUID().toString());
            }
        }

        if (!StringUtils.isEmpty(password)) {
            user.setPassword(password);
        }
        if (isProfileChanged) {
            registrationService.sendActivationMessage(user);
            user.setActive(false);
        }

        userRepo.save(user);
    }
}