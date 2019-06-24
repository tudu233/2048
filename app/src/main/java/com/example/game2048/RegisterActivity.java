package com.example.game2048;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


/**
 * 注册界面
 */
public class RegisterActivity extends Activity implements View.OnClickListener {

    private EditText et_user;
    private EditText et_password;
    private Button btn_register;
    private Button btn_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();

    }

    //控件初始化
    private void initView() {
        setTitle("注册");
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
            case R.id.btn_register://注册

                String name = et_user.getText().toString().trim();
                String pass = et_password.getText().toString().trim();

                User user = new User();
                user.setUsername(name);
                user.setUserpwd(pass);

                if (TextUtils.isEmpty(name) || TextUtils.isEmpty(pass)) {
                    Toast.makeText(this, "用户名和密码不能为空！", Toast.LENGTH_SHORT).show();
                    return;
                }

                int result = UserDao.getInstance(getApplicationContext()).saveUser(user);
                if (result == 1) {
                    Toast.makeText(this, "注册成功！", Toast.LENGTH_SHORT).show();
                    finish();
                } else if (result == -1) {
                    Toast.makeText(this, "用户名已经存在！", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "注册失败！", Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.btn_login://返回登录

                finish();

                break;
        }
    }

}
