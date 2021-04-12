
package database;

import model.DataModel;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Database {
    
    private static String fileName = "DB.db";

    public static void createNewTable() {
        // SQLite connection string

        String url = "jdbc:sqlite:" + fileName;
        
        // SQL statement for creating a new table
        String sql = "CREATE TABLE IF NOT EXISTS wave (\n"
                + "	id integer PRIMARY KEY,\n"
                + "	wavelength real NOT NULL,\n"
                + "	distSlits real NOT NULL,\n"
                + "	distSlitScreen real NOT NULL,\n"
                + "	slitWidth real NOT NULL \n"
                + ");";
        
        try  {
            Connection conn = DriverManager.getConnection(url);
            Statement stmt = conn.createStatement();
            // create a new table
            stmt.execute(sql);
            if (!rowExists(1)) {
                sql = "INSERT INTO Wave(wavelength,distSlits, distSlitScreen, slitWidth) VALUES(?,?,?,?)";
                PreparedStatement pstmt = conn.prepareStatement(sql);
                // Sets default values if database doesn't exist yet
                pstmt.setDouble(1, 300);
                pstmt.setDouble(2, 25);
                pstmt.setDouble(3, 3);
                pstmt.setDouble(4, 10);
                pstmt.executeUpdate();
            }
        } catch (SQLException e) {

            System.out.println(e.getMessage());
        }



    }

    public static void insertData(double wavelength, double distSlits, double distSlitScreen, double slitWidth) {

        String url = "jdbc:sqlite:" + fileName;
        String sql = "UPDATE wave SET wavelength = ? , "
                + "distSlits = ? , "
                + "distSlitScreen = ? , "
                + "slitWidth = ? "
                + "WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(url);

             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // set the corresponding param
            pstmt.setDouble(1, wavelength);
            pstmt.setDouble(2, distSlits);
            pstmt.setDouble(3, distSlitScreen);
            pstmt.setDouble(4, slitWidth);
            pstmt.setInt(5, 1);

            // update
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }
    
    private static boolean rowExists(int id){
        
        boolean exists = false;
        String url = "jdbc:sqlite:" + fileName;
        String sql = "SELECT * from wave WHERE Id=?";
        
        try (Connection conn = DriverManager.getConnection(url);

            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            exists = rs.next();
            
    } catch (SQLException e) {
        System.out.println(e.getMessage());
    }
        return exists;
    }

    public static DataModel getData() {

        String url = "jdbc:sqlite:" + fileName;
        String sql = "SELECT * from wave";

        DataModel dataModel = null;

        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {
            ResultSet resultSet = stmt.executeQuery(sql);

            dataModel = new DataModel(resultSet.getDouble("wavelength"), resultSet.getDouble("distSlits"), resultSet.getDouble("distSlitScreen"), resultSet.getDouble("slitWidth"));

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return dataModel;
    }

}
    

