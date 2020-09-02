package spring.in.action.db.tx;

import spring.in.action.db.excetion.RollbackException;

public interface FooService {
    void insertRecord();
    void insertThenRollback() throws RollbackException;
    void invokeInsertThenRollback() throws RollbackException;
}
