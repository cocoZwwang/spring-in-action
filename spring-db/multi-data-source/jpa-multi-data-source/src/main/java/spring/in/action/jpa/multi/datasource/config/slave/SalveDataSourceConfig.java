package spring.in.action.jpa.multi.datasource.config.slave;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.persistence.EntityManager;
import javax.sql.DataSource;

@Configuration
@EnableJpaRepositories(
        entityManagerFactoryRef = "slaveEntityManagerFactoryBean",
        transactionManagerRef = "slaveTxManager",
        basePackages = "spring.in.action.jpa.multi.datasource.repository.slave")
public class SalveDataSourceConfig {
    @Bean
    @ConfigurationProperties("spring.datasource.slave")
    @Qualifier("slaveDataSource")
    public DataSource slaveDataSource(){
        return DataSourceBuilder.create().build();
    }

    @Bean
    @Qualifier("slaveEntityManager")
    public EntityManager slaveEntityManager(@Qualifier("slaveEntityManagerFactoryBean")
                    LocalContainerEntityManagerFactoryBean entityManagerFactoryBean) {
        return entityManagerFactoryBean.getObject().createEntityManager();
    }

    @Bean
    @Qualifier("slaveEntityManagerFactoryBean")
    public LocalContainerEntityManagerFactoryBean slaveEntityManagerFactoryBean(
            @Qualifier("slaveDataSource") DataSource dataSource,JpaProperties jpaProperties,
            EntityManagerFactoryBuilder builder) {
        return builder.dataSource(dataSource)
                .properties(jpaProperties.getProperties())
                .packages("spring.in.action.jpa.multi.datasource.domain.slave")// 实体类的位置
                .persistenceUnit("slavePersistenceUnit")
                .build();
    }

    @Bean
    @Qualifier("slaveTxManager")
    public PlatformTransactionManager slaveTxManager(@Qualifier("slaveEntityManagerFactoryBean")
                                                                 LocalContainerEntityManagerFactoryBean
                                                                 entityManagerFactoryBean){
        return new JpaTransactionManager(entityManagerFactoryBean.getObject());
    }
}
