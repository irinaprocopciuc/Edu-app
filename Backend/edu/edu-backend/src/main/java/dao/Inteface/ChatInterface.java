package dao.Inteface;

import model.CommentDetails;
import model.Message;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface ChatInterface {

    int addNewMessage(Message message) throws SQLException;

    int findUser(String userId);

    List<Map<String,String>> getMessages(String user1, String user2);
}
