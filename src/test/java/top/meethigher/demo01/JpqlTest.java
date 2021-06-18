package top.meethigher.demo01;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import top.meethigher.demo01.dao.CustomerDao;
import top.meethigher.demo01.domain.Customer;

import java.util.Arrays;
import java.util.List;

/**
 * JpqlTest
 *
 * @author kit chen
 * @github https://github.com/meethigher
 * @blog https://meethigher.top
 * @time 2021/4/13
 */
@RunWith(SpringJUnit4ClassRunner.class)//声明spring提供的单元测试环境
@ContextConfiguration(locations = "classpath:spring.xml")//指定spring容器的配置信息
public class JpqlTest {
    @Autowired
    private CustomerDao customerDao;
    @Test
    public void testFindJpql() {
        Customer customer = customerDao.findJpql("美杜莎");
        System.out.println(customer);
    }
    @Test
    public void testFindCustomerNameAndId(){
        Customer customer = customerDao.findCustomerNameAndId(4L,"美杜莎");
        System.out.println(customer);
    }

    /**
     * 测试JPQL的更新操作
     * SpringDataJPA中使用jpql完成更新/删除操作
     * 1. 需要手动添加事务的支持
     * 2. 默认会执行结束之后，回滚事务
     */
    @Test
    @Transactional
    @Rollback(value=false)
    public void testUpdateNameById(){
        customerDao.updateNameById(4L, "小舞");
        Customer customer = customerDao.findById(4L).get();
        System.out.println(customer);
    }

    @Test
    public void testFindSql() {
        List<Object[]> sql = customerDao.findSql();
        for (Object[] obj :
                sql) {
            System.out.println(Arrays.toString(obj));
        }
    }

    @Test
    public void testFindSqlByName() {
        List<Object[]> sqlByName = customerDao.findSqlByName("%美%");
        for (Object[] obj :
                sqlByName) {
            System.out.println(Arrays.toString(obj));
        }
    }

    @Test
    public void testFindByCustName() {
        Customer customer = customerDao.findByCustName("小舞");
        System.out.println(customer);
    }

    @Test
    public void testFindByCustNameLike(){
        List<Customer> 小 = customerDao.findByCustNameLike("%小%");
        System.out.println(小 );
    }

    @Test
    public void testFindByCustNameLikeAndCustAddress(){
        List<Customer> 斗破苍穹 = customerDao.findByCustNameLikeAndCustAddress("%小%", "斗破苍穹");
        System.out.println(斗破苍穹);
    }
}
