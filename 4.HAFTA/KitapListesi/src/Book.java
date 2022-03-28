public class Book {
    private final String bookName;
    private final int pageNum;
    private final String author;
    private final String date;

    public Book(String bookName, int pageNum, String author, String date) {
        this.bookName = bookName;
        this.pageNum = pageNum;
        this.author = author;
        this.date = date;

    }

    public String getBookName() {
        return bookName;
    }

    public int getPageNum() {
        return pageNum;
    }

    public String getAuthor() {
        return author;
    }

    public String getDate() {
        return date;
    }
}
