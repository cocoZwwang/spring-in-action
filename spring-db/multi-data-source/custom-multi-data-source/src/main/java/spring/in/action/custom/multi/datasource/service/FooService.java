package spring.in.action.custom.multi.datasource.service;


import spring.in.action.custom.multi.datasource.domain.Foo;

public interface FooService {
    void save(Foo foo);

    void saveWithException(Foo foo);

    void showALl();
}
