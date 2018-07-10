package com.csranger.house.biz.dao;

import com.csranger.house.common.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Dao层，接口，抽象方法具体实现是SQL语句，在mapper目录下的xml文件里
 */

@Repository     // 可删，why? 参考  https://stackoverflow.com/questions/25379348/idea-inspects-batis-mapper-bean-wrong
@Mapper
public interface UserDao {

    // 查询所有用户
    List<User> selectUsers();

    int insert(User account);

    int delete(String email);

    int update(User updateUser);

    List<User> selectUsersByQuery(User user);
}
