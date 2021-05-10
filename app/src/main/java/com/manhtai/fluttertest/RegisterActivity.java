package com.manhtai.fluttertest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.JsonObject;
import com.manhtai.fluttertest.itf.GameApi;
import com.manhtai.fluttertest.model.BaseJson;

import org.json.JSONException;
import org.json.JSONObject;

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

    TelephonyManager teleManager;
    String deviceId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        getView();
        teleManager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        btnTurnBack.setOnClickListener(v -> finish());
        btnRegister.setOnClickListener(v -> register());
        imgbRegisterFb.setOnClickListener(v -> loginGuest());
        imgbRegisterGg.setOnClickListener(v -> loginGuest());
        imgbRegisterLogo.setOnClickListener(v -> loginGuest());

    }

    private void register() {
        if(validate()){
            String userName = edtRegisterUserName.getText().toString();
            String password = edtRegisterPassword.getText().toString();

            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("gameId","1");
                jsonObject.put("userName",userName);
                jsonObject.put("password",password);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Log.d(TAG, "register: " + jsonObject.toString());
            GameApi.GAME_API.rigisterUser(jsonObject.toString())
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
                            Log.d(TAG, "onFailure: " + t);
                            Toast.makeText(RegisterActivity.this, "Lỗi không xác định", Toast.LENGTH_SHORT).show();
                        }
                    });
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
        Log.d(TAG, "clickView: " + deviceId);
        GameApi.GAME_API.loginUserGuest(jsonObject.toString()).enqueue(new Callback<BaseJson>() {
            @Override
            public void onResponse(Call<BaseJson> call, Response<BaseJson> response) {
                NotificationE(response.body());
            }

            @Override
            public void onFailure(Call<BaseJson> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t);
                Toast.makeText(RegisterActivity.this, "Lỗi không xác định", Toast.LENGTH_SHORT).show();
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
    private void NotificationE(BaseJson baseJson) {
        Log.d(TAG, "NotificationE:" + baseJson.getRequestId());
        if (baseJson.getError().getCode() == 200) {
            //Giả định 200 là mã đăng nhập thành công
            Toast.makeText(RegisterActivity.this, baseJson.getError().getMessage(), Toast.LENGTH_SHORT).show();
        } else if (baseJson.getError().getCode() == 400) {
            //Giả định 200 là mã đăng nhập không thành công
            Toast.makeText(RegisterActivity.this, baseJson.getError().getMessage(), Toast.LENGTH_SHORT).show();
        }
        Toast.makeText(RegisterActivity.this, baseJson.getError().getMessage(), Toast.LENGTH_SHORT).show();
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