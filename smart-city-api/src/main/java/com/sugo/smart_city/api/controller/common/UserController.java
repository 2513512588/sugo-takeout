package com.sugo.smart_city.api.controller.common;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.sugo.smart_city.bean.model.User;
import com.sugo.smart_city.common.aspect.annotation.ParseParam;
import com.sugo.smart_city.common.aspect.annotation.RequestSingleParam;
import com.sugo.smart_city.common.util.Result;
import com.sugo.smart_city.security.annotation.ParseUser;
import com.sugo.smart_city.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;


/**
 * @author hehaoyang
 */
@Slf4j
@RestController
@Api(tags = "用户相关接口")
@RequestMapping("/api/commons/user")
public class UserController {

    @Resource
    private UserService userService;
    @Resource
    private PasswordEncoder passwordEncoder;

    @Resource
    private JavaMailSender javaMailSender;

    @ApiOperation(value = "绑定邮箱")
    @PostMapping("/bindEmail")
    @ParseParam
    public Result bindEmail(@RequestSingleParam("email") String email){
        // 构建一个邮件对象
        SimpleMailMessage message = new SimpleMailMessage();
        // 设置邮件主题
        message.setSubject("智慧城市");
        // 设置邮件发送者，这个跟application.yml中设置的要一致
        message.setFrom("2513512588@qq.com");
        // 设置邮件接收者，可以有多个接收者，中间用逗号隔开，以下类似
        message.setTo(email);
        // 设置邮件发送日期
        message.setSentDate(new Date());
        // 设置邮件的正文
        message.setText("验证码");
        // 发送邮件
        javaMailSender.send(message);

        return Result.ok();
    }

    @ApiOperation(value = "用户注册")
    @PostMapping("/register")
    public Result register(@Validated @RequestBody User user){
        user.setRoleId(1);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", user.getUsername()).or().eq("phone", user.getPhone())
                .or().eq("email", user.getEmail());
        User queryUser = userService.getOne(queryWrapper);
        if (queryUser != null){
            if (queryUser.getPhone().equals(user.getPhone())){
                return Result.error().message("手机号已存在");
            }else if (queryUser.getUsername().equals(user.getUsername())){
                return Result.error().message("用户名已存在");
            }else if (queryUser.getEmail().equals(user.getEmail())){
                return Result.error().message("邮箱地址已存在");
            }
        }
        return Result.auto(userService.save(user));
    }

//    @ApiOperation(value = "用户登录")
//    @PostMapping("/login")
//    public R login(@RequestBody LoginUser user){
//        return R.ok();
//    }

    @ApiOperation(value = "用户修改资料")
    @PostMapping("/updateInfo")
    public Result updateInfo(@RequestBody User user, @ParseUser Integer userId){
        User user1 = new User();
        user1.setNickname(user.getNickname());
        user1.setId(userId);
        user1.setAvatar(user.getAvatar());
        user1.setSex(user.getSex());
        return Result.auto(userService.updateById(user1));
    }

    @ApiOperation(value = "查询用户基本信息")
    @GetMapping("/getInfo")
    public Result getInfo(@ParseUser User user){
        return Result.ok().data("user", user);
    }

    @ApiOperation(value = "修改密码")
    @PostMapping("/updatePwd")
    @ParseParam
    public Result updatePwd(@RequestSingleParam("oldPassword") String oldPassword,
                            @RequestSingleParam("newPassword") String newPassword,
                            @ParseUser User user){
        if (passwordEncoder.matches(user.getPassword(), oldPassword)){
            String pwd = passwordEncoder.encode(newPassword);
            return Result.auto(userService.updateById(User.builder()
                                                .id(user.getId())
                                                .password(pwd).build()));
        }else {
            return Result.error().message("用户老密码错误");
        }
    }

    /**
     * todo
     * @param userId 当前用户id
     */
    @ApiOperation(value = "改绑手机号")
    @PostMapping("/updatePhone")
    @ParseParam
    public Result updatePhone(@ParseUser Integer userId, @RequestSingleParam("phone") String phone){
        return Result.ok();
    }
}
