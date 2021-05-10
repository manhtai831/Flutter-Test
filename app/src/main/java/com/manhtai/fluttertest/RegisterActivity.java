package com.manhtai.fluttertest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.manhtai.fluttertest.itf.GameApi;
import com.manhtai.fluttertest.model.BaseJson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {
    private static final String TAG = "AAAA";
    private TextInputEditText edtRegisterUserName;
    private TextView tvRegisterError1;
    private TextInputEditText edtRegisterPassword;
    private TextInputEditText edtRegisterCPassword;
    private TextView tvRegisterError3;
    private Button btnRegister;
    private Button btnTurnBack;
    private ImageButton imgbRegisterLogo;
    private ImageButton imgbRegisterGg;
    private ImageButton imgbRegisterFb;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        getView();
        btnTurnBack.setOnClickListener(v -> finish());
        btnRegister.setOnClickListener(v -> {
            if(validate()){
                String userName = edtRegisterUserName.getText().toString();
                String password = edtRegisterPassword.getText().toString();

                GameApi.GAME_API.rigisterUser("1",userName,password)
                        .enqueue(new Callback<BaseJson>() {
                            @Override
                            public void onResponse(Call<BaseJson> call, Response<BaseJson> response) {
                                BaseJson baseJson = response.body();
                                if(baseJson.getError().getCode() == 200){
                                    Toast.makeText(RegisterActivity.this, baseJson.getError().getMessage(), Toast.LENGTH_SHORT).show();
                                }else if(baseJson.getError().getCode() == 400){
                                    Toast.makeText(RegisterActivity.this, baseJson.getError().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                                Toast.makeText(RegisterActivity.this, baseJson.getError().getMessage(), Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onFailure(Call<BaseJson> call, Throwable t) {
                                Toast.makeText(RegisterActivity.this, "Lỗi không xác định", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
    }

    private boolean validate() {
        String userName = edtRegisterUserName.getText().toString();
        String password = edtRegisterPassword.getText().toString();
        String cPassword = edtRegisterCPassword.getText().toString();

        if (userName.length() < 6) {
            tvRegisterError1.setText("Tài khoản phải dài hơn 6 kí tự");
            tvRegisterError3.setText("");
            return false;
        } else if (!userName.matches("[a-z0-9]{6,}")) {
            tvRegisterError1.setText("Tài khoản chỉ từ 0 - 9, a - z và không chứa kí tự đặc biệt");
            tvRegisterError3.setText("");
            return false;
        } else if (password.length() < 6) {
            tvRegisterError3.setText("Mật khẩu quá ngắn");
            tvRegisterError1.setText("");
            return false;
        }else if(!password.equals(cPassword)){
            tvRegisterError3.setText("Mật khẩu không khớp");
            tvRegisterError1.setText("");
            return false;
        }

        return true;

    }

    private void getView() {
        edtRegisterUserName = findViewById(R.id.edt_register_user_name);
        tvRegisterError1 = findViewById(R.id.tv_register_error_1);
        edtRegisterPassword = findViewById(R.id.edt_register_password);
        edtRegisterCPassword = findViewById(R.id.edt_register_c_password);
        tvRegisterError3 = findViewById(R.id.tv_register_error_3);
        btnRegister = findViewById(R.id.btn_register);
        btnTurnBack = findViewById(R.id.btn_turn_back);
        imgbRegisterLogo = findViewById(R.id.imgb_register_logo);
        imgbRegisterGg = findViewById(R.id.imgb_register_gg);
        imgbRegisterFb = findViewById(R.id.imgb_register_fb);
    }
}