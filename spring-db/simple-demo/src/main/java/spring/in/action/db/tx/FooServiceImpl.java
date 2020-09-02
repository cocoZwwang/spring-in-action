package spring.in.action.db.tx;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.in.action.db.excetion.RollbackException;

@Service
public class FooServiceImpl implements FooService {
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public void insertRecord() {
        jdbcTemplate.update("insert into foo (bar) values (?)","AAA");
    }

    @Transactional(rollbackFor = RollbackException.class)
    @Override
    public void insertThenRollback() throws RollbackException {
        jdbcTemplate.update("insert into foo (bar) values (?)","BBB");
        throw new RollbackException();
    }

    @Override
    public void invokeInsertThenRollback() throws RollbackException {
        insertThenRollback();
    }
}
