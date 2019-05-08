package endgame.data.dreamcorporation;

import java.util.ArrayList;

public class Node<String> {

  protected ArrayList<String> element;
  protected Node<String> next;

  public Node() {}

  public Node(String element) {
    this.element.add(element);
  }
}
