package nielsj.crypto.exercises;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

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


    cipherEditTextI =  findViewById(R.id.cipherEditTextI);
    cipherEditTextII = findViewById(R.id.cipherEditTextII);

    cipherEditTextI.setText(ciphertextI);
    cipherEditTextII.setText(ciphertextII);

    // Attach EditText to button
    crackIButton = findViewById(R.id.crackIButton);
    crackIButton.setTag(cipherEditTextI);
    crackIIButton = findViewById(R.id.crackIIButton);
    crackIIButton.setTag(cipherEditTextII);

    decryptedTextView = findViewById(R.id.decryptedTextView);

    Caesar caesar = new Caesar();

    View.OnClickListener crackListener = new View.OnClickListener() {
      public void onClick(View v) {
        EditText editText = (EditText) v.getTag();
        String ct = editText.getText().toString();
        decryptedTextView.setText("The ciphertext is one of these options:");
        ArrayList<String> pts = caesar.crack(ct);

        for (int i = 0; i < pts.size(); i++) {
          decryptedTextView.append("\n"  + pts.get(i) + " (key=" + i + ")");
        }
      }
    };

    crackIButton.setOnClickListener(crackListener);
    crackIIButton.setOnClickListener(crackListener);
  }
}