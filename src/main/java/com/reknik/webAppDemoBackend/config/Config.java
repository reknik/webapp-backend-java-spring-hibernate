package com.reknik.webAppDemoBackend.config;

import com.zaxxer.hikari.HikariDataSource;
import java.util.Properties;
import javax.sql.DataSource;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.SessionFactory;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@PropertySource("classpath:persistence.properties")
@PropertySource("classpath:application.yaml")
@EnableTransactionManagement
@Slf4j
public class Config {

  @Autowired
  private Environment env;

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
    return new ModelMapper();
  }

  private Properties getHibernateProperties() {

    // set hibernate properties
    Properties props = new Properties();

    props.setProperty("hibernate.dialect", env.getProperty("hibernate.dialect"));
    props.setProperty("hibernate.show_sql", env.getProperty("hibernate.show_sql"));

    return props;
  }


  // need a helper method
  // read environment property and convert to int

  private int getIntProperty(String propName) {

    String propVal = env.getProperty(propName);

    // now convert to int

    return Integer.parseInt(propVal);
  }

  @Bean
  public LocalSessionFactoryBean sessionFactory() {

    // create session factorys
    LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();

    // set the properties
    sessionFactory.setDataSource(myDataSource());
    sessionFactory.setPackagesToScan("com.reknik.webAppDemoBackend.entity");
    sessionFactory.setHibernateProperties(getHibernateProperties());

    return sessionFactory;
  }

  @Bean
  @Autowired
  public HibernateTransactionManager transactionManager(SessionFactory sessionFactory) {

    HibernateTransactionManager txManager = new HibernateTransactionManager();
    txManager.setSessionFactory(sessionFactory);

    return txManager;
  }

}
