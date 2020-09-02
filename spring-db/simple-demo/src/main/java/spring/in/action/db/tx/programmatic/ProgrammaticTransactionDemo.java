package spring.in.action.db.tx.programmatic;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.support.TransactionTemplate;

/**
 * 手动调用 spring 事务
 */
@Slf4j
@SpringBootApplication
public class ProgrammaticTransactionDemo implements ApplicationListener<ApplicationReadyEvent> {
    @Autowired
    TransactionTemplate transactionTemplate;

    @Autowired
    JdbcTemplate jdbcTemplate;

    public static void main(String[] args) {
        SpringApplication.run(ProgrammaticTransactionDemo.class,args);
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        transactionTemplate.executeWithoutResult(transactionStatus -> {
            jdbcTemplate.update("insert into foo (bar) values (?)","HHHH");
            transactionStatus.setRollbackOnly();
        });

        log.info("count: {}", jdbcTemplate.queryForObject("select count(*) from foo", Long.class));
    }
}
