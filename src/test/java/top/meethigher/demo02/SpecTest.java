package top.meethigher.demo02;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import top.meethigher.demo02.dao.CustomerDao;
import top.meethigher.demo02.domain.Customer;

import javax.persistence.criteria.*;
import java.util.List;
import java.util.Optional;

/**
 * SpecTest
 *
 * @author kit chen
 * @github https://github.com/meethigher
 * @blog https://meethigher.top
 * @time 2021/4/14
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring.xml")
public class SpecTest {
    @Autowired
    private CustomerDao customerDao;

    /**
     * 根据条件查询单个对象
     */
    @Test
    public void testFindOne() {
        //匿名内部类
        Specification<Customer> specification = new Specification<Customer>() {
            /**
             * 根据id查询
             * 需要明确
             *  1.查询方式：cb
             *  2.比较的属性名称：root
             * @param root
             * @param cq
             * @param cb
             * @return
             */
            @Override
            public Predicate toPredicate(Root<Customer> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
                //1. 获取比较的属性
                Path<Object> custId = root.get("custId");
                //2. 构造查询条件，
                // 第一个参数，比较的对象；第二个参数，取值
                Predicate predicate = cb.equal(custId, 4L);
                return predicate;
            }
        };
        Optional<Customer> one = customerDao.findOne(specification);
        if (one.isPresent()) {
            System.out.println(one.get());
        }
    }

    /**
     * 根据客户名和所属行业查询
     * SELECT * FROM customer WHERE cust_name =? AND cust_industry=?
     */
    @Test
    public void testFindByCustNameAndCustIndustry() {
        List<Customer> all = customerDao.findAll(new Specification<Customer>() {
            /**
             * 1.root获取客户名、所属行业
             * 2.将以上两个查询联系起来
             * @param root
             * @param cq
             * @param cb
             * @return
             */
            @Override
            public Predicate toPredicate(Root<Customer> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
                Predicate equal = cb.equal(root.get("custName"), "小舞");
                Predicate equal1 = cb.equal(root.get("custIndustry"), "蛇人族");
                Predicate and = cb.and(equal, equal1);
                return and;
            }
        });
        System.out.println(all);
    }

    /**
     * 根据(客户名或者客户名称)和地址进行查询
     * SELECT * FROM customer WHERE cust_address =? AND (cust_id = 4 OR cust_name =?)
     */
    @Test
    public void testFindByAddressAndNameOrId() {
        List<Customer> all = customerDao.findAll(new Specification<Customer>() {
            @Override
            public Predicate toPredicate(Root<Customer> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
                Predicate custId = cb.equal(root.get("custId"), 4L);
                Predicate custName = cb.equal(root.get("custName"), "小舞");
                Predicate custAddress = cb.equal(root.get("custAddress"), "斗破苍穹");
                Predicate or = cb.or(custId, custName);
                return cb.and(custAddress, or);
            }
        });
        System.out.println(all);
    }

    /**
     * 根据名称模糊查询，查询列表
     */
    @Test
    public void testFindLike() {
        List<Customer> all = customerDao.findAll(new Specification<Customer>() {
            @Override
            public Predicate toPredicate(Root<Customer> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
                Path<Object> objectPath = root.get("custName");
                Predicate custName = cb.like(objectPath.as(String.class), "%小%");
                return custName;
            }
        });
        System.out.println(all);
    }

    /**
     * 测试排序
     */
    @Test
    public void testSort() {
        /**
         * 创建排序对象
         * 第一个参数：排序的顺序，倒序DESC、正序ASC
         * 第二个参数：排序的属性名称
         */
//        Sort orders = Sort.by("custId").descending();
        Sort orders = Sort.by(Sort.Direction.DESC, "custId");
        List<Customer> all = customerDao.findAll(orders);
        all.forEach(System.out::println);
    }

    /**
     * 分页查询
     */
    @Test
    public void testPage() {
        Specification<Customer> specification = new Specification<Customer>() {
            @Override
            public Predicate toPredicate(Root<Customer> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
                return null;
            }
        };
        /**
         * 第一个参数：当前页，从0开始
         * 第二个参数：查询的个数
         */
        Sort custId = Sort.by("custId").descending();
        Pageable page = PageRequest.of(0, 2, custId);
        Page<Customer> all = customerDao.findAll(specification, page);
        System.out.println(all.getTotalElements());//总条数
        System.out.println(all.getTotalPages());//总页数
        System.out.println(all.getContent());//内容
    }
}
