import netscape.javascript.JSObject;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;


public class Menu {


    @SuppressWarnings("unchecked")
    public static void menu() {
        Scanner input = new Scanner(System.in);

        Bank.addUser();

        System.out.println("Hosgeldiniz!\n");

        System.out.println("Tc Giriniz:");
        String tc = input.next();

        System.out.println("Şifrenizi giriniz");
        String sifre = input.next();

        Bank.girisKontrol(tc,sifre);

        while (true) {
            showMenu();
            int i = input.nextInt();
            if(i==0){
                break;
            }
            switch (i) {
                case 1:
                    break;
                case 2:
                    break;
                case 3:
                    krediBorcOdeme();
                    break;
                case 4:
                    break;
                default:
                    System.out.println("Lütfen seçeneklerden birini giriniz!");
                    break;
            }
        }
    }

    private static void krediBorcOdeme() {

    }

    public static void showMenu() {
        System.out.println("1-Para Çekme");
        System.out.println("2-Para Yatırma");
        System.out.println("3-Kredi Borcu Ödeme");
        System.out.println("4-Kredi Kartı Ekstresi Öde");
        System.out.println("0-Çıkış Yap");

        System.out.println("Yapmak istediğiniz işlemi seçiniz:");
    }


}
