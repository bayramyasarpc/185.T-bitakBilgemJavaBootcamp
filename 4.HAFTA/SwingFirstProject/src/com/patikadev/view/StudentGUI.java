package com.patikadev.view;

import com.patikadev.helper.Config;
import com.patikadev.helper.Helper;
import com.patikadev.model.Patika;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class StudentGUI extends JFrame {

    private JPanel wrapper;
    private JPanel pnl_top;
    private JPanel pnl_bottom;
    private JTabbedPane tabbedPane1;
    private JScrollPane scrl_patika_list;
    private JTable tbl_patika_list;
    private JPanel pnl_patika_form;
    private JTextField fld_patika_id;
    private JScrollPane scrl_kayitlipatika_list;
    private JTable tbl_kayitlipatika_list;

    private DefaultTableModel mdl_patika_list;
    private Object[] row_patika_list;

    public  StudentGUI(){
        add(wrapper);
        setSize(1000,500);

        int x= Helper.screenLocationCenter("x",getSize());
        int y=Helper.screenLocationCenter("y",getSize());
        setLocation(x,y);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);//UI kapattıgımızda arka plandan da kapanmasını sağlar
        setTitle(Config.PROJECT_TITLE);
        setVisible(true);

        //Patika List
        mdl_patika_list=new DefaultTableModel();
        Object[] col_header_patika_list={"ID","Patika Name"};
        mdl_patika_list.setColumnIdentifiers(col_header_patika_list);

        row_patika_list= new Object[col_header_patika_list.length];

        tbl_patika_list.setModel(mdl_patika_list);
        tbl_patika_list.getTableHeader().setReorderingAllowed(false);
        tbl_patika_list.getColumnModel().getColumn(0).setMaxWidth(75);
        tbl_patika_list.getColumnModel().getColumn(1).setMaxWidth(275);

        printPatikaFromDB();
        //##Patika List
    }

    private void printPatikaFromDB() {
        DefaultTableModel defaultTableModel=new DefaultTableModel();
        defaultTableModel.setRowCount(0);
        int i=1;
        for(Patika p : Patika.getList()){

            row_patika_list[0]=i++;
            row_patika_list[1]=p.getName();

            mdl_patika_list.addRow(row_patika_list);
        }
    }
}
