package com.example.myadministrator.Model;

public class UserDetails {

    String gender,profilepic,useremail,username,userrpassword,userjoin,height,weight,age;

    public UserDetails() {
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public UserDetails(String gender, String profilepic, String useremail, String username, String userrpassword, String userjoin, String height, String weight, String age) {
        this.gender = gender;
        this.profilepic = profilepic;
        this.useremail = useremail;
        this.username = username;
        this.userrpassword = userrpassword;
        this.userjoin = userjoin;
        this.height = height;
        this.weight = weight;
        this.age = age;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }


    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getProfilepic() {
        return profilepic;
    }

    public void setProfilepic(String profilepic) {
        this.profilepic = profilepic;
    }

    public String getUseremail() {
        return useremail;
    }

    public void setUseremail(String useremail) {
        this.useremail = useremail;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserrpassword() {
        return userrpassword;
    }

    public void setUserrpassword(String userrpassword) {
        this.userrpassword = userrpassword;
    }

    public String getUserjoin() {
        return userjoin;
    }

    public void setUserjoin(String userjoin) {
        this.userjoin = userjoin;
    }


}
