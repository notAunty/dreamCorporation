package endgame.data.dreamcorporation;

import android.content.Intent;
import android.os.Bundle;
import android.view.SurfaceView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class dialogQrScanner extends AppCompatActivity{

  SurfaceView qrPreview;
//  CameraSource cameraSource;
//  BarcodeDetector barcodeDetector;
  String result;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.dialog_qr_scanner);

    IntentIntegrator integrator = new IntentIntegrator(this);
    integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE);
    integrator.setPrompt("Scan upline's QR Code.");
    integrator.setCameraId(0);
    integrator.setBeepEnabled(true);
    integrator.setBarcodeImageEnabled(false);
    integrator.initiateScan();
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCoede, Intent intent) {
    IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCoede, intent);

    if (result != null) {
      if (result.getContents() != null) {
        Toast.makeText(this, "Scan cancelled.", Toast.LENGTH_LONG).show();
      } else {
        Toast.makeText(this, result.getContents(), Toast.LENGTH_LONG).show();
      }
    } else {
      super.onActivityResult(requestCode, resultCoede, intent);
    }
  }
}
