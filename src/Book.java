class Book {

    private String id;
    private String author;
    private String title;
    private double price;
    private double moneySpent;

    Book(String id, String author, String title, double price) {
        this.id = id;
        this.author = author;
        this.title = title;
        this.price = price;
        moneySpent = 0;
    }

    double getMoneySpent() {
        return moneySpent;
    }

    void addMoneySpent(double val) {
        moneySpent += val;
    }

    double getPrice() {
        return price;
    }

    String getId() {
        return id;
    }
}
