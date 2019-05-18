package endgame.data.dreamcorporation;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;

public class Balance {
  private static double first = 0.5;
  private static double second = 0.12 + 0.03;
  private static String[] uplines = new String[6];
  private static double[] commission= new double[6];
//  private static double[] oldBalance= new double[5];

  private static double fee;
  private static String adminUid;
  private static FirebaseAuth mAuth = FirebaseAuth.getInstance();

  public static void calcRev(String scannedUpId) {
    // To set current user upId
    GetFirebase.usersRef.child(mAuth.getUid()).child("upId").setValue(scannedUpId);

    fee = GetFirebase.getFee();
    adminUid = GetFirebase.getAdminUid();

    uplines[0] = scannedUpId;
    uplines[5] = adminUid;
    getUplines(scannedUpId, 1);

    Log.e("Fee", String.valueOf(fee));

//    getOldBalance();
    calculate();
    GetFirebase.addBalance(uplines, commission, mAuth.getUid());
    GetFirebase.getFirebase();
  }

  public static void getUplines(String uid, int i) {
    if (i == 5) return;

    String tempUid = GetFirebase.getUsers(uid).getUplineUid();
    if (tempUid != null) {
      uplines[i] = tempUid;
      getUplines(tempUid, i + 1);
    } else getUplines(adminUid, i + 1);

//    if (i == 5) {
//      getOldBalance();
//      calculate();
//      addBalance();
//      return;
//    }
//
//    usersRef.child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
//      @Override
//      public void onDataChange(DataSnapshot dataSnapshot) {
//        if (dataSnapshot.child("upId").exists()) {
//          String upId = dataSnapshot.child("upId").getValue().toString();
//          uplines[i] = upId;
//          Log.e(i + "", upId);
//          getUplines(upId,  i + 1);
//        } else {
//          uplines[i] = adminUid;
//        }
//      }
//      @Override
//      public void onCancelled(@NonNull DatabaseError databaseError) {
//      }
//    });
  }

  public static void calculate() {
    for (int i = 0; i < 6; i++) {
      switch (i) {
        case 0:
          commission[i] = fee * first;
          break;
        case 5:
          commission[i] = fee * 0.2;
          break;
        default:
          commission[i] = fee * (second - (i * 0.03));
          break;
      }
    }
  }

//  public static void getOldBalance() {
////    Log.e("uplines", uplines.toString());
//    // mAuth.getUid() will get current logged in user punya UID
//    // This block of code will get the latest balance of the user from Firebase
//    for (int i = 0; i < 5; i++){
//      oldBalance[i] = GetFirebase.getUsers(uplines[i]).getBalance();
//    }
//  }

//  public static void addBalance() {
//    for (int i = 0; i < 5; i++) {
//      GetFirebase.addBalance(uplines[i], commission[i]);
////      tempbaby=i;
////      oldBalance[i] = oldBalance[i] + commission[i];
//////      Log.e("soh", String.valueOf(oldBalance[i]));
////      GetFirebase.usersRef.child(uplines[i]).child("b").setValue(oldBalance[i]);
////      if (uplines[i].equals(adminUid)) {
////        adminRef.child(mAuth.getUid()).child("b").addListenerForSingleValueEvent(new ValueEventListener() {
////          @Override
////          public void onDataChange(DataSnapshot dataSnapshot) {
////            temporary = Double.parseDouble(dataSnapshot.getValue().toString());
////            temporary = temporary + commission[tempbaby];
////          }
////
////          @Override
////          public void onCancelled(@NonNull DatabaseError databaseError) {
////          }
////        });
////        usersRef.child(adminUid).child("b").setValue(temporary);
////      }
//    }
//  }
//  public static String getDirectUplineUid() {
//    return uplines[0];
//  }
}
