package com.cyl.test.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyl.test.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

public interface UserMapper extends BaseMapper<User> {

}
