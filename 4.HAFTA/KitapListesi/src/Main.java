import java.util.ArrayList;
import java.util.List;


public class Main {
    public static List<Book> books = new ArrayList<>();
    public static void main(String[] args) {



        Book book1 =new Book("Book1",100,"author1","11/12/1970");
        Book book2 =new Book("Book2",101,"author2","11/12/1970");
        Book book3 =new Book("Book3",12,"author3","11/12/1970");
        Book book4 =new Book("Book4",73,"author4","11/12/1970");
        Book book5 =new Book("Book5",104,"author5","11/12/1970");
        Book book6 =new Book("Book6",95,"author6","11/12/1970");
        Book book7 =new Book("Book7",106,"author7","11/12/1970");
        Book book8 =new Book("Book8",107,"author8","11/12/1970");
        Book book9 =new Book("Book9",108,"author9","11/12/1970");
        Book book10 =new Book("Book10",109,"author10","11/12/1970");

        books.add(book1);
        books.add(book2);
        books.add(book3);
        books.add(book4);
        books.add(book5);
        books.add(book6);
        books.add(book7);
        books.add(book8);
        books.add(book9);
        books.add(book10);

        //sayfa sayısı 100 den fazla olaları yeni bir listede olusturmak.
        listOfBookOrderByPage();
        System.out.println("#############");

        //Kitap ismi ile yazarını mapleme
        mapBookNameAndAuthor();
        System.out.println("----------------");

        //ArrayList deki tüm kitapları listeleme
        listAllBooks();

    }
    public static void listOfBookOrderByPage(){
            books
                .stream()
                .filter(kitap -> kitap.getPageNum() > 100)
                .forEach(kitaplar->System.out.println(kitaplar.getBookName()+" ->"+kitaplar.getPageNum()));
    }

    public static void listAllBooks(){
        books
                .forEach(kitaplar -> System.out.println(kitaplar.getBookName()+" -> "+
                        kitaplar.getPageNum()+" -> "+kitaplar.getDate()+" -> "+kitaplar.getAuthor()));
    }
    public static void mapBookNameAndAuthor(){
       books
               .stream()
               .map(merge-> merge.getBookName() + "->" + merge.getAuthor())
               .forEach(System.out::println);
    }
}
