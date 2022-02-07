package com.sugo.takeout.rider.controller;

import cn.hutool.core.lang.ObjectId;
import com.sugo.takeout.common.util.Result;
import com.sugo.takeout.rider.server.WebSocketServer;
import com.sugo.takeout.security.annotation.ParseUser;
import com.sugo.takeout.security.enums.Role;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rider/takeout/socket")
@Api(tags = "websocket接口")
@AllArgsConstructor
@Slf4j
public class SocketController {

    /**
     * 注册websocket身份
     */
    @GetMapping("/register")
    public Result register(@ParseUser(Role.ROLE_TAKEOUT_RIDER) Integer riderId){
        String next = ObjectId.next();
        if (WebSocketServer.RIDER_MAP.containsValue(riderId)){
            WebSocketServer.RIDER_MAP.values().remove(riderId);
        }
        WebSocketServer.RIDER_MAP.put(next, riderId);
        return Result.ok().data(next);
    }

}
