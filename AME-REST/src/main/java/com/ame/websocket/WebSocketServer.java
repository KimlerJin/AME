package com.ame.websocket;

import com.ame.dto.CurrentUserDto;
import com.ame.entity.UserEntity;
import jakarta.annotation.PostConstruct;
import jakarta.websocket.*;
import jakarta.websocket.server.ServerEndpoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicInteger;

@ServerEndpoint(value = "/ws/path/asset")
@Component
public class WebSocketServer {

    private static final CopyOnWriteArraySet<Session> SessionSet = new CopyOnWriteArraySet<>();


    private static final AtomicInteger OnlineCount = new AtomicInteger(0);

    private static Logger log = LoggerFactory.getLogger(WebSocketServer.class);

    @PostConstruct
    public void init() {
        log.info("websocket info 加载");
    }

    @OnOpen
    public void onOpen(Session session) {
        SessionSet.add(session);
        int i = OnlineCount.incrementAndGet();
        log.info("有连接加入，当前连接数为：{}", i);
    }

    @OnClose
    public void onClose(Session session) {
        SessionSet.remove(session);
        int cnt = OnlineCount.decrementAndGet();
        log.info("有连接关闭，当前连接数为：{}", cnt);
    }

    @OnMessage
    public void onMessage(String message, Session session) throws IOException {
        log.info("来自客户端的消息：{}",message);
        SendMessage(session, "收到消息，消息内容："+message);

    }

    /**
     * 出现错误
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
        log.error("发生错误：{}，Session ID： {}",error.getMessage(),session.getId());
        error.printStackTrace();
    }

    /**
     * 发送消息，实践表明，每次浏览器刷新，session会发生变化。
     * @param session
     * @param message
     */
    public static void SendMessage(Session session, String message) throws IOException {
        try {
//          session.getBasicRemote().sendText(String.format("%s (From Server，Session ID=%s)",message,session.getId()));
            CurrentUserDto currentUserDto = new CurrentUserDto();
            currentUserDto.setName(message);
            currentUserDto.setEmployeeNo(message);
            session.getBasicRemote().sendObject(currentUserDto);
        } catch (IOException e) {
            log.error("发送消息出错：{}", e.getMessage());
            e.printStackTrace();
        } catch (EncodeException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 群发消息
     * @param message
     * @throws IOException
     */
    public static void BroadCastInfo(String message) throws IOException {
        for (Session session : SessionSet) {
            if(session.isOpen()){
                SendMessage(session, message);
            }
        }
    }

    /**
     * 指定Session发送消息
     * @param sessionId
     * @param message
     * @throws IOException
     */
    public static void SendMessage(String message,String sessionId) throws IOException {
        Session session = null;
        for (Session s : SessionSet) {
            if(s.getId().equals(sessionId)){
                session = s;
                break;
            }
        }
        if(session!=null){
            SendMessage(session, message);
        }
        else{
            log.warn("没有找到你指定ID的会话：{}",sessionId);
        }
    }


}
