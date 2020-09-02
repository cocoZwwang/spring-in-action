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

@MapperScan(basePackages = "spring.in.action.mybaits.multi.datasource.mapper.slave",
        sqlSessionFactoryRef = "slaveSqlSessionFactory",
        sqlSessionTemplateRef = "slaveSqlSessionTemplate")
@Configuration
public class SlaveDataSourceConfig {

    @Bean
    @Qualifier("slaveDataSource")
    @ConfigurationProperties("spring.datasource.slave")
    public DataSource slaveDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean
    @Qualifier("slaveSqlSessionFactory")
    public SqlSessionFactory slaveSqlSessionFactory(
            @Qualifier("slaveDataSource") DataSource slaveDataSource) throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(slaveDataSource);
        factoryBean.setMapperLocations(new PathMatchingResourcePatternResolver()
                .getResources("classpath:mapping/slave/*.xml"));
        return factoryBean.getObject();
    }

    @Bean
    @Qualifier("slaveSqlSessionTemplate")
    public SqlSessionTemplate slaveSqlSessionTemplate(
            @Qualifier("slaveSqlSessionFactory") SqlSessionFactory slaveSqlSessionFactory) {
        return new SqlSessionTemplate(slaveSqlSessionFactory);
    }

    @Bean
    @Qualifier("slaveTxManager")
    public PlatformTransactionManager slaveTxManager(@Qualifier("slaveDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

}
