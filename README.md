# FoodOrderingAppFireBase

Sample Food ordering App using real time firebase database. 

In this project i have used :

  1) Firebase realtime database : https://firebase.google.com/docs/database/android/read-and-write
  2) Deisgn Pattern (MVC) 
  3) Gps Tracker (Location)
  4) Date Picker 
  5) FirebaseRecyclerAdapter (Recycler View)
  6) DrawerLayout
  7) NavigationView
  8) Materialedittext
  9) Shared Preferences 
  
The project contains :

  1) Sign Up 
  2) Login  
  3) List of orders 
  4) Creating an Order 
  
  1) Sign Up :
     For doing signup a user has to enter his Name, Phone number and password. After authentication the user will be              redirected to Orders Screen where he will see all the list of orders. The user will be authenticated using firebase          realtime database 'User'. If the phone number already exists, then the user cannot create another account. 
     
  2) Login :
     After doing successfull signup and logging out, he can relogin using login form. Here the user has to enter Phone            number and password. If user ticks checkbox, the details are stored in shared preferences. 
     
  3) List of orders : 
     Here the orders are shown using a reclycler view
     
  4) Creating an Order :
     An order can be created with some details. All these details will be stored in firebase. 
     
     
