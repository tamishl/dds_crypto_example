package nielsj.crypto.exercises;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Button;
import android.widget.EditText;
import nielsj.crypto.R;
import nielsj.crypto.model.Caesar;

public class CaesarCracker extends AppCompatActivity {

  TextView decryptedTextView;
  Button crackIButton, crackIIButton;
  EditText cipherEditTextI, cipherEditTextII;
  String ciphertextI = "HCLJHLZHYDLHAAHJRHAZPE",
         ciphertextII = "NIRPNRFNEUBYQLBHEUBEFRF";


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.caesarcracker);
    Caesar caesar = new Caesar();
    cipherEditTextI = (EditText) findViewById(R.id.cipherEditTextI);
    cipherEditTextI.setText(ciphertextI);
    cipherEditTextII = (EditText) findViewById(R.id.cipherEditTextII);
    cipherEditTextII.setText(ciphertextII);
    decryptedTextView = (TextView) findViewById(R.id.deryptedTextView);
    crackIButton = (Button) findViewById(R.id.crackIButton);
    crackIButton.setOnClickListener(new View.OnClickListener() {
      public void onClick(View v) {
        String ct = cipherEditTextI.getText().toString();
        decryptedTextView.setText("The ciphertext is " + ct);
        int k = 0;
        String key = String.valueOf(k);
        decryptedTextView.append("\nThe current key is " + key);
      }
    });
    crackIIButton = (Button) findViewById(R.id.crackIIButton);
    crackIIButton.setOnClickListener(new View.OnClickListener() {
      public void onClick(View v) {
        String ct = cipherEditTextII.getText().toString();
        decryptedTextView.setText("The ciphertext is " + ct);
      }
    });
  }
}
