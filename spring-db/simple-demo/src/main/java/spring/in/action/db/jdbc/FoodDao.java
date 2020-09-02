package spring.in.action.db.jdbc;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import spring.in.action.db.domain.Foo;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Repository
public class FoodDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private SimpleJdbcInsert simpleJdbcInsert;


    public void insertData() {
        //利用JdbcTemple插入数据
        Arrays.asList("b", "c").forEach(bar -> {
            jdbcTemplate.update("insert into FOO (BAR) values (?)", bar);
        });
        //利用SimpleJdbcInsert新增数据
        Map<String, Object> map = new HashMap<>();
        map.put("bar", "d");
        Number id = simpleJdbcInsert.executeAndReturnKey(map);
        log.info("ID of d: {}", id.longValue());
    }

    public void showData() {
        log.info("count:{}", jdbcTemplate.queryForObject("select count(*) from foo", Long.class));

        List<String> list = jdbcTemplate.queryForList("select bar from foo", String.class);
        list.forEach(bar -> log.info("bar:{}", bar));

        List<Foo> foos = jdbcTemplate.query("select * from foo", (resultSet, i) -> Foo.builder()
                .id(resultSet.getLong(1))
                .bar(resultSet.getString(2))
                .build());
        foos.forEach(foo -> log.info("foo:{}", foo));
    }
}
