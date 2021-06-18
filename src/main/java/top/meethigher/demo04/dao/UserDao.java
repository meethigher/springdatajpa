package top.meethigher.demo04.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import top.meethigher.demo04.domain.User;

/**
 * UserDao
 *
 * @author kit chen
 * @github https://github.com/meethigher
 * @blog https://meethigher.top
 * @time 2021/4/18
 */
public interface UserDao extends JpaRepository<User,Integer>, JpaSpecificationExecutor<User> {
}
