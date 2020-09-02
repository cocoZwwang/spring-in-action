package spring.in.action.jpa.multi.datasource.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.in.action.jpa.multi.datasource.domain.primary.Foo;
import spring.in.action.jpa.multi.datasource.repository.primary.FooRepository;

import java.util.List;


@Service
@Slf4j
public class FooServiceImpl implements FooService {

    @Autowired
    FooRepository fooRepository;


    @Transactional
    public void save(Foo foo) {
        fooRepository.save(foo);
    }

    @Transactional
    public void saveWithException(Foo foo) {
        fooRepository.save(foo);
        throw new RuntimeException("随便抛个异常！");
    }


    public void showALl() {
        List<Foo> list = fooRepository.findAll();
        log.info(list.toString());
    }
}
