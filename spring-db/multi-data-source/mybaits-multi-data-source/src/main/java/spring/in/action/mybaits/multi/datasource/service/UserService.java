package spring.in.action.mybaits.multi.datasource.service;

import spring.in.action.mybaits.multi.datasource.domain.slave.User;

public interface UserService {

    void save(User user);

    void saveWithException(User user);

    void showAll();

}
