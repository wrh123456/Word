package com.example.word14_filepersistencetest;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;


public class MainActivity extends AppCompatActivity {
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText=(EditText)findViewById(R.id.edit1);
        String input=load();
        if(!input.isEmpty()){
            editText.setText(input);
            editText.setSelection(input.length());
            //设置光标位置在最后的位置
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        String text=editText.getText().toString();
        save(text);
    }
    public void save(String s){
        FileOutputStream out=null;
        BufferedWriter writer=null;
        try {
            //MODE_PRICATE是默认模式，表示当指定同样的文件名时，所写入的内容将会覆盖原文件中的内容。
            //MODE_APPEND则表示如果该文件已存在，就在文件里面追加内容，不存在就创建新文件
            out=openFileOutput("data", Context.MODE_PRIVATE);
            writer=new BufferedWriter(new OutputStreamWriter(out));
            writer.write(s);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if (writer != null)
                    writer.close();
                if (out != null)
                    out.close();
            }catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
    public String load(){
        FileInputStream in=null;
        BufferedReader reader=null;
        StringBuilder builder=new StringBuilder();
        try {
            in=openFileInput("data");
            reader=new BufferedReader(new InputStreamReader(in));
            String line="";
            while ((line=reader.readLine())!=null){
                builder.append(line);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(reader!=null){
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return builder.toString();
    }
}
