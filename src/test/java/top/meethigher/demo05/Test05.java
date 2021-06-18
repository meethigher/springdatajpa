package top.meethigher.demo05;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import top.meethigher.demo03.dao.PersonDao;
import top.meethigher.demo03.dao.UnitDao;
import top.meethigher.demo03.domain.Person;
import top.meethigher.demo03.domain.Unit;

import java.util.Optional;
import java.util.Set;

/**
 * Test05
 *
 * @author kit chen
 * @github https://github.com/meethigher
 * @blog https://meethigher.top
 * @time 2021/4/23
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring.xml")
public class Test05 {

    @Autowired
    private PersonDao personDao;
    @Autowired
    private UnitDao unitDao;

    @Test
    @Transactional//解决java代码中的noSession
    public void testQueryOneToMany() {
        Unit unit = unitDao.getOne(3);//懒加载
//        Unit unit = unitDao.findById(3).get();//立即加载
        //对象导航查询
            Set<Person> persons = unit.getPersons();//对象导航查询，也就是级联查询，需要配置注解
        System.out.println("测试懒加载");
        System.out.println(unit);
        System.out.println(persons.size());

    }

    @Test
    @Transactional//解决java代码中的noSession
    public void testQueryManyToOne() {
        Person one = personDao.getOne(3);
        Unit unit = one.getUnit();
        System.out.println("测试懒加载");
        System.out.println(unit);
    }
}
