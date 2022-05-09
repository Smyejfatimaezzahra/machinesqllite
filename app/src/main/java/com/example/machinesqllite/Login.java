package com.example.machinesqllite;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;

import service.UserService;

public class Login extends AppCompatActivity implements View.OnClickListener {
    Button b;
    TextView name;
    TextView pass;
    PopupWindow popupWindow;
    UserService us;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        b=findViewById(R.id.button_signin);
        name=findViewById(R.id.et_username);
        pass=findViewById(R.id.et_password);
        us=new UserService(this);
        us.fillDatabaseWithData();
        b.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view==b){
            String firstp=name.getText().toString();
            String secondp=pass.getText().toString();
           if( us.findByPassAndName(firstp,secondp)) {
               Intent intent  = new Intent(Login.this,MachineActivity.class);
               startActivity(intent);}
           else{
               AlertDialog.Builder builder = new AlertDialog.Builder(Login.this);
               builder.setMessage("incorrecte login or password")

                       .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                           public void onClick(DialogInterface dialog, int id) {
                               // User cancelled the dialog
                           }
                       })
                       .create();
               // Create the AlertDialog object and return it
               builder.show();
           }



        }

    }
}