package com.cs.analyzefood.chat;

import com.cs.analyzefood.entity.vo.chat.ContentVo;
import com.cs.analyzefood.util.JsonUtil;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Component
@ServerEndpoint("/webSocket/chat/{roomName}")
public class WsServer {

    //使用map来收集session，key为roomName，value为同一个房间的用户集合
    //concurrentMap的key不存在时报错，不是返回null
    private static final Map<String, Set<Session>> rooms = new ConcurrentHashMap();


    @OnOpen
    public void connect(@PathParam("roomName") String roomName,Session session) throws Exception {
        // 将session按照房间名来存储，将各个房间的用户隔离
        if (!rooms.containsKey(roomName)) {
            // 创建房间不存在时，创建房间
            Set<Session> room = new HashSet<>();
            // 添加用户
            room.add(session);
            rooms.put(roomName, room);
        } else {
            // 房间已存在，直接添加用户到相应的房间
            rooms.get(roomName).add(session);
        }
        System.out.println("a client has connected!");
    }

    @OnClose
    public void disConnect(@PathParam("roomName") String roomName, Session session) {
        rooms.get(roomName).remove(session);
        System.out.println("a client has disconnected!");
    }


    //接受消息(房间号,消息)
    @OnMessage
    public void receiveMsg(@PathParam("roomName") String roomName, String content, Session session) throws Exception {
        System.out.println(roomName);
        ContentVo vo = JsonUtil.fromJson(content, ContentVo.class);
        String msg = vo.getMsg();
        String headImg = vo.getHeadImg();
        System.out.println(msg);
        System.out.println(headImg);
        String message;
        for (Session session1 : rooms.get(roomName)) {
            //判断是否发送给自己
            if (session1.equals(session)) {
                message = getMessage(msg, headImg, true);
            } else {
                message = getMessage(msg, headImg, false);
            }

            //发送
            session1.getBasicRemote().sendText(message);

        }
    }

    //封装
    private String getMessage(String msg, String headImg, boolean isLeft){
        //创建时间
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date = new Date();
        String time = simpleDateFormat.format(date);
        String message;
        if (isLeft) {
            message = "<div class='clearfloat'><div class='author-name'><small class='chat-date'>"+time+"</small> </div><div class='right'> <div class='chat-message'> "+ msg + "</div><div class='chat-avatars'><img src='/AnalyzeFood/"+headImg+"' /></div> </div> </div>";
        } else {
            message = "<div class='clearfloat'><div class='author-name'><small class='chat-date'>"+time+"</small> </div><div class='left'> <div class='chat-avatars'><img src='/AnalyzeFood/"+headImg+"'/></div><div class='chat-message'> "+ msg + "</div> </div> </div>";
        }
        return message;
    }


    @OnError
    public void onError(Session session,Throwable error) {
        error.printStackTrace();
    }
}