package spring.in.action.mybaits.multi.datasource.mapper.primary;

import spring.in.action.mybaits.multi.datasource.domain.primary.Foo;

import java.util.List;

public interface FooMapper {
    List<Foo> selectAll();

    void insert(Foo foo);
}
