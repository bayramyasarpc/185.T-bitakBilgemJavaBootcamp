import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;

public class Bank {
    public static void addUser() {
        JSONObject user = new JSONObject();
        user.put("name", "bayram");
        user.put("TC", "21423456776");
        user.put("money",2000);
        user.put("sifre","user1");


        JSONObject user2 = new JSONObject();
        user2.put("name", "fatma");
        user2.put("TC", "32342343134");
        user2.put("money",3000);
        user2.put("sifre","user2");

        JSONObject user3 = new JSONObject();
        user3.put("name", "ali");
        user3.put("TC", "67854234567");
        user3.put("money",4000);
        user3.put("sifre","user3");

        JSONObject user4 = new JSONObject();
        user4.put("name", "mustafa");
        user4.put("TC", "98362372672");
        user4.put("money",4000);
        user4.put("sifre","user4");

        JSONObject users = new JSONObject();
        users.put("Users", user);

        JSONObject users2 = new JSONObject();
        users2.put("Users", user2);

        JSONObject users3 = new JSONObject();
        users3.put("Users", user3);

        JSONObject users4 = new JSONObject();
        users4.put("Users", user4);

        JSONArray userList = new JSONArray();
        userList.add(user);
        userList.add(user2);
        userList.add(user3);
        userList.add(user4);

        try {
            FileWriter file = new FileWriter("Users.json");
            file.write(userList.toJSONString());
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void  readObject(){
        JSONParser jsonParser = new JSONParser();
        try {
            FileReader fileReader = new FileReader("Users.json");
            Object object = jsonParser.parse(fileReader);

            JSONArray list = (JSONArray) object;
            System.out.println(list);

            list.forEach(e -> parseUsersList((JSONObject) e));
        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }
    }

    public static void parseUsersList(JSONObject object) {
        Object name = object.get("name");
        System.out.println(name);

        Object tc = object.get("TC");
        System.out.println(tc);
    }
    public static void parseUsersListKontrol(JSONObject object,String tc,String sifre) {
        if(object.get("TC").equals(tc) && object.get("sifre").equals(sifre)){
            System.out.println("basarılı giriş yapıldı");
        }
    }

    public static void girisKontrol(String tc,String sifre) {
        JSONParser jsonParser = new JSONParser();
        try {
            FileReader fileReader = new FileReader("Users.json");
            Object object = jsonParser.parse(fileReader);

            JSONArray list = (JSONArray) object;

            list.forEach(e -> {
                parseUsersListKontrol((JSONObject) e, tc, sifre);

            });

        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }

    }
}
