//package com.example.ecommerceapp;
//
//import android.annotation.SuppressLint;
//import android.content.ContentValues;
//import android.content.Context;
//import android.database.Cursor;
//import android.database.sqlite.SQLiteDatabase;
//import android.database.sqlite.SQLiteOpenHelper;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class DatabaseHelper extends SQLiteOpenHelper {
//    private static final String DATABASE_NAME = "ecommerce.db";
//    private static final int DATABASE_VERSION = 1;
//
//    public DatabaseHelper(Context context) {
//        super(context, DATABASE_NAME, null, DATABASE_VERSION);
//    }
//
//    @Override
//    public void onCreate(SQLiteDatabase db) {
//        db.execSQL("CREATE TABLE users (id INTEGER PRIMARY KEY AUTOINCREMENT, username TEXT, email TEXT, password TEXT)");
//        db.execSQL("CREATE TABLE products (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, description TEXT, price REAL, image_url TEXT)");
//        db.execSQL("CREATE TABLE orders (id INTEGER PRIMARY KEY AUTOINCREMENT, user_id INTEGER, total_price REAL, date TEXT)");
//        db.execSQL("CREATE TABLE order_items (id INTEGER PRIMARY KEY AUTOINCREMENT, order_id INTEGER, product_id INTEGER, quantity INTEGER)");
//        db.execSQL("CREATE TABLE cart (id INTEGER PRIMARY KEY AUTOINCREMENT, user_id INTEGER, product_id INTEGER, quantity INTEGER)");
//
//        insertSampleProducts(db);
//    }
//
//    private void insertSampleProducts(SQLiteDatabase db) {
//        String[] products = {
//                "INSERT INTO products (name, description, price, image_url) VALUES ('Smartphone', 'High-end smartphone with advanced features', 999.99, 'smartphone.jpg')",
//                "INSERT INTO products (name, description, price, image_url) VALUES ('Laptop', 'Powerful laptop for work and gaming', 1299.99, 'laptop.jpg')",
//                "INSERT INTO products (name, description, price, image_url) VALUES ('Headphones', 'Noise-cancelling wireless headphones', 249.99, 'headphones.jpg')",
//                "INSERT INTO products (name, description, price, image_url) VALUES ('Smartwatch', 'Fitness tracker with heart rate monitor', 199.99, 'smartwatch.jpg')",
//                "INSERT INTO products (name, description, price, image_url) VALUES ('Tablet', '10-inch tablet with high-resolution display', 399.99, 'tablet.jpg')"
//        };
//
//        for (String product : products) {
//            db.execSQL(product);
//        }
//    }
//
//    @Override
//    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        // Handle database upgrades
//    }
//
//    // User methods
//    public long addUser(String username, String email, String password) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues values = new ContentValues();
//        values.put("username", username);
//        values.put("email", email);
//        values.put("password", password);
//        return db.insert("users", null, values);
//    }
//
//    public User getUser(String username, String password) {
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor cursor = db.query("users", new String[]{"id", "username", "email"},
//                "username = ? AND password = ?", new String[]{username, password},
//                null, null, null);
//
//        User user = null;
//        if (cursor.moveToFirst()) {
//            user = new User(
//                    cursor.getInt(0),
//                    cursor.getString(1),
//                    cursor.getString(2)
//            );
//        }
//        cursor.close();
//        return user;
//    }
//
//    // Product methods
//    public List<Product> getAllProducts() {
//        List<Product> products = new ArrayList<>();
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor cursor = db.query("products", null, null, null, null, null, null);
//
//        if (cursor.moveToFirst()) {
//            do {
//                Product product = new Product(
//                        cursor.getInt(0),
//                        cursor.getString(1),
//                        cursor.getString(2),
//                        cursor.getDouble(3),
//                        cursor.getString(4)
//                );
//                products.add(product);
//            } while (cursor.moveToNext());
//        }
//        cursor.close();
//        return products;
//    }
//
//    // Cart methods
//    public long addToCart(int userId, int productId, int quantity) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues values = new ContentValues();
//        values.put("user_id", userId);
//        values.put("product_id", productId);
//        values.put("quantity", quantity);
//        return db.insert("cart", null, values);
//    }
//
//    public List<CartItem> getCartItems(int userId) {
//        List<CartItem> cartItems = new ArrayList<>();
//        SQLiteDatabase db = this.getReadableDatabase();
//        String query = "SELECT c.id, p.id, p.name, p.price, c.quantity FROM cart c " +
//                "JOIN products p ON c.product_id = p.id " +
//                "WHERE c.user_id = ?";
//        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(userId)});
//
//        if (cursor.moveToFirst()) {
//            do {
//                CartItem item = new CartItem(
//                        cursor.getInt(0),
//                        cursor.getInt(1),
//                        cursor.getString(2),
//                        cursor.getDouble(3),
//                        cursor.getInt(4)
//                );
//                cartItems.add(item);
//            } while (cursor.moveToNext());
//        }
//        cursor.close();
//        return cartItems;
//    }
//
//    // Order methods
//    public long createOrder(int userId, double totalPrice, String date) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues values = new ContentValues();
//        values.put("user_id", userId);
//        values.put("total_price", totalPrice);
//        values.put("date", date);
//        return db.insert("orders", null, values);
//    }
//
//    public void addOrderItem(long orderId, int productId, int quantity) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues values = new ContentValues();
//        values.put("order_id", orderId);
//        values.put("product_id", productId);
//        values.put("quantity", quantity);
//        db.insert("order_items", null, values);
//    }
//
//    public List<Order> getUserOrders(int userId) {
//        List<Order> orders = new ArrayList<>();
//        SQLiteDatabase db = this.getReadableDatabase();
//        String query = "SELECT * FROM orders WHERE user_id = ? ORDER BY date DESC";
//        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(userId)});
//
//        if (cursor.moveToFirst()) {
//            do {
//                Order order = new Order(
//                        cursor.getInt(0),
//                        cursor.getInt(1),
//                        cursor.getDouble(2),
//                        cursor.getString(3)
//                );
//                orders.add(order);
//            } while (cursor.moveToNext());
//        }
//        cursor.close();
//        return orders;
//    }
//    public Product getProductById(int productId) {
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor cursor = db.query("products", null, "id = ?", new String[]{String.valueOf(productId)},
//                null, null, null);
//
//        Product product = null;
//        if (cursor.moveToFirst()) {
//            product = new Product(
//                    cursor.getInt(0),
//                    cursor.getString(1),
//                    cursor.getString(2),
//                    cursor.getDouble(3),
//                    cursor.getString(4)
//            );
//        }
//        cursor.close();
//        return product;
//    }
//    public User getUserById(int userId) {
//        SQLiteDatabase db = this.getReadableDatabase();
//        String query = "SELECT * FROM " + TABLE_USERS + " WHERE " + COLUMN_USER_ID + " = ?";
//        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(userId)});
//
//        User user = null;
//        if (cursor.moveToFirst()) {
//            @SuppressLint("Range") String username = cursor.getString(cursor.getColumnIndex(COLUMN_USERNAME));
//            @SuppressLint("Range") String email = cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL));
//            @SuppressLint("Range") String password = cursor.getString(cursor.getColumnIndex(COLUMN_PASSWORD));
//
//            user = new User(userId, username, email, password);
//        }
//        cursor.close();
//        return user;
//    }
//
//
//}

package com.example.ecommerceapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "ecommerce.db";
    private static final int DATABASE_VERSION = 6;  // Incremented for the new columns

    private static final String TABLE_USERS = "users";
    private static final String COLUMN_USER_ID = "id";
    private static final String COLUMN_USERNAME = "username";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_PASSWORD = "password";
    private static final String COLUMN_ADDRESS = "address"; // New
    private static final String COLUMN_PHONE = "phone"; // New

    private static final String TABLE_PRODUCTS = "products";
    private static final String COLUMN_PRODUCT_ID = "id";
    private static final String COLUMN_PRODUCT_NAME = "name";
    private static final String COLUMN_PRODUCT_DESCRIPTION = "description";
    private static final String COLUMN_PRODUCT_PRICE = "price";
    private static final String COLUMN_PRODUCT_IMAGE_URL = "image_url";

    private static final String TABLE_CART = "cart";
    private static final String COLUMN_CART_ID = "id";
    private static final String COLUMN_CART_USER_ID = "user_id";
    private static final String COLUMN_CART_PRODUCT_ID = "product_id";
    private static final String COLUMN_CART_QUANTITY = "quantity";

    private static final String TABLE_ORDERS = "orders";
    private static final String COLUMN_ORDER_ID = "id";
    private static final String COLUMN_ORDER_USER_ID = "user_id";
    private static final String COLUMN_ORDER_TOTAL_PRICE = "total_price";
    private static final String COLUMN_ORDER_DATE = "date";

    private static final String TABLE_ORDER_ITEMS = "order_items";
    private static final String COLUMN_ORDER_ITEM_ID = "id";
    private static final String COLUMN_ORDER_ITEM_ORDER_ID = "order_id";
    private static final String COLUMN_ORDER_ITEM_PRODUCT_ID = "product_id";
    private static final String COLUMN_ORDER_ITEM_QUANTITY = "quantity";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_USERS + " (" +
                COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_USERNAME + " TEXT, " +
                COLUMN_EMAIL + " TEXT, " +
                COLUMN_PASSWORD + " TEXT, " +
                COLUMN_ADDRESS + " TEXT, " +  // New column for address
                COLUMN_PHONE + " TEXT)");

        db.execSQL("CREATE TABLE " + TABLE_PRODUCTS + " (" +
                COLUMN_PRODUCT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_PRODUCT_NAME + " TEXT, " +
                COLUMN_PRODUCT_DESCRIPTION + " TEXT, " +
                COLUMN_PRODUCT_PRICE + " REAL, " +
                COLUMN_PRODUCT_IMAGE_URL + " TEXT)");

        db.execSQL("CREATE TABLE " + TABLE_ORDERS + " (" +
                COLUMN_ORDER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_ORDER_USER_ID + " INTEGER, " +
                COLUMN_ORDER_TOTAL_PRICE + " REAL, " +
                COLUMN_ORDER_DATE + " TEXT)");

        db.execSQL("CREATE TABLE " + TABLE_ORDER_ITEMS + " (" +
                COLUMN_ORDER_ITEM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_ORDER_ITEM_ORDER_ID + " INTEGER, " +
                COLUMN_ORDER_ITEM_PRODUCT_ID + " INTEGER, " +
                COLUMN_ORDER_ITEM_QUANTITY + " INTEGER)");

        db.execSQL("CREATE TABLE " + TABLE_CART + " (" +
                COLUMN_CART_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_CART_USER_ID + " INTEGER, " +
                COLUMN_CART_PRODUCT_ID + " INTEGER, " +
                COLUMN_CART_QUANTITY + " INTEGER)");

        insertSampleProducts(db);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 2) {
            // Example for version 2 upgrades
            db.execSQL("ALTER TABLE " + TABLE_USERS + " ADD COLUMN " + COLUMN_ADDRESS + " TEXT");
            db.execSQL("ALTER TABLE " + TABLE_USERS + " ADD COLUMN " + COLUMN_PHONE + " TEXT");

        }
        if (oldVersion <= 6) {
            // Example for version 6 upgrades
            // Handle any new columns or tables introduced in version 6
            // e.g., db.execSQL("CREATE TABLE ...");
            insertSampleProducts(db);
        }
    }

    private void insertSampleProducts(SQLiteDatabase db) {
        String[] products = {
                "INSERT INTO " + TABLE_PRODUCTS + " (" +
                        COLUMN_PRODUCT_NAME + ", " +
                        COLUMN_PRODUCT_DESCRIPTION + ", " +
                        COLUMN_PRODUCT_PRICE + ", " +
                        COLUMN_PRODUCT_IMAGE_URL + ") VALUES ('Smartphone', 'High-end smartphone with advanced features', 999.99, 'https://images.pexels.com/photos/607812/pexels-photo-607812.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=750&w=1260')",
                "INSERT INTO " + TABLE_PRODUCTS + " (" +
                        COLUMN_PRODUCT_NAME + ", " +
                        COLUMN_PRODUCT_DESCRIPTION + ", " +
                        COLUMN_PRODUCT_PRICE + ", " +
                        COLUMN_PRODUCT_IMAGE_URL + ") VALUES ('Laptop', 'Powerful laptop for work and gaming', 1299.99, 'https://images.pexels.com/photos/18105/pexels-photo.jpg?auto=compress&cs=tinysrgb&dpr=2&h=750&w=1260')",
                "INSERT INTO " + TABLE_PRODUCTS + " (" +
                        COLUMN_PRODUCT_NAME + ", " +
                        COLUMN_PRODUCT_DESCRIPTION + ", " +
                        COLUMN_PRODUCT_PRICE + ", " +
                        COLUMN_PRODUCT_IMAGE_URL + ") VALUES ('Headphones', 'Noise-cancelling wireless headphones', 249.99, 'https://images.pexels.com/photos/373945/pexels-photo-373945.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=750&w=1260')",
                "INSERT INTO " + TABLE_PRODUCTS + " (" +
                        COLUMN_PRODUCT_NAME + ", " +
                        COLUMN_PRODUCT_DESCRIPTION + ", " +
                        COLUMN_PRODUCT_PRICE + ", " +
                        COLUMN_PRODUCT_IMAGE_URL + ") VALUES ('Smartwatch', 'Fitness tracker with heart rate monitor', 199.99, 'https://images.pexels.com/photos/267394/pexels-photo-267394.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=750&w=1260')",
                "INSERT INTO " + TABLE_PRODUCTS + " (" +
                        COLUMN_PRODUCT_NAME + ", " +
                        COLUMN_PRODUCT_DESCRIPTION + ", " +
                        COLUMN_PRODUCT_PRICE + ", " +
                        COLUMN_PRODUCT_IMAGE_URL + ") VALUES ('Tablet', '10-inch tablet with high-resolution display', 399.99, 'https://images.pexels.com/photos/434346/pexels-photo-434346.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=750&w=1260')"
        };

        for (String product : products) {
            db.execSQL(product);
        }
    }

    // Method to add a new user with address and phone
    public long addUser(String username, String email, String password, String address, String phone) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USERNAME, username);
        values.put(COLUMN_EMAIL, email);
        values.put(COLUMN_PASSWORD, password);
        values.put(COLUMN_ADDRESS, address);  // New
        values.put(COLUMN_PHONE, phone);      // New
        return db.insert(TABLE_USERS, null, values);
    }

    // Method to update user information including address and phone
    public int updateUser(int userId, String username, String email, String address, String phone) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USERNAME, username);
        values.put(COLUMN_EMAIL, email);
        values.put(COLUMN_ADDRESS, address);  // Update address
        values.put(COLUMN_PHONE, phone);      // Update phone
        return db.update(TABLE_USERS, values, COLUMN_USER_ID + " = ?", new String[]{String.valueOf(userId)});
    }

    // Retrieve user by username and password, include address and phone in return
    public User getUser(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USERS,
                new String[]{COLUMN_USER_ID, COLUMN_USERNAME, COLUMN_EMAIL, COLUMN_ADDRESS, COLUMN_PHONE},
                COLUMN_USERNAME + " = ? AND " + COLUMN_PASSWORD + " = ?",
                new String[]{username, password}, null, null, null);

        User user = null;
        if (cursor.moveToFirst()) {
            user = new User(
                    cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_USER_ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USERNAME)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EMAIL)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ADDRESS)),  // Get address
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PHONE))     // Get phone
            );
        }
        cursor.close();
        return user;
    }

    public User getUserById(int userId) {
        SQLiteDatabase db = this.getReadableDatabase();

        String[] columns = {
                COLUMN_USER_ID,
                COLUMN_USERNAME,
                COLUMN_EMAIL,
                COLUMN_ADDRESS,
                COLUMN_PHONE
        };


        String selection = COLUMN_USER_ID + " = ?";
        String[] selectionArgs = { String.valueOf(userId) };

        Cursor cursor = db.query(TABLE_USERS, columns, selection, selectionArgs, null, null, null);

        User user = null;
        if (cursor != null && cursor.moveToFirst()) {
            user = new User(
                    cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_USER_ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USERNAME)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EMAIL)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ADDRESS)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PHONE))
            );
            cursor.close();
        }

        return user;
    }

    public List<CartItem> getCartItems(int userId) {
        List<CartItem> cartItems = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT c.id, p.id, p.name, p.price, c.quantity FROM cart c " +
                "JOIN products p ON c.product_id = p.id " +
                "WHERE c.user_id = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(userId)});

        if (cursor.moveToFirst()) {
            do {
                CartItem item = new CartItem(
                        cursor.getInt(0),
                        cursor.getInt(1),
                        cursor.getString(2),
                        cursor.getDouble(3),
                        cursor.getInt(4)
                );
                cartItems.add(item);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return cartItems;
    }
    public long createOrder(int userId, double totalPrice, String date) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("user_id", userId);
        values.put("total_price", totalPrice);
        values.put("date", date);
        return db.insert("orders", null, values);
    }
    public long addToCart(int userId, int productId, int quantity) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_CART_USER_ID, userId);
        values.put(COLUMN_CART_PRODUCT_ID, productId);
        values.put(COLUMN_CART_QUANTITY, quantity);
        return db.insert(TABLE_CART, null, values);
    }

    public void addOrderItem(long orderId, int productId, int quantity) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("order_id", orderId);
        values.put("product_id", productId);
        values.put("quantity", quantity);
        db.insert("order_items", null, values);
    }
    public List<Order> getUserOrders(int userId) {
        List<Order> orders = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM orders WHERE user_id = ? ORDER BY date DESC";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(userId)});

        if (cursor.moveToFirst()) {
            do {
                Order order = new Order(
                        cursor.getInt(0),
                        cursor.getInt(1),
                        cursor.getDouble(2),
                        cursor.getString(3)
                );
                orders.add(order);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return orders;
    }
    public void clearCart(int userId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("cart", "user_id = ?", new String[]{String.valueOf(userId)});
    }
    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_PRODUCTS, null, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                Product product = new Product(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getDouble(3),
                        cursor.getString(4)
                );
                products.add(product);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return products;
    }
    public Product getProductById(int productId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_PRODUCTS, null, COLUMN_PRODUCT_ID + " = ?", new String[]{String.valueOf(productId)},
                null, null, null);

        Product product = null;
        if (cursor.moveToFirst()) {
            product = new Product(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getDouble(3),
                    cursor.getString(4)
            );
        }
        cursor.close();
        return product;
    }

}
