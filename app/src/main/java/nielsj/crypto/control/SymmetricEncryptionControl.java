package nielsj.crypto.control;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import nielsj.crypto.R;

// class SymmetricEncryptionUI is the user interface to symmetric encryption
// It selects between Caesar and AES encryption
// (for AES, the class also selects ECB or CBC)

public class SymmetricEncryptionControl extends AppCompatActivity {

  // The attributes of this activity are the views and buttons
  Button caesarButton, aesButton;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.symmetricencryption);

    // The Caesar button
    caesarButton = (Button) findViewById(R.id.caesarButton);
    caesarButton.setOnClickListener(new View.OnClickListener() {
      public void onClick(View v) {
        Intent i = new Intent(getApplicationContext(),
                          CaesarControl.class);
        startActivity(i);
      }
    });

    // The AES button
    aesButton = (Button) findViewById(R.id.aesButton);
    aesButton.setOnClickListener(new View.OnClickListener() {
      public void onClick(View v) {
        Intent i = new Intent(getApplicationContext(),
                          AESControl.class);
        startActivity(i);
      }
    });
  }
}