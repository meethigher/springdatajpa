package top.meethigher.demo03.top.meethigher;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import top.meethigher.demo03.dao.PersonDao;
import top.meethigher.demo03.dao.UnitDao;
import top.meethigher.demo03.domain.Person;
import top.meethigher.demo03.domain.Unit;

import java.util.Optional;

/**
 * OneToManyTest
 *
 * @author kit chen
 * @github https://github.com/meethigher
 * @blog https://meethigher.top
 * @time 2021/4/15
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring.xml")
public class OneToManyTest {
    @Autowired
    private PersonDao personDao;
    @Autowired
    private UnitDao unitDao;


    @Test
    @Transactional //配置事务
    @Rollback(false) //不自动回滚
    public void testAdd1() {
        Unit unit = new Unit();
        unit.setUnitName("小米科技");
        unit.setUnitAddress("北京");
        Person person = new Person();
        person.setPersonName("雷军");
        person.setPersonAddress("北京");

        /**
         * 保存一个unit和person到数据库中，但是两个并没有关系，外键为空
         * 原因：我们的实体类当中，没有配置关系，需要添加进去
         */
        unit.getPersons().add(person);

        unitDao.save(unit);
        personDao.save(person);


    }

    @Test
    @Transactional //配置事务
    @Rollback(false) //不自动回滚
    public void testAdd2() {
        Unit unit = new Unit();
        unit.setUnitName("腾讯科技");
        unit.setUnitAddress("北京");
        Person person = new Person();
        person.setPersonName("马化腾");
        person.setPersonAddress("深圳");


        person.setUnit(unit);

        unitDao.save(unit);
        personDao.save(person);


    }

    @Test
    @Transactional //配置事务
    @Rollback(false) //不自动回滚
    public void testAdd3() {
        Unit unit = new Unit();
        unit.setUnitName("腾讯科技");
        unit.setUnitAddress("北京");
        Person person = new Person();
        person.setPersonName("马化腾");
        person.setPersonAddress("深圳");

        person.setUnit(unit);
        unit.getPersons().add(person);
        unitDao.save(unit);
        personDao.save(person);
    }

    //级联添加
    @Test
    @Transactional //配置事务
    @Rollback(false) //不自动回滚
    public void testCascadeAdd() {
        Unit unit = new Unit();
        unit.setUnitName("腾讯科技");
        unit.setUnitAddress("北京");
        Person person = new Person();
        person.setPersonName("马化腾");
        person.setPersonAddress("深圳");

        person.setUnit(unit);
        unit.getPersons().add(person);
//        unitDao.save(unit);//不配置cascade，只会保存自己
        personDao.save(person);//不设置cascade会报错
    }

    /**
     * 级联删除
     */
    @Test
    @Transactional
    @Rollback(false)
    public void testCascadeRemove() {
        //查询
        Optional<Person> byId = personDao.findById(2);
        //删除
        byId.ifPresent(person -> personDao.delete(byId.get()));
    }
}
