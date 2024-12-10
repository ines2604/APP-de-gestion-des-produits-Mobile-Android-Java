package com.example.gestiondeproduits;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddActivity extends AppCompatActivity {

    private EditText productid;
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
        setContentView(R.layout.addproduct);

        productid=findViewById(R.id.ID);
        productName = findViewById(R.id.idname);
        productQuantity = findViewById(R.id.idQuantity);
        productDescription = findViewById(R.id.iddescription);
        productPrice = findViewById(R.id.idprice);
        buttonQuitter = findViewById(R.id.exit);
        submitProductButton = findViewById(R.id.addProductButton);

        dbHandler = new MyDbHandler(this);

        submitProductButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String productId=productid.getText().toString();
                String name = productName.getText().toString();
                String priceStr = productPrice.getText().toString();
                String quantityStr = productQuantity.getText().toString();
                String description = productDescription.getText().toString();

                if (name.isEmpty() || priceStr.isEmpty() || quantityStr.isEmpty() || description.isEmpty()) {
                    Toast.makeText(AddActivity.this, "Please enter all fields", Toast.LENGTH_SHORT).show();
                } else {
                    float price = Float.parseFloat(priceStr);
                    int quantity = Integer.parseInt(quantityStr);
                    int id=Integer.parseInt(productId);

                    dbHandler.insertProduct(id,name, quantity,description, price,AddActivity.this);}
        }});

        buttonQuitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}