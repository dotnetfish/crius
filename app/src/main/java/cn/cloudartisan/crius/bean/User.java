package cn.cloudartisan.crius.bean;

import java.io.Serializable;

public class User
  implements Serializable
{
  public static final String GENDER_FEMALE = "0";
  public static final String GENDER_MAN = "1";
  public static final String OFF_LINE = "0";
  public static final String ON_LINE = "1";
  public static final long serialVersionUID = 4733464888738356502L;
  private String account;
  private String gender;
  private String latitude;
  private String location;
  private String longitude;
  private String motto;
  private String name;
  private String password;
  private String userToken;
  public boolean equals(Object paramObject)
  {
    if ((paramObject instanceof User))
    {
      paramObject = (User)paramObject;
      if ((this.account != null) && (((User)paramObject).account != null)) {
        return this.account.equals(((User)paramObject).account);
      }
    }
    return false;
  }
  
  public int hashCode()
  {
    return this.account.hashCode();
  }

  public String getGender() {
    return gender;
  }

  public void setGender(String gender) {
    this.gender = gender;
  }

  public void setAccount(String account) {
    this.account = account;
  }

  public  String getAccount(){
    return  this.account;
  }
  public String getLatitude() {
    return latitude;
  }

  public void setLatitude(String latitude) {
    this.latitude = latitude;
  }

  public String getLocation() {
    return location;
  }

  public void setLocation(String location) {
    this.location = location;
  }

  public String getLongitude() {
    return longitude;
  }

  public void setLongitude(String longitude) {
    this.longitude = longitude;
  }

  public String getMotto() {
    return motto;
  }

  public void setMotto(String motto) {
    this.motto = motto;
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

  public String getUserToken() {
    return userToken;
  }

  public void setUserToken(String userToken) {
    this.userToken = userToken;
  }
}