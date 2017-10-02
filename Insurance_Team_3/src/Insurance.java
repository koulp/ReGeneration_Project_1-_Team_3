import view.Show;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.text.ParseException;

public class Insurance {
    public static void main(String[] args) throws FileNotFoundException, ParseException, SQLException, ClassNotFoundException {
        Show show  = new Show();
        show.shows();
    }
}
