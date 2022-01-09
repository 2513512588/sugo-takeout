package com.sugo.smart_city.security.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sugo.smart_city.bean.model.TakeoutRider;
import com.sugo.smart_city.bean.model.TakeoutSeller;
import com.sugo.smart_city.bean.model.User;
import com.sugo.smart_city.security.dto.JwtUser;
import com.sugo.smart_city.security.enums.Role;
import com.sugo.smart_city.security.service.UserSecurityService;
import com.sugo.smart_city.service.RoleService;
import com.sugo.smart_city.service.TakeoutRiderService;
import com.sugo.smart_city.service.TakeoutSellerService;
import com.sugo.smart_city.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

import static com.sugo.smart_city.security.enums.Role.ROLE_TAKEOUT_RIDER;
import static com.sugo.smart_city.security.enums.Role.ROLE_TAKEOUT_SELLER;


/**
 * @author hehaoyang
 */
@Service
public class UserSecurityServiceImpl implements UserSecurityService {

    @Resource
    private UserService userService;
    @Resource
    private RoleService roleService;
    @Resource
    private TakeoutSellerService takeoutSellerService;
    @Resource
    private TakeoutRiderService takeoutRiderService;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Wrapper<User> queryWrapper = new QueryWrapper<>(User.builder().username(s).build());
        User user = userService.getOne(queryWrapper);
        if (user == null){
            throw new UsernameNotFoundException("用户不存在");
        }
        if (user.getStatus() != 1){
            throw new UsernameNotFoundException("用户已被封禁");
        }
        //商家存放商家id 骑手存放骑手id
        Map<String, Object> attachData = new HashMap<>();
        if (Role.parse(user.getRoleId()) == ROLE_TAKEOUT_SELLER){
            TakeoutSeller takeoutSeller = takeoutSellerService.getOne(new QueryWrapper<>(TakeoutSeller.builder().userId(user.getId()).build()));
            attachData.put(ROLE_TAKEOUT_SELLER.getName(), takeoutSeller.getId());
        }else if (Role.parse(user.getRoleId()) == ROLE_TAKEOUT_RIDER){
            TakeoutRider takeoutRider = takeoutRiderService.getOne(new QueryWrapper<>(TakeoutRider.builder().userId(user.getId()).build()));
            attachData.put(ROLE_TAKEOUT_RIDER.getName(), takeoutRider.getId());
        }
        return new JwtUser(user, roleService.getById(user.getRoleId()), attachData);
    }
}
