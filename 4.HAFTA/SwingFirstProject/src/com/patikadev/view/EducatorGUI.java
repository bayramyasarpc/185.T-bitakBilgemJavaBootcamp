package com.patikadev.view;

import com.patikadev.helper.Config;
import com.patikadev.helper.Helper;
import com.patikadev.model.Course;
import com.patikadev.model.CourseContent;
import com.patikadev.model.Educator;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class EducatorGUI extends JFrame{
    private JPanel wrapper;
    private JPanel pnl_top;
    private JPanel pnl_bottom;
    private JTabbedPane tabbedPane1;
    private JScrollPane scrl_egitim_list;
    private JTable tbl_egitim_list;
    private JButton btn_syf_yenile;
    private JPanel pnl_egitim_form;
    private JTextField fld_egitim_name;
    private JTextField fld_egitim_baslık;
    private JTextField fld_egitim_acıklama;
    private JTextField fld_egitim_youtube;
    private JTextField fld_egitim_quiz;
    private JTextField fld_egitim_egitimID;
    private JButton btn_egitim_add;
    private JScrollPane scrl_icerik_list;
    private JTable tbl_icerik_list;
    private JPanel pln_sh;
    private JTextField fld_sh_baslik;
    private JTextField fld_sh_courseID;
    private JButton btn_sh_icerik;

    //Eğitimler
    private DefaultTableModel mdl_egitim_list;
    private Object[] row_egitim_list;
    private Educator educator;

    //İçerikler
    private DefaultTableModel mdl_icerik_list;
    private Object[] row_icerik_list;

    public  EducatorGUI(Educator educator){
        this.educator=educator;
        Helper.setLayout();
        add(wrapper);
        setSize(1000,500);

        int x= Helper.screenLocationCenter("x",getSize());
        int y=Helper.screenLocationCenter("y",getSize());
        setLocation(x,y);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);//UI kapattıgımızda arka plandan da kapanmasını sağlar
        setTitle(Config.PROJECT_TITLE);
        setVisible(true);

        //Lessons
        mdl_egitim_list =  new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        Object[] col_egitim_list={"ID","Ders Adı","Programlama Dili","Patika"};
        mdl_egitim_list.setColumnIdentifiers(col_egitim_list);

        row_egitim_list= new Object[col_egitim_list.length];

        tbl_egitim_list.setModel(mdl_egitim_list);
        tbl_egitim_list.getTableHeader().setReorderingAllowed(false);
        tbl_egitim_list.getColumnModel().getColumn(0).setMaxWidth(75);

        printEgitimFromDB();

        tbl_egitim_list.getSelectionModel().addListSelectionListener(e -> {
            if(tbl_egitim_list.getSelectedRow()!=-1){
                String egitim_adi = tbl_egitim_list.getValueAt(tbl_egitim_list.getSelectedRow(), 1).toString();
                String egitim_id = tbl_egitim_list.getValueAt(tbl_egitim_list.getSelectedRow(), 0).toString();
                fld_egitim_name.setText(egitim_adi);
                fld_egitim_egitimID.setText(egitim_id);
            }
        });

        //##Lessons

        //İcerik
        mdl_icerik_list=new DefaultTableModel();
        Object[] col_icerik_list={"ID","Title","Description","Youtube","Quiz","CourseID"};
        mdl_icerik_list.setColumnIdentifiers(col_icerik_list);

        row_icerik_list=new Object[col_icerik_list.length];

        tbl_icerik_list.setModel(mdl_icerik_list);
        tbl_icerik_list.getTableHeader().setReorderingAllowed(false);
        tbl_icerik_list.getColumnModel().getColumn(0).setMaxWidth(75);

        printIcerikFromDB();


        //##İcerik

        btn_syf_yenile.addActionListener(e -> {
            printEgitimFromDB();
        });

        btn_egitim_add.addActionListener(e -> {
            if(Helper.isEmpty(fld_egitim_baslık) || Helper.isEmpty(fld_egitim_acıklama) || Helper.isEmpty(fld_egitim_name )||
                    Helper.isEmpty(fld_egitim_quiz)|| Helper.isEmpty(fld_egitim_youtube) || Helper.isEmpty(fld_egitim_egitimID)){
                Helper.showMessage("fill");
            }else{
                String adi=fld_egitim_name.getText();
                String baslik=fld_egitim_baslık.getText();
                String aciklama=fld_egitim_acıklama.getText();
                String youtube=fld_egitim_youtube.getText();
                String quiz=fld_egitim_quiz.getText();
                int course_id= Integer.parseInt(fld_egitim_egitimID.getText());

                if(CourseContent.isAdd(baslik,aciklama,youtube,quiz,course_id)){
                    Helper.showMessage("done");

                    fld_egitim_egitimID.setText(null);
                    fld_egitim_name.setText(null);
                    fld_egitim_acıklama.setText(null);
                    fld_egitim_baslık.setText(null);
                    fld_egitim_quiz.setText(null);
                    fld_egitim_youtube.setText(null);
                }
                else{
                    Helper.showMessage("error");
                }

            }
        });
    }

    private void printIcerikFromDB() {
        DefaultTableModel defaultTableModel= (DefaultTableModel) tbl_icerik_list.getModel();
        defaultTableModel.setRowCount(0);

        int i=1;
        for(CourseContent courseContent : CourseContent.getList()){
            row_icerik_list[0]=i++;
            row_icerik_list[1]=courseContent.getTitle();
            row_icerik_list[2]=courseContent.getDescription();
            row_icerik_list[3]=courseContent.getYoutube();
            row_icerik_list[4]=courseContent.getQuiz();
            row_icerik_list[5]=courseContent.getCourseID();

            mdl_icerik_list.addRow(row_icerik_list);
        }


    }

    private void printEgitimFromDB() {
        //Her seferinde tabloyu sıfırlamak gerekir.
        DefaultTableModel defaultTableModel= (DefaultTableModel) tbl_egitim_list.getModel();
        defaultTableModel.setRowCount(0);
        int i=1;
        for(Course c : Course.getListByUser(educator.getId())) {

                row_egitim_list[0] = i++;
                row_egitim_list[1] = c.getName();
                row_egitim_list[2] = c.getLang();
                row_egitim_list[3] = c.getPatika().getName();

                mdl_egitim_list.addRow(row_egitim_list);
        }
    }
}
