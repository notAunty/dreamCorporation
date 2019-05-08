package endgame.data.dreamcorporation;

public class Level {
  public Node head = null;
  public Node tail = null;
  public boolean haveChild = false;

  public void add(String e, boolean haveChild) {
    Node newNode = new Node(e);
    if (head == null) {
      head = newNode;
      tail = newNode;
    } else {
      tail.next = newNode;
      tail = newNode;
    }
  }

//  public void push(String o) {
//    array.add(o);
//  }
}
