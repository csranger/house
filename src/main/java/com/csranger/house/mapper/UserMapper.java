package com.csranger.house.mapper;

import com.csranger.house.common.model.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Dao层，接口，抽象方法具体实现是SQL语句，在mapper目录下的xml文件里
 */

@Mapper
public interface UserMapper {

    // 查询所有用户
    List<User> selectUsers();
}
