package com.manhtai.fluttertest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.manhtai.fluttertest.itf.GameApi;
import com.manhtai.fluttertest.model.BaseJson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Random;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "AAA";
    private TextInputEditText edtLoginUserName;
    private TextInputEditText edtLoginPassword;
    private Button btnLogin;
    private Button btnRegister;
    private ImageButton imgbLoginLogo;
    private ImageButton imgbLoginGg;
    private ImageButton imgbLoginFb;
    private TextView tvLoginError;
    TelephonyManager teleManager;
    String deviceId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getView();
        teleManager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        clickView();

    }

    private void clickView() {
        btnLogin.setOnClickListener(v -> login());
        imgbLoginFb.setOnClickListener(v -> loginGuest());
        imgbLoginGg.setOnClickListener(v -> loginGuest());
        imgbLoginLogo.setOnClickListener(v -> loginGuest());
        btnRegister.setOnClickListener(v -> {
            startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
        });
    }

    private void login() {
        if (validate() == null) {
            String userName = edtLoginUserName.getText().toString();
            String password = edtLoginPassword.getText().toString();
            String appKey = String.valueOf(System.currentTimeMillis());
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("gameId", "1");
                jsonObject.put("userName", userName);
                jsonObject.put("password", password);
                jsonObject.put("appKey", appKey);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            GameApi.GAME_API.loginUser(jsonObject.toString())
                    .enqueue(new Callback<BaseJson>() {
                        @Override
                        public void onResponse(Call<BaseJson> call, Response<BaseJson> response) {
                            NotificationE(response.body());
                        }

                        @Override
                        public void onFailure(Call<BaseJson> call, Throwable t) {
                            Toast.makeText(LoginActivity.this, "Lỗi không xác định", Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            tvLoginError.setText(validate());
        }
    }

    private void loginGuest() {
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE);

        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, 500);
        } else {
            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                deviceId = "Đây là android 10 vì thế k thể lấy được deviceID";
            }else if (Build.VERSION.SDK_INT >= 26) {
                deviceId = teleManager.getImei();

            }else {
                deviceId = teleManager.getDeviceId();
            }
        }
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("gameId","1");
            jsonObject.put("guestId", "user" + String.valueOf(System.currentTimeMillis()));
            jsonObject.put("appKey", String.valueOf(System.currentTimeMillis()));
            jsonObject.put("deviceInfo",deviceId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        GameApi.GAME_API.loginUserGuest(jsonObject.toString()).enqueue(new Callback<BaseJson>() {
            @Override
            public void onResponse(Call<BaseJson> call, Response<BaseJson> response) {
                NotificationE(response.body());
            }

            @Override
            public void onFailure(Call<BaseJson> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t);
                Toast.makeText(LoginActivity.this, "Lỗi không xác định", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void NotificationE(BaseJson baseJson) {
        Log.d(TAG, "NotificationE:" + baseJson.getRequestId());
        if (baseJson.getError().getCode() == 200) {
            //Giả định 200 là mã đăng nhập thành công
            Toast.makeText(LoginActivity.this, baseJson.getError().getMessage(), Toast.LENGTH_SHORT).show();
        } else if (baseJson.getError().getCode() == 400) {
            //Giả định 200 là mã đăng nhập không thành công
            Toast.makeText(LoginActivity.this, baseJson.getError().getMessage(), Toast.LENGTH_SHORT).show();
        }
        Toast.makeText(LoginActivity.this, baseJson.getError().getMessage(), Toast.LENGTH_SHORT).show();
    }

    private String validate() {
        String userName = edtLoginUserName.getText().toString();
        String password = edtLoginPassword.getText().toString();
        String error = null;
        if (userName.length() < 6) {
            error = "Tài khoản phải dài hơn 6 kí tự";

        } else if (!userName.matches("[a-z0-9]{6,}")) {
            error = "Tài khoản chỉ từ 0 - 9, a - z và không chứa kí tự đặc biệt";
        } else if (password.length() < 6) {
            error = "Mật khẩu quá ngắn";
        }

        return error;

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 500 && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                deviceId = "Đây là android 10 vì thế k thể lấy được deviceID";
            }else  if (Build.VERSION.SDK_INT >= 26) {
                deviceId = teleManager.getImei();

            }  else {
                deviceId = teleManager.getDeviceId();
            }
        }
    }

    private void getView() {
        edtLoginUserName = findViewById(R.id.edt_login_user_name);
        edtLoginPassword = findViewById(R.id.edt_login_password);
        btnLogin = findViewById(R.id.btn_login);
        btnRegister = findViewById(R.id.btn_register);
        imgbLoginLogo = findViewById(R.id.imgb_login_logo);
        imgbLoginGg = findViewById(R.id.imgb_login_gg);
        imgbLoginFb = findViewById(R.id.imgb_login_fb);
        tvLoginError = findViewById(R.id.tv_login_error);
    }
}