package endgame.data.dreamcorporation.network;

import endgame.data.dreamcorporation.R;
import tellh.com.recyclertreeview_lib.LayoutItemType;

public class NodeDir implements LayoutItemType {
  public String dirName;

  public NodeDir(String dirName) {
    this.dirName = dirName;
  }

  @Override
  public int getLayoutId() {
    return R.layout.item_dir;
  }
}