package TaskManager.Model;

import Exceptions.*;

/**
 * Invariante de representación:
 * - User_id can't be empty, and has to start with US
 * - User_name can't be empty
 * - User_email can't be empty
 */

public class User {
    /* Var */
    private String userId;
    private String userName;
    private String userEmail;

    /* Constructor */
    public User(String userId, String userName, String userEmail){
        setUserId(userId);
        setUserName(userName);
        setUserEmail(userEmail);
    }

    /* Setters */
    public void setUserId(String userId) {
        if(userId == null || !userId.startsWith("US")){
            throw new ValidationException("User ID should start with US");
        }
        this.userId = userId;
    }

    public void setUserName(String userName) {
        if(userName.trim().isEmpty()){
            throw new ValidationException("User name should not be empty");
        }
        this.userName = userName;
    }

    public void setUserEmail(String userEmail) {
        if(userEmail.trim().isEmpty()){
            throw new ValidationException("User email should not be empty");
        }
        this.userEmail = userEmail;
    }

    /* Setters */

    public String getUserId() { return userId; }

    public String getUserName() { return userName; }

    public String getUserEmail() { return userEmail; }
}
