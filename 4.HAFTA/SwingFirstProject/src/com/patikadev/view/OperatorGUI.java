package com.patikadev.view;

import com.patikadev.helper.Config;
import com.patikadev.helper.Helper;
import com.patikadev.helper.Item;
import com.patikadev.model.Course;
import com.patikadev.model.Operator;
import com.patikadev.model.Patika;
import com.patikadev.model.Users;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class OperatorGUI extends JFrame {
    //Users
    private JPanel wrapper;
    private JTabbedPane tab_operator;
    private JLabel lbl_welcome;
    private JPanel pnl_top;
    private JButton btn_logout;
    private JPanel pnl_user_list;
    private JScrollPane scrl_user_list;
    private JTable tbl_user_list;
    private JPanel pnl_user_form;
    private JTextField fld_user_name;
    private JTextField fld_user_username;
    private JPasswordField fld_user_password;
    private JComboBox cmb_user_type;
    private JButton btn_user_form;
    private JTextField fld_user_id;
    private JButton btn_user_delete;
    private JTextField fld_sh_user_name;
    private JTextField fld_sh_user_username;
    private JComboBox cmb_sh_user_type;
    private JButton btn_user_sh;
    private JPanel pnl_patika_list;
    private JTable tbl_patika_list;
    private JScrollPane scrl_patika_list;
    private JPanel pnl_patika_add;
    private JTextField fld_patika_name;
    private JButton btn_patika_add;
    private JPanel pnl_user_sh;
    private JPanel pnl_course_list;
    private JScrollPane scrl_course_list;
    private JTable tbl_course_list;
    private JPanel pnl_course_add;
    private JTextField fld_course_name;
    private JTextField fld_course_lang;
    private JComboBox cmb_course_patika;
    private JComboBox cmb_course_user;
    private JButton btn_course_add;

    private DefaultTableModel mdl_user_list;
    private Object[] row_user_list;

    //Patika
    private DefaultTableModel mdl_patika_list;
    private Object[] row_patika_list;
    private JPopupMenu patika_popup_menu;

    //course
    private DefaultTableModel mdl_course_list;
    private Object[] row_course_list;

    private final Operator operator;

    public OperatorGUI(Operator operator){
        this.operator=operator;

        add(wrapper);
        setSize(1000,500);

        int x= Helper.screenLocationCenter("x",getSize());
        int y=Helper.screenLocationCenter("y",getSize());
        setLocation(x,y);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);//UI kapattıgımızda arka plandan da kapanmasını sağlar
        setTitle(Config.PROJECT_TITLE);
        setVisible(true);

        //Kendi ayarlamızı burada yapabilriz.
        lbl_welcome.setText("Hosgeldiniz! "+operator.getName());


        //Model user list
        mdl_user_list=new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                if(column==0){//id değerlerinin değiştirilebilir özelliğini kapattım.
                    return false;
                }
                return super.isCellEditable(row, column);
            }
        };


        Object[] col_user_list={"ID","Ad Soyad","Kullanıcı Adı","Şifresi","Üyelik Tipi"};
        mdl_user_list.setColumnIdentifiers(col_user_list);

        //objenin pointerı
        row_user_list=new Object[col_user_list.length];
        printUserfromDB();

        tbl_user_list.setModel(mdl_user_list);
        //table header sıralamasının sabitlenmesi
        tbl_user_list.getTableHeader().setReorderingAllowed(false);

        //table da seçilen row un id sini silmek için field değerini seçilen id ile atandı.
        tbl_user_list.getSelectionModel().addListSelectionListener(e -> {
            try {
                if(tbl_user_list.getSelectedRow()!=-1){
                    String select_user_row= tbl_user_list.getValueAt(tbl_user_list.getSelectedRow(),0).toString();
                    fld_user_id.setText(select_user_row);
                }
            }catch (Exception e1){
                System.out.println(e1.getMessage());
            }
        });

        //table dinleyerek tabloda update işlemi gerçekleşiyorsa bunu DB de güncelleme işlemi yapılacaktır.
        tbl_user_list.getModel().addTableModelListener(e -> {
            if(e.getType() == TableModelEvent.UPDATE){

                int user_id= (int) tbl_user_list.getValueAt(tbl_user_list.getSelectedRow(),0);
                String user_name= (String) tbl_user_list.getValueAt(tbl_user_list.getSelectedRow(),1);
                String user_username= (String) tbl_user_list.getValueAt(tbl_user_list.getSelectedRow(),2);
                String user_password= (String) tbl_user_list.getValueAt(tbl_user_list.getSelectedRow(),3);
                String user_type= (String)  tbl_user_list.getValueAt(tbl_user_list.getSelectedRow(),4);

                Users u=new Users(user_id,user_name,user_username,user_password,user_type);

                if(Users.update(u)){
                    Helper.showMessage("done");
                }
                printUserfromDB();
                printEducatorToCombobox();
                printCourseFromDB();

            }
        });//## user list

        //Model Patika List
        //Patika
        patika_popup_menu=new JPopupMenu();
        JMenuItem updateMenu = new JMenuItem("güncelle");
        JMenuItem deleteMenu = new JMenuItem("sil");
        patika_popup_menu.add(updateMenu);
        patika_popup_menu.add(deleteMenu);

        //seçilen satırdaki id değerine göre DB den o veriyi çekip gereki işlemler yapılacaktır.
        updateMenu.addActionListener(e -> {
            int selected_id= Integer.parseInt(tbl_patika_list.getValueAt(tbl_patika_list.getSelectedRow(),0).toString());
            UpdatePatikaGUI updatePatikaGUI = new UpdatePatikaGUI(Patika.getFetch(selected_id));

            updatePatikaGUI.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    printPatikaFromDB();
                    printPatikaNameToCombobox();
                    printCourseFromDB();
                }
            });
        });

        deleteMenu.addActionListener(e -> {
            if(Helper.confirm("sure")){
                int selected_id= Integer.parseInt(tbl_patika_list.getValueAt(tbl_patika_list.getSelectedRow(),0).toString());
                if(Patika.delete(selected_id)){
                    Helper.showMessage("done");
                    printPatikaFromDB();
                    printPatikaNameToCombobox();
                    printCourseFromDB();
                }else{
                    Helper.showMessage("error");
                }
            }
        });

        mdl_patika_list =new DefaultTableModel();

        //table header column'larının değerleri
        Object[] clm_patika_list = {"ID","Name"};
        mdl_patika_list.setColumnIdentifiers(clm_patika_list);

        row_patika_list=new Object[clm_patika_list.length];
        printPatikaFromDB();

        tbl_patika_list.setModel(mdl_patika_list);
        tbl_patika_list.setComponentPopupMenu(patika_popup_menu);

        tbl_patika_list.getTableHeader().setReorderingAllowed(false);
        tbl_patika_list.getColumnModel().getColumn(0).setMaxWidth(75);

        //mouse ile sag tıkladığımda hangi rowda olduğunu bulup o row un selected olduğunu gösterir.
        tbl_patika_list.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                Point point = e.getPoint();
                int selected_row = tbl_patika_list.rowAtPoint(point);
                tbl_patika_list.setRowSelectionInterval(selected_row,selected_row);
            }
        });
        //## patika list

        //course list
        mdl_course_list =new DefaultTableModel();
        Object[] col_course_list={"ID","Ders Adı","Programlama Dili","Patika","Eğitmen"};
        mdl_course_list.setColumnIdentifiers(col_course_list);
        row_course_list =new Object[col_course_list.length];

        tbl_course_list.setModel(mdl_course_list);
        tbl_course_list.getColumnModel().getColumn(0).setMaxWidth(75);
        tbl_course_list.getTableHeader().setReorderingAllowed(false);

        printCourseFromDB();

        printPatikaNameToCombobox();
        printEducatorToCombobox();
        //##course list

        //functional interface olduğu için lambda expression kullanabildik.
        //Kullanıcılar sayfasına kullanıcı ekleme
        btn_user_form.addActionListener(e -> {
            if(Helper.isEmpty(fld_user_name) || Helper.isEmpty(fld_user_username) || Helper.isEmpty(fld_user_password)){
                Helper.showMessage("fill");
            }else {
                String name=fld_user_name.getText();
                String username=fld_user_username.getText();
                String password=fld_user_password.getText();
                String type=cmb_user_type.getSelectedItem().toString();

                if(Users.isAdd(name,username,password,type)){
                    Helper.showMessage("done");
                    printUserfromDB();
                    printEducatorToCombobox();

                    //formda veriler yazılıp ekleme işlemi yaptıktan sonra fieldları boşaltır.
                    fld_user_name.setText(null);
                    fld_user_username.setText(null);
                    fld_user_password.setText(null);
                }
            }
        });
        //id ye göre User silme islemi
        btn_user_delete.addActionListener(  e -> {
            if(fld_user_id.getText().isEmpty()){
                Helper.showMessage("fill");
            }else{
                if(Helper.confirm("sure")){
                    if(Users.delete(Integer.parseInt(fld_user_id.getText()))){
                        Helper.showMessage("done");
                        printUserfromDB();
                        printEducatorToCombobox();
                        printCourseFromDB();
                        fld_user_id.setText(null);
                    }else{
                        Helper.showMessage("error");
                    }
                }
            }
        });
        scrl_user_list.addMouseListener(new MouseAdapter() {
        });

        //Filtreleme işlemi yapıldı
        btn_user_sh.addActionListener(e -> {
            String name=fld_sh_user_name.getText();
            String username=fld_sh_user_username.getText();
            String type= Objects.requireNonNull(cmb_sh_user_type.getSelectedItem()).toString();

            String query=Users.searchQuery(name,username,type);
            ArrayList<Users> searchingUserList = Users.searchUserList(query);
            printUserfromDB(searchingUserList);
        });
        btn_logout.addActionListener(e -> {
            dispose();
            LoginGUI loginGUI=new LoginGUI();
        });

        //Patika liste veri ekleme
        btn_patika_add.addActionListener(e -> {
            //ekleme işlemi
            if(Helper.isEmpty(fld_patika_name)){
                Helper.showMessage("fill");
            }else{
                if(Patika.isAdd(fld_patika_name.getText())){
                    Helper.showMessage("done");
                    printPatikaFromDB();
                    printPatikaNameToCombobox();
                }else{
                    Helper.showMessage("error");
                }
            }

        });
        btn_course_add.addActionListener(e -> {
            Item patikaItem = (Item) cmb_course_patika.getSelectedItem();
            Item userItem = (Item) cmb_course_user.getSelectedItem();

            if(Helper.isEmpty(fld_course_name) || Helper.isEmpty(fld_course_lang)){
                Helper.showMessage("fill");
            }else{
                if(Course.addCourse(userItem.getKey(),patikaItem.getKey(),fld_course_name.getText(),fld_course_lang.getText())){
                    Helper.showMessage("done");
                    printCourseFromDB();
                    fld_course_name.setText(null);
                    fld_course_lang.setText(null);
                }else{
                    Helper.showMessage("error");
                }
            }
        });
    }

    private void printCourseFromDB() {
        DefaultTableModel defaultTableModel= (DefaultTableModel) tbl_course_list.getModel();
        defaultTableModel.setRowCount(0);

        for(Course c : Course.getList()){
            row_course_list[0]=c.getId();
            row_course_list[1]=c.getName();
            row_course_list[2]=c.getLang();
            row_course_list[3]=c.getPatika().getName();
            row_course_list[4]=c.getEducator().getName();

            mdl_course_list.addRow(row_course_list);
        }
    }

    private void printPatikaFromDB() {

        //tabloyu boşaltır
        DefaultTableModel defaultTableModel= (DefaultTableModel) tbl_patika_list.getModel();
        defaultTableModel.setRowCount(0);

        //object içine DB den gelen veriler atanır.Objeyide modelin içine gömülür.
        for(Patika p : Patika.getList()){
            row_patika_list[0]=p.getId();
            row_patika_list[1]=p.getName();

            mdl_patika_list.addRow(row_patika_list);
        }
    }

    public void printUserfromDB(){

        //tabloyu boşaltır.
        DefaultTableModel defaultTableModel = (DefaultTableModel) tbl_user_list.getModel();
        defaultTableModel.setRowCount(0);

        for(Users u : Users.getList()){

            row_user_list[0]=u.getId();
            row_user_list[1]=u.getName();
            row_user_list[2]=u.getUsername();
            row_user_list[3]=u.getPassword();
            row_user_list[4]=u.getType();

            mdl_user_list.addRow(row_user_list);
        }
    }
    public void printUserfromDB(List<Users> list){

        //tabloyu boşaltır.
        DefaultTableModel defaultTableModel = (DefaultTableModel) tbl_user_list.getModel();
        defaultTableModel.setRowCount(0);

        for(Users u : list){

            row_user_list[0]=u.getId();
            row_user_list[1]=u.getName();
            row_user_list[2]=u.getUsername();
            row_user_list[3]=u.getPassword();
            row_user_list[4]=u.getType();

            mdl_user_list.addRow(row_user_list);
        }
    }

    public void printPatikaNameToCombobox(){
        cmb_course_patika.removeAllItems();

        for(Patika p : Patika.getList()){
            cmb_course_patika.addItem(new Item(p.getId(),p.getName()));
        }
    }

    public void printEducatorToCombobox(){
        cmb_course_user.removeAllItems();

        for(Users educator : Users.getList()){
            if(educator.getType().equals("educator")){
                cmb_course_user.addItem(new Item(educator.getId(),educator.getName()));
            }
        }
    }

    public static void main(String[] args) {
        Helper.setLayout();
        Operator operator =new Operator(1,"Bayram YASAR","bayram","1234","operator");


        OperatorGUI operatorGUI=new OperatorGUI(operator);
    }
}
