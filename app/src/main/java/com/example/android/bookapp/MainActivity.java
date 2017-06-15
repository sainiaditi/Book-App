package com.example.android.bookapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.SearchView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import static android.R.id.empty;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = (Button) findViewById(R.id.searchButton);
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                EditText et = (EditText) findViewById(R.id.editText);
                String book = et.getText().toString();
                if(book.length() == 0){
                    Toast.makeText(getBaseContext(),"Field can not be empty",Toast.LENGTH_LONG).show();
                }else{
                    Intent intent = new Intent(getBaseContext(), BookActivity.class);
                    intent.putExtra("Query",book);
                    startActivity(intent);
                }
            }
        } );

    }

}
