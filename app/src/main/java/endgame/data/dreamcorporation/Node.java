//package endgame.data.dreamcorporation;
//
//import java.util.ArrayList;
//
//public class Node<String> {
//
//  protected String uid;
//  protected ArrayList<String> downlines;
//  protected ArrayList<Boolean> gotDownlines;
//  protected ArrayList<Node> downlinesNode;
//
//  public Node(String uid, ArrayList<String> downlines, ArrayList<Boolean> gotDownlines) {
//    this.uid = uid;
//    this.downlines = downlines;
//    this.gotDownlines = gotDownlines;
//  }
//
//  public Node(String uid, ArrayList<String> downlines) {
//    this.uid = uid;
//    this.downlines = downlines;
//  }
//
//  public Node(String uid) {
//    this.uid = uid;
//  }
//
//  public void addBool(String downlines, boolean gotDownlines) {
//    this.gotDownlines.add(this.downlines.indexOf(downlines), gotDownlines);
//  }
//
//  public ArrayList<Node> getDownlinesNode() {
//    return downlinesNode;
//  }
//
//  public void addDownlinesNode(Node downlinesNode) {
//    this.downlinesNode.add(downlinesNode);
//  }
//
//  public String getUid() {return uid;}
//
//  //  public void compile() {
////    int size = downlines.size();
////    downlinesNode = new Node[size];
////    for (int i = 0; i < size; i++) {
////      downlinesNode[i] = new Node(this.downlines.get(i));
////    }
////  }
//}
