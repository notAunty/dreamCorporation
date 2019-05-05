package endgame.data.dreamcorporation.network;

import endgame.data.dreamcorporation.R;
import tellh.com.recyclertreeview_lib.LayoutItemType;

public class Dir implements LayoutItemType {
  public String dirName;

  public Dir(String dirName) {
    this.dirName = dirName;
  }

  @Override
  public int getLayoutId() {
    return R.layout.item_dir;
  }
}