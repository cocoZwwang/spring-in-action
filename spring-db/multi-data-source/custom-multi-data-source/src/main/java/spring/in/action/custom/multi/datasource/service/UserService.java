package spring.in.action.custom.multi.datasource.service;


import spring.in.action.custom.multi.datasource.domain.User;

public interface UserService {

    void save(User user);

    void saveWithException(User user);

    void showAll();

}
