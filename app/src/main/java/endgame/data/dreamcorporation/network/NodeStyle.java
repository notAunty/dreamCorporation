package endgame.data.dreamcorporation.network;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.github.johnkil.print.PrintView;
import com.unnamed.b.atv.model.TreeNode;

import endgame.data.dreamcorporation.R;

public class NodeStyle extends TreeNode.BaseNodeViewHolder<NodeStyle.IconTreeItem> {

  public NodeStyle(Context context) {
    super(context);
  }

  @Override
  public View createNodeView(TreeNode node, IconTreeItem value) {
    final LayoutInflater inflater = LayoutInflater.from(context);
    final View view = inflater.inflate(R.layout.list_item, null, false);

    TextView tvValue = view.findViewById(R.id.title);
    tvValue.setText(value.text);

    final PrintView iconView = (PrintView) view.findViewById()

    return view;
  }

  public static class IconTreeItem {
    public int icon = R.drawable.ic_add;
    public String text = "Test";
  }
}
