package top.meethigher.demo03.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import top.meethigher.demo03.domain.Unit;

/**
 * UnitDao
 *
 * @author kit chen
 * @github https://github.com/meethigher
 * @blog https://meethigher.top
 * @time 2021/4/15
 */
public interface UnitDao extends JpaRepository<Unit,Integer>, JpaSpecificationExecutor<Unit> {
}
