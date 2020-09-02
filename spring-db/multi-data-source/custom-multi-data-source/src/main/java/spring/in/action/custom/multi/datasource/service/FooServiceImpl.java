package spring.in.action.custom.multi.datasource.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.in.action.custom.multi.datasource.domain.Foo;
import spring.in.action.custom.multi.datasource.mapper.FooMapper;

import java.util.List;

@Service
@Slf4j
public class FooServiceImpl implements FooService {

    @Autowired
    FooMapper fooMapper;


    @Transactional
    public void save(Foo foo) {
        fooMapper.insert(foo);
    }

    @Transactional
    public void saveWithException(Foo foo) {
        fooMapper.insert(foo);
        throw new RuntimeException("随便抛个异常！");
    }


    public void showALl() {
        List<Foo> list = fooMapper.selectAll();
        log.info(list.toString());
    }
}
