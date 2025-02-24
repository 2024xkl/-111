package com.loan.system_loan.repository;
import com.loan.system_loan.pojo.UserSpecification;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.loan.system_loan.pojo.User;
import java.util.Optional;
import java.util.List;

@Repository //spring bean
public interface UserRepository extends CrudRepository<User,Integer>, JpaSpecificationExecutor<User> {

    List<User> findByUserName(String userName);
    Optional<User> findByCardId(String cardId);
    Optional<User> findByPhone(String phone);    // 根据手机号查询
    Optional<User> findByEmail(String email);    // 根据邮箱查询

    // 查询所有用户
    List<User> findAll();

}
