import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by Karl Danielsson - JAVA 20B
 * Date: 2021-02-05
 * Time: 15:55
 * Project: ShoeWorld
 */
public class Grade {

    private int id;
    private String grade;
    private int points;

    public int getId() {
        return id;
    }

    public String getGrade() {
        return grade;
    }

    public int getPoints() {
        return points;
    }
}
