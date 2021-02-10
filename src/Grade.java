import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Karl Danielsson - JAVA 20B
 * Date: 2021-02-05
 * Time: 15:55
 * Project: ShoeWorld
 */
public class Grade {

    private static final String TABLE_NAME = "grades";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_GRADE = "grade";
    private static final String COLUMN_POINTS = "points";

    private int id;
    private String grade;
    private int points;
    private List<Grade> gradeList;

    public Grade() {
        queryAllData();
    }

    private Grade(int id, String grade, int points) {
        this.id = id;
        this.grade = grade;
        this.points = points;
        gradeList = new ArrayList<>();
    }

    private void queryAllData() {
        try (Connection connection = DriverManager.getConnection(DB.CONNECTION_STRING, DB.USER_NAME, DB.PASSWORD);
             Statement statement = connection.createStatement();) {

            ResultSet results = statement.executeQuery("SELECT " + COLUMN_ID + ", " + COLUMN_GRADE + ", " + COLUMN_POINTS + " FROM " + TABLE_NAME);
            while (results.next()) {
                id = results.getInt(COLUMN_ID);
                grade = results.getString(COLUMN_GRADE);
                points = results.getInt(COLUMN_POINTS);
                //gradeList.add(new Grade(results.getInt(COLUMN_ID), results.getString(COLUMN_GRADE), results.getInt(COLUMN_POINTS)));
                gradeList.add(new Grade(id, grade, points));
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public int getId() {
        return id;
    }

    public String getGrade() {
        return grade;
    }

    public int getPoints() {
        return points;
    }

    public List<Grade> getGradeList() {
        return gradeList;
    }
}
