package Model;

public class Users {
    String profilePic , username , password , mail , userID , lastMessage ;

    public Users(String profilePic, String username, String password, String mail, String userID, String lastMessage) {
        this.profilePic = profilePic;
        this.username = username;
        this.password = password;
        this.mail = mail;
        this.userID = userID;
        this.lastMessage = lastMessage;
    }
    //default constructor
    public Users(){}
    //sign up constructor
    public Users(String username , String mail , String password )
    {
        this.username =  username ;
        this.mail = mail ;
        this.password = password ;

    }
    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getMail() {
        return mail;
    }

    public String getUserID() {
        return userID;
    }

    public String getLastMessage() {
        return lastMessage;
    }
}
