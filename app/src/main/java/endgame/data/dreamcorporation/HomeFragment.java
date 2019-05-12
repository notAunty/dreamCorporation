package endgame.data.dreamcorporation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.integration.android.IntentIntegrator;
import com.leinardi.android.speeddial.SpeedDialActionItem;
import com.leinardi.android.speeddial.SpeedDialView;

import java.util.ArrayList;

import endgame.data.dreamcorporation.profile.Word;
import endgame.data.dreamcorporation.profile.WordAdapter;

public class HomeFragment extends Fragment {

  private int counter = 0;
  private ListView listView;
  private ArrayList<Word> words;
  private FirebaseAuth mAuth = FirebaseAuth.getInstance();
  private FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
  private DatabaseReference usersRef = mDatabase.getReference("users");
  private DatabaseReference transRef = mDatabase.getReference("transactions");
  private SpeedDialView speedDial;
  private ArrayList<String> downlines;

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View v = inflater.inflate(R.layout.fragment_home, container, false);

    // Balance
    final TextView balance = v.findViewById(R.id.home_balance);
    usersRef.child(mAuth.getUid()).child("b").addListenerForSingleValueEvent(new ValueEventListener() {
      @Override
      public void onDataChange(DataSnapshot dataSnapshot) {
        balance.setText(getString(R.string.currency) + " " + dataSnapshot.getValue().toString());
      }

      @Override
      public void onCancelled(@NonNull DatabaseError databaseError) {}
    });

    displayList(v);

    speedDial = v.findViewById(R.id.home_fab);
    speedDial.inflate(R.menu.menu_fab);

    speedDial.setOnActionSelectedListener(new SpeedDialView.OnActionSelectedListener() {
      @Override
      public boolean onActionSelected(SpeedDialActionItem speedDialActionItem) {
        switch (speedDialActionItem.getId()) {
          case R.id.fabScanQR:
            scanQRCode();
            Balance.calcRev();
            addUpline();
            uplineAccountAddDownline();
            return false;
          case R.id.fabEnterDetails:
            return false;
          default:
            return false;
        }
      }
    });

    return v;
  }

  private void scanQRCode(){
    new IntentIntegrator(getActivity())
            .setOrientationLocked(false)
            .setBeepEnabled(false)
            .initiateScan();
  }

  public void displayList(View view){
    words = new ArrayList<Word>();
    words.add(new Word(getResources().getString(R.string.example),getResources().getString(R.string.example)));
    words.add(new Word("CHANGE TO BETTER ONE, this no ripple then tapped",getResources().getString(R.string.example)));
    words.add(new Word(getResources().getString(R.string.example), getResources().getString(R.string.example)));
    words.add(new Word(getResources().getString(R.string.example), getResources().getString(R.string.example)));
    words.add(new Word(getResources().getString(R.string.example), getResources().getString(R.string.example)));
    words.add(new Word(getResources().getString(R.string.example), getResources().getString(R.string.example)));
    words.add(new Word(getResources().getString(R.string.example), getResources().getString(R.string.example)));
    words.add(new Word(getResources().getString(R.string.example), getResources().getString(R.string.example)));
    words.add(new Word(getResources().getString(R.string.example), getResources().getString(R.string.example)));
    words.add(new Word(getResources().getString(R.string.example), getResources().getString(R.string.example)));
    words.add(new Word(getResources().getString(R.string.example), getResources().getString(R.string.example)));
    words.add(new Word(getResources().getString(R.string.example), getResources().getString(R.string.example)));
    words.add(new Word(getResources().getString(R.string.example), getResources().getString(R.string.example)));
    WordAdapter itemAdapter = new WordAdapter(getActivity(),  words);
    listView = (ListView) view.findViewById(R.id.home_listView);
    listView.setAdapter(itemAdapter);
  }

  public void addUpline() {
    usersRef.child(mAuth.getUid()).child("fN").setValue(Balance.getDirectUplineUid());
  }

  public void uplineAccountAddDownline() {
    // Get upline punya downline Array
    usersRef.child(Balance.getDirectUplineUid()).addListenerForSingleValueEvent(new ValueEventListener() {
      @Override
      public void onDataChange(DataSnapshot dataSnapshot) {
        if (!dataSnapshot.child("dwId").exists()) {
          downlines = new ArrayList<String>();
          downlines.add(mAuth.getUid());
        } else {
          downlines = (ArrayList<String>) dataSnapshot.getValue();
          downlines.add(mAuth.getUid());
        }
      }

      @Override
      public void onCancelled(@NonNull DatabaseError databaseError) {}
    });
    usersRef.child(Balance.getDirectUplineUid()).child("dwId").setValue(downlines);
  }
}