package top.meethigher.demo01;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import top.meethigher.demo01.dao.CustomerDao;
import top.meethigher.demo01.domain.Customer;


import java.util.List;
import java.util.Optional;

/**
 * CustomerDaoTest
 *
 * @author kit chen
 * @github https://github.com/meethigher
 * @blog https://meethigher.top
 * @time 2021/4/12
 */
@RunWith(SpringJUnit4ClassRunner.class)//声明spring提供的单元测试环境
@ContextConfiguration(locations = "classpath:spring.xml")//指定spring容器的配置信息
public class CustomerDaoTest {
    @Autowired
    private CustomerDao customerDao;

    /**
     * 根据id查询
     */
    @Test
    public void testFindById() {
        Optional<Customer> byId = customerDao.findById(3L);
        if(byId.isPresent()){
            System.out.println(byId.get());
        }
    }
    /**
     * save()保存或者更新
     * 如果没有id主键属性，就是保存
     * 如果存在id主键属性，并且在数据库查到了，就是更新，否则就是不携带主键进行保存
     */
    @Test
    public void testSave() {
        Customer customer = new Customer();
        customer.setCustId(1L);
        customer.setCustName("雅妃");
        /**
         * 这种直接save存在的问题是，会将没有属性的字段置为null
         * 解决办法是，先查询获取对象，再更新
         */
        customerDao.save(customer);
    }

    /**
     * 根据id删除
     */
    @Test
    public void testDeleteById() {
        customerDao.deleteById(1L);
    }
    /**
     * 查询所有
     */
    @Test
    public void testFindAll() {
        List<Customer> list = customerDao.findAll();
//        list.stream().forEach(x->{
//            System.out.println(x);
//        });
        //使用lambda实现上面
        list.forEach(System.out::println);
    }

    /**
     * 测试统计查询
     */
    @Test
    public void testCount(){
        long count = customerDao.count();
        System.out.println(count);
    }

    /**
     * 判断id为4的客户是否存在
     *
     */
    @Test
    public void testExist(){
        boolean b = customerDao.existsById(4L);
        System.out.println(b);
    }

    /**
     * 测试getOne
     * getOne是懒加载
     */
    @Test
    @Transactional//保证getOne正常运行
    public void testGetOne(){
        Customer one = customerDao.getOne(4L);
        System.out.println(one);
    }
}
