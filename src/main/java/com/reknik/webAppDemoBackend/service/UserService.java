package com.reknik.webAppDemoBackend.service;

import com.reknik.webAppDemoBackend.entity.WebAppUser;
import java.util.List;
import java.util.Optional;

public interface UserService {
  Optional<WebAppUser> getUser(String username);

  List<WebAppUser> getUsers();

  void saveUser(WebAppUser user);

  void updateUser(WebAppUser user);

  void deleteUser(int userId);
}
