package com.likelion.dao;

import com.likelion.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class) //스프링에만 있는 기능을 junit에서도 쓸 수있다.
@ContextConfiguration(classes = UserDaoFactory.class)
class UserDaoTest {


    @Autowired //쓰려면 @ExtendWith(SpringExtension.class)이 필요함
    ApplicationContext context;
    UserDao userDao;
    User user1;
    User user2;
    User user3;

    @BeforeEach
    void setUp(){
        this.userDao = context.getBean("awsUserDao", UserDao.class);
        user1 = new User("1","서정희", "1234");
        user2 = new User("2","서정희2", "1234");
        user3 = new User("3","서정희3", "1234");

    }

    @Test
    void addAndGet() throws SQLException {
        User user1 = new User("1","서정희", "1234");

        userDao.deleteAll();
        assertEquals(0,userDao.getCount());

        String id = "29";
        userDao.add(user1);
        assertEquals(1,userDao.getCount());
        User user = userDao.findById(user1.getId());

        assertEquals(user1.getName(), user.getName());
        //user1.getName()은 메소드내에서 선언되어있는 값 user는 디비에서 가져온 값
        assertEquals("1234", user.getPassword());
    }

    @Test
    void count() throws SQLException{

        userDao.deleteAll();
        assertEquals(0,userDao.getCount());
        userDao.add(user1);
        assertEquals(1,userDao.getCount());
        userDao.add(user2);
        assertEquals(2,userDao.getCount());
        userDao.add(user3);
        assertEquals(3,userDao.getCount());
    }

    @Test
    void findById(){
        assertThrows(EmptyResultDataAccessException.class,()->{
            userDao.findById("30");
        });

    }
}
