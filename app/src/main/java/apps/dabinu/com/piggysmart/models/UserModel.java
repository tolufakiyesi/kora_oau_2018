package apps.dabinu.com.piggysmart.models;

public class UserModel{

    public String username, phoneNumber, email;

    public UserModel(){

    }

    public UserModel(String username, String phoneNumber, String email){
        this.username = username;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }
}
