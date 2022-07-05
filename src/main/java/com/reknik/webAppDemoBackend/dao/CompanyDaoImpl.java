package com.reknik.webAppDemoBackend.dao;

import com.reknik.webAppDemoBackend.entity.Company;
import com.reknik.webAppDemoBackend.entity.dto.CompanyDto;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.annotations.QueryHints;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public class CompanyDaoImpl implements CompanyDao {

  final SessionFactory sessionFactory;

  @Autowired
  public CompanyDaoImpl(SessionFactory sessionFactory) {
    this.sessionFactory = sessionFactory;
  }

  @Override
  public Optional<Company> getCompanyById(int id) {
    Session session = sessionFactory.getCurrentSession();
    Query<Company> query = session.createQuery("select distinct c from Company c left join fetch c.employees where c.id=:paramId", Company.class)
        .setHint(QueryHints.PASS_DISTINCT_THROUGH, false);
    query.setParameter("paramId", id);
    return Optional.ofNullable(query.getSingleResult());
  }

  @Override
  public List<Company> getCompanies() {
    Session session = sessionFactory.getCurrentSession();
    return session.createQuery("select distinct c from Company c left join fetch c.employees", Company.class)
        .setHint(QueryHints.PASS_DISTINCT_THROUGH, false).getResultList();
  }

  @Override
  public void saveCompany(Company company) {
    Session session = sessionFactory.getCurrentSession();
    session.saveOrUpdate(company);
  }

  @Override
  public void saveCompany(CompanyDto company) {
    Session session = sessionFactory.getCurrentSession();
    session.saveOrUpdate(company);
  }

  @Override
  public void deleteCompanyById(int id) {
    Session session = sessionFactory.getCurrentSession();
    Query<Company> query = session.createQuery("DELETE FROM Company where id =:paramId", Company.class);
    query.setParameter("paramId", id);
    query.executeUpdate();
  }
}
