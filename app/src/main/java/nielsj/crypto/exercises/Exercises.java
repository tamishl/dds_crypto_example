package nielsj.crypto.exercises;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import nielsj.crypto.R;

// class SymmetricEncryptionUI is the user interface to symmetric encryption
// It selects between Caesar and AES encryption
// (for AES, the class also selects ECB or CBC)

public class Exercises extends AppCompatActivity {

  // The attributes of this activity are the exercise buttons
  Button caesarCrackerButton, aesCrackerButton, sha256CrackerButton;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.exercises);

    // The Caesar button
    caesarCrackerButton = (Button) findViewById(R.id.caesarCrackerButton);
    caesarCrackerButton.setOnClickListener(new View.OnClickListener() {
      public void onClick(View v) {
        Intent i = new Intent(getApplicationContext(),
                          CaesarCracker.class);
        startActivity(i);
      }
    });

    // The AES button
    aesCrackerButton = (Button) findViewById(R.id.aesCrackerButton);
    aesCrackerButton.setOnClickListener(new View.OnClickListener() {
      public void onClick(View v) {
        Intent i = new Intent(getApplicationContext(),
                          AESCracker.class);
        startActivity(i);
      }
    });

    // the SHA256 button
    sha256CrackerButton = (Button) findViewById(R.id.sha256CrackerButton);
    sha256CrackerButton.setOnClickListener(new View.OnClickListener() {
      public void onClick(View v) {
        Intent i = new Intent(getApplicationContext(),
                SHA256Cracker.class);
        startActivity(i);
      }
    });
  }
}
