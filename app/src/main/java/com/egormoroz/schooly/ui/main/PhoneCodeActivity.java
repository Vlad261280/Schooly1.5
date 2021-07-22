package com.egormoroz.schooly.ui.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.egormoroz.schooly.ErrorList;
import com.egormoroz.schooly.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.appbar.AppBarLayout;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.auth.PhoneAuthProvider.OnVerificationStateChangedCallbacks;

import org.jetbrains.annotations.NotNull;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class PhoneCodeActivity extends AppCompatActivity {
    final int Phone_Request_Code = 102;
    final int PhoneSMSResend = 30000;
    final int Second = 1000;
    String TAG = "############";
    String phone;
    ImageView backButton;
    CountDownTimer timer;
    Context context;
    EditText SMSCode;
    ImageView continueButton;
    OnVerificationStateChangedCallbacks callbacks;
    Intent data;
    String currentVerificationCode;
    PhoneAuthProvider.ForceResendingToken currentResendToken;
    FirebaseAuth AuthenticationBase;
    TextView timerText, errorText, resendCode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_phonecode);
        initElements();
        initData();
        initFirebase();
        sendSMS();
        resendSMS();
        enterCode();
    }
    public void initElements(){
        resendCode = findViewById(R.id.resendCodeTextView);
        backButton = findViewById(R.id.backfromphonecode);
        SMSCode = findViewById(R.id.VerificationCode);
        continueButton = findViewById(R.id.continueButton);
        timerText = findViewById(R.id.time);
        errorText = findViewById(R.id.errorMessage);
        context = getBaseContext();
    }
    public void initData(){
        data = new Intent();
        phone = getIntent().getStringExtra("Phone");
    }
    public void initFirebase(){
        AuthenticationBase = FirebaseAuth.getInstance();
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
    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        AuthenticationBase.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = task.getResult().getUser();
                            user.delete();
                            data.putExtra("IsPhoneValid", true);
                            setResult(RESULT_OK, data);
                            finish();
                            // Update UI
                        } else {
                            // Sign in failed, display a message and update the UI
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                showErrorMessage(ErrorList.WRONG_SMS_CODE_ERROR);
                            }
                        }
                    }
                });
    }
    public void sendSMS(){
        callbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull @NotNull PhoneAuthCredential credential) {
                Log.d(TAG, "Verification completed with: " + credential);
                signInWithPhoneAuthCredential(credential);
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
                startTimer();
            }
        };
        phoneVerification(phone);
    }
    public void startTimer(){
        resendCode.setVisibility(View.GONE);
        resendCode.setEnabled(false);
        timer = new CountDownTimer(PhoneSMSResend, Second) {
            @Override
            public void onTick(long l) {
                timerText.setText("You can resend SMS after: " + l / Second);
            }

            @Override
            public void onFinish() {
                timerText.setVisibility(View.GONE);
                resendCode.setVisibility(View.VISIBLE);
                resendCode.setEnabled(true);
                timerText.setText("You can resend SMS");
            }
        };
        timer.start();
    }
    public void enterCode(){
        SMSCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(String.valueOf(SMSCode.getText()).trim().length() != 6)
                    continueButton.setEnabled(false);
                else {
                    continueButton.setEnabled(true);
                    continueButton.setBackgroundColor(getColor(R.color.app_color));
                }
            }
        });
        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String code = String.valueOf(SMSCode.getText()).trim();
                PhoneAuthCredential credential = PhoneAuthProvider.getCredential(currentVerificationCode, code);
                signInWithPhoneAuthCredential(credential);
            }
        });
    }
    public void showErrorMessage(ErrorList tag){
        switch (tag){
            case SMS_LIMIT_ERROR:
                errorText.setText("Sorry, you exceeded your SMS limit: 4 sms per hour");
                break;
            case TO_MANY_REQUESTS_ERROR:
                errorText.setText("Sorry, our database has too many request, please retry later");
                break;
            case UNKNOWN_REGISTRATION_ERROR:
                errorText.setText("Sorry, you have unknown error, please retry");
                break;
            case WRONG_SMS_CODE_ERROR:
                errorText.setText("Wrong code, please check it out");
                break;
            case WRONG_PHONE_NUMBER_ERROR:
                errorText.setText("Phone number is invalid, please check it out");
                break;
        }
    }
    public void resendSMS(){
        resendCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendSMS();
                startTimer();
            }
        });
    }
}
