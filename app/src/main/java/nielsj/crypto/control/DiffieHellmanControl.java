package nielsj.crypto.control;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import androidx.appcompat.app.AppCompatActivity;
import nielsj.crypto.R;
import nielsj.crypto.model.*;

public class DiffieHellmanControl extends AppCompatActivity {
  // Variables
  TextView welcomeTextView;
  RadioButton b4RadioButton, b256RadioButton, b512RadioButton,
              b1024RadioButton, b2048RadioButton;
  RadioGroup keysizeRadioGroup;
  Button generateParametersButton, clearButton,
         generateAliceValuesButton, generateAliceSecretButton,
         generateBobValuesButton, generateBobSecretButton;
  EditText baseValueEditText, modulusValueEditText,
           alicePrivateValueEditText, alicePublicValueEditText, aliceSecretValueEditText,
           bobPrivateValueEditText, bobPublicValueEditText, bobSecretValueEditText;
  DiffieHellman.Member alice = new DiffieHellman.Member(),
                       bob = new DiffieHellman.Member();
  DiffieHellman dh;

  // Methods
  // onCreate() lets buttons trigger the DH crypto object computations
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.diffiehellman);
    welcomeTextView = (TextView) findViewById(R.id.welcomeTextView);
    // I need to define radiobuttons before writing actual welcome-text
    // since welcome-text displays keysize, which is initialized in XML-layout-file
    b4RadioButton = (RadioButton) findViewById(R.id.b4RadioButton);
    b256RadioButton = (RadioButton) findViewById(R.id.b256RadioButton);
    b512RadioButton = (RadioButton) findViewById(R.id.b512RadioButton);
    b1024RadioButton = (RadioButton) findViewById(R.id.b1024RadioButton);
    b2048RadioButton = (RadioButton) findViewById(R.id.b2048RadioButton);
    keysizeRadioGroup = (RadioGroup) findViewById(R.id.keysizeRadioGroup);
    keysizeRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
      public void onCheckedChanged(RadioGroup g, int checkedId) {
        RadioButton checkedRadioButton = (RadioButton) findViewById(checkedId);
        dh = initDH(checkedRadioButton, welcomeTextView);
      }
    });
    RadioButton checkedRadioButton = (RadioButton) keysizeRadioGroup.findViewById(keysizeRadioGroup.getCheckedRadioButtonId());
    dh = initDH(checkedRadioButton, welcomeTextView);
    generateParametersButton= (Button) findViewById(R.id.generateParametersButton);
    generateParametersButton.setOnClickListener(new View.OnClickListener() {
      public void onClick(View v) {
        dh.generateDHParameters();
        baseValueEditText.setText(dh.getBase());
        modulusValueEditText.setText(dh.getModulus());
       // clearOtherValues();
      }
    });
    clearButton = (Button) findViewById(R.id.clearButton);
    baseValueEditText = (EditText) findViewById(R.id.baseValueEditText);
    modulusValueEditText = (EditText) findViewById(R.id.modulusValueEditText);
    alicePrivateValueEditText = (EditText) findViewById(R.id.alicePrivateValueEditText);
    alicePublicValueEditText = (EditText) findViewById(R.id.alicePublicValueEditText);
    aliceSecretValueEditText = (EditText) findViewById(R.id.aliceSecretValueEditText);
    bobPrivateValueEditText = (EditText) findViewById(R.id.bobPrivateValueEditText);
    bobPublicValueEditText = (EditText) findViewById(R.id.bobPublicValueEditText);
    bobSecretValueEditText = (EditText) findViewById(R.id.bobSecretValueEditText);
    clearButton.setOnClickListener(new View.OnClickListener() {
      public void onClick(View v) {
        baseValueEditText.setText("");
        modulusValueEditText.setText("");
        alicePrivateValueEditText.setText("");
        alicePublicValueEditText.setText("");
        aliceSecretValueEditText.setText("");
        bobPrivateValueEditText.setText("");
        bobPublicValueEditText.setText("");
        bobSecretValueEditText.setText("");
      }
    });
    generateAliceValuesButton = (Button) findViewById(R.id.generateAliceValuesButton);
    generateAliceValuesButton.setOnClickListener(new View.OnClickListener() {
      public void onClick(View v) {
        alice.generateValues();
        alicePrivateValueEditText.setText(alice.getPrivateValue());
        alicePublicValueEditText.setText(alice.getPublicValue());
      }
    });
    generateBobValuesButton = (Button) findViewById(R.id.generateBobValuesButton);
    generateBobValuesButton.setOnClickListener(new View.OnClickListener() {
      public void onClick(View v) {
        bob.generateValues();
        bobPrivateValueEditText.setText(bob.getPrivateValue());
        bobPublicValueEditText.setText(bob.getPublicValue());
      }
    });
    generateAliceSecretButton = (Button) findViewById(R.id.generateAliceSecretButton);
    generateAliceSecretButton.setOnClickListener(new View.OnClickListener() {
      public void onClick(View v) {
        aliceSecretValueEditText.setText(alice.getSharedSecret());
      }
    });
    generateBobSecretButton = (Button) findViewById(R.id.generateBobSecretButton);
    generateBobSecretButton.setOnClickListener(new View.OnClickListener() {
      public void onClick(View v) {
        bobSecretValueEditText.setText(bob.getSharedSecret());
      }
    });
  }

  DiffieHellman initDH(RadioButton button, TextView welcomeTextView) {
     String s = button.getText().toString();
     int keysize = Integer.parseInt(s);
     DiffieHellman dh = new DiffieHellman(alice, bob, keysize);
     updateWelcomeText(dh, welcomeTextView);
     return dh;
  }

  void updateWelcomeText(DiffieHellman dh, TextView welcomeTextView) {
    welcomeTextView.setText("Diffie-Hellman " + dh.getKeySize() + " bit keysize");
     welcomeTextView.append(" (" + dh.getProvider() + ")");
    if (dh.getKeySize() > 4 && dh.getKeySize() < 2048)
      welcomeTextView.append("\nParameters p, q are refreshed");
    else welcomeTextView.append("\nParameters p, q are fixed");
      // when keysize == 2048 the fixed parameters are from RFC 5114
  }
}