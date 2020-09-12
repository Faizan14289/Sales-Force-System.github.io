package com.example.customer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.customer.ModelClasses.customer;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {

    private EditText txt_cnic,txt_password;
    private ProgressDialog d;
    private Button btn_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }

        setContentView(R.layout.activity_login);

        txt_cnic = findViewById(R.id.txt_cnic);
        txt_password = findViewById(R.id.txt_password);
        btn_login = findViewById(R.id.btn_login);

//        getSupportActionBar().hide();

        FirebaseDatabase db=FirebaseDatabase.getInstance();
        final DatabaseReference table_user=db.getReference("customer-list");
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                d=new ProgressDialog(Login.this);
                d.setMessage("Please wait...");
                d.show();
                table_user.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.child(txt_cnic.getText().toString()).exists()){
                            d.dismiss();
                            customer user=dataSnapshot.child(txt_cnic.getText().toString()).getValue(customer.class);
                            if(user.getPassword().equalsIgnoreCase(txt_password.getText().toString())){
                                Intent home=new Intent(Login.this, MainPage.class);
                                SharedPreferences sharedpreferences;
                                sharedpreferences = getSharedPreferences("cnic", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedpreferences.edit();
                                editor.putString("cnic", txt_cnic.getText().toString());
                                editor.commit();
                                startActivity(home);

                                finish();
                            }else {
                                Toast.makeText(getApplicationContext(),"wrong password",Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            d.dismiss();
                            Toast.makeText(getApplicationContext(),"User not exist in the database",Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });
    }
}
