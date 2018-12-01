import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

class ReadBooksFile {

    private List<Book> books;

    ReadBooksFile() {
        books = new ArrayList<>();
    }

    List<Book> booksList(String filePath) {
        // Reading the file line by line
        if (filePath == null || filePath.isEmpty() ) {
            System.out.println();
        }
        try (Stream<String> lines = Files.lines(Paths.get(filePath))) {
            lines.forEachOrdered(this::processLine);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return books;
    }

    private void processLine(String s) {
        // Assuming no duplicate books ids are present
        String[] bookDetails = s.split(",");
        books.add(new Book(bookDetails[0], bookDetails[2], bookDetails[1], Double.parseDouble(bookDetails[3])));
    }

}
