package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Parents extends AppCompatActivity {

    TextView textView;
    Button btn;
    EditText user, passw, kword;
    DBhelper db = new DBhelper(this);
    String phrase;
    Integer money;
    String login = "";
    String gotlog, task;
    String gotpass;
    String gotkw;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parents);
        textView = findViewById(R.id.error);
        btn = findViewById(R.id.filledin);
        user = findViewById(R.id.login);
        kword = findViewById(R.id.phrase);
        passw = findViewById(R.id.password);

        Intent intent = getIntent();
        String infa = intent.getStringExtra("money");
        phrase = intent.getStringExtra("citata");
        task = intent.getStringExtra("task");
        money = Integer.parseInt(infa);
        login = intent.getStringExtra("login");

        btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                gotlog = user.getText().toString();
                gotpass = passw.getText().toString();
                gotkw = kword.getText().toString();
                if (gotlog.equals("") || gotkw.equals("") || gotpass.equals("")){
                    textView.setText("Вы не заполнили все поля!");
                }
                else{
                    if (db.check_presence(gotlog) && !db.check_with_kw(gotlog, gotkw)){
                        textView.setText("Для изменения пароля введите правильное ключевое слово!");
                    }
                    else if(db.check_with_kw(gotlog, gotkw) && db.check_with_pw(gotlog, gotpass)){
                        Object[] data = db.get(gotlog, gotkw, gotpass);
                        money = (Integer) data[0];
                        phrase = (String) data[1];
                        task = (String) data[2];
                        textView.setText("Вы вошли!");
                        login = gotlog;
                    }
                    else if (db.check_with_kw(gotlog, gotkw) && !db.check_with_pw(gotlog, gotpass)){
                        db.change_pass(gotlog, gotkw, gotpass);
                        textView.setText("Пароль был изменен!");
                        login = gotlog;
                    }
                    else if (!db.check_presence(gotlog)){
                        money = 0;
                        phrase = "Здесь будет мотивирующая фраза";
                        task = "Здесь будет ваше задание";
                        db.add(gotlog, gotkw, gotpass, phrase, money, task);
                        textView.setText("Пользователь был добавлен!");
                        login = gotlog;
                    }
                }
            }
        });
    }
    public void go_back_par(View v){
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("money", money.toString());
        intent.putExtra("citata", phrase);
        intent.putExtra("login", login);
        intent.putExtra("task", task);
        setResult(RESULT_OK, intent);
        finish();
    }

}