package com.example.project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ClipData;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.InputDevice;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class Work_List extends AppCompatActivity {
    Integer money;
    Button button, but_ok;
    TextView textView, textView2;
    String current_citata;
    String cur_log = "", gotlog="", gotpass="";
    DBhelper db = new DBhelper(this);
    String task;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_list);
        Intent intent = getIntent();
        String infa = intent.getStringExtra("money");
        current_citata = intent.getStringExtra("citata");
        task = intent.getStringExtra("task");
        money = Integer.parseInt(infa);
        cur_log = intent.getStringExtra("login");
        textView = findViewById(R.id.textwork);
        textView.setText(money.toString());
        textView2 = findViewById(R.id.work);
        textView2.setText(task);
        button = (Button) findViewById(R.id.add);
        but_ok = (Button) findViewById(R.id.check);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog();
            }
        });
        but_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialogCheck();
            }
        });
    }
    public void go_back_work(View v){
        Intent intent = new Intent();
        intent.putExtra("money", money.toString());
        intent.putExtra("citata", current_citata);
        intent.putExtra("login", cur_log);
        intent.putExtra("task", task);
        setResult(RESULT_OK, intent);
        finish();
    }

    public void openDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(Work_List.this);
        EditText textic = new EditText(Work_List.this);
        textic.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setPositiveButton("Добавить", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                textView2.setText(textic.getText());
                task = textic.getText().toString();
                db.change_task(gotlog, task);
            }
        }).setView(textic).setCancelable(false).setNegativeButton("Отмена",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        dialog.cancel();
                    }
                }).show();
    }
    public void openDialogCheck(){
        AlertDialog.Builder builder = new AlertDialog.Builder(Work_List.this);
        EditText pass = new EditText(Work_List.this);
        pass.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setPositiveButton("Отметить выполнение", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                gotpass = pass.getText().toString();
                gotlog = cur_log;
                if (!gotpass.equals("") && !gotlog.equals("")){
                    if (db.check_with_pw(gotlog, gotpass)){
                        task = "Здесь будет ваше дело";
                        textView2.setText(task);
                        money += 1;
                        textView.setText(money.toString());
                        db.change_money(gotlog, money);
                        db.change_task(gotlog, task);
                    }
                }
            }
        }).setView(pass).setCancelable(false).setNegativeButton("Отмена",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        dialog.cancel();
                    }
                }).show();
    }
}
