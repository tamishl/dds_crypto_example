package nielsj.crypto.exercises;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Button;
import nielsj.crypto.model.AES;
import nielsj.crypto.view.Hex;
import nielsj.crypto.R;

public class AESCracker extends AppCompatActivity {

  // In the assignment, the following
  // ciphertexts and key fragment are given
  String ct1 = "B9B61F1ECDAE93CAA9DCBCEFDF454729"
          +    "A995802CBF9870C68CB90C4D80E56139";

  // If you like you can also try to guess the key
  // used to encrypt ct2

  String ct2 = "3991842C588CEB0EF36C6CF7C3E0E359"
          +    "C5E9CC80F01365A0DDD6CFBD56EBB428";
  String keyHex28Zeroes = "0000000000000000"
          + "000000000000";
  // 28 (16+12) zeros = 14 zero bytes

  EditText cipherEditText;
  Button crackButton, clearButton;
  TextView keyValueTextView, plaintextValueTextView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.aescracker);
    cipherEditText =  (EditText) findViewById(R.id.cipherEditText);
    cipherEditText.setText(ct1);
    crackButton = (Button) findViewById(R.id.crackButton);
    clearButton = (Button) findViewById(R.id.clearButton);
    keyValueTextView = (TextView) findViewById(R.id.keyValue);
    plaintextValueTextView = (TextView) findViewById(R.id.plaintextValue);
    crackButton.setOnClickListener(new View.OnClickListener() {
      public void onClick(View v) {
        crack();
      }
    });
    clearButton.setOnClickListener(new View.OnClickListener() {
      public void onClick(View view) {
        keyValueTextView.setText("(key value)");
        plaintextValueTextView.setText("(plaintext value)");
      }
    });
  }
  void crack() {
    AES crypto = new AES("ECB", "NoPadding");
    String cipherText = cipherEditText.getText().toString();
    String pt = "";
    String keyGuess = "";
    String hex1, hex2;
    for (int i = 0; i <= 255; i++){
      for (int j = 0; j <= 255; j++)
      {
        hex1 = Hex.byteToHexPair(i);
        hex2 = Hex.byteToHexPair(j);
        keyGuess = keyHex28Zeroes + hex1 + hex2;
        pt = crypto.decrypt(cipherText, keyGuess);
        if (pt.startsWith("Heil Hitler")){
          keyValueTextView.setText(keyGuess);
          plaintextValueTextView.setText(pt);
          return;
        }
      }
    }
//    int b1 = 0, b2 = 1;
//    String hex1, hex2;
//    hex1 = Hex.byteToHexPair(b1);
//    hex2 = Hex.byteToHexPair(b2);
//    keyGuess = keyHex28Zeroes + hex1 + hex2;
//    pt = crypto.decrypt(cipherText, keyGuess);
//    keyValueTextView.setText(keyGuess);
//    plaintextValueTextView.setText(pt);
  }
}

// The answers are:
// ct1:    key = "00000000000000000000000000009A6E";
//   plaintext = "Heil Hitler. Attack London 9.00"
// ct2:    key = "0000000000000000000000000000E37F"
//   plaintext = "Heil Hitler. Back to base"
