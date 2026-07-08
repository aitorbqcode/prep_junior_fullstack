package TaskManager.Repository;

import Exceptions.SelectException;
import TaskManager.Model.User;
import Exceptions.AppException;
import java.sql.*;
import static DataBase.DatabaseConnection.getConnection;

public class UserRepository {

    /* Insert User */
    public void insertUser(User user) {
        //We write the sql comand
        String sql = "INSERT INTO task_manager.users VALUES (?, ?, ?)";

        //We get a connection and use the prepareStatement to connect with the sql comand
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            //We add the values for the command
            ps.setString(1, user.getUserId());
            ps.setString(2, user.getUserName());
            ps.setString(3, user.getUserEmail());

            //And execute the update to insert the user
            ps.executeUpdate();
            System.out.println("User created correctly");

        } catch (SQLException e) {
            throw new AppException("Error inserting user: " + e.getMessage());
        }
    }

    /* Find a user by ID */
    public User findById(String userId){

        //We write the sql comand
        String sql = "SELECT * FROM task_manager.users WHERE users.user_id = ?";

        //We create a user struct to return
        User user;

        //We get a connection and use the prepareStatement to connect with the sql comand
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            //We add the user_id for the command
            ps.setString(1, userId);

            //We get the result using the result set, which if the a user the if will be true and we will create the user
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new User(
                            rs.getString("user_id"),
                            rs.getString("user_name"),
                            rs.getString("user_email")
                    );
                }
                return null; //User not found
            }
        } catch (SQLException e) {
            throw new SelectException("Error en SELECT: " + e.getMessage());
        }
    }

    /* Get all users */
    public static void selectUsers() {
        //We write the sql comand
        String sql = "SELECT * FROM task_manager.users";

        //We get a connection and use the prepareStatement to connect with the sql comand
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            //We print all the users
            while (rs.next()) {
                System.out.println(
                        rs.getString("user_id") + " | " +
                                rs.getString("user_name") + " | " +
                                rs.getString("user_email")
                );
            }

        } catch (SQLException e) {
            throw new SelectException("Error en SELECT: " + e.getMessage());
        }
    }
}