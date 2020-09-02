package spring.in.action.custom.multi.datasource;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.JdbcTemplateAutoConfiguration;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Import;
import spring.in.action.custom.multi.datasource.context.MultiDataSourceRegister;
import spring.in.action.custom.multi.datasource.domain.Foo;
import spring.in.action.custom.multi.datasource.domain.User;
import spring.in.action.custom.multi.datasource.service.FooService;
import spring.in.action.custom.multi.datasource.service.UserService;

/**
 * 事务问题没有解决
 */
@Import(MultiDataSourceRegister.class)
@MapperScan(basePackages = "spring.in.action.custom.multi.datasource.mapper")
//@EnableTransactionManagement
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class,
        DataSourceTransactionManagerAutoConfiguration.class,
        JdbcTemplateAutoConfiguration.class})
public class CustomMultiDataSourceDemo implements ApplicationListener<ApplicationReadyEvent> {

    @Autowired
    UserService userService;

    @Autowired
    FooService fooService;

    public static void main(String[] args) {
        SpringApplication.run(CustomMultiDataSourceDemo.class, args);
    }

    public void onApplicationEvent(ApplicationReadyEvent event) {

        userService.save(new User(100, "yang"));
        try {
            userService.saveWithException(new User(101, "black"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        userService.showAll();

        fooService.save(new Foo(100, "sfdsfs"));
        try {
            fooService.saveWithException(new Foo(101, "yyyyyyy"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        fooService.showALl();

    }
}
