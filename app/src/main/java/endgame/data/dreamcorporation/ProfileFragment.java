package endgame.data.dreamcorporation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;

public class ProfileFragment extends Fragment {

  private FirebaseAuth mAuth;

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    mAuth = FirebaseAuth.getInstance(); // Initialize Firebase Auth // IMPORTANT
    View view = inflater.inflate(R.layout.fragment_profile, container, false);

    TextView fullName = (TextView) view.findViewById(R.id.profile_name);
    String tempUID = mAuth.getUid();

    if (!tempUID.isEmpty()) fullName.setText(tempUID);

    return view;
  }
}
