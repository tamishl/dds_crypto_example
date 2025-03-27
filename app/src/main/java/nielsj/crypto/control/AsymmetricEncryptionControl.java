package nielsj.crypto.control;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import nielsj.crypto.R;

// class AsymmetricEncryption is the user interface to asymmetric encryption


public class AsymmetricEncryptionControl extends AppCompatActivity {

    Button rsaButton, dhButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.asymmetricencryption);

        // The RSA button

        rsaButton = (Button) findViewById(R.id.rsaButton);
        rsaButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),
                        RSAControl.class);
                startActivity(i);
            }
        });

        // The Diffie-Hellman button
        dhButton = (Button) findViewById(R.id.dhButton);
        dhButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),
                        DiffieHellmanControl.class);
                startActivity(i);
            }
        });
    }
}
