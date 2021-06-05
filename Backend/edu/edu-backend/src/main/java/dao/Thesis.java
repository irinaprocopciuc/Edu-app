package dao;

import dao.Inteface.ThesisInterface;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Repository("Thesis")
public class Thesis implements ThesisInterface {

    private static Connection conn;

    public Thesis() throws ClassNotFoundException, SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/edu", "root", "root");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Override
    public List<Map<String, String>> getAllThesis() {
        List<Map<String, String>> thesisList = new ArrayList<>();

        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt
                    .executeQuery("select t.idTheme, t.type, t.name, t.details, t.technologies, t.idTeacher, u.name, t.idStudent from proposedtheme t inner join user u on t.idTeacher = u.idUser;");
            while (rs.next()) {
                Map<String, String> user = new HashMap<>();
                user.put("idTheme", rs.getString(1));
                user.put("type", rs.getString(2));
                user.put("name", rs.getString(3));
                user.put("details", rs.getString(4));
                user.put("technologies", rs.getString(5));
                user.put("idTeacher", rs.getString(6));
                user.put("teacherName", rs.getString(7));
                user.put("idStudent", rs.getString(8));
                thesisList.add(user);
            }
            return thesisList;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return thesisList;
    }

    @Override
    public List<Map<String, String>> getChosenTheme(String idStudent) {
        List<Map<String, String>> thesisList = new ArrayList<>();

        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt
                    .executeQuery("select t.idTheme, t.type, t.name, t.details, t.technologies, t.idTeacher, u.name, t.idStudent from proposedtheme t inner join user u on t.idTeacher = u.idUser where t.idStudent ='"+idStudent+"';");
            while (rs.next()) {
                Map<String, String> user = new HashMap<>();
                user.put("idTheme", rs.getString(1));
                user.put("type", rs.getString(2));
                user.put("name", rs.getString(3));
                user.put("details", rs.getString(4));
                user.put("technologies", rs.getString(5));
                user.put("idTeacher", rs.getString(6));
                user.put("teacherName", rs.getString(7));
                user.put("idStudent", rs.getString(8));
                thesisList.add(user);
            }
            return thesisList;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return thesisList;
    }

    public List<Map<String, String>> getTeacherThemes(String idTeacher) {
        List<Map<String, String>> thesisList = new ArrayList<>();

        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt
                    .executeQuery("select t.idTheme, t.type, t.name, t.details, t.technologies,t.idStudent, u.name from proposedtheme t inner join user u on t.idStudent = u.idUser where t.idTeacher ='"+idTeacher+"';");
            while (rs.next()) {
                Map<String, String> user = new HashMap<>();
                user.put("idTheme", rs.getString(1));
                user.put("type", rs.getString(2));
                user.put("name", rs.getString(3));
                user.put("details", rs.getString(4));
                user.put("technologies", rs.getString(5));
                user.put("idStudent", rs.getString(6));
                user.put("studentName", rs.getString(7));
                thesisList.add(user);
            }
            return thesisList;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return thesisList;
    }


    @Override
    public int chooseTheme(model.Thesis thesis) {
        try {
            String query = "update proposedtheme set idStudent = ? where idTheme = ?;";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1,thesis.getIdUser());



            stmt.setString(2,thesis.getIdTheme());
            if(stmt.executeUpdate()>0){
                return 0;
            }

            return -1;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return -2;
    }
}
