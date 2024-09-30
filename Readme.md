# QuickShop - E-Commerce Android App

QuickShop is a fully-featured **E-Commerce Android Application** built using Java, utilizing SQLite as the local database. This app allows users to browse products, manage a shopping cart, place orders using Cash on Delivery (COD), view order history, and manage their profile. The app also includes a **Bottom Navigation Bar** for easy navigation and a **Splash Screen** for a smooth user experience.

## Features

### User Authentication
- **Login & Registration**: Users can create an account and log in to access their profile, products, and orders.
- **Session Management**: Managed via `SharedPreferences` to keep users logged in across sessions.

### Product Browsing and Cart Management
- **Product Listing**: Users can browse products in a scrollable layout with detailed descriptions.
- **Add to Cart**: Users can add products to their cart and manage quantities before checkout.
- **Order Placement**: Supports **Cash on Delivery (COD)**. Users can view the total amount before confirming their order.

### Order Management
- **Order History**: Users can view a list of all their past orders along with details for each order.

### Profile Management
- **Profile Page**: Users can view and update their profile information, including name, email, phone number, and address. The profile page also includes:
  - **Email Support**: For customer assistance.
  - **Call Support**: Direct contact for help.

### UI and Navigation
- **Bottom Navigation Bar**: Quick and easy access to all major sections of the app (Home, Cart, Orders, Profile).
- **Splash Screen**: A clean splash screen for improved user experience during app launch.

### Other Key Features
- **Adapters**: Custom adapters for efficient and smooth display of product lists and order details.
- **SharedPreferences**: Used for session management and storing user preferences.
- **Email & Call Integration**: Users can email or call customer support directly from the app.

## Tech Stack

- **Language**: Java
- **Database**: SQLite (built-in Android database)
- **UI Components**: RecyclerView, Adapters, Bottom Navigation Bar, Splash Screen
- **Session Management**: SharedPreferences
- **Others**: Email Intent, Call Intent

