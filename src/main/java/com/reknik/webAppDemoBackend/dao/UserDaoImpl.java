package com.reknik.webAppDemoBackend.dao;

import com.reknik.webAppDemoBackend.entity.WebAppUser;
import java.util.List;
import java.util.Optional;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserDaoImpl implements UserDao {

  private final SessionFactory sessionFactory;

  @Autowired
  public UserDaoImpl(SessionFactory sessionFactory) {
    this.sessionFactory = sessionFactory;
  }

  @Override
  public Optional<WebAppUser> getUser(String username) {
    Session session = sessionFactory.getCurrentSession();
    Query<WebAppUser> query = session.createQuery("FROM WebAppUser where username =:aUsername", WebAppUser.class);
    query.setParameter("aUsername", username);
    return Optional.ofNullable(query.getSingleResult());
  }

  @Override
  public List<WebAppUser> getUsers() {
    Session session = sessionFactory.getCurrentSession();
    Query<WebAppUser> query = session.createQuery("FROM WebAppUser order by username", WebAppUser.class);
    return query.getResultList();
  }

  @Override
  public void saveUser(WebAppUser user) {
    Session session = sessionFactory.getCurrentSession();
    session.saveOrUpdate(user);
  }

  @Override
  public void deleteUser(int userId) {
    Session session = sessionFactory.getCurrentSession();
    Query<WebAppUser> query = session.createQuery("DELETE FROM WebAppUser where userID =:userId", WebAppUser.class);
    query.setParameter("userId", userId);
    query.executeUpdate();
  }
}
