

public class Main {
    public static void main(String[] args) {
	ThreadRace threadRace= new ThreadRace();

    //1 den 1000 kadar olan sayılar listeye eklend, ve 4 ayrı 2500lik liste olusturuldu
    threadRace.sayiAtama();

        //4 ayrı liste için 4 tane thread olusturuldu ve kendi bölgelerinde çift ve tek sayıları kontrol etti
        for(int i=1;i<=4;i++){
            int finalI = i;

            Thread thread= new Thread(() -> threadRace.searchForAllList(finalI));
            thread.start();
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        //4 thread in elemiş olduğu odd ve even listeler print edildi.
        threadRace.oddSayilariBastir();
        threadRace.evenSayilariBastir();

    }
}
