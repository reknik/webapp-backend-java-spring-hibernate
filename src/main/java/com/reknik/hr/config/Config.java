package com.reknik.hr.config;

import com.reknik.hr.entity.*;
import com.reknik.hr.entity.dto.EmployeeDTO;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.security.InvalidParameterException;

@Configuration
@PropertySource("classpath:persistence.properties")
@PropertySource("classpath:application.yaml")
@EnableTransactionManagement
@Slf4j
public class Config {

  private final Environment env;

  public Config(Environment env) {
    this.env = env;
  }

  // define a bean for ViewResolver

  @Bean
  public DataSource myDataSource() {

    // create connection pool
    HikariDataSource dataSource = new HikariDataSource();

    // set the jdbc driver
    dataSource.setDriverClassName(env.getProperty("jdbc.driver"));

    // for sanity's sake, let's log url and user ... just to make sure we are reading the data
    log.info("jdbc.url=" + env.getProperty("jdbc.url"));
    log.info("jdbc.user=" + env.getProperty("jdbc.user"));

    // set database connection props
    dataSource.setJdbcUrl(env.getProperty("jdbc.url"));
    dataSource.setUsername(env.getProperty("jdbc.user"));
    dataSource.setPassword(env.getProperty("jdbc.password"));

    // set connection pool props
    dataSource.setMinimumIdle(getIntProperty("connection.pool.minPoolSize"));
    dataSource.setMaximumPoolSize(getIntProperty("connection.pool.maxPoolSize"));
    dataSource.setMaxLifetime(getIntProperty("connection.pool.maxIdleTime"));

    return dataSource;
  }

  @Bean
  public ModelMapper modelMapper() {
    ModelMapper modelMapper = new ModelMapper();
    TypeMap<Employee, EmployeeDTO> employeeMapper = modelMapper.createTypeMap(Employee.class, EmployeeDTO.class);
    employeeMapper.addMapping(employee -> employee.getAddresses().stream().map(Address::getId).toList(),
        EmployeeDTO::setAddresses);
    employeeMapper.addMapping(employee -> employee.getCompanies().stream().map(Company::getId).toList(),
        EmployeeDTO::setCompanies);
    employeeMapper.addMapping(employee -> employee.getContacts().stream().map(Contact::getId).toList(),
        EmployeeDTO::setContacts);
    employeeMapper.addMapping(employee -> employee.getJobs().stream().map(Job::getId).toList(), EmployeeDTO::setJobs);
    return modelMapper;
  }


  // need a helper method
  // read environment property and convert to int

  private int getIntProperty(String propName) {

    String propVal = env.getProperty(propName);

    // now convert to int

    if (propVal == null) {
      throw new InvalidParameterException("Couldn't find property " + propName);
    }

    return Integer.parseInt(propVal);
  }

}
