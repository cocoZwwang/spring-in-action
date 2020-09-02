package spring.in.action.db.jdbc;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import javax.sql.DataSource;

@SpringBootApplication
@Slf4j
public class SimpleJdbcDemo implements ApplicationListener<ApplicationReadyEvent> {

    @Autowired
    FoodDao foodDao;

    @Autowired
    BatchFoodDao batchFoodDao;

    public static void main(String[] args) {
        SpringApplication.run(SimpleJdbcDemo.class,args);
    }


    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        foodDao.insertData();
        foodDao.showData();
        log.info("================================================");
        batchFoodDao.batchInsertData();
        foodDao.showData();
    }

    @Bean
    public SimpleJdbcInsert simpleJdbcInsert(JdbcTemplate jdbcTemplate) {
        return new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("foo")
                .usingGeneratedKeyColumns("id");
    }

    public NamedParameterJdbcTemplate namedParameterJdbcTemplate(DataSource dataSource){
        return new NamedParameterJdbcTemplate(dataSource);
    }


}
