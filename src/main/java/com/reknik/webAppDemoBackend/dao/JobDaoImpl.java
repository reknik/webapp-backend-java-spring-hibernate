package com.reknik.webAppDemoBackend.dao;

import com.reknik.webAppDemoBackend.entity.Job;
import com.reknik.webAppDemoBackend.entity.dto.JobDto;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public class JobDaoImpl implements JobDao {

  final SessionFactory sessionFactory;

  @Autowired
  public JobDaoImpl(SessionFactory sessionFactory) {
    this.sessionFactory = sessionFactory;
  }

  @Override
  public Optional<Job> getJobById(int id) {
    Session session = sessionFactory.getCurrentSession();
    Query<Job> query = session.createQuery("FROM Job where id =:paramId", Job.class);
    query.setParameter("paramId", id);
    return Optional.ofNullable(query.getSingleResult());
  }

  @Override
  public List<Job> getJobs() {
    Session session = sessionFactory.getCurrentSession();
    Query<Job> query = session.createQuery("FROM Job order by id", Job.class);
    return query.getResultList();
  }

  @Override
  public void saveJob(Job Job) {
    Session session = sessionFactory.getCurrentSession();
    session.saveOrUpdate(Job);
  }

  @Override
  public void saveJob(JobDto Job) {
    Session session = sessionFactory.getCurrentSession();
    session.saveOrUpdate(Job);
  }

  @Override
  public void deleteJobById(int id) {
    Session session = sessionFactory.getCurrentSession();
    Query<Job> query = session.createQuery("DELETE FROM Job where id =:paramId", Job.class);
    query.setParameter("paramId", id);
    query.executeUpdate();
  }
}
