package nielsj.crypto.control;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import nielsj.crypto.R;
import nielsj.crypto.model.*;

// class CaesarUI is the user interface to Caesar encryption

public class CaesarControl extends AppCompatActivity {

  // Most attributes of this activity are the views
  TextView welcomeTextView, decryptedTextView;
  Button encryptButton, decryptButton;
  EditText keyEditText, plaintextEditText, encryptedEditText;

  // The other main attribute is the crypto object
  // that does the cryptographic work

  nielsj.crypto.model.Caesar caesar = new Caesar();

  // Methods

  // onCreate() instantiates views based on XML file symmetricencryptionui.xml
  // and let buttons trigger the crypto object
  // to do encryption and decryption

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.caesar);
    welcomeTextView = (TextView) findViewById(R.id.welcomeTextView);
    welcomeTextView.setText("Caesar");
    keyEditText = (EditText) findViewById(R.id.keyEditText);
    keyEditText.setText(caesar.getKey());
    plaintextEditText = (EditText) findViewById(R.id.plaintextEditText);
    encryptButton = (Button) findViewById(R.id.encryptButton);
    encryptButton.setOnClickListener(new View.OnClickListener() {
      public void onClick(View v) {
        updateKey();
        String pt = plaintextEditText.getText().toString();
        String ct = caesar.encrypt(pt);
        encryptedEditText.setText(ct);
      }
    });
    encryptedEditText = (EditText) findViewById(R.id.encryptedEditText);
    decryptButton = (Button) findViewById(R.id.decryptButton);
    decryptButton.setOnClickListener(new View.OnClickListener() {
      public void onClick(View v) {
        updateKey();
        String ct = encryptedEditText.getText().toString();
        String pt = caesar.decrypt(ct);
        decryptedTextView.setText(pt);
      }
    });
    decryptedTextView = (TextView) findViewById(R.id.decryptedTextView);

  }
  private void updateKey(){
    String keyString = keyEditText.getText().toString();
    caesar.setKey(keyString);
    keyEditText.setText(caesar.getKey());
  }


}
