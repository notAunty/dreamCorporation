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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import endgame.data.dreamcorporation.network.LeafNode;
import endgame.data.dreamcorporation.network.ParentNode;
import endgame.data.dreamcorporation.viewbinder.DirectoryNodeBinder;
import endgame.data.dreamcorporation.viewbinder.FileNodeBinder;
import tellh.com.recyclertreeview_lib.TreeNode;
import tellh.com.recyclertreeview_lib.TreeViewAdapter;

public class NetworkFragment extends Fragment {

  private RecyclerView rv;
  private TreeViewAdapter adapter;

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_network, container, false);

    rv = (RecyclerView) view.findViewById(R.id.rv);

//    initView();
    initData();

    return view;
  }

  private void initData() {
    List<TreeNode> nodes = new ArrayList<>();
    TreeNode<ParentNode> app = new TreeNode<>(new ParentNode("Mike Jones"));
    nodes.add(app);
    app.addChild(
            new TreeNode<>(new ParentNode("manifests"))
                    .addChild(new TreeNode<>(new LeafNode("Abby Jones")))
    );
    app.addChild(
            new TreeNode<>(new ParentNode("Bonny Jones")).addChild(
                    new TreeNode<>(new ParentNode("Come on make me feel alive!")).addChild(
                            new TreeNode<>(new ParentNode("Moby Dick")).addChild(
                                    new TreeNode<>(new ParentNode("Ben"))
                                            .addChild(new TreeNode<>(new LeafNode("hth543g34gh453543g543g345g54gertthrhctr")))
                                            .addChild(new TreeNode<>(new LeafNode("hth543g34gh453543g543g345g54gertthrhctr Jones")))
                                            .addChild(new TreeNode<>(new LeafNode("Abby hth543g34gh453543g543g345g54gertthrhctr")))
                                            .addChild(new TreeNode<>(new LeafNode("Abby hth543g34gh453543g543g345g54gertthrhctr")))
                                            .addChild(new TreeNode<>(new LeafNode("Abby hth543g34gh453543g543g345g54gertthrhctr")))
                                            .addChild(new TreeNode<>(new LeafNode("hth543g34gh453543g543g345g54gertthrhctr Jones")))
                                            .addChild(new TreeNode<>(new LeafNode("Abby Jones")))
                                            .addChild(new TreeNode<>(new LeafNode("Abby hth543g34gh453543g543g345g54gertthrhctr")))
                                            .addChild(new TreeNode<>(new LeafNode("Abby Jones")))
                                            .addChild(new TreeNode<>(new LeafNode("Abby Jones")))
                                            .addChild(new TreeNode<>(new LeafNode("hth543g34gh453543g543g345g54gertthrhctr Jones")))
                                            .addChild(new TreeNode<>(new LeafNode("Abby Jones")))
                                            .addChild(new TreeNode<>(new LeafNode("Abby Jones")))
                                            .addChild(new TreeNode<>(new LeafNode("Abby Jones")))
                                            .addChild(new TreeNode<>(new LeafNode("Abby Jones")))
                                            .addChild(new TreeNode<>(new LeafNode("Abby Jones")))
                                            .addChild(new TreeNode<>(new LeafNode("Abby Jones")))
                                            .addChild(new TreeNode<>(new LeafNode("Abby Jones")))
                                            .addChild(new TreeNode<>(new LeafNode("Abby Jones")))
                            )
                    )
            )
    );
    rv.setLayoutManager(new LinearLayoutManager(getContext()));
    adapter = new TreeViewAdapter(nodes, Arrays.asList(new FileNodeBinder(), new DirectoryNodeBinder()));
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
        DirectoryNodeBinder.ViewHolder dirViewHolder = (DirectoryNodeBinder.ViewHolder) holder;
        final ImageView ivArrow = dirViewHolder.getIvArrow();
        int rotateDegree = isExpand ? 90 : -90;
        ivArrow.animate().rotationBy(rotateDegree)
                .start();
      }
    });
    rv.setAdapter(adapter);
  }

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
