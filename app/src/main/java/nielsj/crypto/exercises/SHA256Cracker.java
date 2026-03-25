package nielsj.crypto.exercises;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Button;
import android.view.View;
import nielsj.crypto.model.SHA256;
import nielsj.crypto.R;

import androidx.appcompat.app.AppCompatActivity;

public class SHA256Cracker extends AppCompatActivity {

  EditText hashBeginningEditText;
  Button crackButton, clearButton;
  TextView hashInputTextView, hashValueTextView;

  SHA256 crypto = new SHA256();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.sha256cracker);
    hashBeginningEditText = (EditText) findViewById(R.id.hashBeginningEditText);
    crackButton = (Button) findViewById(R.id.crackButton);
    crackButton.setOnClickListener(new View.OnClickListener() {
      public void onClick(View v) {
        crack();
      }
    });
    hashInputTextView = (TextView) findViewById(R.id.hashInputTextView);
    hashValueTextView = (TextView) findViewById(R.id.hashValueTextView);
    clearButton = (Button) findViewById(R.id.clearButton);
    clearButton.setOnClickListener(new View.OnClickListener() {
      public void onClick(View v) {
        hashInputTextView.setText("(hash input)");
        hashValueTextView.setText("(hash value)");
      }
    });
  }

  void crack() {
    String hashBeginning = hashBeginningEditText.getText().toString();
    String hashGuess = "", hashValue = "";
    boolean found = false;
    long i = 0;
    hashGuess = Long.toString(i);
    hashValue = crypto.hash(hashGuess);
    hashInputTextView.setText("hash input:\n" + hashGuess);
    hashValueTextView.setText("hash valuue:\n" + hashValue);
  }
}