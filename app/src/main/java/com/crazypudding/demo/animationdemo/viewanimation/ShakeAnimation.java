package com.crazypudding.demo.animationdemo.viewanimation;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.CycleInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.crazypudding.demo.animationdemo.R;

public class ShakeAnimation extends AppCompatActivity {

    private EditText mEtAccount, mEtPassword;
    private Button mBtnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shake_animation);

        mEtAccount = findViewById(R.id.tv_account);
        mEtPassword = findViewById(R.id.tv_password);
        mBtnLogin = findViewById(R.id.btn_login);
        mBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String account = mEtAccount.getText().toString().trim();
                String password = mEtPassword.getText().toString().trim();
                if (TextUtils.isEmpty(account)) {
                    animate(mEtAccount);
                }
                if (TextUtils.isEmpty(password)) {
                    animate(mEtPassword);
                }
                if (!TextUtils.isEmpty(account) && !TextUtils.isEmpty(password)) {
                    Toast.makeText(ShakeAnimation.this, "account:" + account + ",password:" + password, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void animate(View target) {
        // 载入 Animation 实例
        Animation shakeAnimation = AnimationUtils.loadAnimation(ShakeAnimation.this, R.anim.shake);
        // 设置插值器 CycleInterpolator 达到晃动的效果
        shakeAnimation.setInterpolator(new CycleInterpolator(6));
        // 开始动画
        target.startAnimation(shakeAnimation);
    }
}
