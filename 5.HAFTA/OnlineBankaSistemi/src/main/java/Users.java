public class Users {
    private String name;
    private String tc;
    private String password;
    private int money;
    private String sifre;

    public Users(String name, String tc, String password,int money,String sifre) {
        this.name = name;
        this.tc = tc;
        this.password = password;
        this.money=money;
        this.sifre=sifre;
    }

    public String getSifre() {
        return sifre;
    }

    public void setSifre(String sifre) {
        this.sifre = sifre;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTc() {
        return tc;
    }

    public void setTc(String tc) {
        this.tc = tc;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
