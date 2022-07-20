//package ru.mis2022.service.entity.impl;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//import ru.mis2022.models.entity.User;
//import ru.mis2022.repositories.UserRepository;
//import ru.mis2022.service.entity.UserService;
//
//@Service
//public class UserDetailsServiceImpl implements UserDetailsService, UserService {
//  @Autowired
//  UserRepository userRepository;
//
//  @Autowired
//  UserServiceImpl userService;
//
//  @Override
//  @Transactional
//  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//    User user = userRepository.findByEmail(email);
//    return UserDetailsImpl.build(user);
//  }
//
//}
