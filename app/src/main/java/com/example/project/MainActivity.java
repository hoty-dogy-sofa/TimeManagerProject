package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView textView;
    Button btn1, btn2, btn3;
    Integer money = 0;
    String current_citata = "Здесь будет мотивирующая фраза";
    String current_login = "", task = "Здесь будет ваше задание";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.textmain);
        textView.setText(money.toString());
        btn1 = findViewById(R.id.btnwork);
        btn2 = findViewById(R.id.btnshop);
        btn3 = findViewById(R.id.btnparents);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                go_work(view);
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                go_shop(view);
            }
        });
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                go_par(view);
            }
        });
    }
    public void go_par(View v){
        Intent intent = new Intent(this, Parents.class);
        intent.putExtra("money", money.toString());
        intent.putExtra("citata", current_citata);
        intent.putExtra("login", current_login);
        intent.putExtra("task", task);
        startActivityForResult(intent, 1);
    }
    public void go_work(View v){
        Intent intent = new Intent(this, Work_List.class);
        intent.putExtra("money", money.toString());
        intent.putExtra("citata", current_citata);
        intent.putExtra("login", current_login);
        intent.putExtra("task", task);
        startActivityForResult(intent, 1);
    }
    public void go_shop(View v){
        Intent intent = new Intent(this, Shop_Activity.class);
        intent.putExtra("money", money.toString());
        intent.putExtra("citata", current_citata);
        intent.putExtra("login", current_login);
        intent.putExtra("task", task);
        startActivityForResult(intent, 1);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data == null){
            return;
        }
        String infa = data.getStringExtra("money");
        current_citata = data.getStringExtra("citata");
        current_login = data.getStringExtra("login");
        task = data.getStringExtra("task");
        money = Integer.parseInt(infa);
        textView = findViewById(R.id.textmain);
        textView.setText(money.toString());
    }
}