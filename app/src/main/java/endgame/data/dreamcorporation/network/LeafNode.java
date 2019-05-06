package endgame.data.dreamcorporation.network;

import endgame.data.dreamcorporation.R;
import tellh.com.recyclertreeview_lib.LayoutItemType;

/**
 * Created by tlh on 2016/10/1 :)
 */

public class LeafNode implements LayoutItemType {
    public String leafName;

    public LeafNode(String leafName) {
        this.leafName = leafName;
    }

    @Override
    public int getLayoutId() {
        return R.layout.network_leaf;
    }
}
