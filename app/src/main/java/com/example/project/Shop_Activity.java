package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Shop_Activity extends AppCompatActivity {
    TextView textView, citata;
    Button button;
    Integer money;
    String cur_citata, task;
    String cur_log = "";
    DBhelper db = new DBhelper(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);
        citata = (TextView) findViewById(R.id.citata);
        button = (Button) findViewById(R.id.buy);
        Intent intent = getIntent();
        String infa = intent.getStringExtra("money");
        cur_citata = intent.getStringExtra("citata");
        cur_log = intent.getStringExtra("login");
        task = intent.getStringExtra("task");
        citata.setText(cur_citata);
        money = Integer.parseInt(infa);
        textView = findViewById(R.id.textshop);
        textView.setText(money.toString());
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (money >= 2 && !cur_log.equals("")){
                    money -= 2;
                    textView.setText(money.toString());
                    String phrase = CitataHelper.create();
                    citata.setText(phrase);
                    db.change_money(cur_log, money);
                    db.change_citata(cur_log, phrase);
                }
                else{
                    citata.setText("Не хватает денег или не выполнен вход");
                }
            }
        });
    }
    public void go_back_shop(View v){
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("money", money.toString());
        intent.putExtra("citata", citata.getText().toString());
        intent.putExtra("login", cur_log);
        intent.putExtra("task", task);
        setResult(RESULT_OK, intent);
        finish();
    }
}