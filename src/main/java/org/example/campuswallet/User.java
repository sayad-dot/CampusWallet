
package org.example.campuswallet;

public class User {
    private String ID;
    private String name;
    private String password;
    private String email;
    private String dob;
    private String phone;




    // Constructor
    public User(String ID, String name,String password,  String email, String dob, String phone) {

        this.ID=ID;
        this.name = name;
        this.password = password;
        this.email = email;
        this.dob = dob;
        this.phone = phone;


    }

    // Getters and setters

    public String getId(){
        return ID;
    }
    public void setId(String ID){
        this.ID=ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }



    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }


}
