package dao;

import dao.Inteface.ChatInterface;
import model.CommentDetails;
import model.Message;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Repository("Chat")
public class Chat implements ChatInterface {

    private static Connection conn;

    public Chat() throws ClassNotFoundException, SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/edu", "root", "root");
        } catch (Exception e) {
            System.out.println(e);
        }
    }


    @Override
    public int addNewMessage(Message message) {
        try {
                String query = "insert into message (idSender,idReceiver,message,date) values (?,?,?,?)";
                PreparedStatement stmt = null;

                stmt = conn.prepareStatement(query);

                stmt.setString(1,message.getIdSender());
                stmt.setString(2,message.getIdReceiver());
                stmt.setString(3,message.getMessage());

                DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                df.setTimeZone(TimeZone.getTimeZone("GTM"));
                stmt.setString(4,df.format(message.getDate()));

                if(stmt.executeUpdate()>0){
                    query = "select idMessage from message where idSender=? and idReceiver = ? and message = ? and date = ? ;";

                    stmt = conn.prepareStatement(query);

                    stmt.setString(1,message.getIdSender());
                    stmt.setString(2,message.getIdReceiver());
                    stmt.setString(3,message.getMessage());
                    stmt.setString(4,df.format(message.getDate()));
                    ResultSet rs = stmt.executeQuery();
                    if(rs.next()){
                        return rs.getInt(1);
                    }
                    rs.close();
                    stmt.close();
                }
                stmt.close();
                return -1;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return -1;
    }


    @Override
    public List<Map<String,String>> getMessages(String user1, String user2){
        try {
             List<Map<String,String>> comments = new ArrayList<Map<String,String>>();
                String query = "select idSender,idReceiver,message,date from message where idSender in (" +user1+ ","+user2 +") and idReceiver in(" +user1+ "," +user2 +");";
                PreparedStatement stmt = conn.prepareStatement(query);
                ResultSet rs = stmt.executeQuery();

                Map<String,String> comment;
                while(rs.next()){
                    comment= new HashMap<String,String>();
                    comment.put("idSender",rs.getString(1));
                    comment.put("idReceiver",rs.getString(2));
                    comment.put("message",rs.getString(3));
                    comment.put("date",rs.getString(4));
                    comments.add(comment);
                }

                rs.close();
                stmt.close();
                return comments;

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
