package endgame.data.dreamcorporation;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class GetName {
  private static String tempFullName = "" ;
  private static FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
  private static DatabaseReference usersRef = mDatabase.getReference("users");

  protected static String getNameDirectly(String uid) {
    Log.e("uid: ", uid);
    usersRef.child(uid).child("fN").addListenerForSingleValueEvent(new ValueEventListener() {
      @Override
      public void onDataChange(DataSnapshot dataSnapshot) {
        Log.e("fN: ", Encryption.decodeDirectly(dataSnapshot.getValue().toString()));
        tempFullName = Encryption.decodeDirectly(dataSnapshot.getValue().toString());
      }

      @Override
      public void onCancelled(@NonNull DatabaseError databaseError) {}
    });
    return tempFullName;
  }

  protected static String getName(String u, String k) {
    final String uid = u;
    final String key = k;
    Log.e("uid: ", uid);
    usersRef.child(uid).child("fN").addListenerForSingleValueEvent(new ValueEventListener() {
      @Override
      public void onDataChange(DataSnapshot dataSnapshot) {
        Log.e("fN: ", Encryption.decode(dataSnapshot.getValue().toString(), key));
        tempFullName = Encryption.decode(dataSnapshot.getValue().toString(), key);
      }

      @Override
      public void onCancelled(@NonNull DatabaseError databaseError) {}
    });
    return tempFullName;
  }
}
