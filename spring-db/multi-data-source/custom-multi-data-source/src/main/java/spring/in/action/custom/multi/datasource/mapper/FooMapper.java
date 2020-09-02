package spring.in.action.custom.multi.datasource.mapper;


import spring.in.action.custom.multi.datasource.annotation.DataSource;
import spring.in.action.custom.multi.datasource.domain.Foo;

import java.util.List;

public interface FooMapper {
    List<Foo> selectAll();

    void insert(Foo foo);
}
