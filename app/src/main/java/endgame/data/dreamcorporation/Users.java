package endgame.data.dreamcorporation;

import java.util.ArrayList;

public class Users {

  private String fullName;
  private double balance;
  private String uplineUid;
  private String userName;
  private ArrayList<String> downlineUid;

  public Users(String userName, String fullName, double balance) {
    this.userName = userName;
    this.fullName = fullName;
    this.balance = balance;
    this.downlineUid = new ArrayList<>();
  }

  public String getFullName() {
    return fullName;
  }

  public double getBalance() {
    return balance;
  }

  public String getUplineUid() {
    return uplineUid;
  }

  public String getUserName() {
    return userName;
  }

  public void setBalance(double balance) {
    this.balance = balance;
  }

  public void setUplineUid(String uplineUid) {
    this.uplineUid = uplineUid;
  }

  public ArrayList<String> getDownlineUid() {
    return downlineUid;
  }

  public void setDownlineUid(ArrayList<String> downlineUid) {
    this.downlineUid = downlineUid;
  }
}
