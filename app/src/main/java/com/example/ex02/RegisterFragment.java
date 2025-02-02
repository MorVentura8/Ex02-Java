package com.example.ex02;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RegisterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RegisterFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public RegisterFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RegisterFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RegisterFragment newInstance(String param1, String param2) {
        RegisterFragment fragment = new RegisterFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_register, container, false);

        Button registerButton = view.findViewById(R.id.btnRegister);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (validRegister(view)) {
                    registerUser(view);
                    Navigation.findNavController(view).navigate(R.id.action_registerFragment_to_loginFragment);
                    Toast.makeText(requireContext(), "Registered successfully!", Toast.LENGTH_SHORT).show();

                }
                else
                {
                    //TODO: לבדוק אם יש כפילויות או שגיאות בשם משתמש וטלפון ולהוציא הודעה בהתאם לכל מצב
                    Toast.makeText(requireContext(), "Error: one or more fields are incorrect", Toast.LENGTH_SHORT).show();

                }
            }
        });
        return view;
    }

    private void registerUser(View view) {
        EditText userNameText = view.findViewById(R.id.etUsername);
        EditText passwordText = view.findViewById(R.id.etPassword);
        EditText phoneNumberText = view.findViewById(R.id.etPhone);

        String userName = userNameText.getText().toString();
        String password = passwordText.getText().toString();
        String phoneNumber = phoneNumberText.getText().toString();

        FirebaseDatabase database = FirebaseDatabase.getInstance("https://consoleapp-84935-default-rtdb.firebaseio.com");
        DatabaseReference myRef = database.getReference("Users").child(userName);

        Users uset = new Users(userName,password,phoneNumber); // מעבירים מה שרוצים לבנאי שלנו
        myRef.setValue(uset);

    }

    public boolean validRegister(View view) {

        EditText userNameText = view.findViewById(R.id.etUsername);
        EditText passwordText = view.findViewById(R.id.etPassword);
        EditText confirmPasswordText = view.findViewById(R.id.etConfirmPassword);
        EditText phoneNumberText = view.findViewById(R.id.etPhone);

        String userName = userNameText.getText().toString();
        String password = passwordText.getText().toString();
        String confirmPassword = confirmPasswordText.getText().toString();
        String phoneNumber = phoneNumberText.getText().toString();

        if (!userName.isEmpty() && password.equals(confirmPassword) && validPhoneNumber(phoneNumber))
        {
            // להירשם לdatabase


            return true;
        }
        else
        {
            return false;
        }

    }

    public boolean validPhoneNumber(String phoneNumber) {

        if (phoneNumber == null || phoneNumber.isEmpty()) {
            return false;
        }

        return phoneNumber.matches("\\d{10}");

    }


}