package com.example.gestiondeproduits;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private EditText searchBar;
    private MyDbHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHandler = new MyDbHandler(this);
        listView = findViewById(R.id.productList);
        searchBar = findViewById(R.id.searchBar);

        loadProductsFromDatabase("");

        searchBar.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER ) {
                    String searchQuery = searchBar.getText().toString();
                    loadProductsFromDatabase(searchQuery);
                    return true;
                }
                return false;
            }
        });

        listView.setOnItemClickListener((parent, view, position, id) -> {
            TextView quantityView = view.findViewById(R.id.productQuantity);
            TextView descriptionView = view.findViewById(R.id.productDescription);
            TextView priceView = view.findViewById(R.id.productPrice);

            if (quantityView.getVisibility() == View.GONE) {
                quantityView.setVisibility(View.VISIBLE);
                descriptionView.setVisibility(View.VISIBLE);
                priceView.setVisibility(View.VISIBLE);
            } else {
                quantityView.setVisibility(View.GONE);
                descriptionView.setVisibility(View.GONE);
                priceView.setVisibility(View.GONE);
            }
        });

        Button buttonAdd = findViewById(R.id.addProductButton);
        buttonAdd.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddActivity.class);
            startActivity(intent);
        });

        Button buttondelete = findViewById(R.id.deleteProductButton);
        buttondelete.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, deleteproduct.class);
            startActivity(intent);
        });

        Button buttonupdate = findViewById(R.id.editProductButton);
        buttonupdate.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, updateproduct.class);
            startActivity(intent);
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
        // Recharger les produits à chaque fois que l'activité revient au premier plan
        loadProductsFromDatabase("");
    }
    private void loadProductsFromDatabase(String searchQuery) {
        Cursor cursor;
        if (TextUtils.isEmpty(searchQuery)) {
            cursor = dbHandler.getAllProducts();
        } else {
            cursor = dbHandler.searchProducts(searchQuery);
        }

        SimpleCursorAdapter adapter = new SimpleCursorAdapter(
                this,
                R.layout.list_item_product,
                cursor,
                new String[]{
                        MyDbHandler.COLUMN_PRODUCTNAME,
                        MyDbHandler.COLUMN_QUANTITY,
                        MyDbHandler.COLUMN_DESCRIPTION,
                        MyDbHandler.COLUMN_PRICE
                },
                new int[]{
                        R.id.productName,
                        R.id.productQuantity,
                        R.id.productDescription,
                        R.id.productPrice
                },
                0
        );
        adapter.setViewBinder(new SimpleCursorAdapter.ViewBinder() {
            @Override
            public boolean setViewValue(View view, Cursor cursor, int columnIndex) {
                int priceIndex = cursor.getColumnIndex(MyDbHandler.COLUMN_PRICE);
                int descIndex = cursor.getColumnIndex(MyDbHandler.COLUMN_DESCRIPTION);
                int quantityIndex = cursor.getColumnIndex(MyDbHandler.COLUMN_QUANTITY);

                if (view.getId() == R.id.productQuantity && columnIndex == quantityIndex) {
                    String quantity = "Quantity: " + cursor.getString(quantityIndex) + " pc";
                    ((TextView) view).setText(quantity);
                    return true;
                }

                if (view.getId() == R.id.productPrice && columnIndex == priceIndex) {
                    String price = "Price: " + cursor.getString(priceIndex) + " dt";
                    ((TextView) view).setText(price);
                    return true;
                }

                if (view.getId() == R.id.productDescription && columnIndex == descIndex) {
                    String description = "Description: " + cursor.getString(descIndex);
                    ((TextView) view).setText(description);
                    return true;
                }

                return false;
            }
        });

        listView.setAdapter(adapter);
    }

}
