package com.example.raytine.keepmoving.user.model;

public class User {

    private String objectId;
    private String telephone;
    private String nickname;
    private String gender;
    private int age;
    private String address;
    private float wallet;
    private int type;

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public float getWallet() {
        return wallet;
    }

    public void setWallet(float wallet) {
        this.wallet = wallet;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("objectId : ").append(objectId)
                .append(", telephone : ").append(telephone)
                .append(", type : ").append(type)
                .append(", nickname : ").append(nickname)
                .append(", gender : ").append(gender)
                .append(", age : ").append(age)
                .append(", address : ").append(address)
                .append(", wallet : ").append(wallet);
        return sb.toString();
    }
}
