package com.ame.config;


import jakarta.persistence.EntityManagerFactory;
import org.hibernate.SessionFactory;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
//@ComponentScan(basePackages = {"com.ame.service", "com.ame.dao"})
@EntityScan(basePackages = "com.ame.entity")
@EnableTransactionManagement
public class HibernateAutoConfig {


}
