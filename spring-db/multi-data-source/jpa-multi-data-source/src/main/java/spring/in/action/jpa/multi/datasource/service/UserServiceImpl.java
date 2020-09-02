package spring.in.action.jpa.multi.datasource.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.in.action.jpa.multi.datasource.domain.slave.User;
import spring.in.action.jpa.multi.datasource.repository.slave.UserRepository;

import java.util.List;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Transactional(rollbackFor = Exception.class, transactionManager = "slaveTxManager")
    public void save(User user) {
        userRepository.save(user);
    }

    @Transactional(rollbackFor = Exception.class, transactionManager = "slaveTxManager")
    public void saveWithException(User user) {
        userRepository.save(user);
        throw new RuntimeException("随便抛个异常！");
    }

    public void showAll() {
        List<User> list = userRepository.findAll();
        log.info(list.toString());
    }
}
