package com.saavor.user.activity;

import android.accounts.*;
import android.accounts.Account;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.multidex.MultiDex;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.text.style.URLSpan;
import android.util.Base64;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.HttpMethod;
import com.saavor.user.Classes.Preferences;
import com.saavor.user.Model.Facebookdata;
import com.saavor.user.Model.LoginData;
import com.saavor.user.Model.SignUpData;
import com.saavor.user.Model.SignupReturn;
import com.saavor.user.R;
import com.saavor.user.backend.API;
import com.saavor.user.backend.OnResultReceived;
import com.saavor.user.backend.RequestSource;
import com.saavor.user.processor.GetApiClient;
import com.saavor.user.processor.PostApiClient;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.FirebaseApp;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.saavor.user.activity.DashBoard.notcount;

public class SignUp extends BaseActivity implements View.OnClickListener, LocationListener, OnResultReceived, GoogleApiClient.OnConnectionFailedListener {

    ImageView mClear;
    Button mSignup_submit;
    private CheckBox mChk_agree;
    private EditText mPassword, mConfirmPassword, mFirstname, mMi, mLastname, mEmail, mPhone, countrycodeEt;
    Boolean check = false;
    SignUpData signUpData;

    private static final String TAG = MainActivity.class.getSimpleName();
    private int mFacerequestcode = 64206;
    private ImageView mFacebookLogin, mGoogleLogin;
    private GoogleApiClient mGoogleApiClient;
    private static final int RC_SIGN_IN = 007;
    private String mFirstName, mLastName;
    private CallbackManager callbackManager;
    private Facebookdata facedata;
    private FragmentManager fm = getSupportFragmentManager();
    private Button checkk;
    private LocationManager locationManager;
    private Location location ,location2;
    private String provider;
    public double latitude=0;
    public double longitude=0;
    boolean term = false;
    private Spinner SP_countrycode;
    private Boolean diagloc = true;
    // flag for GPS Status
    boolean isGPSEnabled = false;
    String countrycode = "+1";
    String token = "";
    // flag for network status
    boolean isNetworkEnabled = false;
    Handler handler;

    // flag for GPS Tracking is enabled
    boolean isGPSTrackingEnabled = false;
    private ArrayList<String> arrayForSpinner = new ArrayList<>();


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(SignUp.this.getApplicationContext());
        AppEventsLogger.activateApp(this);
        setContentView(R.layout.activity_sign_up);


        notcount=0;
        MultiDex.install(this);

//        try {
//            PackageInfo info = getPackageManager().getPackageInfo("com.saavor.user", PackageManager.GET_SIGNATURES);
//            for (Signature signature : info.signatures) {
//                MessageDigest md = MessageDigest.getInstance("SHA");
//                md.update(signature.toByteArray());
//                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
//            }
//        } catch (PackageManager.NameNotFoundException e) {
//
//        } catch (NoSuchAlgorithmException e) {
//
//        }


        checkk = (Button) findViewById(R.id.check_but);
        checkk.setOnClickListener(this);
        countrycodeEt = (EditText) findViewById(R.id.countrycodeEt);
       // countrycodeEt.setOnClickListener(this);

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        callbackManager = CallbackManager.Factory.create();
        mFirstname = (EditText) findViewById(R.id.et_firstname);
        mMi = (EditText) findViewById(R.id.et_mi);
        mLastname = (EditText) findViewById(R.id.et_lastname);
        mEmail = (EditText) findViewById(R.id.et_email);
        mPhone = (EditText) findViewById(R.id.et_mobileno);
        mPassword = (EditText) findViewById(R.id.et_password);
        mConfirmPassword = (EditText) findViewById(R.id.et_confirmpassword);
        mClear = (ImageView) findViewById(R.id.tool_up_clear);
        mChk_agree = (CheckBox) findViewById(R.id.chk_terms);
        mSignup_submit = (Button) findViewById(R.id.btn_signupsubmit);
        mFacebookLogin = (ImageView) findViewById(R.id.but_signface_login);
        mGoogleLogin = (ImageView) findViewById(R.id.but_signgoogle_login);
        signUpData = new SignUpData();
        mOnResultReceived = this;

        if (!FirebaseApp.getApps(this).isEmpty()) {
            FirebaseMessaging.getInstance().subscribeToTopic("test");
            token = FirebaseInstanceId.getInstance().getToken();
            Log.e("Notification Token", "" + token);
        }

        mPhone.addTextChangedListener(new TextWatcher() {
            //we need to know if the user is erasing or inputting some new character
            private boolean backspacingFlag = false;
            //we need to block the :afterTextChanges method to be called again after we just replaced the EditText text
            private boolean editedFlag = false;
            //we need to mark the cursor position and restore it after the edition
            private int cursorComplement;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //we store the cursor local relative to the end of the string in the EditText before the edition
                cursorComplement = s.length() - mPhone.getSelectionStart();
                //we check if the user is inputting or erasing a character
                if (count > after) {
                    backspacingFlag = true;
                } else {
                    backspacingFlag = false;
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // nothing to do here =D
            }

            @Override
            public void afterTextChanged(Editable s) {
                String string = s.toString();
                //what matters are the phone digits beneath the mask, so we always work with a raw string with only digits
                String phone = string.replaceAll("[^\\d]", "");

                //if the text was just edited, :afterTextChanged is called another time... so we need to verify the flag of edition
                //if the flag is false, this is a original user-typed entry. so we go on and do some magic
                if (!editedFlag) {

                    //we start verifying the worst case, many characters mask need to be added
                    //example: 999999999 <- 6+ digits already typed
                    // masked: (999) 999-999
                    if (phone.length() >= 6 && !backspacingFlag) {
                        //we will edit. next call on this textWatcher will be ignored
                        editedFlag = true;
                        //here is the core. we substring the raw digits and add the mask as convenient
                        String ans = "(" + phone.substring(0, 3) + ") " + phone.substring(3, 6) + "-" + phone.substring(6);
                        mPhone.setText(ans);
                        //we deliver the cursor to its original position relative to the end of the string
                        mPhone.setSelection(mPhone.getText().length() - cursorComplement);

                        //we end at the most simple case, when just one character mask is needed
                        //example: 99999 <- 3+ digits already typed
                        // masked: (999) 99
                    } else if (phone.length() >= 3 && !backspacingFlag) {
                        editedFlag = true;
                        String ans = "(" + phone.substring(0, 3) + ") " + phone.substring(3);
                        mPhone.setText(ans);
                        mPhone.setSelection(mPhone.getText().length() - cursorComplement);
                    }
                    // We just edited the field, ignoring this cicle of the watcher and getting ready for the next
                } else {
                    editedFlag = false;
                }
            }
        });

        TextView spannabletextview = (TextView) findViewById(R.id.tv_agreement);
        TextView logintxt = (TextView) findViewById(R.id.tv_already);
        logintxt.setOnClickListener(this);

        mChk_agree.setOnClickListener(this);
        mClear.setOnClickListener(this);
        mSignup_submit.setOnClickListener(this);
        mFacebookLogin.setOnClickListener(this);
        mGoogleLogin.setOnClickListener(this);


        facedata = new Facebookdata();
        facebook_login();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, SignUp.this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();


        //********* Simple Span to color text ************//

        final StyleSpan bss = new StyleSpan(android.graphics.Typeface.BOLD);
        final StyleSpan bss1 = new StyleSpan(android.graphics.Typeface.BOLD);
        final StyleSpan bss2 = new StyleSpan(android.graphics.Typeface.BOLD);// Span to make text bold

        // this is the text that we going to work on
        SpannableString text = new SpannableString("By creating an account you agree to Saavor Terms and Conditions, Privacy Policy and EULA.");

        //******** URL Span *********************//
        text.setSpan(new URLSpan("http://demohelpdesk.saavor.io/user/termsofservice"), 43, 63, 0);
        text.setSpan(new URLSpan("http://demohelpdesk.saavor.io/user/privacypolicy"), 64, 79, 1);
        text.setSpan(new URLSpan("http://demohelpdesk.saavor.io/user/eula"), 83, 88, 2);
        //******** ForegroundColor *********************//
        text.setSpan(new ForegroundColorSpan(Color.BLACK), 43, 63, 0);
        text.setSpan(new ForegroundColorSpan(Color.BLACK), 64, 79, 1);
        text.setSpan(new ForegroundColorSpan(Color.BLACK), 83, 88, 2);

        text.setSpan(bss, 43, 63, Spannable.SPAN_INCLUSIVE_INCLUSIVE); // make first 4 characters Bold
        text.setSpan(bss1, 64, 79, Spannable.SPAN_INCLUSIVE_INCLUSIVE); // make first 4 characters Bold
        text.setSpan(bss2, 83, 88, Spannable.SPAN_INCLUSIVE_INCLUSIVE); // make first 4 characters Bold
        // make our ClickableSpans and URLSpans work
        spannabletextview.setMovementMethod(LinkMovementMethod.getInstance());

        // Put the SpannableString in textview
        spannabletextview.setText(text, TextView.BufferType.SPANNABLE);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

//            case R.id.countrycodeEt:
//                Intent intent1 = new Intent(SignUp.this, CountrylistActivity.class);
//                intent1.putExtra("class_name", "SignUp");
//                startActivity(intent1);
//                break;
//

            case R.id.tool_up_clear: /** Start a new Activity signup.java */
                intent = new Intent(this, MainActivity.class);
                this.startActivity(intent);
                break;

            case R.id.but_signgoogle_login:

                if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    // Toast.makeText(this, "GPS is Enabled in your devide", Toast.LENGTH_SHORT).show();
                    signIn();

                } else {
                    showGPSDisabledAlertToUser();
                }

                break;

            case R.id.tv_already:

                intent = new Intent(this, Login.class);
                this.startActivity(intent);
                break;

            case R.id.check_but:

                if (!term) {
                    checkk.setBackgroundResource(R.drawable.selected);
                    term = true;
                } else if (term) {
                    checkk.setBackgroundResource(R.drawable.dselected);
                    term = false;
                }
                break;


            case R.id.but_signface_login:

                if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    // Toast.makeText(this, "GPS is Enabled in your devide", Toast.LENGTH_SHORT).show();
                    disconnectFromFacebook();
                    LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("user_photos", "email", "user_birthday", "public_profile"));

                } else {
                    showGPSDisabledAlertToUser();
                }

                break;

            case R.id.btn_signupsubmit:



                if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {

                    load_dialog.show();
                    // Toast.makeText(this, "GPS is Enabled in your devide", Toast.LENGTH_SHORT).show();
                    try {
                        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    if (mFirstname.getText().toString().equals("")) {
                        load_dialog.cancel();
                        displayAlert(this, "First name can't be blank");
                    } else if (mLastname.getText().toString().equals("")) {
                        load_dialog.cancel();
                        displayAlert(this, "Last name can't be blank");
                    } else if (mPhone.getText().toString().equals("")) {
                        load_dialog.cancel();
                        displayAlert(this, "Mobile Number can't be blank");
                    } else if ((mPhone.getText().length() < 14)) {
                        load_dialog.cancel();
                        displayAlert(this, "Mobile Number must be 10 digit");
                    } else if (mEmail.getText().toString().equals("")) {
                        load_dialog.cancel();
                        displayAlert(this, "Email can't be blank");
                    } else if (!isValidEmail(mEmail.getText().toString())) {
                        load_dialog.cancel();
                        displayAlert(this, "Please enter the valid email");
                    } else if (mPassword.getText().toString().equals("")) {
                        load_dialog.cancel();
                        displayAlert(this, "Password can't be blank");
                    } else if (mPassword.getText().toString().length() < 7) {
                        load_dialog.cancel();
                        displayAlert(this, "Password must be between 8-16 Character");
                    } else if (!match()) {
                        load_dialog.cancel();
                        displayAlert(this, "Password must contain at least 1 Uppercase Alphabet, 1 Lowercase Alphabet, 1 Number and 1 Special Character");
                    } else if (mConfirmPassword.getText().toString().equals("")) {
                        load_dialog.cancel();
                        displayAlert(this, "Confirm Password can't be blank");
                    } else if (!mPassword.getText().toString().equals(mConfirmPassword.getText().toString())) {
                        load_dialog.cancel();
                        displayAlert(this, "Confirm Password and Password not matched");
                    } else if (!term) {
                        load_dialog.cancel();
                        displayAlert(this, "Please accept terms & conditions first.");
                    } else {
                        signUpData.setFirstName(mFirstname.getText().toString());
                        signUpData.setLastName(mLastname.getText().toString());
                        signUpData.setFacebookId("");
                        signUpData.setGoogleId("");
                        signUpData.setUserId("0");
                        signUpData.setCreateDate(date_format);
                        signUpData.setLatitude("" + latitude);
                        signUpData.setLongitude("" + longitude);
                        signUpData.setDeviceToken("" + token);
                        signUpData.setDeviceType("Android");
                        signUpData.setMI(mMi.getText().toString());

                        // String mobile = ;
                        //   .replace("(", "")
//                    mobile = mobile.replace(")", "");
//                    mobile = mobile.replace(" ", "");
//                    mobile = mobile.replace("-", "");
                        String mobile = countrycodeEt.getText().toString() + " " + mPhone.getText().toString();

                        signUpData.setMobileNo(mobile);
                        signUpData.setPassword(mPassword.getText().toString());
                        signUpData.setEmail(mEmail.getText().toString());

                        basicInformation.setPassword(mPassword.getText().toString());
                        basicInformation.setFirstName(mFirstname.getText().toString());
                        basicInformation.setLastName(mLastname.getText().toString());
                        basicInformation.setEmail(mEmail.getText().toString());


                        if (diagloc) {
                            String jsonString = gson.toJson(signUpData, SignUpData.class).toString();
                            PostApiClient oChangePsswordApi = new PostApiClient(mOnResultReceived);
                            oChangePsswordApi.executePostRequest(API.fsignup(), jsonString);
                        } else {
                            load_dialog.cancel();
                            Toast.makeText(SignUp.this, "Unable to fetch your location try again after sometime", Toast.LENGTH_LONG).show();
                        }
                }
                break;
        }

                else {
                    showGPSDisabledAlertToUser();
                }
    }}

    public static boolean isValidEmail(String st_email) {
        if (st_email == null) {
            return false;
        } else {
            return Patterns.EMAIL_ADDRESS.matcher(st_email).matches();
        }

    }

    public boolean match() {
        String password = mPassword.getText().toString();

        //Minimum 8 and Maximum 16 characters at least 1 Uppercase Alphabet, 1 Lowercase Alphabet, 1 Number and 1 Special Character:

        Boolean retun = false;
        String myPattern = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[$@$!#%*?&])[A-Za-z\\d$@$#!%*?&]{8,16}";
        Pattern p = Pattern.compile(myPattern, Pattern.DOTALL);
        Matcher m = p.matcher(password);

        if (m.matches()) {
            Log.d("Matcher", "PATTERN MATCHES!");
            retun = true;
        } else {
            Log.d("MATCHER", "PATTERN DOES NOT MATCH!");
            retun = false;
        }

        return retun;
    }

    @Override
    public void dispatchString(RequestSource from, String what) {


       if (what.equals("-3")) {

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    load_dialog.cancel();
                    displayAlert(SignUp.this, "Sorry! The process failed due to some technical error. Please try after some time.");
                }
            });
        } else {

            try {
                JSONObject Jsonobject = new JSONObject(what);
                String jsonString = Jsonobject.toString();

                gson = new Gson();
                signupReturn = gson.fromJson(jsonString, SignupReturn.class);
                System.out.println(">>>>" + what);
                String check = signupReturn.getReturnCode();
                final String message = signupReturn.getReturnMessage();


                if (check.equals("1")) {

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            try {
                                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                                inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }


                            load_dialog.cancel();


                            SharedPreferences preferencesService =getSharedPreferences("Services", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editorService = preferencesService.edit();
                            editorService.clear();
                            editorService.commit();

                            SharedPreferences preferencesIsAppSubmit =getSharedPreferences("ChefFilters", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editorIsAppSubmit = preferencesIsAppSubmit.edit();
                            editorIsAppSubmit.clear();
                            editorIsAppSubmit.commit();


                            SharedPreferences preferencesCuisinesId =getSharedPreferences("CuisinesId", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editorCuisinesId = preferencesCuisinesId.edit();
                            editorCuisinesId.clear();
                            editorCuisinesId.commit();



                            if (signupReturn.getIsSocialAccount().equals("1")) {
                                //  Toast.makeText(SignUp.this, "" + message, Toast.LENGTH_LONG).show();

                                SharedPreferences settings = SignUp.this.getSharedPreferences("MyPREFERENCES", Context.MODE_PRIVATE);
                                settings.edit().clear().commit();
                                //saving basic information
                                savingbasic();

                                mTabel.putString("session", "true");
                                mTabel.commit();

                                Intent intent = new Intent(SignUp.this, DashBoard.class);
                                intent.putExtra("onetime", 1);
                                SignUp.this.startActivity(intent);
                            } else {
                                SharedPreferences settings = SignUp.this.getSharedPreferences("MyPREFERENCES", Context.MODE_PRIVATE);
                                settings.edit().clear().commit();

                                mTabel.putString("session", "true");
                                mTabel.commit();

                                Intent intent = new Intent(SignUp.this, Referral.class);
                                intent.putExtra("onetime", 1);
                                SignUp.this.startActivity(intent);

                                savingbasic();

                                // Toast.makeText(SignUp.this, "Login successful", Toast.LENGTH_LONG).show();
                            }

                        }
                    });
                } else if (check.equals("0")) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            load_dialog.cancel();
                            displayAlert(SignUp.this, message);

                        }
                    });

                } else if (check.equals("-1")) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            load_dialog.cancel();
                            displayAlert(SignUp.this, message);
                        }
                    });
                } else if (check.equals("5")) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            load_dialog.cancel();
                            intent = new Intent(getApplicationContext(), MainActivity.class);
                            intent.putExtra("Sessionexp", 1);
                            startActivity(intent);
                        }
                    });
                }
            } catch (Exception e) {
                System.out.println(">>>>" + e);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        load_dialog.cancel();
                      // displayAlert(SignUp.this, "Something went wrong! Please try again!");
                        nointernet();
                    }
                });
            }

            Log.e("response", "" + what);
        }
    }

    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
      //  displayAlert(SignUp.this, "Something went wrong! Please try again!");
    }

    public void facebook_login() {
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                System.out.println("onSuccess");
                String accessToken = loginResult.getAccessToken().getToken();
                Log.i("accessToken", accessToken);

                GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {

                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        Log.i("LoginActivity", response.toString());
                        // Get facebook data from login
                        String jsonString = object.toString();
                        gson = new Gson();
                        facedata = gson.fromJson(jsonString, Facebookdata.class);

                        if(facedata.getEmail() == null || facedata.getEmail().equals(""))
                        {
                            showCustomDialog();
                        }
                        else
                        {
                            load_dialog.show();
                            signUpData.setFirstName(facedata.getFirstName());
                            signUpData.setLastName(facedata.getLastName());
                            signUpData.setFacebookId(facedata.getId().toString());
                            signUpData.setGoogleId("");
                            signUpData.setUserId("0");
                            signUpData.setCreateDate(date_format);
                            signUpData.setLatitude("" + latitude);
                            signUpData.setLongitude("" + longitude);
                            signUpData.setMI(mMi.getText().toString());
                            signUpData.setMobileNo(mPhone.getText().toString());
                            signUpData.setPassword(mPassword.getText().toString());
                            signUpData.setEmail(facedata.getEmail());
                            signUpData.setDeviceToken("" + token);
                            signUpData.setDeviceType("Android");

                            basicInformation.setPassword("");
                            basicInformation.setFirstName(facedata.getFirstName());
                            basicInformation.setLastName(facedata.getLastName());
                            basicInformation.setEmail(facedata.getEmail());

                            if (diagloc) {

                                String jsonStrings = gson.toJson(signUpData, SignUpData.class).toString();
                                PostApiClient oChangePsswordApi = new PostApiClient(mOnResultReceived);
                                oChangePsswordApi.executePostRequest(API.fsignup(), jsonStrings);

                            }
                            else
                            {
                                Toast.makeText(SignUp.this, "Unable to fetch your location try again after sometime", Toast.LENGTH_LONG).show();
                                //displayAlert(CardBook.this, "" + message);
                            }

                        }



                    }
                });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id, first_name, last_name, email,gender, birthday, location"); // Par�metros que pedimos a facebook
                request.setParameters(parameters);
                request.executeAsync();
            }

            @Override
            public void onCancel() {
            }

            @Override
            public void onError(FacebookException error) {
                displayAlert(SignUp.this, "" + error.getMessage());
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        checkgps();
//        if (Preferences.readString(SignUp.this, Preferences.SIGN_UP_COUNTRY_CODE, "1").equals(""))
//            countrycodeEt.setText("+1");
//        else
//            countrycodeEt.setText("+1");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }

        if (requestCode == mFacerequestcode) {
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void handleSignInResult(GoogleSignInResult result)
    {

        Log.d(TAG, "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess())
        { load_dialog.show();
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();

            Log.e(TAG, "display name: " + acct.getDisplayName());
            String personName = "";

            result.getSignInAccount().getGivenName();
            result.getSignInAccount().getDisplayName();
            result.getSignInAccount().getAccount();

//            try {
//                if (acct.getDisplayName() == null) {
//                    personName = result.getSignInAccount().getGivenName();
//                } else {
                    personName = result.getSignInAccount().getDisplayName();
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//
//            }



                if( personName == null || personName.equals(""))
                {

                    handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            signIn();

                        }
                    }, 2000);

                }
                else
                {
                    boolean found = false;
                    String search  = "@";

                    if ( personName.toLowerCase().indexOf(search.toLowerCase()) != -1 ) {

                        System.out.println("I found the keyword");
                        found=true;

                    } else {

                        System.out.println("not found");
                        found= false;

                    }

                    if(found)
                    {
                        signIn();
                    }
                    else
                    {
                        String email = acct.getEmail();
                        String firstname="", lastname = "";
                        try {
                            String[] aSplit = personName.split(" ");

                            firstname = aSplit[0];

                            // now for the rest of string we can just append the rest
                            for (int i = 1; i < aSplit.length; i++) {
                                lastname += aSplit[i];
                                lastname += " ";
                            }

                            System.out.println("First word = [" + firstname + "]");
                            System.out.println("Rest of string = [" + lastname + "]");

                        }catch (Exception e) {
                            e.printStackTrace();
                            //displayAlert(SignUp.this, "Something went wrong! Please try again!");
                        }

                        signUpData.setFirstName(firstname);
                        signUpData.setLastName(lastname);
                        signUpData.setFacebookId("");
                        signUpData.setGoogleId(acct.getId().toString());
                        signUpData.setUserId("0");
                        signUpData.setCreateDate(date_format);
                        signUpData.setLatitude("" + latitude);
                        signUpData.setLongitude("" + longitude);
                        signUpData.setMI(mMi.getText().toString());
                        signUpData.setMobileNo(countrycode + mPhone.getText().toString());
                        signUpData.setPassword(mPassword.getText().toString());
                        signUpData.setEmail(acct.getEmail().toString());
                        signUpData.setDeviceToken("" + token);
                        signUpData.setDeviceType("Android");

                        basicInformation.setPassword("");
                        basicInformation.setFirstName(firstname);
                        basicInformation.setLastName(lastname);
                        basicInformation.setEmail(acct.getEmail().toString());

                        if (diagloc) {
                            String jsonString = gson.toJson(signUpData, SignUpData.class).toString();
                            PostApiClient oChangePsswordApi = new PostApiClient(mOnResultReceived);
                            oChangePsswordApi.executePostRequest(API.fsignup(), jsonString);
                        } else {
                            load_dialog.cancel();
                            Toast.makeText(SignUp.this, "Unable to fetch your location try again after sometime", Toast.LENGTH_LONG).show();
                        }

                        Log.e(TAG, "Name: " + personName + ", email: " + email);
                    }

                }

        } else {
            // Signed out, show unauthenticated UI.
            load_dialog.cancel();

        }
    }

    public void savingbasic() {
        basicInformation.setUserId(signupReturn.getUserId().toString());
        basicInformation.setSessionToken(signupReturn.getSessionToken().toString());
        basicInformation.setRefferal(signupReturn.getReferralCode().toString());
        mBasicInformation = gson.toJson(basicInformation);
        mTabel.putString("basicinformation", mBasicInformation);
        mTabel.commit();
    }

    @Override
    public void onBackPressed() {
        // do nothing.
    }


    @Override
    public void onLocationChanged(Location location) {
//        latitude = location.getLatitude();
//        longitude = location.getLongitude();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    public void getLoc() {
        try {
            locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

                    if (locationManager != null) {
                        location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        location2 = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

                        if (location != null) {
                            latitude = location.getLatitude();
                            longitude = location.getLongitude();

                            diagloc = true;


                        }
                        else if(location2 != null)
                        {
                            latitude = location.getLatitude();
                            longitude = location.getLongitude();

                            diagloc = true;
                        }
                        else
                        {
                            latitude=0;
                            longitude=0;
                            diagloc=false;
                        }
                    }
                }


         catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showGPSDisabledAlertToUser()
    {
        //dialog intialization
        dialog = new Dialog(SignUp.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.no_gps_layout);
        dialog.setCancelable(false);

        Button settings=(Button)dialog.findViewById(R.id.gps_settings);

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { dialog.dismiss();
                //startActivityForResult(new Intent(android.provider.Settings.ACTION_SETTINGS), 0);
                SignUp.this.startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
            }
        });

        dialog.show();
    }

    public void checkgps()
    {
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            // Toast.makeText(this, "GPS is Enabled in your devide", Toast.LENGTH_SHORT).show();
            getLoc();
            //dialog.dismiss();

        } else {
            showGPSDisabledAlertToUser();
        }
    }

    public void nointernet()
    {
        //dialog intialization
        dialog = new Dialog(SignUp.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.no_internet);
        dialog.setCancelable(false);

        Button settings=(Button)dialog.findViewById(R.id.not_settings);
        Button retry=(Button)dialog.findViewById(R.id.not_retry);
        retry.setText("Ok");

        retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { dialog.dismiss();
                SignUp.this.startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
            }
        });

        dialog.show();

    }

    public void disconnectFromFacebook() {

        if (AccessToken.getCurrentAccessToken() == null) {
            return; // already logged out
        }

        new GraphRequest(AccessToken.getCurrentAccessToken(), "/me/permissions/", null, HttpMethod.DELETE, new GraphRequest
                .Callback() {
            @Override
            public void onCompleted(GraphResponse graphResponse) {

                LoginManager.getInstance().logOut();

            }
        }).executeAsync();
    }


    public void showCustomDialog() {

        final Dialog dialog = new Dialog(SignUp.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.enter_email_diag);
        Button cancel = (Button) dialog.findViewById(R.id.cancel_fb);
        Button save = (Button) dialog.findViewById(R.id.save_fb);
        final EditText emailfetch = (EditText) dialog.findViewById(R.id.edt_email_fb);
        cancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dialog.dismiss();


                InputMethodManager inputMgr = (InputMethodManager)SignUp.this.getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMgr.toggleSoftInput(0, 0);
                inputMgr.showSoftInput(emailfetch, InputMethodManager.SHOW_IMPLICIT);

            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager inputMgr = (InputMethodManager)SignUp.this.getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMgr.toggleSoftInput(0, 0);
                inputMgr.showSoftInput(emailfetch, InputMethodManager.SHOW_IMPLICIT);


                String email = emailfetch.getText().toString();

                if (email.equals("")) {
                    displayAlert(SignUp.this, "Email can't be empty");
                } else if (!isValidEmail(email)) {
                    displayAlert(SignUp.this, "Enter a valid Email ID");
                }
                else
                {
                    load_dialog.show();
                    signUpData.setFirstName(facedata.getFirstName());
                    signUpData.setLastName(facedata.getLastName());
                    signUpData.setFacebookId(facedata.getId().toString());
                    signUpData.setGoogleId("");
                    signUpData.setUserId("0");
                    signUpData.setCreateDate(date_format);
                    signUpData.setLatitude("" + latitude);
                    signUpData.setLongitude("" + longitude);
                    signUpData.setMI(mMi.getText().toString());
                    signUpData.setMobileNo(mPhone.getText().toString());
                    signUpData.setPassword(mPassword.getText().toString());
                    signUpData.setEmail(email);
                    signUpData.setDeviceToken("" + token);
                    signUpData.setDeviceType("Android");

                    basicInformation.setPassword("");
                    basicInformation.setFirstName(facedata.getFirstName());
                    basicInformation.setLastName(facedata.getLastName());
                    basicInformation.setEmail(facedata.getEmail());

                    if (diagloc) {

                        String jsonStrings = gson.toJson(signUpData, SignUpData.class).toString();
                        PostApiClient oChangePsswordApi = new PostApiClient(mOnResultReceived);
                        oChangePsswordApi.executePostRequest(API.fsignup(), jsonStrings);

                    }
                    else
                    {
                        Toast.makeText(SignUp.this, "Unable to fetch your location try again after sometime", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        dialog.show();

    }
}