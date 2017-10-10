package com.example.anando.hciapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.dd.processbutton.iml.ActionProcessButton;

public class LogInActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

    }

    @Override
    protected void onStart() {
        super.onStart();
        TextView SingUp = (TextView)findViewById(R.id.link_signup);
        /*
            Register User.
         */
        SingUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent singUpPage = new Intent(LogInActivity.this,RegisterActivity.class);
                startActivity(singUpPage);
            }
        });

        /*
            Go To main Home after sign in
         */
        ActionProcessButton logIn = (ActionProcessButton)findViewById(R.id.login_button);
        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LogInActivity.this,HomeActivity.class));
            }
        });
    }
}
