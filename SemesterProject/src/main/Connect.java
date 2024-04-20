import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Connect {
    /**
     * Connect to a sample database.
     * @return a Connection object to the database
     */
    public static Connection connect() {
        Connection conn = null;
        try {
            // DB parameters
            String url = "jdbc:sqlite:C:/sqlite/library.db";
            // Create a connection to the database
            conn = DriverManager.getConnection(url);
            System.out.println("Connection to SQLite has been established.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    /**
     * Fetches all book titles from the 'books' table and capitalizes them.
     * @return a list of formatted book titles
     */
    public static List<String> selectAndFormatAllTitles() {
        List<String> titles = new ArrayList<>();
        String sql = "SELECT Title FROM books;";

        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                titles.add(rs.getString("Title"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return capitalizeBookTitles(titles); // Capitalize titles before returning
    }

    /**
     * Capitalizes the first letter of each word in each book title.
     * @param titles List of book titles
     * @return List of capitalized book titles
     */
    private static List<String> capitalizeBookTitles(List<String> titles) {
        List<String> capitalizedTitles = new ArrayList<>();
        for (String title : titles) {
            String capitalized = capitalizeEachWord(title);
            capitalizedTitles.add(capitalized);
        }
        return capitalizedTitles;
    }

    private static String capitalizeEachWord(String title) {
        StringBuilder titleBuilder = new StringBuilder();
        String[] words = title.split("\\s+");
        for (String word : words) {
            if (!word.isEmpty()) {
                titleBuilder.append(Character.toUpperCase(word.charAt(0)));
                titleBuilder.append(word.substring(1).toLowerCase());
                titleBuilder.append(" ");
            }
        }
        return titleBuilder.toString().trim();
    }
}
