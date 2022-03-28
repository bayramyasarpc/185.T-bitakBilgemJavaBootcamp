package com.patikadev.helper;

import javax.swing.*;
import java.awt.*;

public class Helper {

    public static void setLayout(){
        for(UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()){
            System.out.println(info.getName()+" ->"+info.getClassName());
            if("Nimbus".equals(info.getName())){
                try {
                    UIManager.setLookAndFeel(info.getClassName());
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.println("###########");
    }


    public static int screenLocationCenter(String eksen , Dimension size){
        return switch (eksen) {
            case "x" -> (Toolkit.getDefaultToolkit().getScreenSize().width - size.width) / 2;
            case "y" -> (Toolkit.getDefaultToolkit().getScreenSize().height - size.height) / 2;
            default -> 0;
        };
    }

    public static boolean isEmpty(JTextField field){
        return field.getText().trim().isEmpty();
    }

    public static void showMessage(String str){
        String msg;
        String title;
        switch (str){
            case "fill":
                msg="Lütfem tüm alanları doldurunuz!";
                title="Hata!";
                break;
            case "done":
                msg="İşlem başarılı!";
                title="Sonuç";
                break;
            case "error":
                msg="Eklerken bir hata oluştu";
                title="Hata!";
                break;
            default:
                msg= str;
                title= "Mesaj";
                break;
        }
        optionPaneTr();
        JOptionPane.showMessageDialog(null,msg,title,JOptionPane.INFORMATION_MESSAGE);
    }

    public static void optionPaneTr(){
        UIManager.put("OptionPane.okButtonText","Tamam");
        UIManager.put("OptionPane.yesButtonText","Evet");
        UIManager.put("OptionPane.noButtonText","Hayır");
    }

    public static boolean confirm(String str) {
        String msg;
        switch (str){
            case "sure":
                msg= "Bu işlemi gerçekleştirmek istediğinizden eminmisiniz ?";
                break;
            default:
                msg=str;
        }
        return JOptionPane.showConfirmDialog(null,msg,"Son kararın mı?",JOptionPane.YES_NO_OPTION)==0;
    }
}
