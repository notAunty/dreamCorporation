package endgame.data.dreamcorporation;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class HomeFragment extends Fragment {

  private int counter = 0;
  private TextView recent = null;
  final Context context = getActivity();

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View v = inflater.inflate(R.layout.fragment_home, container, false);
    FloatingActionButton fab = v.findViewById(R.id.home_fab);
    recent = v.findViewById(R.id.recent);

    // Begin TESTING
    fab.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent i = new Intent(v.getContext(), activity_testing_data.class);
        startActivity(i);
      }
    });
    // End TESTING

    return v;
  }

}