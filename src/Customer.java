class Customer {

    private String email;
    private double moneySpent;

    Customer(String email, double moneySpent) {
        this.email = email;
        this.moneySpent = moneySpent;
    }

    String getEmail() {
        return email;
    }

    void mnoneySpentChanged(double price) {
        moneySpent += price;
    }

    double getMoneySpent() {
        return moneySpent;
    }

}
