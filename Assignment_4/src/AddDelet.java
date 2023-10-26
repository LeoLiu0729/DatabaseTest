    import java.sql.*;
    import java.io.File;
    import java.io.PrintWriter;


    public class AddDelet {

        private static final String URL = "jdbc:mysql://localhost:3306/Main";
        private static final String USERNAME = "root";
        private static final String PASSWORD = "";

        public static void addStudent(int id,String name, int Class) {
            try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
                String checkSql = "SELECT COUNT(*) FROM Student WHERE id = ?";
                PreparedStatement checkStmt = connection.prepareStatement(checkSql);
                checkStmt.setInt(1, id);
                ResultSet checkResult = checkStmt.executeQuery();

                if (checkResult.next() && checkResult.getInt(1) > 0) {
                    System.out.println("A student with ID " + id + " already exists. Please using a new ID.");
                    return;
                }

                String sql = "INSERT INTO Student(id, name, class) VALUES (?, ?,?)";
                PreparedStatement pstmt = connection.prepareStatement(sql);
                pstmt.setInt(1,id);
                pstmt.setString(2, name);
                pstmt.setInt( 3, Class);
                int rowsAffected = pstmt.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Student added successfully!");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        public static void viewAllStudents() {
            try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT * FROM Student");
                PrintWriter writer = new PrintWriter(new File("students.txt"));
                while (resultSet.next()) {
                    writer.println(resultSet.getInt("id") + ", " + resultSet.getString("name") + ", " + resultSet.getInt("class"));
                }
                writer.close();
                System.out.println("Student data has been written to students.txt");
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (Exception e) {
                System.out.println("Error writing to file: " + e.getMessage());
            }
        }

        public static void updateStudent(int id, String name, int Class) {
            try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
                String sql = "UPDATE Student SET name = ?, class = ? WHERE id = ?";
                PreparedStatement pstmt = connection.prepareStatement(sql);
                pstmt.setString(1, name);
                pstmt.setInt(2, Class);
                pstmt.setInt(3, id);
                int rowsAffected = pstmt.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Student updated successfully!");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        public static void deleteStudent(int id) {
            try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
                String sql = "DELETE FROM Student WHERE id = ?";
                PreparedStatement pstmt = connection.prepareStatement(sql);
                pstmt.setInt(1, id);
                int rowsAffected = pstmt.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Student deleted successfully!");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
