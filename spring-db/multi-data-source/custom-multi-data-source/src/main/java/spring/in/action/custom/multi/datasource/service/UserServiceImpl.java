package spring.in.action.custom.multi.datasource.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.in.action.custom.multi.datasource.domain.User;
import spring.in.action.custom.multi.datasource.mapper.UsersMapper;

import java.util.List;

@Service
@Slf4j
public class UserServiceImpl implements UserService{

    @Autowired
    UsersMapper usersMapper;

    @Transactional
    public void save(User user) {
        usersMapper.insert(user);
    }

    @Transactional
    public void saveWithException(User user) {
        usersMapper.insert(user);
        throw  new RuntimeException("随便抛个异常！");
    }

    public void showAll() {
        List<User> list = usersMapper.selectAll();
        log.info(list.toString());
    }
}
