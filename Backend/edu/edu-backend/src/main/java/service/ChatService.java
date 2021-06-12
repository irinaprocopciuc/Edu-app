package service;

import dao.Inteface.ChatInterface;
import dao.Inteface.CommentInterface;
import model.CommentDetails;
import model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ChatService {

    private final ChatInterface conn;

    @Autowired
    public ChatService(@Qualifier("Chat") ChatInterface connection) {
        this.conn = connection;
    }
    public Map<String,String> addNewMessage(Message message) throws SQLException {

        Map<String,String> response = new HashMap<String,String>();

        if(conn.findUser(message.getIdSender())==-1){
            response.put("error","Couldn't find user");
            response.put("code","400");
            return response;
        }

        if(conn.findUser(message.getIdReceiver())==-1){
            response.put("error","Couldn't find user");
            response.put("code","400");
            return response;
        }

        int messageId = conn.addNewMessage(message);
        if(messageId==-1){
            response.put("error","Couldn't add the message");
            response.put("code","400");
            return response;
        }
        if(messageId==-2){
            response.put("error","Course or file cannot be found!");
            response.put("code","400");
            return response;
        }

        response.put("error","Message added!");
        response.put("code","200");
        return response;

    }

    public List<Map<String,String>> getMessages(String user1, String user2){
        return conn.getMessages(user1,user2);
    }

}
