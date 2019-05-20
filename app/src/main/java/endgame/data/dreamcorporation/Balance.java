package endgame.data.dreamcorporation;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;

public class Balance {
  private static double first = 0.5;
  private static double second = 0.12 + 0.03;
  private static String[] uplines = new String[6];
  private static double[] commission= new double[6];

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
}
