package com.agentweave.backend.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackages = "com.agentweave.backend.persistence",
        entityManagerFactoryRef = "taskEntityManagerFactory",
        transactionManagerRef = "taskTransactionManager"
)
public class TaskDataSourceConfig {

    @Bean
    @ConfigurationProperties("spring.datasource.task")
    public DataSourceProperties taskDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    public DataSource taskDataSource(@Qualifier("taskDataSourceProperties") DataSourceProperties properties) {
        return properties.initializeDataSourceBuilder().build();
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean taskEntityManagerFactory(
            @Qualifier("taskDataSource") DataSource dataSource,
            EntityManagerFactoryBuilder builder
    ) {
        return builder
                .dataSource(dataSource)
                .packages("com.agentweave.backend.persistence")
                .persistenceUnit("tasks")
                .build();
    }

    @Bean
    public PlatformTransactionManager taskTransactionManager(
            @Qualifier("taskEntityManagerFactory") LocalContainerEntityManagerFactoryBean emf
    ) {
        return new JpaTransactionManager(emf.getObject());
    }
}
