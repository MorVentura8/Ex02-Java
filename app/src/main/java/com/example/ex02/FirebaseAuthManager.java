package com.example.ex02;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.auth.AuthResult;
import java.util.HashMap;

public class FirebaseAuthManager {
    private FirebaseAuth auth;
    private FirebaseDatabase database;

    public FirebaseAuthManager() {
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
    }
}
