package com.patikadev.view;

import com.patikadev.helper.Config;
import com.patikadev.helper.Helper;
import com.patikadev.model.Patika;

import javax.swing.*;


public class UpdatePatikaGUI extends JFrame{
    private JPanel wrapper;
    private JTextField fld_patika_update_name;
    private JButton btn_update;
    private Patika patika;

    public UpdatePatikaGUI(Patika patika){
        this.patika=patika;
        add(wrapper);
        setSize(300,300);
        setLocation(Helper.screenLocationCenter("x",getSize()),Helper.screenLocationCenter("y",getSize()));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle(Config.PROJECT_TITLE);
        setVisible(true);

        fld_patika_update_name.setText(patika.getName());
        btn_update.addActionListener(e -> {
            if(Helper.isEmpty(fld_patika_update_name)){
                Helper.showMessage("fill");
            }else{
                if(Patika.isUpdate(this.patika.getId(),fld_patika_update_name.getText())){
                    Helper.showMessage("done");
                }
                //açılan ekranı kapatır
                dispose();
            }
        });
    }
}
