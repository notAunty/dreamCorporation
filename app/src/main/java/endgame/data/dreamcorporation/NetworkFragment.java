package endgame.data.dreamcorporation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import endgame.data.dreamcorporation.network.NodeDir;
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

    initData();
    initView();

    return view;
  }

  private void initData() {
    List<TreeNode> nodes = new ArrayList<>();
    TreeNode<NodeDir> app = new TreeNode<>(new NodeDir("app"));
    nodes.add(app);
    app.addChild(
            new TreeNode<>(new NodeDir("manifests"))
                    .addChild(new TreeNode<>(new File("AndroidManifest.xml")))
    );
    app.addChild(
            new TreeNode<>(new NodeDir("java")).addChild(
                    new TreeNode<>(new NodeDir("tellh")).addChild(
                            new TreeNode<>(new NodeDir("com")).addChild(
                                    new TreeNode<>(new NodeDir("recyclertreeview"))
                                            .addChild(new TreeNode<>(new File("NodeDir")))
                                            .addChild(new TreeNode<>(new File("DirectoryNodeBinder")))
                                            .addChild(new TreeNode<>(new File("File")))
                                            .addChild(new TreeNode<>(new File("FileNodeBinder")))
                                            .addChild(new TreeNode<>(new File("TreeViewBinder")))
                            )
                    )
            )
    );
    TreeNode<NodeDir> res = new TreeNode<>(new NodeDir("res"));
    nodes.add(res);
    res.addChild(
            new TreeNode<>(new NodeDir("layout")).lock() // lock this TreeNode
                    .addChild(new TreeNode<>(new File("activity_main.xml")))
                    .addChild(new TreeNode<>(new File("item_dir.xml")))
                    .addChild(new TreeNode<>(new File("item_file.xml")))
    );
    res.addChild(
            new TreeNode<>(new NodeDir("mipmap"))
                    .addChild(new TreeNode<>(new File("ic_launcher.png")))
    );

    rv.setLayoutManager(new LinearLayoutManager(this));
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

  private void initView() {
    rv = (RecyclerView) findViewById(R.id.rv);
  }


  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.menu_main, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    int id = item.getItemId();
    switch (id) {
      case R.id.id_action_close_all:
        adapter.collapseAll();
        break;
      default:
        break;
    }
    return super.onOptionsItemSelected(item);
  }
}
