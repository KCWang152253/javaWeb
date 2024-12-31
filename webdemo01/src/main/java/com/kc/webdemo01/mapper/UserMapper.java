package com.kc.webdemo01.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kc.webdemo01.bean.UserData;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author KCWang
 * @version 1.0
 * @date 2024/12/31 下午12:53
 */
@Mapper
public interface UserMapper extends BaseMapper<UserData> {

}

