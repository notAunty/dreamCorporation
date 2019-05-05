package endgame.data.dreamcorporation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.unnamed.b.atv.model.TreeNode;
import com.unnamed.b.atv.view.AndroidTreeView;

import endgame.data.dreamcorporation.network.IconTreeItemHolder;

public class NetworkFragment extends Fragment {

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_network, container, false);
    NodeStyle.IconTreeItem nodeItem = new NodeStyle.IconTreeItem();
    TreeNode root = TreeNode.root();

    TreeNode parent = new TreeNode(new IconTreeItemHolder.IconTreeItem(R.string.ic_folder, "Folder with very long name ")).setViewHolder(
            new ArrowExpandSelectableHeaderHolder(getActivity()));;
    TreeNode child0 = new TreeNode(nodeItem).setViewHolder(new NodeStyle(getContext()));
    TreeNode child1 = new TreeNode(nodeItem).setViewHolder(new NodeStyle(getContext()));
    parent.addChildren(child0, child1);
    root.addChild(parent);

    AndroidTreeView tView = new AndroidTreeView(getActivity(), root).setDefaultViewHolder();
    ((LinearLayout) view.findViewById(R.id.treeContainer)).addView(tView.getView());

    return view;
  }
}
