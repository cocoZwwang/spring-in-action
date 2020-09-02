package spring.in.action.jpa.multi.datasource.config.primary;

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
@EnableJpaRepositories(entityManagerFactoryRef = "primaryEntityManagerFactoryBean",
        transactionManagerRef = "primaryTxManager",
        basePackages = "spring.in.action.jpa.multi.datasource.repository.primary")//数据仓库的位置
public class primaryDataSourceConfig {

    @Bean
    @Primary
    @ConfigurationProperties("spring.datasource.primary")
    @Qualifier("primaryDataSource")
    public DataSource primaryDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean
    @Primary
    @Qualifier("primaryEntityManager")
    public EntityManager primaryEntityManager(LocalContainerEntityManagerFactoryBean entityManagerFactoryBean) {
        return entityManagerFactoryBean.getObject().createEntityManager();
    }

    @Bean
    @Primary
    @Qualifier("primaryEntityManagerFactoryBean")
    public LocalContainerEntityManagerFactoryBean primaryEntityManagerFactoryBean(
            DataSource dataSource, JpaProperties jpaProperties,
            EntityManagerFactoryBuilder builder) {
        return builder.dataSource(dataSource)
                .properties(jpaProperties.getProperties())
                .packages("spring.in.action.jpa.multi.datasource.domain.primary")// 实体类的位置
                .persistenceUnit("primaryPersistenceUnit")
                .build();
    }

    /**
     * 这里要使用 JpaTransactionManager 不能使用 DataSourceTransactionManager
     */
    @Bean
    @Primary
    @Qualifier("primaryTxManager")
    public PlatformTransactionManager primaryTxManager(LocalContainerEntityManagerFactoryBean entityManagerFactoryBean) {
        return new JpaTransactionManager(entityManagerFactoryBean.getObject());
    }
}
