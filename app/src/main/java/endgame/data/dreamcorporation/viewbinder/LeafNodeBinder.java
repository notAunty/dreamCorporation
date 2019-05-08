package endgame.data.dreamcorporation.viewbinder;

import android.view.View;
import android.widget.TextView;

import endgame.data.dreamcorporation.R;
import endgame.data.dreamcorporation.network.LeafNode;
import tellh.com.recyclertreeview_lib.TreeNode;
import tellh.com.recyclertreeview_lib.TreeViewBinder;

/**
 * Created by tlh on 2016/10/1 :)
 */

public class LeafNodeBinder extends TreeViewBinder<LeafNodeBinder.ViewHolder> {
    @Override
    public ViewHolder provideViewHolder(View itemView) {
        return new ViewHolder(itemView);
    }

    @Override
    public void bindView(ViewHolder holder, int position, TreeNode node) {
        LeafNode leafNodeNode = (LeafNode) node.getContent();
        holder.tvName.setText(leafNodeNode.leafName);
        holder.tvName.setText(leafNodeNode.leafName);
    }

    @Override
    public int getLayoutId() {
        return R.layout.network_leaf;
    }

    public class ViewHolder extends TreeViewBinder.ViewHolder {
        public TextView tvName;

        public ViewHolder(View rootView) {
            super(rootView);
            this.tvName = (TextView) rootView.findViewById(R.id.tv_name);
        }

    }
}
