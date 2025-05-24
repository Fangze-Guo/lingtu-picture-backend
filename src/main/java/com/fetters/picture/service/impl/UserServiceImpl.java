package com.fetters.picture.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fetters.picture.model.entity.User;
import com.fetters.picture.service.UserService;
import com.fetters.picture.mapper.UserMapper;
import org.springframework.stereotype.Service;

/**
* @author 99716
* @description 针对表【user(用户)】的数据库操作Service实现
* @createDate 2025-05-24 10:55:26
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

}




