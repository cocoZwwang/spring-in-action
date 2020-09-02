package spring.in.action.jpa.multi.datasource.repository.slave;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import spring.in.action.jpa.multi.datasource.domain.slave.User;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
}
