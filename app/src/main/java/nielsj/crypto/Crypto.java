package nielsj.crypto;

import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

// import org.w3c.dom.Text;

import nielsj.crypto.control.*;
import nielsj.crypto.view.FormattingControl;
import nielsj.crypto.exercises.Exercises;

// This is the top class/acticity
// It defines buttons for selecting symmetric encryption, etc.

public class Crypto extends AppCompatActivity {
  // String provider is the default provider
  // (to be used if provider BC/BouncyCastle does not work)
  public final static String provider = "AndroidOpenSSL";
  Button symmetricEncryptionButton, hashingButton,
          asymmetricEncryptionButton, formattingButton,
          exercisesButton;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.crypto);
    symmetricEncryptionButton = (Button) findViewById(R.id.symmetricEncryptionButton);
    symmetricEncryptionButton.setOnClickListener(new View.OnClickListener() {
      public void onClick(View v) {
        Intent i = new Intent(getApplicationContext(),
                          SymmetricEncryptionControl.class);
        startActivity(i);
      }
    });
    hashingButton = (Button) findViewById(R.id.hashingButton);
    hashingButton.setOnClickListener(new View.OnClickListener() {
      public void onClick(View v) {
        Intent i = new Intent(getApplicationContext(),
                            HashingControl.class);
        startActivity(i);
      }
    });
    asymmetricEncryptionButton = (Button) findViewById(R.id.asymmetricEncryptionButton);
    asymmetricEncryptionButton.setOnClickListener(new View.OnClickListener() {
      public void onClick(View v) {
      Intent i = new Intent(getApplicationContext(),
              AsymmetricEncryptionControl.class);
      startActivity(i);
      }
    });
    formattingButton = (Button) findViewById(R.id.formattingButton);
    formattingButton.setOnClickListener(new View.OnClickListener() {
      public void onClick(View v) {
        Intent i = new Intent(getApplicationContext(),
                FormattingControl.class);
        startActivity(i);
      }
    });
    exercisesButton = (Button) findViewById(R.id.exercisesButton);
    exercisesButton.setOnClickListener(new View.OnClickListener() {
      public void onClick(View v) {
        Intent i = new Intent(getApplicationContext(),
                Exercises.class);
        startActivity(i);
      }
    });
  }
}