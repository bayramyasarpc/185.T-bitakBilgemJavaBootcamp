package com.patikadev.model;

import com.patikadev.helper.DBConnector;
import com.patikadev.helper.Helper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class CourseContent {

    private String title;
    private String description;
    private String youtube;
    private String quiz;
    private int courseID;

    public CourseContent(String title, String description, String youtube, String quiz, int courseID) {
        this.title = title;
        this.description = description;
        this.youtube = youtube;
        this.quiz = quiz;
        this.courseID = courseID;
    }

    public static boolean isAdd(String baslik, String aciklama, String youtube, String quiz, int course_id) {
        String query ="INSERT INTO course_content (title,description,youtube,quiz,courseID) VALUES (?,?,?,?,?) ";
            try {
                PreparedStatement pr= DBConnector.getInstance().prepareStatement(query);
                pr.setString(1,baslik);
                pr.setString(2,aciklama);
                pr.setString(3,youtube);
                pr.setString(4,quiz);
                pr.setInt(5,course_id);

                int i = pr.executeUpdate();
                if(i==-1){
                    Helper.showMessage("error");
                }

            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }

        return true;
    }

    public static ArrayList<CourseContent> getList() {
        String query="SELECT *FROM course_content";
        ArrayList<CourseContent> list=new ArrayList<>();
        CourseContent obj=null;
        try {
            Statement st=DBConnector.getInstance().createStatement();
            ResultSet rs = st.executeQuery(query);

            while(rs.next()){
                obj=new CourseContent(
                rs.getString("title"),
                rs.getString("description"),
                rs.getString("youtube"),
                rs.getString("quiz"),
                rs.getInt("courseID"));

                list.add(obj);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getYoutube() {
        return youtube;
    }

    public void setYoutube(String youtube) {
        this.youtube = youtube;
    }

    public String getQuiz() {
        return quiz;
    }

    public void setQuiz(String quiz) {
        this.quiz = quiz;
    }

    public int getCourseID() {
        return courseID;
    }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }
}
