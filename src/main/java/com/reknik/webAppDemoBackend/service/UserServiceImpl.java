package com.reknik.webAppDemoBackend.service;

import com.reknik.webAppDemoBackend.dao.UserDao;
import com.reknik.webAppDemoBackend.entity.WebAppUser;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service("userService")
@Transactional
public class UserServiceImpl implements UserService, UserDetailsService {

  private final UserDao userDao;

  private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);

  @Autowired
  public UserServiceImpl(UserDao userDao) {
    this.userDao = userDao;
  }

  @Override
  public Optional<WebAppUser> getUser(String username) {
    return userDao.getUser(username);
  }

  @Override
  public List<WebAppUser> getUsers() {
    return userDao.getUsers();
  }

  @Override
  public void saveUser(WebAppUser user) {
    user.setId(0);
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    userDao.saveUser(user);
  }

  @Override
  public void updateUser(WebAppUser user) {
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    userDao.saveUser(user);
  }

  @Override
  public void deleteUser(int userId) {
    userDao.deleteUser(userId);
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return getUser(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
  }
}
