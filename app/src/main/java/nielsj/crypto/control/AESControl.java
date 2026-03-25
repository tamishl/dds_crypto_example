package nielsj.crypto.control;

import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import nielsj.crypto.R;
import nielsj.crypto.model.*;

// class AESControl is the user interface to AES encryption

public class AESControl extends AppCompatActivity {
  TextView welcomeTextView, decryptedTextView;
  RadioGroup paddingRadioGroup, modeRadioGroup;
  RadioButton ecbRadioButton, cbcRadioButton,
              noPaddingRadioButton, paddingRadioButton;
  Button encryptButton, decryptButton;
  EditText keyEditText, ivEditText, plaintextEditText, encryptedEditText;

  // The AES object does the cryptographic work
  AES aes;

  // Methods

  // The encrypt/decrypt buttons trigger the crypto object
  // to do encryption and decryption

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.aes);
    welcomeTextView = (TextView) findViewById(R.id.welcomeTextView);
    ecbRadioButton = (RadioButton) findViewById(R.id.ecbRadioButton);
    cbcRadioButton = (RadioButton) findViewById(R.id.cbcRadioButton);
    noPaddingRadioButton = (RadioButton) findViewById(R.id.noPaddingRadioButton);
    paddingRadioButton = (RadioButton) findViewById(R.id.paddingRadioButton);
    ivEditText = (EditText) findViewById(R.id.ivEditText);
    aes = initAES(welcomeTextView, ivEditText);
    modeRadioGroup = (RadioGroup) findViewById(R.id.modeRadioGroup);
    paddingRadioGroup = (RadioGroup) findViewById(R.id.paddingRadioGroup);
    modeRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(RadioGroup g, int checkedId) {
        // RadioButton checkedRadioButton = (RadioButton) findViewById(checkedId);
        aes = initAES(welcomeTextView, ivEditText);
      }
    });
    paddingRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(RadioGroup g, int checkedId) {
        // RadioButton checkedRadioButton = (RadioButton) findViewById(checkedId);
        aes = initAES(welcomeTextView, ivEditText);
      }
    });
    plaintextEditText = (EditText) findViewById(R.id.plaintextEditText);
    keyEditText = (EditText) findViewById(R.id.keyEditText);
    keyEditText.setText(aes.getKey());
    encryptButton = (Button) findViewById(R.id.encryptButton);
    encryptedEditText = (EditText) findViewById(R.id.encryptedEditText);
    encryptButton.setOnClickListener(new View.OnClickListener() {
      public void onClick(View v) {
        String pt = plaintextEditText.getText().toString();
        String key = keyEditText.getText().toString();
        if (aes.getOperation().equals("CBC")) {
          String iv = ivEditText.getText().toString();
          aes.setIV(iv);
        }
        String ct = aes.encrypt(pt, key);
        encryptedEditText.setText(ct);
      }
    });

    decryptButton = (Button) findViewById(R.id.decryptButton);
    decryptedTextView = (TextView) findViewById(R.id.decryptedTextView);
    decryptButton.setOnClickListener(new View.OnClickListener() {
      public void onClick(View v) {
        String ct = encryptedEditText.getText().toString();
        String key = keyEditText.getText().toString();
        if (aes.getOperation().equals("CBC")) {
          String iv = ivEditText.getText().toString();
          aes.setIV(iv);
        }
        String pt = aes.decrypt(ct, key);
        decryptedTextView.setText(pt);
      }
    });
  }
  AES initAES(TextView welcomeTextView, EditText ivEditText) {
    AES aes;
    String operation = "";
    if (ecbRadioButton.isChecked()) {
      operation = "ECB";
      ivEditText.setText("(no iv)");
    }
    if (cbcRadioButton.isChecked()) {
      operation = "CBC";
      ivEditText.setText(AES.getIV());
    }
    String padding = "";
    if (noPaddingRadioButton.isChecked())
      padding = "NoPadding";
    if (paddingRadioButton.isChecked())
      padding = "PKCS5Padding";
    welcomeTextView.setText("AES with " + operation + " and " + padding);
    aes = new AES(operation, padding);
    welcomeTextView.append("\n(security provider: " + aes.getProvider() + ")");
    return aes;
  }
}