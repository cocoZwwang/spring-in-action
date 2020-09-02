package spring.in.action.mybaits.multi.datasource.mapper.slave;

import spring.in.action.mybaits.multi.datasource.domain.slave.User;

import java.util.List;

public interface UsersMapper {

    List<User> selectAll();

    void insert(User user);
}
