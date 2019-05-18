package endgame.data.dreamcorporation;

public class Transactions {

  private String recipient;
  private String downlinesId;
  private long timeStamp;
  private double amount;

  public Transactions(String recipient, String downlinesId, long timeStamp, double amount) {
    this.recipient = recipient;
    this.downlinesId = downlinesId;
    this.timeStamp = timeStamp;
    this.amount = amount;
  }

  public String getRecipient() {
    return recipient;
  }

  public String getDownlinesId() {
    return downlinesId;
  }

  public long getTimeStamp() {
    return timeStamp;
  }

  public double getAmount() {
    return amount;
  }
}
