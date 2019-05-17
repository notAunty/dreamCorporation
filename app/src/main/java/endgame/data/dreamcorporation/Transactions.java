package endgame.data.dreamcorporation;

public class Transactions {

  private String uplineId;
  private String downlinesId;
  private long timeStamp;

  public Transactions(String uplineId, String downlinesId, long timeStamp) {
    this.uplineId = uplineId;
    this.downlinesId = downlinesId;
    this.timeStamp = timeStamp;
  }

  public String getUplineId() {
    return uplineId;
  }

  public String getDownlinesId() {
    return downlinesId;
  }

  public long getTimeStamp() {
    return timeStamp;
  }
}
