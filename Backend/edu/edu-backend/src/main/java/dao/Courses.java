package dao;

import java.io.File;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dao.Inteface.CoursesInterface;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Repository;

@Repository("Courses")
public class Courses implements CoursesInterface {
    private static Connection conn;

    public Courses() throws ClassNotFoundException, SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/edu", "root", "root");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Override
    public List<Map<String, String>> getCourses(String userId) {
        List<Map<String, String>> coursesList = new ArrayList<>();
        String specId = null;
        String yearSemId = null;
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt
                    .executeQuery("select idSpecialization, idYearSemester from user where idUser ='" + userId + "';");
            rs.next();
            specId = rs.getString(1);
            yearSemId = rs.getString(2);

            ResultSet res = stmt.executeQuery("select c.idclass,c.name, c.numberofcredits, t.name from edu.class c inner join edu.user t on c.idTeacherC = t.idUser where c.idSpec ='" + specId
                    + "' and c.idYearSem ='" + yearSemId + "';");

            while (res.next()) {
                Map<String, String> courses = new HashMap<>();
                courses.put("idclass", res.getString(1));
                courses.put("name", res.getString(2));
                courses.put("credits", res.getString(3));
                courses.put("teachername", res.getString(4));
                coursesList.add(courses);
            }
            return coursesList;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return coursesList;
    }

    @Override
    public List<Map<String, String>> getCoursesForTeacher(String teacherId) {
        List<Map<String, String>> coursesList = new ArrayList<>();
        String specId = null;
        String yearSemId = null;
        try {
            Statement stmt = conn.createStatement();

            ResultSet res = stmt.executeQuery("select c.idclass,c.name, c.numberofcredits, t.name from edu.class c inner join edu.user t on c.idTeacherC = t.idUser where c.idTeacherC ='" + teacherId + "';");

            while (res.next()) {
                Map<String, String> courses = new HashMap<>();
                courses.put("idclass", res.getString(1));
                courses.put("name", res.getString(2));
                courses.put("credits", res.getString(3));
                courses.put("teachername", res.getString(4));
                coursesList.add(courses);
            }
            return coursesList;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return coursesList;
    }

    @Override
    public List<String> getCourseFiles(String courseName) {

        List<String> response = new ArrayList<String>();

        if (new File(System.getProperty("user.dir") + "\\courseFiles\\" + courseName).exists()) {
            File dir = new File(System.getProperty("user.dir") + "\\courseFiles\\" + courseName + "\\");

            for (File file : dir.listFiles()) {
                response.add(file.getName());
            }
        }

        return response;
    }

    @Override
    public List<String> getCourseUserProjects(String courseName, String userId) {

        List<String> response = new ArrayList<String>();

        if (new File(System.getProperty("user.dir") + "\\projects\\" + courseName + "\\" + userId).exists() && findUserById(userId)) {
            File dir = new File(System.getProperty("user.dir") + "\\projects\\" + courseName + "\\" + userId);

            for (File file : dir.listFiles()) {
                response.add(file.getName());
            }
        }

        return response;
    }


    @Override
    public List<Map<String, String>> getCourseProjects(String courseName) throws SQLException {

        if (new File(System.getProperty("user.dir") + "\\projects\\" + courseName).exists()) {
            List<Map<String, String>> response = new ArrayList<>();
            File dir = new File(System.getProperty("user.dir") + "\\projects\\" + courseName);

            for (File file : dir.listFiles()) {
                if (file.isDirectory()) {
                    Statement stmt = conn.createStatement();
                    ResultSet rs = stmt.executeQuery("select name from user where idUser = '"+file.getName()+"';");
                    if(rs.next()){
                        String userName = rs.getString(1);
                        for (File studentFile : file.listFiles()) {
                            Map<String,String> fileDetails = new HashMap<>();
                            fileDetails.put("username",userName);
                            fileDetails.put("filename",studentFile.getName());
                            fileDetails.put("userId",file.getName());
                            response.add(fileDetails);
                        }
                    }

                } else {
                    System.out.println("There is a file in the wrong place! " + courseName);
                }
            }
            return response;
        }

        return null;
    }


    @Override
    public Resource sendStudentProject(String fileName, String courseName, String userId) {

        if(new File(System.getProperty("user.dir")+"\\projects\\"+courseName+"\\"+userId+"\\"+fileName).exists()){
            Path filePath = Paths.get(System.getProperty("user.dir")+"\\projects\\"+courseName+"\\"+userId+"\\"+fileName);
            try {
                Resource fileToReturn = new UrlResource(filePath.toUri());
                return fileToReturn;

            } catch (MalformedURLException e) {
                return null;
            }
        }
        return null;
    }


    private boolean findUserById(String userId) {
        try {
            Statement stmt = conn.createStatement();

            ResultSet res = stmt.executeQuery("select name from user where idUser ='" + userId + "';");

            if (res.next()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
