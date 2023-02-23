package com.reknik.hr.service.impl;

import com.reknik.hr.entity.WebAppUser;
import java.util.List;
import java.util.Optional;

import com.reknik.hr.repository.UserRepository;
import com.reknik.hr.service.UserService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service("userService")
@Transactional
public class UserServiceImpl implements UserService, UserDetailsService {

  private final UserRepository userRepository;

  private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);

  @Autowired
  public UserServiceImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public Optional<WebAppUser> getUser(String username) {
    return userRepository.findFirstByUsername(username);
  }

  @Override
  public List<WebAppUser> getUsers() {
    return userRepository.findAll();
  }

  @Override
  public void saveUser(WebAppUser user) {
    user.setId(0);
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    userRepository.save(user);
  }

  @Override
  public void updateUser(WebAppUser user) {
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    userRepository.save(user);
  }

  @Override
  public void deleteUser(long userId) {
    userRepository.deleteById(userId);
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return getUser(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
  }
}
