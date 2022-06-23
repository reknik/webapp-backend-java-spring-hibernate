package com.reknik.webAppDemoBackend.dao;

import com.reknik.webAppDemoBackend.entity.WebAppUser;
import java.util.List;
import java.util.Optional;

public interface UserDao {
  Optional<WebAppUser> getUser(String username);

  List<WebAppUser> getUsers();

  void saveUser(WebAppUser user);

  void deleteUser(int userId);
}
