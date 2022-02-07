package com.sugo.takeout.rider.server;


import com.sugo.takeout.bean.enums.RedisKey;
import com.sugo.takeout.common.util.RedisUtil;
import com.sugo.takeout.common.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author hehaoyang
 */
@ServerEndpoint("/delivery/{code}")
@Component
@Slf4j
public class WebSocketServer {

    private static final ConcurrentHashMap<String, WebSocketServer> WEB_SOCKET_MAP = new ConcurrentHashMap<>();
    public static final ConcurrentHashMap<String, Integer> RIDER_MAP = new ConcurrentHashMap<>();
    private static final AtomicInteger ONLINE_COUNT = new AtomicInteger(0);
    private Integer riderId;


    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("code") String code) throws IOException {
        Integer riderId = RIDER_MAP.get(code);
        if (riderId != null){
            ONLINE_COUNT.incrementAndGet(); // 在线数加1
            this.riderId = riderId;
            WEB_SOCKET_MAP.put(code, this);
            log.debug("有新连接加入：{}，当前在线人数为：{}", session.getId(), ONLINE_COUNT.get());
        }else {
            session.close();
        }
        log.debug("this => {}", this);
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose(Session session, @PathParam("code") String code) {
        if (RIDER_MAP.containsKey(code)){
            ONLINE_COUNT.decrementAndGet(); // 在线数减1
            RIDER_MAP.remove(code);
            WEB_SOCKET_MAP.remove(code);
            log.debug("有一连接关闭：{}，当前在线人数为：{}", session.getId(), ONLINE_COUNT.get());
        }else {
            log.debug("未注册的用户！");
        }
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message
     *            客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        StringUtil.validLatLng(message);
        log.debug("服务端收到客户端[{}]的消息:{}", session.getId(), message);
        RedisUtil.putH(RedisKey.RIDER_LOCATION.getName(), this.riderId, message);
    }

    /**
     * 服务端发送消息给客户端
     */
    private void sendMessage(String message, Session toSession) {
        try {
            log.debug("服务端给客户端[{}]发送消息{}", toSession.getId(), message);
            toSession.getBasicRemote().sendText(message);
        } catch (Exception e) {
            log.error("服务端发送消息给客户端失败：{}", e.getMessage());
        }
    }


}
