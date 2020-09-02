package spring.in.action.custom.multi.datasource.mapper;

import spring.in.action.custom.multi.datasource.annotation.DataSource;
import spring.in.action.custom.multi.datasource.domain.User;

import java.util.List;

@DataSource(value = "slave1")
public interface UsersMapper {

    List<User> selectAll();

    void insert(User user);
}
