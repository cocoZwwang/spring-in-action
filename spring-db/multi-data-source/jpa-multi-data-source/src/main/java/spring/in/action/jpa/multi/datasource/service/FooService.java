package spring.in.action.jpa.multi.datasource.service;


import spring.in.action.jpa.multi.datasource.domain.primary.Foo;

public interface FooService {
    void save(Foo foo);

    void saveWithException(Foo foo);

    void showALl();
}
