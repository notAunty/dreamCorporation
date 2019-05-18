package endgame.data.dreamcorporation;

import java.util.ArrayList;

public class Users {

  private String fullName;
  private double balance;
  private String uplineUid;
  private String userName;
  private ArrayList<String> downlineUid;
  private ArrayList<String> transactions;

  public Users(String userName, String fullName, double balance) {
    this.userName = userName;
    this.fullName = fullName;
    this.balance = balance;
    this.downlineUid = new ArrayList<>();
    this.transactions = new ArrayList<>();
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

  public ArrayList<String> getTransactions() {
    return transactions;
  }

  public void setTransactions(ArrayList<String> transactions) {
    this.transactions = transactions;
  }
}
