package spring.in.action.mybaits.multi.datasource.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;


@Configuration
@MapperScan(basePackages = "spring.in.action.mybaits.multi.datasource.mapper.primary",
        sqlSessionFactoryRef = "primarySqlSessionFactory",
        sqlSessionTemplateRef = "primarySqlSessionTemplate")
public class PrimaryDataSourceConfig {

    @Bean
    @Primary
    @Qualifier("primaryDataSource")
    @ConfigurationProperties("spring.datasource.primary")
    public DataSource primaryDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean
    @Primary
    @Qualifier("primarySqlSessionFactory")
    public SqlSessionFactory primarySqlSessionFactory(DataSource dataSource) throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        sqlSessionFactoryBean.setTypeAliasesPackage("spring.in.action.mybaits.multi.datasource.domain.primary");
        sqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver()
                .getResources("classpath:mapping/primary/*.xml"));
        return sqlSessionFactoryBean.getObject();
    }

    @Bean
    @Primary
    @Qualifier("primarySqlSessionTemplate")
    public SqlSessionTemplate primarySqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {

        return new SqlSessionTemplate(sqlSessionFactory);
    }

    @Bean
    @Primary
    @Qualifier("primaryTxManager")
    public PlatformTransactionManager primaryTxManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

}
