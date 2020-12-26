package com.vaibhav.fitnessapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class RegisterActivity extends AppCompatActivity {

    EditText name, emailId, phone, OTP, password, confirmPassword, idProof, address, city, state, age, preExisting;
    Button register, resend;
    TextView loginText;
    FirebaseAuth mAuth;
    private String verificationID;
    PhoneAuthProvider.ForceResendingToken token;
    private Uri filePath;
    ProgressDialog dialog;
    private final int PICK_IMAGE_REQUEST = 22;
    int TAKE_IMAGE_CODE = 10001;
    boolean loggedIn = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        try {
            SharedPreferences sharedPreferences = getSharedPreferences("loggedInStatus", MODE_PRIVATE);
            loggedIn = sharedPreferences.getBoolean("loggedIn", false);
            Log.d("MSg", "" + loggedIn);
        } catch(Exception e) {
            Log.d("MSg catch", "" + loggedIn);
        }
        if(loggedIn) {
            Intent intent=new Intent(RegisterActivity.this, DrawerUser.class);
            startActivity(intent);
            finish();
        }

        name = findViewById(R.id.name);
        emailId = findViewById(R.id.emailId);
        phone = findViewById(R.id.phone);
        password = findViewById(R.id.password);
        confirmPassword = findViewById(R.id.confirmPassword);
        idProof = findViewById(R.id.idProof);
        address = findViewById(R.id.address);
        city = findViewById(R.id.city);
        state = findViewById(R.id.state);
        age = findViewById(R.id.age);
        preExisting = findViewById(R.id.preExisting);
        OTP = findViewById(R.id.otp);

        register = findViewById(R.id.registerButton);
        resend = findViewById(R.id.resend);

        loginText = findViewById(R.id.loginText);

        mAuth = FirebaseAuth.getInstance();

        User user = new User();

        resend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resendVerificationCode("+91"+ phone.getText().toString().trim(), token);
            }
        });

        loginText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });


        String otp = OTP.getText().toString().trim();

        OTP.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                if(otp.length()==6)     //size as per your requirement
                {
                    String otp = OTP.getText().toString().trim();
                    verifyVerificationCode(otp);
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            public void afterTextChanged(Editable s) {

            }
        });

        phone.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                if(phone.getText().toString().length() == 10)     //size as per your requirement
                {
                    sendVerificationCode("+91" + phone.getText().toString());
                    String uid = phone.getText().toString();
                    Log.i("/myUidddd",""+uid);


                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            public void afterTextChanged(Editable s) {

            }
        });

        idProof.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPictureDialog();
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String otp = OTP.getText().toString().trim();

                if(otp.isEmpty() || otp.length() <6) {
                    OTP.setText("");
                    OTP.setFocusable(true);
                    OTP.setError("Enter correct OTP");
                    return;
                }

                if (age.getText().toString() == null) {
                    age.setText("");
                    age.setFocusable(true);
                    age.setError("Age cannot be empty");
                    return;
                }
                if (city.getText().toString().isEmpty()) {
                    city.setText("");
                    city.setFocusable(true);
                    city.setError("City cannot be empty");
                    return;
                }
                if (state.getText().toString().isEmpty()) {
                    state.setText("");
                    state.setFocusable(true);
                    state.setError("State cannot be empty");
                    return;
                }
                if (address.getText().toString().isEmpty()) {
                    address.setText("");
                    address.setFocusable(true);
                    address.setError("Address cannot be empty");
                    return;
                }
                if (name.getText().toString().isEmpty()) {
                    name.setText("");
                    name.setFocusable(true);
                    name.setError("Name cannot be empty");
                    return;
                }
                if (password.getText().toString().length() <= 6) {
                    password.setText("");
                    password.setFocusable(true);
                    password.setError("Weak Password. Too short");
                    return;
                }
                if (!password.getText().toString().equals(confirmPassword.getText().toString())) {
                    confirmPassword.setText("");
                    confirmPassword.setFocusable(true);
                    confirmPassword.setError("Must be same as password");
                    return;
                }
                if (preExisting.getText().toString().length() == 0) {
                    user.setPreExist("None");
                }
                if (!(Patterns.EMAIL_ADDRESS.matcher(emailId.getText().toString()).matches())) {
                    emailId.setText("");
                    emailId.setFocusable(true);
                    emailId.setError("Invalid Email");
                    return;
                } else if (phone.getText().toString().length() != 10) {
                    phone.setError("Enter a valid mobile");
                    phone.setFocusable(true);
                    phone.setText("");
                    return;
                }
                user.setName(name.getText().toString().trim());
                user.setEmailId(emailId.getText().toString().trim());
                user.setPhone(phone.getText().toString().trim());
                user.setAddress(address.getText().toString().trim());
                user.setPassword(password.getText().toString());
                user.setCity(city.getText().toString().trim());
                user.setState(state.getText().toString().trim());
                user.setAge(Integer.parseInt(age.getText().toString().trim()));
                user.setPreExist(preExisting.getText().toString().trim());

                register(user);
            }
        });

    }

    private void register(User user) {
        AuthCredential credential = EmailAuthProvider.getCredential(user.getEmailId(), user.getPassword());

        mAuth.getCurrentUser().linkWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            if(task.getException() instanceof FirebaseAuthUserCollisionException){
                                Toast.makeText(RegisterActivity.this,"User Already Registered",Toast.LENGTH_LONG).show();
                            }else {
                                Log.i("Done register", task.getResult().toString());
                                saveUserInFirebase(user);
                            }
                            //    FirebaseUser user = task.getResult().getUser();
                        } else {
                            Toast.makeText(RegisterActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

    private void verifyVerificationCode(String otp) {
        //creating the credential
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationID, otp);

        //signing the user
        signInWithPhoneAuthCredential(credential);
    }

    public void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(RegisterActivity.this,"Done",Toast.LENGTH_SHORT).show();

                        }
                    }
                });

    }

    private void sendVerificationCode(String mobile) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                mobile,
                30,
                TimeUnit.SECONDS,
                TaskExecutors.MAIN_THREAD,
                mCallbacks);
    }





    private void resendVerificationCode(String phoneNumber,
                                          PhoneAuthProvider.ForceResendingToken token) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,        // Phone number to verify
                30,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks,         // OnVerificationStateChangedCallbacks
                token);             // ForceResendingToken from callbacks
    }



    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
            //Getting the code sent by SMS
            String code = phoneAuthCredential.getSmsCode();


            if (code != null) {
                OTP.setText(code);
                //verifying the code
                verifyVerificationCode(code);
            }
        }


        @Override
        public void onVerificationFailed(FirebaseException e) {
            Toast.makeText(RegisterActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            verificationID = s;
            token = forceResendingToken;

        }

    };

    private void showPictureDialog(){

        AlertDialog.Builder picDialog = new AlertDialog.Builder(this);
        picDialog.setTitle("Choose an Action");
        String[] picDialogItems = {
                "Select photo from gallery",
                "Capture photo from camera" };
        picDialog.setItems(picDialogItems, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {

                switch(which){

                    case 0:
                        SelectImage();
                        break;
                    case 1:
                        handleImageClick(idProof);
                        break;
                }
            }
        });
        picDialog.show();

    }
    public void handleImageClick(View view) {

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, TAKE_IMAGE_CODE);
        }
    }

    private void SelectImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(
                Intent.createChooser(
                        intent,
                        "Select Image from here..."),
                PICK_IMAGE_REQUEST);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == TAKE_IMAGE_CODE) {
            switch (resultCode) {
                case RESULT_OK:
                    Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                    handleUpload(bitmap);
                    dialog = new ProgressDialog(RegisterActivity.this);
                    dialog.setMessage("Uploading");
                    dialog.show();
            }
        }
        else if (requestCode == PICK_IMAGE_REQUEST
                && resultCode == RESULT_OK
                && data != null
                && data.getData() != null) {
            filePath = data.getData();
            try {
                final Bitmap bitmap = MediaStore
                        .Images
                        .Media
                        .getBitmap(
                                getContentResolver(),
                                filePath);
                handleUpload(bitmap);
                dialog = new ProgressDialog(RegisterActivity.this);
                dialog.setMessage("Uploading");
                dialog.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void handleUpload (Bitmap bitmap){

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos);
        final StorageReference reference = FirebaseStorage.getInstance().getReference()
                .child("Idproof")
                .child(name.getText().toString().trim() + phone.getText().toString().trim() + ".jpeg");

        reference.putBytes(baos.toByteArray())
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        getDownloadUrl(reference);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                    }
                });

    }



    private void getDownloadUrl(StorageReference reference) {
        reference.getDownloadUrl()
                .addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        setUserProfileUrl(uri);
                    }
                });
    }

    private void setUserProfileUrl(Uri uri) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        UserProfileChangeRequest request = new UserProfileChangeRequest.Builder()
                .setPhotoUri(uri)
                .build();

        user.updateProfile(request)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        dialog.dismiss();
                        idProof.setText("Uploaded");
                        Toast.makeText(RegisterActivity.this, "Image Uploaded Successfully", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(RegisterActivity.this, "Profile image failed...", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    void saveUserInFirebase(User user) {

        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        String uid = firebaseUser.getUid(); // This is uid of User which we have just created

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("users").document(uid).set(user)
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isComplete()) {
                            SharedPreferences sharedPreferences = getSharedPreferences("loggedInStatus", MODE_PRIVATE);
                            SharedPreferences.Editor editStatus = sharedPreferences.edit();
                            editStatus.putBoolean("loggedIn", true);
                            editStatus.apply();
                            Intent intent = new Intent(RegisterActivity.this, DrawerUser.class);
                            startActivity(intent);
                            finish();
                            Toast.makeText(RegisterActivity.this, "Registered Successfully", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(RegisterActivity.this, "Something Went Wrong !!", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}