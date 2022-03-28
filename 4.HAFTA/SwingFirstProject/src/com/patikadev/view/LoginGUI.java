package com.patikadev.view;

import com.patikadev.helper.Config;
import com.patikadev.helper.Helper;
import com.patikadev.model.Educator;
import com.patikadev.model.Operator;
import com.patikadev.model.Users;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginGUI extends JFrame {
    private JPanel wrapper;
    private JPanel wtop;
    private JPanel wbotton;
    private JLabel lbl_icon;
    private JTextField fld_user_username;
    private JTextField fld_user_password;
    private JButton btn_login;
    private ImageIcon icon;

    public LoginGUI() {
        add(wrapper);
        setSize(400, 400);
        setLocation(Helper.screenLocationCenter("x", getSize()), Helper.screenLocationCenter("y", getSize()));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setTitle(Config.PROJECT_TITLE);

        //Açılan screen resize özelliği
        setResizable(false);
        setVisible(true);

        icon = new ImageIcon("src/Logo.png");
        Image img = icon.getImage();
        Image scaleImg = img.getScaledInstance(getWidth() / 2, getHeight() / 3, Image.SCALE_SMOOTH);
        ImageIcon lastScaleIcon = new ImageIcon(scaleImg);
        lbl_icon.setIcon(lastScaleIcon);
        btn_login.addActionListener(e -> {
            if (Helper.isEmpty(fld_user_username) || Helper.isEmpty(fld_user_password)) {
                Helper.showMessage("fill");
            } else {
                Users u = Users.getFetch(fld_user_username.getText(), fld_user_password.getText());
                if (u == null) {
                    Helper.showMessage("Kullanıcı Bulunamadı");
                } else {
                    switch (u.getType()) {
                        case "operator":
                            OperatorGUI operatorGUI = new OperatorGUI((Operator) u);
                            break;
                        case "educator":
                            EducatorGUI educatorGUI=new EducatorGUI((Educator)u);
                            break;
                        case "student":
                            StudentGUI studentGUI=new StudentGUI();
                            break;
                    }
                    dispose();
                }
            }
        });
    }

    public static void main(String[] args) {
        Helper.setLayout();
        LoginGUI loginGUI = new LoginGUI();
    }
}
