package com.example.ex02;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.auth.AuthResult;
import java.util.HashMap;

public class FirebaseAuthManager {
    private static String currentUsername;
    private static Users currentUser;

    public static void setCurrentUser(Users user) {
        currentUser = user;
        currentUsername = user.getUserName();
    }

    public static Users getCurrentUser() {
        return currentUser;
    }
}
