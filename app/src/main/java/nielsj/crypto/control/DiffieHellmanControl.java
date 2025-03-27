package nielsj.crypto.control;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import nielsj.crypto.R;
import nielsj.crypto.model.*;

public class DiffieHellmanControl extends AppCompatActivity {

  TextView welcomeTextView;
  // bobTextView, sharedSecretTextView;
  Button generateParametersButton, generateAliceValuesButton,
          generateBobValuesButton, generateSharedSecretButton;
  EditText baseValueEditText, modulusValueEditText,
          alicePrivateValueEditText, alicePublicValueEditText, aliceSecretValueEditText,
          bobPrivateValueEditText, bobPublicValueEditText, bobSecretValueEditText;

  // The other main attribute is the crypto object
  // that does the cryptographic work

  DiffieHellman dh;
  DiffieHellman.Member alice, bob;

  // Methods

  // onCreate() lets buttons trigger the crypto object
  // to do encryption etc.

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.diffiehellman);
    alice = new DiffieHellman.Member();
    bob = new DiffieHellman.Member();
    dh = new DiffieHellman(alice, bob);
    welcomeTextView = (TextView) findViewById(R.id.welcomeTextView);
    welcomeTextView.setText("Diffie-Hellman with " + dh.getKeySize() + " bit keysize");
    welcomeTextView.append("\n(security provider: " + dh.getProvider() + ")");
    generateParametersButton= (Button) findViewById(R.id.generateParametersButton);
    generateParametersButton.setOnClickListener(new View.OnClickListener() {
      public void onClick(View v) {
        dh.generateDHParameters();
        baseValueEditText.setText(dh.getBase());
        modulusValueEditText.setText(dh.getModulus());
       // clearOtherValues();
      }
    });
    baseValueEditText = (EditText) findViewById(R.id.baseValueEditText);
    modulusValueEditText = (EditText) findViewById(R.id.modulusValueEditText);
    generateAliceValuesButton = (Button) findViewById(R.id.generateAliceValuesButton);
    generateAliceValuesButton.setOnClickListener(new View.OnClickListener() {
      public void onClick(View v) {
        alice.generateValues();
        alicePrivateValueEditText.setText(alice.getPrivateValue());
        alicePublicValueEditText.setText(alice.getPublicValue());
      }
    });
    alicePrivateValueEditText = (EditText) findViewById(R.id.alicePrivateValueEditText);
    alicePublicValueEditText = (EditText) findViewById(R.id.alicePublicValueEditText);
    aliceSecretValueEditText = (EditText) findViewById(R.id.aliceSecretValueEditText);
    generateBobValuesButton = (Button) findViewById(R.id.generateBobValuesButton);
    generateBobValuesButton.setOnClickListener(new View.OnClickListener() {
      public void onClick(View v) {
        bob.generateValues();
        bobPrivateValueEditText.setText(bob.getPrivateValue());
        bobPublicValueEditText.setText(bob.getPublicValue());
      }
    });
    bobPrivateValueEditText = (EditText) findViewById(R.id.bobPrivateValueEditText);
    bobPublicValueEditText = (EditText) findViewById(R.id.bobPublicValueEditText);
    bobSecretValueEditText = (EditText) findViewById(R.id.bobSecretValueEditText);
    generateSharedSecretButton = (Button) findViewById(R.id.generateSharedSecretButton);
    generateSharedSecretButton.setOnClickListener(new View.OnClickListener() {
      public void onClick(View v) {
        aliceSecretValueEditText.setText(alice.getSharedSecret());
        bobSecretValueEditText.setText(bob.getSharedSecret());
      }
    });
  }


}
