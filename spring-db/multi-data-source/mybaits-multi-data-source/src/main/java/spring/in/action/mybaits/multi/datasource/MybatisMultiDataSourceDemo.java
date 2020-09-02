package spring.in.action.mybaits.multi.datasource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.JdbcTemplateAutoConfiguration;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import spring.in.action.mybaits.multi.datasource.domain.primary.Foo;
import spring.in.action.mybaits.multi.datasource.domain.slave.User;
import spring.in.action.mybaits.multi.datasource.mapper.primary.FooMapper;
import spring.in.action.mybaits.multi.datasource.mapper.slave.UsersMapper;
import spring.in.action.mybaits.multi.datasource.service.FooService;
import spring.in.action.mybaits.multi.datasource.service.UserService;

import java.util.List;


@EnableTransactionManagement
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class,
        DataSourceTransactionManagerAutoConfiguration.class,
        JdbcTemplateAutoConfiguration.class})
public class MybatisMultiDataSourceDemo implements ApplicationListener<ApplicationReadyEvent> {

    @Autowired
    UserService userService;

    @Autowired
    FooService fooService;

    public static void main(String[] args) {
        SpringApplication.run(MybatisMultiDataSourceDemo.class, args);
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
