package com.patikadev.model;

import com.patikadev.helper.DBConnector;
import com.patikadev.helper.Helper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Users {
    private int id;
    private String name;
    private String username;
    private String password;
    private String type;


    public Users(int id, String name, String username, String password,String type) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.password = password;
        this.type=type;
    }

    public Users() {
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public static ArrayList<Users> getList() {
        ArrayList<Users> usersList=new ArrayList<>();
        String query="SELECT * FROM users";

        Statement st= null;
        try {
            st = DBConnector.getInstance().createStatement();
            ResultSet resultSet = st.executeQuery(query);
            while (resultSet.next()) {
                Users u = new Users(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("username"),
                        resultSet.getString("password"),
                        resultSet.getString("type"));

                usersList.add(u);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usersList;
    }

    //user nesnesini veri tabanına ekleyip eklemediği kontrol edildi.
    public static boolean isAdd(String name,String username,String password,String type){

        //DB de " " isimli bir user var mı yok mu kontrol eder.
        String query="INSERT INTO users (name,username,password,type)  VALUES (?,?,?,?)";
        Users findUser = getFetch(username);
        if(findUser != null){
            Helper.showMessage("Bu kullanıcı adı daha önceden eklenmistir!");
            return false;
        }

        try {
            PreparedStatement preparedStatement = DBConnector.getInstance().prepareStatement(query);

            preparedStatement.setString(1,name);
            preparedStatement.setString(2,username);
            preparedStatement.setString(3,password);
            preparedStatement.setString(4,type);

            int i = preparedStatement.executeUpdate();

            if(i==-1){
                Helper.showMessage("error");
            }

            return i!=-1;
        } catch (SQLException e) {
            System.out.println(e.getMessage());;
        }
        return true;
    }

    public static Users getFetch(String username){
        Users obj=null;
        String query="SELECT *FROM users WHERE username = ?";

        try {
            PreparedStatement pr=DBConnector.getInstance().prepareStatement(query);
            pr.setString(1,username);
            ResultSet rs=pr.executeQuery();

            if(rs.next()){
                obj=new Users(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("type")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return obj;
    }
    public static Users getFetch(String username,String password){
        Users obj=null;
        String query="SELECT *FROM users WHERE username = ? AND password= ?";

        try {
            PreparedStatement pr=DBConnector.getInstance().prepareStatement(query);
            pr.setString(1,username);
            pr.setString(2,password);
            ResultSet rs=pr.executeQuery();

            if(rs.next()){
                switch (rs.getString("type")){
                    case "operator":
                        obj=new Operator();
                        break;
                    case "educator":
                        obj=new Educator();
                        break;
                    case "student":
                        obj=new Student();
                        break;
                    default:
                        obj=new Users();
                        break;
                }
                obj.setId(rs.getInt("id"));
                obj.setName(rs.getString("name"));
                obj.setUsername(rs.getString("username"));
                obj.setPassword(rs.getString("password"));
                obj.setType(rs.getString("type"));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return obj;
    }
    public static Users getFetch(int id){
        Users obj=null;
        String query="SELECT *FROM users WHERE id = ?";

        try {
            PreparedStatement pr=DBConnector.getInstance().prepareStatement(query);
            pr.setInt(1,id);
            ResultSet rs=pr.executeQuery();

            if(rs.next()){
                obj=new Users(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("type")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return obj;
    }

    public static boolean delete(int id) {
        String query= "DELETE FROM users WHERE id = ?";
        ArrayList<Course> courses= Course.getListByUser(id);

        for(Course c : courses){
            Course.delete(c.getId());
        }

        try {
            PreparedStatement pr=DBConnector.getInstance().prepareStatement(query);
            pr.setInt(1,id);

            return pr.executeUpdate() != -1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public static boolean update(Users users) {
        String query = "UPDATE users SET name=?,username=?,password=?,type=? WHERE id=?";
        try {
            Users findUser = getFetch(users.username);
            if (findUser != null && findUser.getId()!=users.id){
                Helper.showMessage("Bu kullanıcı adı daha önceden eklenmistir!");
                return false;
            }
            //Type enum olarak tutuldu
            //farklı bir type girilip girilmediğini kontrol eder.
            if(isDifferentType(users)) {
                PreparedStatement pr = DBConnector.getInstance().prepareStatement(query);
                pr.setString(1, users.name);
                pr.setString(2, users.username);
                pr.setString(3, users.password);
                pr.setString(4, users.type);
                pr.setInt(5, users.id);
                return pr.executeUpdate() != -1;
            }else {
                Helper.showMessage("error");
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public static boolean isDifferentType(Users users){
        List<String> listType = new ArrayList<>();
        listType.add("operator");
        listType.add("educator");
        listType.add("student");

        for(String s : listType){
            if(s.equals(users.type)){return true;}
        }
        return false;
    }

    public static ArrayList<Users> searchUserList(String query) {
        ArrayList<Users> usersList=new ArrayList<>();

        Statement st= null;
        try {
            st = DBConnector.getInstance().createStatement();
            ResultSet resultSet = st.executeQuery(query);
            while (resultSet.next()) {
                Users u = new Users(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("username"),
                        resultSet.getString("password"),
                        resultSet.getString("type"));

                usersList.add(u);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usersList;
    }

    public static String searchQuery(String name,String username, String type){
        String query = " SELECT * FROM users WHERE name LIKE '%{{name}}%' AND username LIKE '%{{username}}%' AND type LIKE '%{{type}}%' ";

        query=query.replace("{{name}}",name);
        query=query.replace("{{username}}",username);
        query=query.replace("{{type}}",type);

        //System.out.println(query);
        return query;
    }
}
