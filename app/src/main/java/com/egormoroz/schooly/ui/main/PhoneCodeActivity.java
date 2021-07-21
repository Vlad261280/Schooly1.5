package com.egormoroz.schooly.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.egormoroz.schooly.R;
import com.google.android.material.appbar.AppBarLayout;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.auth.PhoneAuthProvider.OnVerificationStateChangedCallbacks;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.TimeUnit;

public class PhoneCodeActivity extends AppCompatActivity {
    final int Phone_Request_Code = 102;
    String TAG = "############";
    String phone;
    ImageView backButton;
    EditText SMSCode;
    ImageView continueButton;
    OnVerificationStateChangedCallbacks callbacks;
    Intent data;
    String currentVerificationCode;
    PhoneAuthProvider.ForceResendingToken currentResendToken;
    FirebaseAuth AuthenticationBase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_phonecode);
        backButton = findViewById(R.id.backfromphonecode);
        SMSCode = findViewById(R.id.VerificationCode);
        continueButton = findViewById(R.id.continueButton);
        data = new Intent();
        phone = getIntent().getStringExtra("Phone");

        AuthenticationBase = FirebaseAuth.getInstance();
        callbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onVerificationCompleted(@NonNull @NotNull PhoneAuthCredential phoneAuthCredential) {
                Log.d(TAG, "Verification completed with: " + phoneAuthCredential);
                data.putExtra("phoneAuthCredential", phoneAuthCredential);
                data.putExtra("IsPhoneValid", true);
                setResult(RESULT_OK, data);
                finish();
            }

            @Override
            public void onVerificationFailed(@NonNull @NotNull FirebaseException e) {
                Log.d(TAG, "Verification failed with: " + e);
                data.putExtra("IsPhoneValid", false);
                setResult(RESULT_OK, data);
                finish();
            }

            @Override
            public void onCodeSent(@NonNull String verificationId,
                                   @NonNull PhoneAuthProvider.ForceResendingToken token) {
                // The SMS verification code has been sent to the provided phone number, we
                // now need to ask the user to enter the code and then construct a credential
                // by combining the code with a verification ID.
                Log.d(TAG, "onCodeSent:" + verificationId);
                // Save verification ID and resending token so we can use them later
                currentVerificationCode = verificationId;
                currentResendToken = token;
                PhoneAuthCredential credential = PhoneAuthProvider.getCredential(currentVerificationCode, "123456");
                while(credential.getSmsCode() == null) {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                data.putExtra("IsPhoneValid", true);
                setResult(RESULT_OK, data);
                finish();
            }
        };
        phoneVerification(phone);
    }
    public void phoneVerification(String phone){
        PhoneAuthOptions options = PhoneAuthOptions.newBuilder(AuthenticationBase)
                .setPhoneNumber(phone)       // Phone number to verify
                .setTimeout(30L, TimeUnit.SECONDS) // Timeout and unit
                .setActivity(this)                 // Activity (for callback binding)
                .setCallbacks(callbacks)          // OnVerificationStateChangedCallbacks
                .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

}
