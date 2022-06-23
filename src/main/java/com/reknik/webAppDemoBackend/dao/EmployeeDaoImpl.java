package com.reknik.webAppDemoBackend.dao;

import com.reknik.webAppDemoBackend.entity.Employee;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.annotations.QueryHints;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public class EmployeeDaoImpl implements EmployeeDao {

  @Autowired
  SessionFactory sessionFactory;

  @Override
  public Optional<Employee> getEmployeeById(int id) {
    Session session = sessionFactory.getCurrentSession();
    Employee employee = session.createQuery("select distinct e from Employee e left join fetch e.companies where e.id =:paramId", Employee.class)
        .setParameter("paramId", id)
        .setHint(QueryHints.PASS_DISTINCT_THROUGH, false).getSingleResult();
    employee = session.createQuery("select distinct e from Employee e left join fetch e.addresses where e=:paramEmployee", Employee.class)
        .setParameter("paramEmployee", employee)
        .setHint(QueryHints.PASS_DISTINCT_THROUGH, false).getSingleResult();
    employee = session.createQuery("select distinct e from Employee e left join fetch e.jobs where e=:paramEmployee", Employee.class)
        .setParameter("paramEmployee", employee)
        .setHint(QueryHints.PASS_DISTINCT_THROUGH, false).getSingleResult();
    employee = session.createQuery("select distinct e from Employee e left join fetch e.contacts where e=:paramEmployee", Employee.class)
        .setParameter("paramEmployee", employee)
        .setHint(QueryHints.PASS_DISTINCT_THROUGH, false).getSingleResult();
    return Optional.ofNullable(employee);
  }

  @Override
  public List<Employee> getEmployees() {
    Session session = sessionFactory.getCurrentSession();
    List<Employee> employees = session.createQuery("select distinct e from Employee e left join fetch e.companies", Employee.class)
        .setHint(QueryHints.PASS_DISTINCT_THROUGH, false).getResultList();
    employees =
        session.createQuery("select distinct e from Employee e left join fetch e.jobs where e in :employee", Employee.class)
            .setParameter("employee", employees)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false).getResultList();
    employees =
        session.createQuery("select distinct e from Employee e left join fetch e.addresses where e in :employee", Employee.class)
            .setParameter("employee", employees)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false).getResultList();
    employees =
        session.createQuery("select distinct e from Employee e left join fetch e.contacts where e in :employee", Employee.class)
            .setParameter("employee", employees)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false).getResultList();
    return employees;
  }

  @Override
  public void saveEmployee(Employee employee) {
    Session session = sessionFactory.getCurrentSession();
    session.saveOrUpdate(employee);
  }

  @Override
  public void deleteEmployeeById(int id) {
    Session session = sessionFactory.getCurrentSession();
    getEmployeeById(id).ifPresent(session::delete);
  }
}
