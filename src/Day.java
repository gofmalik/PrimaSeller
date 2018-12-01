import java.util.Date;

class Day {

    private String purchaseDate;
    private double saleAmount;

    Day(String date, double saleAmount) {
        purchaseDate = date;
        this.saleAmount = saleAmount;
    }

    double getSaleAmount() {
        return saleAmount;
    }

    String getPurchaseDate() {
        return purchaseDate;
    }

    void setSaleAmount(double saleAmount) {
        this.saleAmount += saleAmount;
    }
}
