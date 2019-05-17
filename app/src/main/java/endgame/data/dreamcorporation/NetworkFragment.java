package endgame.data.dreamcorporation;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import endgame.data.dreamcorporation.network.ParentNode;
import endgame.data.dreamcorporation.viewbinder.LeafNodeBinder;
import endgame.data.dreamcorporation.viewbinder.ParentNodeBinder;
import tellh.com.recyclertreeview_lib.TreeNode;
import tellh.com.recyclertreeview_lib.TreeViewAdapter;

public class NetworkFragment extends Fragment {

  private FirebaseAuth mAuth = FirebaseAuth.getInstance();

  private RecyclerView rv;
  private Encryption encryption = new Encryption();
  private TreeViewAdapter adapter;
  private TreeNode<ParentNode> root;
  private String uid = mAuth.getUid();
//  private Node node, tempNode;

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_network, container, false);
    rv = (RecyclerView) view.findViewById(R.id.rv);

//     // Store UID as arrayList
//    usersRef.child(uid).child("dwId").addListenerForSingleValueEvent(new ValueEventListener() {
//      @Override
//      public void onDataChange(DataSnapshot dataSnapshot) {
//
//        ArrayList<String> temp = (ArrayList<String>) dataSnapshot.getValue();
//
//        for (String a: temp) Log.e("UID: ", a);
//        }
//
//      @Override
//      public void onCancelled(@NonNull DatabaseError databaseError) {}});

//    initView();
    initData();

    return view;
  }

  private void initData() {
    List<TreeNode> treeNodes = new ArrayList<>();

    root = new TreeNode<>(new ParentNode(getNameDirectly(uid)));
    treeNodes.add(root);

    generateLine(uid, root);
//    addTreeNode(node);

//    List<TreeNode> treeNodes = new ArrayList<>();
//    TreeNode<ParentNode> app = new TreeNode<>(new ParentNode("Mike Jones"));
//    treeNodes.add(app);
//    app.addChild(
//            new TreeNode<>(new ParentNode("manifests"))
//                    .addChild(new TreeNode<>(new LeafNode("Abby Jones")))
//    );
//    app.addChild(
//            new TreeNode<>(new ParentNode("Bonny Jones")).addChild(
//                    new TreeNode<>(new ParentNode("Come on make me feel alive!")).addChild(
//                            new TreeNode<>(new ParentNode("Moby Dick")).addChild(
//                                    new TreeNode<>(new ParentNode("Ben"))
//                                            .addChild(new TreeNode<>(new LeafNode("hth543g34gh453543g543g345g54gertthrhctr")))
//                                            .addChild(new TreeNode<>(new LeafNode("A Jones")))
//                                            .addChild(new TreeNode<>(new LeafNode("Abby hth543g34gh453543g543g345g54gertthrhctr")))
//                            )
//                    )
//            )
//    );


//    Upline
//    final String tempUpline[] = new String[1];
//    usersRef.child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
//      @Override
//      public void onDataChange(DataSnapshot dataSnapshot) {
//        tempUpline[0] = dataSnapshot.child("upId").getValue().toString();
//      }
//      @Override
//      public void onCancelled(@NonNull DatabaseError databaseError) {}});
//
//    TreeNode<ParentNode> upline = new TreeNode<>(new ParentNode(tempUpline[0]));
//    treeNodes.add(upline);
//
//    // Current
//    upline.addChild();

    rv.setLayoutManager(new LinearLayoutManager(getContext()));
    adapter = new TreeViewAdapter(treeNodes, Arrays.asList(new LeafNodeBinder(), new ParentNodeBinder()));
    // whether collapse child treeNodes when their parent node was close.
//        adapter.ifCollapseChildWhileCollapseParent(true);

    adapter.setOnTreeNodeListener(new TreeViewAdapter.OnTreeNodeListener() {
      @Override
      public boolean onClick(TreeNode node, RecyclerView.ViewHolder holder) {
        if (!node.isLeaf()) {
          //Update and toggle the node.
          onToggle(!node.isExpand(), holder);
//                    if (!node.isExpand())
//                        adapter.collapseBrotherNode(node);
        }
        return false;
      }

      @Override
      public void onToggle(boolean isExpand, RecyclerView.ViewHolder holder) {
        ParentNodeBinder.ViewHolder dirViewHolder = (ParentNodeBinder.ViewHolder) holder;
        final ImageView ivArrow = dirViewHolder.getIvArrow();
        int rotateDegree = isExpand ? 90 : -90;
        ivArrow.animate().rotationBy(rotateDegree)
                .start();
      }
    });
    rv.setAdapter(adapter);
  }

//  private void addTreeNode(Node node) {
//    ArrayList<Node> tempDownline = node.getDownlinesNode();
//
//
//    for (Node a: tempDownline) {
//      if (node.downlines.isEmpty()) root.addChild(new TreeNode<>(new LeafNode((String) node.getUid())));
//      else {
//        root.addChild(new TreeNode<>(new LeafNode((String) node.getUid())));
//      }
//    }
//  }

  private void generateLine(String userId, TreeNode ref) {
    TreeNode tempRef = ref;
    String uid = userId;

    ArrayList<String> tempDw = GetFirebase.getUsers(userId).getDownlineUid();

    if (tempDw.isEmpty()) {

    } else {

    }

//    if (tempDw == null) return;
//    for (String a: tempDw) {
//      if
//    }

//    usersRef.addListenerForSingleValueEvent(new ValueEventListener() {
//      @Override
//      public void onDataChange(DataSnapshot dataSnapshot) {
//        if (dataSnapshot.child(uid).child("dwId").exists()) {
//          ArrayList<String> tempDw = (ArrayList<String>) dataSnapshot.child(uid).child("dwId").getValue();
//          for (String a: tempDw) {
//            if (dataSnapshot.child(a).child("dwId").exists()) {
//              TreeNode r = new TreeNode<>(new ParentNode(GetName.getNameDirectly(a)));
//              tempRef.addChild(r);
//              generateLine(a, r); // Recursive
//            } else {
////              ArrayList<String> tempDw = (ArrayList<String>) dataSnapshot.child(uid).child("dwId").getValue();
////              for (String a : tempDw) {
//                TreeNode r = new TreeNode<>(new LeafNode(GetName.getNameDirectly(a)));
//                tempRef.addChild(r);
////              }
//            }
//          }
//        }
//      }
//      @Override
//      public void onCancelled(@NonNull DatabaseError databaseError) {}
//    });
  }

  protected String getNameDirectly(String uid) {
    Log.e("getNameDirectly uid: ", uid);
    String tempFullName;
//    Log.e("uid: ", uid);
    tempFullName = GetFirebase.getUsers(uid).getFullName();
    Log.e("getNameD tempFullName: ", tempFullName);
    tempFullName = encryption.decodeDirectly(tempFullName);
    return tempFullName;
  }

//  private void generateLine(String userId, TreeNode ref) {
//    final TreeNode tempRef = ref;
//    final String uid = userId;
//
//    usersRef.addListenerForSingleValueEvent(new ValueEventListener() {
//      @Override
//      public void onDataChange(DataSnapshot dataSnapshot) {
//        if (dataSnapshot.child(uid).child("dwId").exists()) {
//          ArrayList<String> tempDw = (ArrayList<String>) dataSnapshot.child("dwId").getValue();
//          for (String a: tempDw) {
//            if (dataSnapshot.child("dwId").exists()) {}
//            TreeNode r = new TreeNode<>(new ParentNode(a));
//            tempRef.addChild(r);
//            generateLine(a, r);
//          }
//        } else {
//          TreeNode r = new TreeNode<>(new LeafNode(dataSnapshot.getKey()));
//          tempRef.addChild(r);
//        }
//      }
//      @Override
//      public void onCancelled(@NonNull DatabaseError databaseError) {}});
//  }

//  private void generateLine(String uid) {
//    usersRef.child(uid).child("dwId").addListenerForSingleValueEvent(new ValueEventListener() {
//      @Override
//      public void onDataChange(DataSnapshot dataSnapshot) {
//        ArrayList<String> tempDw = new ArrayList();
//
//        node = new Node(mAuth.getUid(), tempDw);
//
//        for (String a: tempDw) {
//          final String tempCurrent = a;
//          usersRef.child(tempCurrent).addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//              if (dataSnapshot.child("dwId").exists()) {
//                root.addChild(
//                        new TreeNode<>(new ParentNode((String) node.getUid()));
//                );
//                node.addDownlinesNode(generateLineAgain(tempCurrent));
//              } else root.addChild( new TreeNode<>(new LeafNode((String) node.getUid())); );
//            }
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {}});
//        }
//      }
//      @Override
//      public void onCancelled(@NonNull DatabaseError databaseError) {}});
//  }
//
//  private Node generateLineAgain(String uid) {
//    usersRef.child(uid).child("dwId").addListenerForSingleValueEvent(new ValueEventListener() {
//      @Override
//      public void onDataChange(DataSnapshot dataSnapshot) {
//        ArrayList<String> tempDw = new ArrayList();
//
//        tempNode = new Node(mAuth.getUid(), tempDw);
//
//        for (final String a: tempDw) {
//          final String tempCurrent = a;
//          usersRef.child(tempCurrent).addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//              if (dataSnapshot.child("dwId").exists()) {
//                node.addDownlinesNode(generateLineAgain(tempCurrent));
//              }
//            }
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {}});
//        }
//      }
//      @Override
//      public void onCancelled(@NonNull DatabaseError databaseError) {}});
//    return tempNode;
//  }


//  private void generateLine(String uid) {
//    final String[] tempUpline = new String[1];
//
//    usersRef.child(uid).child("upId").addListenerForSingleValueEvent(new ValueEventListener() {
//      @Override
//      public void onDataChange(DataSnapshot dataSnapshot) {
//        tempUpline[0] = dataSnapshot.getValue().toString();
//      }
//      @Override
//      public void onCancelled(@NonNull DatabaseError databaseError) {}});
//
//    lines = new Level();
//    lines.add(tempUpline[0]);
//    lines.add(uid);
//
//    for (int i = 2; i < 7; i++) {
//      usersRef.child(uid).child("dwId").addListenerForSingleValueEvent(new ValueEventListener() {
//        @Override
//        public void onDataChange(DataSnapshot dataSnapshot) {
//          lines.add(new Level((ArrayList<String>) dataSnapshot.getValue()));
//        }
//        @Override
//        public void onCancelled(@NonNull DatabaseError databaseError) {}});
//      uid =
//    }
//  }


//  private void generateLine(String uid) {
//    final String[] tempUpline = new String[1];
//
//    usersRef.child(uid).child("upId").addListenerForSingleValueEvent(new ValueEventListener() {
//      @Override
//      public void onDataChange(DataSnapshot dataSnapshot) {
//        tempUpline[0] = dataSnapshot.getValue().toString();
//      }
//      @Override
//      public void onCancelled(@NonNull DatabaseError databaseError) {}});
//
//
//
//    lines.add(new Level(tempUpline[0]));
//    lines.add(new Level(uid));
//
//    for (int i = 2; i < 7; i++) {
//      usersRef.child(uid).child("dwId").addListenerForSingleValueEvent(new ValueEventListener() {
//        @Override
//        public void onDataChange(DataSnapshot dataSnapshot) {
//          lines.add(new Level((ArrayList<String>) dataSnapshot.getValue()));
//        }
//        @Override
//        public void onCancelled(@NonNull DatabaseError databaseError) {}});
//        uid =
//    }
//  }



//  private void initView() {
//    rv = (RecyclerView) getView().findViewById(R.id.rv);
//  }


//  @Override
//  public boolean onCreateOptionsMenu(Menu menu) {
//    getMenuInflater().inflate(R.menu.menu_main, menu);
//    return true;
//  }
//
//  @Override
//  public boolean onOptionsItemSelected(MenuItem item) {
//    int id = item.getItemId();
//    switch (id) {
//      case R.id.id_action_close_all:
//        adapter.collapseAll();
//        break;
//      default:
//        break;
//    }
//    return super.onOptionsItemSelected(item);
//  }
}
