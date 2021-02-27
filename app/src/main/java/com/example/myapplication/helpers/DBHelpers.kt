package com.example.myapplication.helpers

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast
import com.example.myapplication.models.ProductData
import com.example.myapplication.models.ShowProduct

class DBHelpers(var context: Context): SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        const val DATABASE_NAME = "CartDatabase"
        const val DATABASE_VERSION = 6
        private const val TABLE_NAME = "my_cart"
        private const val COLUMN_ID = "id"
        private const val COLUMN_PRODUCT = "product_name"
        private const val COLUMN_QUANTITY = "quantity"
        private const val COLUMN_PRICE = "price"
        private const val COLUMN_IMAGE = "image_path"
        private const val COLUMN_MRP  = "mrp"
        const val SQL_CREATE_TABLE =
            "create table $TABLE_NAME($COLUMN_ID char(100) primary key, $COLUMN_PRODUCT char(50), $COLUMN_IMAGE char(100), $COLUMN_QUANTITY integer, $COLUMN_PRICE integer, $COLUMN_MRP integer)"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(SQL_CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
//        if (newVersion > oldVersion) {
//            db?.execSQL("ALTER TABLE $TABLE_NAME REPLACE $COLUMN_MRP integer");
//        }

        val dropTable = "DROP TABLE $TABLE_NAME"
        db?.execSQL(dropTable)
        onCreate(db)
    }

    fun addToCart(product: ProductData) {
        var db = writableDatabase
        var contentValue = ContentValues().apply {
            put(COLUMN_ID, product._id)
            put(COLUMN_PRODUCT, product.productName)
            put(COLUMN_IMAGE, product.image)
            put(COLUMN_PRICE, product.price)
            put(COLUMN_QUANTITY, product.quantity)
            put(COLUMN_MRP, product.mrp)
        }

        var result = db.insert(TABLE_NAME, null, contentValue)

        if (result == -1L) {
            Toast.makeText(context, "Insert Failed", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "Insert Successfully", Toast.LENGTH_SHORT).show()
        }
    }

    fun deleteItem(productName: String){
        var db = writableDatabase
        var whereClause = "$COLUMN_PRODUCT = ?"
        var whereArgs = arrayOf(productName)
        db.delete(TABLE_NAME, whereClause, whereArgs)
    }

    fun getAllProduct(): ArrayList<ShowProduct> {
        var db = readableDatabase
        var productList: ArrayList<ShowProduct> = ArrayList()

        var columns = arrayOf(COLUMN_ID, COLUMN_PRODUCT, COLUMN_QUANTITY, COLUMN_PRICE, COLUMN_IMAGE, COLUMN_MRP)


        var cursor = db.query(TABLE_NAME, columns, null, null, null, null, null)
        if (cursor != null && cursor.moveToFirst()) {
            do {
                var productId = cursor.getString(cursor.getColumnIndex(COLUMN_ID))
                var product = cursor.getString(cursor.getColumnIndex(COLUMN_PRODUCT))
                var price = cursor.getInt(cursor.getColumnIndex(COLUMN_PRICE))
                var quantity = cursor.getInt(cursor.getColumnIndex(COLUMN_QUANTITY))
                var image = cursor.getString(cursor.getColumnIndex(COLUMN_IMAGE))
                var mrp = cursor.getInt(cursor.getColumnIndex(COLUMN_MRP))
                var products = ShowProduct(productId, product, price, quantity, image, mrp)
                productList.add(products)
            } while (cursor.moveToNext())
        }
        return productList
    }

    fun getQuantity(productName: String): Int{
        var quantity = 0
        var db = readableDatabase
        var columns = arrayOf(COLUMN_PRODUCT, COLUMN_QUANTITY)
        var selection = "$COLUMN_PRODUCT = ?"
        var selectionArgs = arrayOf(productName)


        var cursor = db.query(TABLE_NAME, columns, selection, selectionArgs, null, null, null, null)
        if(cursor.moveToFirst()){
            quantity = cursor.getInt(cursor.getColumnIndex(COLUMN_QUANTITY))
            cursor.close()
            return quantity
        }

        return quantity
    }

    fun getNumberOfProducts(): Int
    {
        var db = readableDatabase
        val temp_column= arrayOf("sum($COLUMN_QUANTITY)")
        var cursor = db.query(TABLE_NAME, temp_column, null, null, null, null, null)
        if (cursor != null &&cursor.moveToFirst())
            return cursor.getInt(cursor.getColumnIndex("sum($COLUMN_QUANTITY)"))
        return 0
    }

//    fun getTotalPrice(): Int
//    {
//        var db = readableDatabase
//        val temp_column= arrayOf("sum($COLUMN_PRICE)")
//        var cursor = db.query(TABLE_NAME, temp_column, null, null, null, null, null)
//        if (cursor != null &&cursor.moveToFirst())
//            return cursor.getInt(cursor.getColumnIndex("sum($COLUMN_PRICE)"))
//        return 0
//    }


    fun updateQuantity(name: String, quantity: Int) {
        var db = writableDatabase
        var cv = ContentValues()
        cv.put(COLUMN_QUANTITY, quantity)

        db.update(TABLE_NAME, cv, "$COLUMN_PRODUCT=?", arrayOf(name))
    }

    fun deleteAll(){
        var db = writableDatabase
        db.delete(TABLE_NAME, null, null)
    }



    fun exists(searchItem: String): Boolean {
        var db = readableDatabase
        var columns = arrayOf(COLUMN_PRODUCT)
        var selection = "$COLUMN_PRODUCT =?"
        var selectionArgs = arrayOf(searchItem)
        var limit = "1";

        var cursor =
            db.query(TABLE_NAME, columns, selection, selectionArgs, null, null, null, limit);
        var exists = (cursor.count > 0);
        cursor.close();
        return exists;
    }
}
