package nielsj.crypto.view;

import android.os.Bundle;
import android.widget.EditText;
import android.text.TextWatcher;
import android.text.Editable;
import androidx.appcompat.app.AppCompatActivity;
import nielsj.crypto.R;


public class  FormattingControl extends AppCompatActivity {

  EditText bytesEditText, asciiEditText, bitsEditText, hexesEditText;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.formatting);
    bytesEditText = (EditText) findViewById(R.id.bytesEditText);
    asciiEditText = (EditText) findViewById(R.id.asciiEditText);
    bitsEditText = (EditText) findViewById(R.id.bitsEditText);
    hexesEditText = (EditText) findViewById(R.id.hexesEditText); 
    bytesEditText.addTextChangedListener(new TextWatcher() {
      @Override
      public void beforeTextChanged(CharSequence s, int start, int count, int after) {
      }

      @Override
      public void onTextChanged(CharSequence s, int start, int before, int count) {
      }

      public void afterTextChanged(Editable s) {
        // read the bytes (-128 .. 127)
        String str = s.toString();
        String[] strArray = str.split("[ ]");
        int l = strArray.length;
        byte[] byteArray = new byte[l];
        for (int i = 0; i < l; i++) {
          byteArray[i] = 0;
          try {
            String sByte = strArray[i];
            byteArray[i] = Byte.valueOf(sByte);
          } catch (Exception e) {}
        }

        // ascii output
        asciiEditText.setText(new String(byteArray));
     
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
    bytesEditText.setText("73 66 77"); // IBM 
  }
}