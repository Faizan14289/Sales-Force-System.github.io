package com.example.salesman;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.salesman.Modelclasses.ChatMessage;
import com.example.salesman.Modelclasses.customer;
import com.example.salesman.Modelclasses.sales_man;
import com.firebase.ui.database.FirebaseListAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class chat extends AppCompatActivity {
String cnic;
    sales_man user;
    TextView SalesManName;

    private FirebaseListAdapter<ChatMessage> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        Toolbar toolbar = findViewById(R.id.bgHeader);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        SalesManName = findViewById(R.id.SalesManName);


        FloatingActionButton fab =
                (FloatingActionButton)findViewById(R.id.fab);
        DisplayMessages();
        FirebaseDatabase db=FirebaseDatabase.getInstance();
        final DatabaseReference table_user=db.getReference("salesMan-list");
        table_user.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.child(cnic).exists()){

                    user=dataSnapshot.child(cnic).getValue(sales_man.class);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        SharedPreferences sharedpreferences;
        sharedpreferences = getSharedPreferences("cnic", Context.MODE_PRIVATE);
        cnic = sharedpreferences.getString("cnic", null);
        FirebaseDatabase db1;
        db1=FirebaseDatabase.getInstance();
        DatabaseReference ref1;
        ref1 = db1.getReference("salesMan-list").child(cnic);
        ref1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    sales_man c=dataSnapshot.getValue(sales_man.class);
                    SalesManName.setText(c.getName());
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText input = (EditText)findViewById(R.id.input);

                // Read the input field and push a new instance
                // of ChatMessage to the Firebase database
                FirebaseDatabase.getInstance()
                        .getReference("salesManMessages")
                        .child(cnic)
                        .push()
                        .setValue(new ChatMessage(input.getText().toString(),
                                user.getName())
                        );

                // Clear the input
                input.setText("");
            }
        });
    }

    public void DisplayMessages(){
        SharedPreferences sharedpreferences;
        sharedpreferences = getSharedPreferences("cnic", Context.MODE_PRIVATE);
        String cnic = sharedpreferences.getString("cnic", null);
        ListView listOfMessages = (ListView)findViewById(R.id.list_of_messages);

        adapter = new FirebaseListAdapter<ChatMessage>(chat.this, ChatMessage.class,
                R.layout.message, FirebaseDatabase.getInstance().getReference("salesManMessages").child(cnic)) {
            @Override
            protected void populateView(View v, ChatMessage model, int position) {
                // Get references to the views of message.xml
                TextView messageText = (TextView)v.findViewById(R.id.message_text);
                TextView messageUser = (TextView)v.findViewById(R.id.message_user);
                TextView messageTime = (TextView)v.findViewById(R.id.message_time);

                // Set their text
                messageText.setText(model.getMessageText());
                messageUser.setText(model.getMessageUser());

                // Format the date before showing it
                messageTime.setText(DateFormat.format("dd-MM-yyyy (HH:mm:ss)",
                        model.getMessageTime()));
            }
        };

        listOfMessages.setAdapter(adapter);
    }




}
