package endgame.data.dreamcorporation.network;

import endgame.data.dreamcorporation.R;
import tellh.com.recyclertreeview_lib.LayoutItemType;

public class ParentNode implements LayoutItemType {
  public String parentName;

  public ParentNode(String parentName) {
    this.parentName = parentName;
  }

  @Override
  public int getLayoutId() {
    return R.layout.network_parent;
  }
}