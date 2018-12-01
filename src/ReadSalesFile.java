import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class ReadSalesFile {

    private List<Day> days;
    private List<Customer> customers;
    private List<Book> books;

    ReadSalesFile(List<Book> books) {
        days = new ArrayList<>();
        customers = new ArrayList<>();
        this.books = books;
    }

    void setSalesAndCustomersList(String filePath) {
        // Reading the file line by line
         try(Stream<String> lines = Files.lines(Paths.get(filePath))) {
             lines.forEachOrdered(this::processLine);
         } catch (IOException e) {
             e.printStackTrace();
         }
    }

    private void processLine(String s) {
        // Assuming the file entries are correct
        String[] entries = s.split(",");

        // Fetching customer and day if already present
        List<Customer> customerList = customers.stream().filter(customer -> customer.getEmail().equals(entries[1])).collect(Collectors.toList());
        List<Day> dayList = days.stream().filter(day -> day.getPurchaseDate().equals(entries[0])).collect(Collectors.toList());

        // Calculating sales for a particular entry
        double dayPurchase = 0;
        int j = 3;
        for (int i = 1; i <= Integer.parseInt(entries[3]); i++) {
            String[] bookPurchaseDetails = entries[j+i].split(";");
            dayPurchase += books.stream().filter(book -> book.getId().equalsIgnoreCase(bookPurchaseDetails[0])).mapToDouble(book -> {
                double val = book.getPrice() * Double.parseDouble(bookPurchaseDetails[1]);
                book.addMoneySpent(val);
                return val;
            }).sum();
        }

        // Creating new customer if he has never bought any books or else updating the expenditure of existing customer
        if (customerList.isEmpty()) {
            customers.add(new Customer(entries[1], dayPurchase));
        }
        else {
            customerList.get(0).mnoneySpentChanged(dayPurchase);
        }

        // Creating new day if it's the first sale of that day or else updating the sales of existing day
        if (dayList.isEmpty()) {
            days.add(new Day(entries[0], dayPurchase));
        }
        else {
            dayList.get(0).setSaleAmount(dayPurchase);
        }
    }

    List<Customer> getCustomers() {
        return customers;
    }

    List<Day> getDays() {
        return days;
    }
}
