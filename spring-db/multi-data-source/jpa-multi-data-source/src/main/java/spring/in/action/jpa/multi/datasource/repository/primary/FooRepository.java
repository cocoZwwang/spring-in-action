package spring.in.action.jpa.multi.datasource.repository.primary;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import spring.in.action.jpa.multi.datasource.domain.primary.Foo;

@Repository
public interface FooRepository extends JpaRepository<Foo,Integer> {

}
