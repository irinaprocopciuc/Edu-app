package dao.Inteface;

import dao.Comment;
import model.CommentDetails;
import model.EditComment;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface CommentInterface {

    List<Map<String,String>> getComments(String fileName, String courseName);

    int addComment(CommentDetails commentDetails) throws SQLException;

    int deleteComment(int commentId);

    int editComment(EditComment editComment);

    int checkCommentRights(CommentDetails commentDetails);

    int checkUserRights(EditComment editComment);

    int findUser(String userId);


}
