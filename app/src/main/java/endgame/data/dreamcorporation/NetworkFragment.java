package endgame.data.dreamcorporation;

import android.os.Bundle;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import endgame.data.dreamcorporation.viewbinder.LeafNodeBinder;
import endgame.data.dreamcorporation.viewbinder.ParentNodeBinder;
import tellh.com.recyclertreeview_lib.TreeNode;
import tellh.com.recyclertreeview_lib.TreeViewAdapter;

public class NetworkFragment extends Fragment {

  private FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
  private DatabaseReference usersRef = mDatabase.getReference("users");
  private DatabaseReference transRef = mDatabase.getReference("transaction");
  private FirebaseAuth mAuth = FirebaseAuth.getInstance();
  private String uid = mAuth.getUid();
  private RecyclerView rv;
  private TreeViewAdapter adapter;
  private Level lines;

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_network, container, false);

//    generateLine(mAuth.getUid());

    rv = (RecyclerView) view.findViewById(R.id.rv);
//    initView();
    initData();

    return view;
  }

  private void initData() {
    List<TreeNode> nodes = new ArrayList<>();



//    // Upline
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
//    nodes.add(upline);
//
//    // Current
//    upline.addChild();



//    List<TreeNode> nodes = new ArrayList<>();
//    TreeNode<ParentNode> app = new TreeNode<>(new ParentNode("Mike Jones"));
//    nodes.add(app);
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

    rv.setLayoutManager(new LinearLayoutManager(getContext()));
    adapter = new TreeViewAdapter(nodes, Arrays.asList(new LeafNodeBinder(), new ParentNodeBinder()));
    // whether collapse child nodes when their parent node was close.
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

  private void generateLine(String uid) {
    usersRef.child(uid).child("dwId").addListenerForSingleValueEvent(new ValueEventListener() {
      @Override
      public void onDataChange(DataSnapshot dataSnapshot) {
        ArrayList<String> tempDw = new ArrayList();
        ArrayList<Boolean> tempHaveChild;
        tempDw = (ArrayList<String>) dataSnapshot.getValue();

        for (String a: tempDw) {
          usersRef.child(a).child("dwId").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
              if (dataSnapshot.getValue() != null) {
//                tempHaveChild.add(true);
              }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}});
        }
      }
      @Override
      public void onCancelled(@NonNull DatabaseError databaseError) {}});
  }


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
