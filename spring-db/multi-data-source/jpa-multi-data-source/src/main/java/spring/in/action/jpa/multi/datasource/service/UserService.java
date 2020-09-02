package spring.in.action.jpa.multi.datasource.service;


import spring.in.action.jpa.multi.datasource.domain.slave.User;

public interface UserService {

    void save(User user);

    void saveWithException(User user);

    void showAll();

}
