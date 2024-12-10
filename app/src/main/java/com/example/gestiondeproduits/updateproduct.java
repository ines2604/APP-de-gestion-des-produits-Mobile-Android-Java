package com.example.gestiondeproduits;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class updateproduct extends AppCompatActivity {

    private EditText idField;
    private EditText productName;
    private EditText productPrice;
    private EditText productQuantity;
    private EditText productDescription;
    private Button submitProductButton;
    private Button buttonQuitter;
    private MyDbHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.updateproduct);

        idField=findViewById(R.id.idproduct);
        productName = findViewById(R.id.idname2);
        productQuantity = findViewById(R.id.idQuantity);
        productDescription = findViewById(R.id.iddescription);
        productPrice = findViewById(R.id.idprice);
        buttonQuitter = findViewById(R.id.exit);
        submitProductButton = findViewById(R.id.updateProductButton);

        dbHandler = new MyDbHandler(this);

        submitProductButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String idStr = idField.getText().toString();
                String name = productName.getText().toString();
                String priceStr = productPrice.getText().toString();
                String quantityStr = productQuantity.getText().toString();
                String description = productDescription.getText().toString();

                if (idStr.isEmpty() || name.isEmpty() || priceStr.isEmpty() || quantityStr.isEmpty() || description.isEmpty()) {
                    Toast.makeText(updateproduct.this, "Please enter all fields", Toast.LENGTH_SHORT).show();
                } else {
                    int id = Integer.parseInt(idStr);
                    float price = Float.parseFloat(priceStr);
                    int quantity = Integer.parseInt(quantityStr);

                    dbHandler.updateProduct(id, name, description, quantity, price, updateproduct.this);
                }
            }
        });

        buttonQuitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}