package nielsj.crypto.view;

import android.os.Bundle;
import android.widget.EditText;
import android.text.TextWatcher;
import android.text.Editable;
import androidx.appcompat.app.AppCompatActivity;
import nielsj.crypto.R;


public class FormattingControl extends AppCompatActivity {

  EditText textEditText, bytesEditText, bitsEditText, hexesEditText;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.formatting);
    textEditText = (EditText) findViewById(R.id.textEditText);
    bytesEditText = (EditText) findViewById(R.id.bytesEditText);
    bitsEditText = (EditText) findViewById(R.id.bitsEditText);
    hexesEditText = (EditText) findViewById(R.id.hexesEditText);
    textEditText.addTextChangedListener(new TextWatcher() {
      @Override
      public void beforeTextChanged(CharSequence s, int start, int count, int after) {
      }

      @Override

      public void onTextChanged(CharSequence s, int start, int before, int count) {
      }

      public void afterTextChanged(Editable s) {
        byte[] byteArray = s.toString().getBytes();

        // byte output
        String bytes = Util.byteArrayToByteString(byteArray);
        String bytesMultiline = Util.stringToMultiLine(bytes, 32);
        bytesEditText.setText(bytesMultiline);

        // bit output
        String bits = Util.byteArrayToBitString(byteArray);
        String bitsMultiline = Util.stringToMultiLine(bits, 32);
        bitsEditText.setText(bitsMultiline);

        // hex output
        String hexes = Hex.byteArrayToHexString(byteArray);
        String hexesMultiLine = Util.stringToMultiLine(hexes, 32);
        hexesEditText.setText(hexesMultiLine);
      }
    } );
  }
}