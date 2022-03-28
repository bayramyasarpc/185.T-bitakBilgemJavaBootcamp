package com.patikadev.model;

import com.patikadev.helper.DBConnector;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Course {
    private int id;
    private int user_id;
    private int patika_id;
    private String name;
    private String lang;

    private Patika patika;
    private Users educator;

    public Course(int id, int user_id, int patika_id, String name, String lang) {
        this.id = id;
        this.user_id = user_id;
        this.patika_id = patika_id;
        this.name = name;
        this.lang = lang;
        this.patika =Patika.getFetch(patika_id);
        this.educator = Users.getFetch(user_id);
    }

    public static ArrayList<Course> getList() {
        ArrayList<Course> courses= new ArrayList<>();
        Course obj =null;

        try {
            Statement st = DBConnector.getInstance().createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM course");

            while(rs.next()){
                obj=new Course(
                        rs.getInt("id"),
                        rs.getInt("user_id"),
                        rs.getInt("patika_id"),
                        rs.getString("name"),
                        rs.getString("lang"));
                courses.add(obj);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return courses;
    }

    public static boolean delete(int id) {
        String query= "DELETE FROM course WHERE id = ?";
        try {
            PreparedStatement pr=DBConnector.getInstance().prepareStatement(query);
            pr.setInt(1,id);

            return pr.executeUpdate() != -1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public static ArrayList<Course> getListByUser(int user_id) {
        ArrayList<Course> courses= new ArrayList<>();
        Course obj =null;

        try {
            Statement st = DBConnector.getInstance().createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM course WHERE user_id = "+user_id);

            while (rs.next()){
                obj=new Course(
                        rs.getInt("id"),
                        rs.getInt("user_id"),
                        rs.getInt("patika_id"),
                        rs.getString("name"),
                        rs.getString("lang"));
                courses.add(obj);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return courses;
    }
    public static boolean addCourse(int user_id,int patika_id,String name, String lang) {
        String query= "INSERT INTO course (user_id,patika_id,name,lang) VALUES (?,?,?,?)";

        try {
            PreparedStatement pr= DBConnector.getInstance().prepareStatement(query);
            pr.setInt(1,user_id);
            pr.setInt(2,patika_id);
            pr.setString(3,name);
            pr.setString(4,lang);

            return pr.executeUpdate() !=-1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getPatika_id() {
        return patika_id;
    }

    public void setPatika_id(int patika_id) {
        this.patika_id = patika_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public Patika getPatika() {
        return patika;
    }

    public void setPatika(Patika patika) {
        this.patika = patika;
    }

    public Users getEducator() {
        return educator;
    }

    public void setEducator(Users educator) {
        this.educator = educator;
    }
}
