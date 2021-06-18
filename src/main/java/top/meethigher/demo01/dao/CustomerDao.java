package top.meethigher.demo01.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import top.meethigher.demo01.domain.Customer;

import java.util.List;

/**
 * CustomerDao
 * 符合SpringDataJpa的dao层接口规范
 * @author kit chen
 * @github https://github.com/meethigher
 * @blog https://meethigher.top
 * @time 2021/4/12
 */
public interface CustomerDao extends JpaRepository<Customer,Long>, JpaSpecificationExecutor<Customer> {
    /**
     * 根据客户名称查询客户
     * jpql：from Customer where custName=?1
     * 配置jpql语句，使用@Query注解
     */
    @Query(value = "from Customer where custName =?1")
    Customer findJpql(String custName);

    /**
     * 根据客户名称和客户id查询客户
     * jpql：from Customer where custName=?1 and custId=?2
     */
    @Query(value = "from Customer where custName=?2 and custId=?1")
    Customer findCustomerNameAndId(Long id,String name);

    /**
     * 使用jpql完成更新的操作
     * 根据id，更新名称
     * 添加更新操作，@Modifying
     */
    @Modifying
    @Query("update Customer set custName=?2 where custId=?1")
    void updateNameById(Long id,String name);

    /**
     * 使用SQL的形式查询，查询全部客户
     */
    @Query(value="select * from customer",nativeQuery = true)
    List<Object[]> findSql();

    /**
     * 使用SQL的形式查询
     * 使用用户名模糊查询所有信息
     */
    @Query(value="select * from customer where cust_name like ?1",nativeQuery = true)
    List<Object[]> findSqlByName(String name);

    /**
     * 使用方法命名规则查询
     */
    Customer findByCustName(String name);

    /**
     * 按照客户名称模糊匹配
     * @param name
     * @return
     */
    List<Customer> findByCustNameLike(String name);

    /**
     * findBy+属性名称+查询方式+多条件连接符(And、Or)+属性名称+查询方式
     */
    List<Customer> findByCustNameLikeAndCustAddress(String name,String address);
}
