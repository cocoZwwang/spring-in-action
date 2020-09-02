package spring.in.action.db.tx;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.jdbc.core.JdbcTemplate;

@Slf4j
@SpringBootApplication
public class DeclarativeTransactionDemo implements ApplicationListener<ApplicationReadyEvent> {
    @Autowired
    FooService fooService;

    @Autowired
    JdbcTemplate jdbcTemplate;

    public static void main(String[] args) {
        SpringApplication.run(DeclarativeTransactionDemo.class,args);
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        fooService.insertRecord();
        log.info("AAA {}",
                jdbcTemplate
                        .queryForObject("SELECT COUNT(*) FROM FOO WHERE BAR='AAA'", Long.class));
        try {
            fooService.insertThenRollback();
        } catch (Exception e) {
            log.info("BBB {}",
                    jdbcTemplate
                            .queryForObject("SELECT COUNT(*) FROM FOO WHERE BAR='BBB'", Long.class));
        }

        try {
            fooService.invokeInsertThenRollback();
        } catch (Exception e) {
            log.info("BBB {}",
                    jdbcTemplate
                            .queryForObject("SELECT COUNT(*) FROM FOO WHERE BAR='BBB'", Long.class));
        }
    }
}
