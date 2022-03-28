import java.util.ArrayList;
import java.util.List;

public class ThreadRace{
    List<Integer> list = new ArrayList<>();

    List<Integer> list1 = new ArrayList<>();//1-2500
    List<Integer> list2 = new ArrayList<>();//2501-5000
    List<Integer> list3 = new ArrayList<>();//5001-7500
    List<Integer> list4 = new ArrayList<>();//7501-10000

    List<Integer> odd =new ArrayList<>();
    List<Integer> even =new ArrayList<>();

    public void sayiAtama(){
        //ArrayList 10000 sayı atandı
        for (int i = 1; i <= 10000; i++) {
            list.add(i);
        }

        //Ana liste 4 eşit aprçaya ayrıldı.
        list1.addAll(list.subList(1,2500));
        list2.addAll(list.subList(2501,5000));
        list3.addAll(list.subList(5001,7500));
        list4.addAll(list.subList(7501,10000));

    }
    public  void searchForAllList(int num) {
        if (num == 1) {
            searchForList1();
        }else if(num==2){
            searchForList2();
        }else if(num==3){
            searchForList3();
        }else {
            searchForList4();
        }
    }

    public   void oddSayilariBastir() {
        for(Integer i : odd){
            System.out.println(i);
        }
    }

    public void evenSayilariBastir() {
        for(Integer i : even){
            System.out.println(i);
        }
    }

    private void searchForList4() {
        for(Integer i: list4){
            ifControl(i);
        }
        System.out.println(Thread.currentThread().getName() +" --> işlemi tamamlanmıştır");
    }

    private void searchForList3() {
        for(Integer i: list3){
            ifControl(i);
        }
        System.out.println(Thread.currentThread().getName() +" --> işlemi tamamlanmıştır");
    }

    private void searchForList2() {
        for(Integer i: list2){
            ifControl(i);
        }
        System.out.println(Thread.currentThread().getName() +" --> işlemi tamamlanmıştır");
    }

    public void searchForList1() {
        for(Integer i: list1){
            ifControl(i);
        }
        System.out.println(Thread.currentThread().getName() +" --> işlemi tamamlanmıştır");
    }

    public void ifControl(Integer i){
        if(i%2==0){
            even.add(i);
        }else{
            odd.add(i);
        }
    }
}
