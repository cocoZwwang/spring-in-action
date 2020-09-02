package spring.in.action.mybaits.multi.datasource.service;

import spring.in.action.mybaits.multi.datasource.domain.primary.Foo;

public interface FooService {
    void save(Foo foo);

    void saveWithException(Foo foo);

    void showALl();
}
