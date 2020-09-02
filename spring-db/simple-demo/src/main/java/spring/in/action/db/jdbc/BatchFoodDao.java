package spring.in.action.db.jdbc;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.stereotype.Repository;
import spring.in.action.db.domain.Foo;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Repository
public class BatchFoodDao {
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public void batchInsertData() {
        //JdbcTemplate批量插入
        List<String> strings = Arrays.asList("bb-1","bb-2");
        jdbcTemplate.batchUpdate("insert into foo (bar) values (?)", new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                preparedStatement.setString(1, strings.get(i));
            }

            @Override
            public int getBatchSize() {
                return 2;
            }
        });

        //NamedParameterJdbcTemplate 批量插入数据
        List<Foo> list = new ArrayList<>();
        list.add(Foo.builder().id(100L).bar("b-100").build());
        list.add(Foo.builder().id(101L).bar("b-101").build());
        namedParameterJdbcTemplate.batchUpdate("insert into foo (id,bar) values(:id,:bar)",
                SqlParameterSourceUtils.createBatch(list));
    }
}
