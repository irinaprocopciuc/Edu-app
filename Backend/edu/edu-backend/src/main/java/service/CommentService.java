package service;

import dao.Comment;
import dao.Inteface.CommentInterface;
import model.CommentDetails;
import model.EditComment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CommentService {

    private final CommentInterface conn;

    @Autowired
    public CommentService(@Qualifier("CommentDB") CommentInterface connection) {
        this.conn = connection;
    }

    public Map<String,String> addComment(CommentDetails commentDetails) throws SQLException {

        Map<String,String> response = new HashMap<String,String>();

        if(conn.findUser(commentDetails.getUserId())==-1){
            response.put("error","Couldn't find user");
            response.put("code","400");
            return response;
        }

        int messageId = conn.addComment(commentDetails);
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

    public List<Map<String,String>> getComments(String fileName, String courseName){
        return conn.getComments(fileName,courseName);
    }

    public int deleteComment(CommentDetails commentDetails){

        int commentId= conn.checkCommentRights(commentDetails);

        if(commentId!=-1){
            return conn.deleteComment(commentId);
        }
        return -3;

    }

    public int editComment(EditComment editComment){

        int rights = conn.checkUserRights(editComment);

        if(rights==0){
            return conn.editComment(editComment);
        }
        return -3;

    }

}
