package com.example.gestiondeproduits;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class MyDbHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "productDB.db";

    public static final String TABLE_PRODUCTS = "products";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_PRODUCTNAME = "productname";
    public static final String COLUMN_QUANTITY = "quantity";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_PRICE = "price";

    public MyDbHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_PRODUCTS_TABLE = "CREATE TABLE " + TABLE_PRODUCTS + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY,"
                + COLUMN_PRODUCTNAME + " TEXT,"
                + COLUMN_QUANTITY + " INTEGER,"
                + COLUMN_DESCRIPTION + " TEXT,"
                + COLUMN_PRICE + " FLOAT"
                + ")";
        db.execSQL(CREATE_PRODUCTS_TABLE);
    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCTS);
        onCreate(db);
    }

    public void insertProduct(Integer id,String productName, int quantity, String description, float price, Context context) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_ID,id);
        values.put(COLUMN_PRODUCTNAME, productName);
        values.put(COLUMN_QUANTITY, quantity);
        values.put(COLUMN_DESCRIPTION, description);
        values.put(COLUMN_PRICE, price);

        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.insert(TABLE_PRODUCTS, null, values);
        if (result == -1) {
            Toast.makeText(context, "Insertion échouée", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Insertion réussie, ID de la ligne insérée : " + result, Toast.LENGTH_SHORT).show();
        }
        db.close();
    }

    public void deleteProduct(int productId, Context context) {
        SQLiteDatabase db = this.getWritableDatabase();
        int rowsAffected = db.delete(TABLE_PRODUCTS, COLUMN_ID + " = ?", new String[]{String.valueOf(productId)});
        if (rowsAffected > 0) {
            Toast.makeText(context, "Produit supprimé avec succès.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Suppression échouée.", Toast.LENGTH_SHORT).show();
        }
        db.close();
    }

    public Cursor searchProducts(String searchQuery) {
        SQLiteDatabase db = this.getReadableDatabase();
        String whereClause = COLUMN_PRODUCTNAME + " LIKE ?";
        String[] whereArgs = new String[]{"%" + searchQuery + "%"};
        return db.query(TABLE_PRODUCTS,
                new String[]{COLUMN_ID + " AS _id", COLUMN_PRODUCTNAME, COLUMN_QUANTITY,COLUMN_DESCRIPTION,COLUMN_PRICE}, // Alias _id
                whereClause, whereArgs, null, null, null);
    }

    public Cursor getAllProducts() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(TABLE_PRODUCTS,
                new String[]{COLUMN_ID + " AS _id", COLUMN_PRODUCTNAME,COLUMN_QUANTITY,COLUMN_DESCRIPTION,COLUMN_PRICE}, // Alias _id
                null, null, null, null, null);
    }

    public void updateProduct(int id, String newName, String newDescription, int newQuantity, float newPrice, Context context) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_PRODUCTNAME, newName);
        values.put(COLUMN_DESCRIPTION, newDescription);
        values.put(COLUMN_QUANTITY, newQuantity);
        values.put(COLUMN_PRICE, newPrice);

        int rowsAffected = db.update(TABLE_PRODUCTS, values, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});

        if (rowsAffected > 0) {
            Toast.makeText(context, "Produit mis à jour avec succès !", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Aucun produit trouvé avec cet ID.", Toast.LENGTH_SHORT).show();
        }
    }
}
