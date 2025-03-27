package nielsj.crypto.control;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import nielsj.crypto.R;
import nielsj.crypto.model.*;

public class RSAControl extends AppCompatActivity {

  // Most attributes of this activity are the views

  TextView welcomeTextView, guideTextView;
  Button generateKeyPairButton, signButton, verifyButton;
  EditText modulusEditText, privateKeyExponentEditText,
          publicKeyExponentEditText, messageEditText,
          signatureEditText, verificationEditText;

  // The other main attribute is the crypto object
  // that does the cryptographic work

  nielsj.crypto.model.RSA rsa;

  // Methods

  // onCreate() lets buttons trigger the crypto object
  // to do encryption etc.

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.rsa);
    rsa = new RSA();
    welcomeTextView = (TextView) findViewById(R.id.welcomeTextView);
    welcomeTextView.setText("RSA with " + rsa.getKeySize() + " bit keysize");
    welcomeTextView.append("\n(security provider: " + rsa.getProvider() + ")");
    generateKeyPairButton= (Button) findViewById(R.id.generateKeyPairButton);
    generateKeyPairButton.setOnClickListener(new View.OnClickListener() {
      public void onClick(View v) {
        rsa.generateKeyPair();
        modulusEditText.setText(rsa.modulus.toString());
        privateKeyExponentEditText.setText(rsa.privateKeyExponent.toString());
        publicKeyExponentEditText.setText(rsa.publicKeyExponent.toString());
      }
    });
    modulusEditText = (EditText) findViewById(R.id.modulusEditText);
    privateKeyExponentEditText = (EditText) findViewById(R.id.privateKeyExponentEditText);
    publicKeyExponentEditText = (EditText) findViewById(R.id.publicKeyExponentEditText);
    guideTextView = (TextView) findViewById(R.id.guideTextView);
    messageEditText = (EditText) findViewById(R.id.messageEditText);
    signButton = (Button) findViewById(R.id.signButton);
    signButton.setOnClickListener(new View.OnClickListener() {
      public void onClick(View v) {
        String message = messageEditText.getText().toString();
        String signature = rsa.sign(message);
        signatureEditText.setText(signature);
      }
    });
    signatureEditText = (EditText) findViewById(R.id.signatureEditText);
    verifyButton = (Button) findViewById(R.id.verifyButton);
    verifyButton.setOnClickListener(new View.OnClickListener() {
      public void onClick(View v) {
        String result;
        String signature = signatureEditText.getText().toString();
        String message = messageEditText.getText().toString();
        if (rsa.verify(signature, message)) {
          result = "Signature is ok";
        } else {
          result = "Signature is not ok";
        }
        verificationEditText.setText(result);}
    });
    verificationEditText = (EditText) findViewById(R.id.verificationEditText);
    clear();
  }

  private void clear() {
    modulusEditText.setText("<modulus>");
    privateKeyExponentEditText.setText("<private key exponent>");
    publicKeyExponentEditText.setText("<public key exponent>");
  }


}
