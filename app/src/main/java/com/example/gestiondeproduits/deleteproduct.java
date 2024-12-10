package com.example.gestiondeproduits;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class deleteproduct extends AppCompatActivity {

    private EditText productIdEditText;
    private Button deleteButton;
    private Button exitButton;
    private MyDbHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.deleteproduct);

        productIdEditText = findViewById(R.id.idprice);
        deleteButton = findViewById(R.id.deleteProductButton);
        exitButton = findViewById(R.id.exit);

        dbHandler = new MyDbHandler(this);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String productIdStr = productIdEditText.getText().toString();
                if (productIdStr.isEmpty()) {
                    Toast.makeText(deleteproduct.this, "Please enter a product ID.", Toast.LENGTH_SHORT).show();
                } else {
                        int productId = Integer.parseInt(productIdStr);
                        dbHandler.deleteProduct(productId, deleteproduct.this);
                }
            }
        });

        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
