package com.sugo.takeout.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sugo.takeout.bean.model.User;
import com.sugo.takeout.mapper.UserMapper;
import com.sugo.takeout.service.UserService;
import org.springframework.stereotype.Service;

/**
 *
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

}




