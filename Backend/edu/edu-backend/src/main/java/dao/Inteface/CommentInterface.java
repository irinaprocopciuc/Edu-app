package dao.Inteface;

import model.CommentDetails;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface CommentInterface {

    List<Map<String,String>> getComments(String fileName, String courseName);

    int addComment(CommentDetails commentDetails) throws SQLException;

    int findUser(String userId);

}
