package top.meethigher.demo04.top.meethigher;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import top.meethigher.demo04.dao.RoleDao;
import top.meethigher.demo04.dao.UserDao;
import top.meethigher.demo04.domain.Role;
import top.meethigher.demo04.domain.User;

import java.util.Optional;
import java.util.function.Consumer;

/**
 * ManyToManyTest
 *
 * @author kit chen
 * @github https://github.com/meethigher
 * @blog https://meethigher.top
 * @time 2021/4/18
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring.xml")
public class ManyToManyTest {
    @Autowired
    private UserDao userDao;
    @Autowired
    private RoleDao roleDao;

    /**
     * 保存一个用户保存一个角色
     */
    @Test
    @Transactional
    @Rollback(false)
    public void testAdd() {
        User user = new User();
        Role role = new Role();
        user.setUserName("雅妃");
        role.setRoleName("监察长老");

        role.getUsers().add(user);
        user.getRoles().add(role);

        roleDao.save(role);
        userDao.save(user);
    }

    /**
     * 级联操作
     * 保护一个用户的同时，保存所有角色的关联
     */
    @Test
    @Transactional
    @Rollback(false)
    public void testCascadeAdd() {
        User user = new User();
        Role role = new Role();
        user.setUserName("雅妃");
        role.setRoleName("监察长老");

        user.getRoles().add(role);

        userDao.save(user);
    }

    /**
     * 级联操作
     * 保护一个用户的同时，保存所有角色的关联
     */
    @Test
    @Transactional
    @Rollback(false)
    public void testCascadeRemove() {
        Optional<User> byId = userDao.findById(1);
        byId.ifPresent(user -> userDao.delete(user));
    }
}
