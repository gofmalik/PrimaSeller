import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class FindTopEntities {

    public static void main(String[] args) {
        if (args.length < 5) {
            System.out.println("Enter arguments as: java $ClassName --books=$pathToBook --sales=$pathToSales --top_selling_books=3 --top_customers=2 --sales_on_date=$date");
            return;
        }

        // HashMap to store arguments by name
        HashMap<String,String> arguments = new HashMap<>();
        String[][] namedArgs= {args[0].split("="), args[1].split("="), args[2].split("="), args[3].split("="), args[4].split("=")};
        for (String[] name : namedArgs) {
            if (name.length == 2) {
                String[] argName = name[0].split("--");
                if (argName.length == 2) {
                    arguments.put(argName[1], name[1]);
                }
                else {
                    System.out.println("Malformed Parameter");
                    System.out.println("Enter arguments as: java $ClassName --books=$pathToBook --sales=$pathToSales --top_selling_books=3 --top_customers=2 --sales_on_date=$date");
                    return;
                }
            }
            else {
                System.out.println("Malformed Parameter");
                System.out.println("Enter arguments as: java $ClassName --books=$pathToBook --sales=$pathToSales --top_selling_books=3 --top_customers=2 --sales_on_date=$date");
                return;
            }
        }

        // Reading the file containing book details
        ReadBooksFile readBooksFile = new ReadBooksFile();
        List<Book> books = readBooksFile.booksList(arguments.get("books"));

        // Reading the file containing sale details
        ReadSalesFile readSalesFile = new ReadSalesFile(books);
        readSalesFile.setSalesAndCustomersList(arguments.get("sales"));

        // Retrieving customer records and per day sales record
        List<Customer> customers = readSalesFile.getCustomers();
        List<Day> days = readSalesFile.getDays();

        // Sorting books to retrieve the books with maximum sales
        books.sort((b1, b2) -> (int) ((b1.getMoneySpent() >= b2.getMoneySpent()) ? b1.getMoneySpent() : b2.getMoneySpent()));

        // Sorting customers to retrieve the customers contributing to maximum sales
        customers.sort((c1, c2) -> (int) ((c1.getMoneySpent() >= c2.getMoneySpent()) ? c1.getMoneySpent() : c2.getMoneySpent()));
        List<Day> day = days.stream().filter(day1 -> day1.getPurchaseDate().equals(arguments.get("sales_on_date"))).collect(Collectors.toList());

        String topBooks = "";
        if (Integer.parseInt(arguments.get("top_selling_books")) <= books.size()) {
            for (int i = 0; i < Integer.parseInt(arguments.get("top_selling_books")); i++) {
                topBooks += books.get(i).getId() + "\t";
            }
            System.out.println("top_selling_books \t" + topBooks);
        }
        else {
            System.out.println("Total books are less than top books asked for.");
            for (Book book : books) {
                topBooks += book.getId() + "\t";
            }
            if (!topBooks.isEmpty())
            System.out.println("top_selling_books\t" + topBooks);
        }

        String topCustomers = "";
        if (Integer.parseInt(arguments.get("top_customers")) <= customers.size()) {
            for (int i = 0; i < Integer.parseInt(arguments.get("top_customers")); i++) {
                topCustomers += customers.get(i).getEmail() + "\t";
            }
            System.out.println("top_customers\t" + topCustomers);
        }
        else {
            System.out.println("Total customers are less than top customers asked for.");
            for (Customer customer : customers) {
                topCustomers += customer.getEmail() + "\t";
            }
            if (!topCustomers.isEmpty())
                System.out.println("top_customers\t" + topBooks);
        }

        // Sales for the concerned date
        if (!day.isEmpty()) {
            Day concernedDay = day.get(0);
            System.out.println("sales_on_date\t" + concernedDay.getPurchaseDate() + "\t" + concernedDay.getSaleAmount());
        }
        else {
            System.out.println("No sales on this date.");
        }
    }

}
