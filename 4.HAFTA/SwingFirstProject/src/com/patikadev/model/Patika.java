package com.patikadev.model;

import com.patikadev.helper.DBConnector;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Patika {
    private int id;
    private String name;

    public Patika(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static ArrayList<Patika> getList(){
        ArrayList<Patika> patikaList = new ArrayList<>();
        String query = "SELECT * FROM patika";


        Statement st = null;

        try {
            st= DBConnector.getInstance().createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()){
                Patika patika=new Patika(rs.getInt("id"), rs.getString("name"));

                patikaList.add(patika);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return patikaList;
    }

    public static boolean isAdd(String name) {
        String query= "INSERT INTO patika (name) VALUES (?)";
        try {
            PreparedStatement pr=DBConnector.getInstance().prepareStatement(query);
            pr.setString(1,name);

            return pr.executeUpdate()!=-1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public static boolean isUpdate(int id,String name){
        String query= "UPDATE patika SET name = ? WHERE id = ?";
        PreparedStatement pr = null;
        try {
            pr = DBConnector.getInstance().prepareStatement(query);
            pr.setString(1,name);
            pr.setInt(2,id);

            return pr.executeUpdate() !=-1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    //patika tablosunda id ye göre bir patika döndürür.
    public static Patika getFetch(int id){
        Patika patika =null;


        try {
            PreparedStatement pr= DBConnector.getInstance().prepareStatement("SELECT * FROM patika WHERE id = ?");
            pr.setInt(1,id);

            ResultSet rs = pr.executeQuery();
            if (rs.next()){
                patika=new Patika(rs.getInt("id"), rs.getString("name"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return patika;
    }
    public static boolean delete(int selected_id) {
        String query= "DELETE FROM patika WHERE id =?";
        ArrayList<Course> courseArrayList = Course.getList();
        for(Course c :  courseArrayList){
            if(c.getPatika().getId()==selected_id){
                Course.delete(c.getId());
            }
        }

        try {
            PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
            pr.setInt(1,selected_id);

             return pr.executeUpdate()!=-1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }
}
