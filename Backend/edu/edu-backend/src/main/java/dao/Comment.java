package dao;

import dao.Inteface.CommentInterface;
import model.CommentDetails;
import model.EditComment;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Repository("CommentDB")
public class Comment implements CommentInterface {

    private static Connection conn;

    public Comment() throws ClassNotFoundException, SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/edu", "root", "root");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Override
    public int addComment(CommentDetails commentDetails) {
        try {
            if(new File(System.getProperty("user.dir")+"\\courseFiles\\"+commentDetails.getCourseName()+"\\"+commentDetails.getFileName()).exists()){
                String query = "insert into comment (fileName,courseName,userId,message,date) values (?,?,?,?,?)";
                PreparedStatement stmt = null;

                stmt = conn.prepareStatement(query);

                stmt.setString(1,commentDetails.getFileName());
                stmt.setString(2,commentDetails.getCourseName());
                stmt.setString(3,commentDetails.getUserId());
                stmt.setString(4,commentDetails.getMessage());

                DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                df.setTimeZone(TimeZone.getTimeZone("GTM"));
                stmt.setString(5,df.format(commentDetails.getDate()));

                if(stmt.executeUpdate()>0){
                    query = "select commentId from comment where fileName=? and courseName = ? and userId = ? and message = ? and date = ?;";

                    stmt = conn.prepareStatement(query);

                    stmt.setString(1,commentDetails.getFileName());
                    stmt.setString(2,commentDetails.getCourseName());
                    stmt.setString(3,commentDetails.getUserId());
                    stmt.setString(4,commentDetails.getMessage());
                    stmt.setString(5,df.format(commentDetails.getDate()));
                    ResultSet rs = stmt.executeQuery();
                    if(rs.next()){
                        return rs.getInt(1);
                    }
                    rs.close();
                    stmt.close();
                }
                stmt.close();
                return -1;//change this for diffrent return info - this case is for not being able to add the comment
            }
            return -2;//this in the case the file does not exist
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return -1;//this is for throwing error
    }

    @Override
    public int deleteComment(int commentId) {
        try {
            String query = "delete from comment where commentId = ?;";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1,commentId);

            if(stmt.executeUpdate()>0){
                return 0;
            }

            return -1;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return -2;
    }

    @Override
    public int editComment(EditComment editComment) {
        try {
            String query = "update comment set message = ?, date = ? where commentId = ?;";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1,editComment.getMessage());

            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            df.setTimeZone(TimeZone.getTimeZone("GTM"));
            stmt.setString(2,df.format(editComment.getDate()));

            stmt.setString(3,editComment.getCommentId());
            if(stmt.executeUpdate()>0){
                return 0;
            }

            return -1;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return -2;
    }

    @Override
    public int checkUserRights(EditComment editComment) {
        try {

            String query = "select userId from comment where commentId= ?;";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1,editComment.getCommentId());
            ResultSet rs = stmt.executeQuery();

            if(rs.next()){
                if(editComment.getUserID().equals(rs.getString(1))){
                    return 0;
                }
                return -1;
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return -1;
    }

    @Override
    public int checkCommentRights(CommentDetails commentDetails) {
        try {

            String query = "select commentId from comment where fileName= ? and courseName = ? and userId = ?  and date = ?;";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1,commentDetails.getFileName());
            stmt.setString(2,commentDetails.getCourseName());
            stmt.setString(3,commentDetails.getUserId());

            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            df.setTimeZone(TimeZone.getTimeZone("GTM"));
            stmt.setString(4,df.format(commentDetails.getDate()));
            ResultSet rs = stmt.executeQuery();

            if(rs.next()){
                return rs.getInt(1);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return -1;
    }

    @Override
    public List<Map<String,String>> getComments(String fileName, String courseName){
        try {
            if(new File(System.getProperty("user.dir")+"\\courseFiles\\"+courseName+"\\"+fileName).exists()){
                List<Map<String,String>> comments = new ArrayList<Map<String,String>>();
                String query = "select commentId,userId,message,date from comment where fileName=? and courseName = ?;";
                PreparedStatement stmt = conn.prepareStatement(query);
                stmt.setString(1,fileName);
                stmt.setString(2,courseName);
                ResultSet rs = stmt.executeQuery();

                Map<String,String> comment;
                while(rs.next()){
                    comment= new HashMap<String,String>();
                    comment.put("commentId",rs.getString(1));
                    comment.put("userId",rs.getString(2));
                    comment.put("message",rs.getString(3));
                    comment.put("date",rs.getString(4));
                    comments.add(comment);
                }

                rs.close();
                stmt.close();
                return comments;
            }
            return null;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public int findUser(String userId) {
        try {
            String query = "select idUser from user where idUser = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1,userId);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                return rs.getInt(1);
            }
            rs.close();
            stmt.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return -1;
    }
}
