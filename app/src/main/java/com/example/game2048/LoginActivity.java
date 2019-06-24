package com.example.game2048;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


/**
 * 登录界面
 */
public class LoginActivity extends Activity implements View.OnClickListener {

    private EditText et_user;
    private EditText et_password;
    private Button btn_register;
    private Button btn_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();

    }

    //控件初始化
    private void initView() {
        setTitle("登录");
        et_user = (EditText) findViewById(R.id.et_user);
        et_password = (EditText) findViewById(R.id.et_password);
        btn_register = (Button) findViewById(R.id.btn_register);
        btn_login = (Button) findViewById(R.id.btn_login);

        btn_register.setOnClickListener(this);
        btn_login.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_register://跳转注册

                startActivity(new Intent(this, RegisterActivity.class));

                break;
            case R.id.btn_login://登录

                String name = et_user.getText().toString().trim();
                String pass = et_password.getText().toString().trim();
                User user = UserDao.getInstance(getApplicationContext()).login(pass, name);
                if (user != null) {
                    Toast.makeText(this, "登录成功！", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(this, MainActivity.class));
                    finish();
                } else {

                    Toast.makeText(this, "用户名不存在或者密码错误！", Toast.LENGTH_SHORT).show();

                }

                break;
        }
    }

}
