package endgame.data.dreamcorporation;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import endgame.data.dreamcorporation.network.LeafNode;
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
  private SwipeRefreshLayout swipeRefreshLayout;
  private View view;
//  private Node node, tempNode;

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    view = inflater.inflate(R.layout.fragment_network, container, false);
    rv = (RecyclerView) view.findViewById(R.id.rv);
    initData();
    refresh();

    return view;
  }

  private void refresh(){
    swipeRefreshLayout = view.findViewById(R.id.network_container);
    swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
      @Override
      public void onRefresh() {
        Toast.makeText(getContext(), "Refreshed.", Toast.LENGTH_SHORT).show();
        // To keep animation for 4 seconds
        new Handler().postDelayed(new Runnable() {
          @Override public void run() {
            // Stop animation (This will be after 3 seconds)
            swipeRefreshLayout.setRefreshing(false);
          }
        }, 1000); // Delay in millis
        initData();
      }
    });
  }

  private void initData() {
    List<TreeNode> treeNodes = new ArrayList<>();

    if (uid.equals(GetFirebase.getAdminUid())) {
      root = new TreeNode<>(new ParentNode(GetFirebase.getUsers(uid).getUserName()));
      treeNodes.add(root);

      generateLineAdmin(uid, root);
    } else {
      String temp = GetFirebase.getUsers(uid).getUplineUid();
      if (temp == null) return;
      root = new TreeNode<>(new ParentNode(GetFirebase.getUsers(temp).getUserName()));
      treeNodes.add(root);

      generateLine(temp, root);
    }

    rv.setLayoutManager(new LinearLayoutManager(getContext()));
    adapter = new TreeViewAdapter(treeNodes, Arrays.asList(new LeafNodeBinder(), new ParentNodeBinder()));
    // whether collapse child treeNodes when their parent node was close.

    adapter.setOnTreeNodeListener(new TreeViewAdapter.OnTreeNodeListener() {
      @Override
      public boolean onClick(TreeNode node, RecyclerView.ViewHolder holder) {
        if (!node.isLeaf()) {
          //Update and toggle the node.
          onToggle(!node.isExpand(), holder);
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

  private void generateLineAdmin(String userId, TreeNode ref) {
    ArrayList<String> tempDw = GetFirebase.getUsers(userId).getDownlineUid();

    for (String a : tempDw) {
      if (!GetFirebase.getUsers(a).getDownlineUid().isEmpty()) {
        TreeNode r = new TreeNode<>(new ParentNode(GetFirebase.getUsers(a).getUserName()));
        ref.addChild(r);
        generateLineAdmin(a, r); //Recursive
      } else {
        TreeNode r = new TreeNode<>(new LeafNode(GetFirebase.getUsers(a).getUserName()));
        ref.addChild(r);
      }
    }
  }

  private void generateLine(String userId, TreeNode ref) {
    ArrayList<String> tempThisUser = GetFirebase.getUsers(uid).getDownlineUid();

    if (tempThisUser.isEmpty()) {
      TreeNode up = new TreeNode<>(new LeafNode(GetFirebase.getUsers(uid).getUserName()));
      ref.addChild(up);
    } else {
      TreeNode up = new TreeNode<>(new ParentNode(GetFirebase.getUsers(uid).getUserName()));
      ref.addChild(up);

      for (String a : tempThisUser) {
        TreeNode r = new TreeNode<>(new LeafNode(GetFirebase.getUsers(a).getUserName()));
        up.addChild(r);
      }
    }
  }
}
