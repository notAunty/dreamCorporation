package endgame.data.dreamcorporation;

public class Node {

  private String uid;
  private int userId;
  private String upline;
  private String[] downlines;

  public Node() {

  }

  public Node(String uid, int userId, String upline, String[] downlines) {
    this.uid = uid;
    this.userId = userId;
    this.upline = upline;
    this.downlines = downlines;
  }
}
