package com.saavor.user.activity;


import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.HttpMethod;
import com.saavor.user.Model.SignUpData;
import com.saavor.user.processor.GetApiClient;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.appevents.AppEventsLogger;
import com.saavor.user.Model.LoginData;
import com.saavor.user.Model.LoginDataReturn;
import com.saavor.user.R;
import com.saavor.user.backend.API;
import com.saavor.user.backend.OnResultReceived;
import com.saavor.user.backend.RequestSource;
import com.saavor.user.processor.PostApiClient;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.text.DecimalFormat;
import java.util.Arrays;

import static com.saavor.user.activity.DashBoard.notcount;

/**
 * A login screen that offers login via email/password.
 */
public class Login extends BaseActivity implements OnClickListener, OnResultReceived, GoogleApiClient.OnConnectionFailedListener {

    private EditText mEmailView;
    private EditText mPassword;
    private Gson gson;
    private ImageView mBack, mValidemail;
    private LoginData loginData;
    public OnResultReceived mOnResultReceived;
    private TextView forgot;
    private static final String TAG = MainActivity.class.getSimpleName();
    private int mFacerequestcode = 64206;
    private ImageView mFacebookLogin, mGoogleLogin;
    private GoogleApiClient mGoogleApiClient;
    private static final int RC_SIGN_IN = 007;
    CallbackManager callbackManager;
    String facebook_email, facebook_id, facebook_name, facebook_url, facebook_gender;
    int click = 0;
    GoogleSignInAccount acct;
    String email_g_login, id_g_login, name_g_login;
    String fb_firstname = "", fb_lastname = "", g_firstname = "", g_lastname = "";
    String token;
    public double latitude=0;
    public double longitude=0;
    boolean term = false;
    private Spinner SP_countrycode;
    private Boolean diagloc = true;
    // flag for GPS Status
    boolean isGPSEnabled = false;
    Handler handler;
    private LocationManager locationManager;
    private Location location ,location2;
    SignUpData signUpData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(Login.this.getApplicationContext());
        AppEventsLogger.activateApp(this);
        setContentView(R.layout.activity_login);

        /////////////////////////////......................gmail...............//////////////////
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(Login.this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        //saving default list
        defaultfilter();

        notcount=0;

        getLoc();

        // Set up the login form.
        mEmailView = (EditText) findViewById(R.id.email);
        mPassword = (EditText) findViewById(R.id.password);
        mBack = (ImageView) findViewById(R.id.tool_back);
        forgot = (TextView) findViewById(R.id.txt_forgot);
        mFacebookLogin = (ImageView) findViewById(R.id.but_facebook_login);
        mGoogleLogin = (ImageView) findViewById(R.id.but_googl_login);
        mValidemail = (ImageView) findViewById(R.id.img_validemail);

        callbackManager = CallbackManager.Factory.create();
        mFacebookLogin.setOnClickListener(this);
        mGoogleLogin.setOnClickListener(this);
        forgot.setOnClickListener(this);
        signUpData = new SignUpData();

        if (!FirebaseApp.getApps(this).isEmpty()) {
            FirebaseMessaging.getInstance().subscribeToTopic("test");
            token = FirebaseInstanceId.getInstance().getToken();
            Log.e("Notification Token", "" + token);
        }
//        mEmailView.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//
//                mEmailView.setFocusable(true);
//                return false;
//            }
//        });
//
//        mEmailView.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//
//                mEmailView.setFocusable(true);
//                return false;
//            }
//        });

        Button loginButton = (Button) findViewById(R.id.email_sign_in_button);
        // Set the dimensions of the sign-in button.

        loginData = new LoginData();

        gson = new Gson();
        loginButton.setOnClickListener(this);
        mBack.setOnClickListener(this);
        mOnResultReceived = this;



        mEmailView.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if (!hasFocus) {
                    GetApiClient oInsertUpdateApi = new GetApiClient(mOnResultReceived);
                    oInsertUpdateApi.setRequestSource(RequestSource.validateemail);
                    oInsertUpdateApi.executeGetRequest(API.validateemail() + mEmailView.getText().toString());
                }
            }
        });

        mEmailView.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {

                if (mEmailView.getText().toString().equals("")) {
                    mValidemail.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
        });
    }

    private void attemptLogin() {

        load_dialog.show();
        String email = mEmailView.getText().toString();
        String password = mPassword.getText().toString();

        if (email.equals("")) {
            displayAlert(this, "Email can't be empty");
            load_dialog.cancel();
        } else if (!isValidEmail(email)) {
            displayAlert(this, "Enter a valid Email ID");
            load_dialog.cancel();
        } else if (password.equals("")) {
            displayAlert(this, "Password can't be empty");
            load_dialog.cancel();
        } else {
            loginData.setEmailId(email);
            loginData.setPassword(password);
            loginData.setDeviceToken("" + token);
            loginData.setDeviceType("Android");
            basicInformation.setPassword(mPassword.getText().toString());

            String jsonString = gson.toJson(loginData, LoginData.class).toString();
            PostApiClient loginApi = new PostApiClient(mOnResultReceived);
            loginApi.setRequestSource(RequestSource.loginbutton);
            loginApi.executePostRequest(API.loginApi(), jsonString);
        }


    }

    public boolean isValidEmail(String st_email) {
        if (st_email == null) {
            return false;
        } else {
            return Patterns.EMAIL_ADDRESS.matcher(st_email).matches();
        }

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.tool_back: /** Start a new Activity signup.java */
                intent = new Intent(this, MainActivity.class);
                this.startActivity(intent);
                break;

            case R.id.email_sign_in_button: /** Start a new Activity signup.java */

            getLoc();

                if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    // Toast.makeText(this, "GPS is Enabled in your devide", Toast.LENGTH_SHORT).show();

                    if(diagloc)
                    {
                        attemptLogin();
                    }
                    else
                    {
                       // Toast.makeText(Login.this, "Unable to fetch your location try again after sometime", Toast.LENGTH_LONG).show();
                        displayAlert(Login.this, "Unable to fetch your location try again after sometime");
                    }


                } else {
                    showGPSDisabledAlertToUser();
                }


                break;

            case R.id.txt_forgot: /** Start a new Activity signup.java */
                Intent intent = new Intent(Login.this, ForgotPassword.class);
                Login.this.startActivity(intent);
                break;

            case R.id.but_googl_login:

                getLoc();

                if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    // Toast.makeText(this, "GPS is Enabled in your devide", Toast.LENGTH_SHORT).show();

                    if(diagloc)
                    {
                        click = 2;
                        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
                        startActivityForResult(signInIntent, RC_SIGN_IN);

                    }
                    else
                    {
                       // Toast.makeText(Login.this, "Unable to fetch your location try again after sometime", Toast.LENGTH_LONG).show();
                        displayAlert(Login.this, "Unable to fetch your location try again after sometime");
                    }


                } else {
                    showGPSDisabledAlertToUser();
                }

                break;
            case R.id.but_facebook_login:

                getLoc();

                if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                if(diagloc)
                {

                    click = 1;
                    disconnectFromFacebook();
                    LoginManager.getInstance().logInWithReadPermissions(Login.this, Arrays.asList("user_photos", "email", "public_profile", " user_birthday"));
                    onFblogin();

                }
                else
                {
                  //  Toast.makeText(Login.this, "Unable to fetch your location try again after sometime", Toast.LENGTH_LONG).show();
                    displayAlert(Login.this, "Unable to fetch your location try again after sometime");
                }


        } else {
            showGPSDisabledAlertToUser();
        }


                break;

        }
    }

    private void onFblogin() {
        callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        System.out.println("Success");
                        GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {
                                Log.e("seeem", "" + response);
                                if (response != null) {
                                    try {
                                        Log.e("response>>>>>", "" + response);
                                        JSONObject graph = response.getJSONObject();
                                        if (graph.has("birthday") || graph.has("email")) {
                                            facebook_email = graph.getString("email");
                                            Log.e("EMAIL>>>>>", graph.getString("email"));
                                        } else {
                                        }
                                        facebook_id = graph.getString("id");
                                        facebook_name = graph.getString("name");
                                        facebook_gender = graph.getString("gender");
                                        facebook_name = graph.getString("name");
                                        Log.e("Gender>>>>>", graph.getString("gender"));
                                        //  coverPhoto = graph.optJSONObject("cover").getString("source");

                                        if (Build.VERSION.SDK_INT > 9) {
                                            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                                            StrictMode.setThreadPolicy(policy);
                                            JSONObject obh = graph.getJSONObject("picture").getJSONObject("data");
                                            URL profile_pic = new URL("https://graph.facebook.com/" + facebook_id + "/picture?");
                                            facebook_url = profile_pic.toString();
                                            Log.e("image", "" + facebook_url);

                                            String[] arr = facebook_name.split(" ");
                                            fb_firstname = arr[0];
                                            fb_lastname = arr[1];


                                            if(facebook_email == null || facebook_email.equals(""))
                                            {
                                                showCustomDialog();
                                            }
                                            else
                                            {

                                                loginData.setEmailId(facebook_email);
                                                loginData.setFacebookId(facebook_id);
                                                loginData.setPassword("");
                                                loginData.setGoogleId("");
                                                loginData.setFirstName(fb_firstname);
                                                loginData.setLastName(fb_lastname);
                                                loginData.setCreateDate(date_format);
                                                loginData.setDeviceToken("" + token);
                                                loginData.setDeviceType("Android");

                                                basicInformation.setPassword("");
                                                load_dialog.show();

                                                String jsonString = gson.toJson(loginData, LoginData.class).toString();
                                                PostApiClient loginApi = new PostApiClient(mOnResultReceived);
                                                loginApi.setRequestSource(RequestSource.loginbutton);
                                                loginApi.executePostRequest(API.loginApi(), jsonString);
                                            }

                                        }

                                    } catch (Exception e) {
                                        e.printStackTrace();

                                    }
                                }
                            }
                        });
                        Bundle parameters = new Bundle();
                        parameters.putString("fields", "id,name,email,birthday,gender,cover,picture");
                        request.setParameters(parameters);
                        request.executeAsync();
                    }

                    @Override
                    public void onCancel() {

                        Log.d("FbLoginActivty", "On cancel");
                    }

                    @Override
                    public void onError(FacebookException error) {
                        Log.d("FbLoginActivty", error.toString());
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int responseCode, Intent intent) {
        super.onActivityResult(requestCode, responseCode, intent);
        if (click == 2) {
            if (requestCode == RC_SIGN_IN) {
                GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(intent);
                handleSignInResult(result);
                Log.e("harsh", "" + result);

            }
        }
        /////////////////.............facebook.............///////
        else {
            super.onActivityResult(requestCode, responseCode, intent);
            callbackManager.onActivityResult(requestCode, responseCode, intent);
            LoginManager loginManager = LoginManager.getInstance();
            loginManager.logOut();
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d(TAG, "onConnectionFailed:" + connectionResult);
    }

    private void handleSignInResult(GoogleSignInResult result) {
        Log.d(TAG, "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess())
        { load_dialog.show();
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();

            Log.e(TAG, "display name: " + acct.getDisplayName());
            String personName = "";

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

                        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
                        startActivityForResult(signInIntent, RC_SIGN_IN);
                    }
                }, 2000);

            }
            else {

                boolean found = false;
                String search = "@";

                if (personName.toLowerCase().indexOf(search.toLowerCase()) != -1) {

                    System.out.println("I found the keyword");
                    found = true;

                } else {

                    System.out.println("not found");
                    found = false;

                }

                if (found) {
                    Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
                    startActivityForResult(signInIntent, RC_SIGN_IN);
                } else {

                    String email = acct.getEmail();
                    String firstname = "", lastname = "";
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

                    } catch (Exception e) {
                        e.printStackTrace();
                        //displayAlert(SignUp.this, "Something went wrong! Please try again!");
                    }
                    loginData.setEmailId(acct.getEmail());
                    loginData.setGoogleId(acct.getId().toString());
                    loginData.setPassword("");
                    loginData.setFacebookId("");
                    loginData.setFirstName(firstname);
                    loginData.setLastName(lastname);
                    loginData.setCreateDate(date_format);
                    loginData.setDeviceToken("" + token);
                    loginData.setDeviceType("Android");

//                    signUpData.setFirstName(firstname);
//                    signUpData.setLastName(lastname);
//                    signUpData.setFacebookId("");
//                    signUpData.setGoogleId(acct.getId().toString());
//                    signUpData.setUserId("0");
//                    signUpData.setCreateDate(date_format);
//                    signUpData.setLatitude("" + latitude);
//                    signUpData.setLongitude("" + longitude);
//                    signUpData.setMI("");
//                    signUpData.setMobileNo("");
//                    signUpData.setPassword("");
//                    signUpData.setEmail(acct.getEmail().toString());
//                    signUpData.setDeviceToken("" + token);
//                    signUpData.setDeviceType("Android");


                    basicInformation.setPassword("");

                    load_dialog.show();

                    String jsonStrings = gson.toJson(loginData, LoginData.class).toString();
                    PostApiClient loginApi = new PostApiClient(mOnResultReceived);
                    loginApi.setRequestSource(RequestSource.loginbutton);
                    loginApi.executePostRequest(API.loginApi(), jsonStrings);

//                    String jsonString = gson.toJson(signUpData, SignUpData.class).toString();
//                    PostApiClient oChangePsswordApi = new PostApiClient(mOnResultReceived);
//                    oChangePsswordApi.setRequestSource(RequestSource.loginbutton);
//                    oChangePsswordApi.executePostRequest(API.fsignup(), jsonString);
                }
            }
        } else {

        }
    }

    @Override
    public void dispatchString(RequestSource from, String what) {
        if (what.equals("-3")) {

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    load_dialog.cancel();
                    displayAlert(Login.this, "Sorry! The process failed due to some technical error. Please try after some time.");
                }
            });
        } else {

            if (from.toString().equalsIgnoreCase("loginbutton")) {

                try {

                    try {
                        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                    JSONObject Jsonobject = new JSONObject(what);
                    String jsonString = Jsonobject.toString();
                    System.out.println(">>>>" + jsonString);

                    gson = new Gson();

                    try {

                        loginDataReturn = gson.fromJson(jsonString, LoginDataReturn.class);


                    } catch (Exception e) {
                        System.out.println("" + e);
                    }

                    String check = loginDataReturn.getReturnCode();
                    System.out.println(">>>>" + check);


                    load_dialog.cancel();

                    if (check.equals("1")) {
                        savingbasiclogin();

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

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

                                try {
                                    // Toast.makeText(Login.this, "login successful" , Toast.LENGTH_LONG).show();
                                    System.out.println(">>>>>>" + loginDataReturn.getReturnMessage().toString());
                                    Intent intent = new Intent(Login.this, DashBoard.class);
                                    intent.putExtra("onetime", 1);
                                    Login.this.startActivity(intent);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    // Toast.makeText(Login.this, "" +e, Toast.LENGTH_LONG).show();
                                    System.out.println(">>>>>>" + e);
                                    System.out.println("{}{}{}{}" + e);

                                }
                            }
                        });
                    } else if (check.equals("0")) {

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                displayAlert(Login.this, "" + loginDataReturn.getReturnMessage());

                            }
                        });

                    } else if (check.equals("-1")) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                load_dialog.cancel();
                                displayAlert(Login.this, loginDataReturn.getReturnMessage());
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
                } catch (JSONException e) {
                    System.out.println("" + e);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            load_dialog.cancel();
                            nointernet();
                           // displayAlert(Login.this, "Something went wrong! Please try again!");
                        }
                    });
                }
            } else if (from.toString().equalsIgnoreCase("validateemail")) {
                try {

                    JSONObject Jsonobject = new JSONObject(what);
                    String jsonString = Jsonobject.toString();

                    gson = new Gson();
                    loginDataReturn = gson.fromJson(jsonString, LoginDataReturn.class);
                    String check = loginDataReturn.getReturnCode();

                    if (check.equals("1")) {

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                mValidemail.setVisibility(View.VISIBLE);
                                mValidemail.setImageResource(R.drawable.checkgreen);
                            }
                        });
                    } else if (check.equals("0")) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                mValidemail.setVisibility(View.VISIBLE);
                                mValidemail.setImageResource(R.drawable.redcross);

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
                        }
                    });
                }
            }

        }
    }


    public void savingbasiclogin() {

        basicInformation.setUserId(loginDataReturn.getUserInfo().getUserId().toString());
        basicInformation.setSessionToken(loginDataReturn.getUserInfo().getSessionToken().toString());
        basicInformation.setRefferal(loginDataReturn.getUserInfo().getReferralCode().toString());
        mBasicInformation = gson.toJson(basicInformation);
        mTabel.putString("basicinformation", mBasicInformation);
        mTabel.commit();
    }

    @Override
    public void onBackPressed() {
        // do nothing.
    }
    public void nointernet()
    {
        //dialog intialization
        dialog = new Dialog(Login.this);
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
            public void onClick(View view) { dialog.cancel();
                startActivityForResult(new Intent(android.provider.Settings.ACTION_SETTINGS), 0);
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

    public void getLoc() {
        try {
            locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

//            // getting GPS status
//            isGPSEnabled = locationManager
//                    .isProviderEnabled(LocationManager.GPS_PROVIDER);
//
//            // getting network status
//            isNetworkEnabled = locationManager
//                    .isProviderEnabled(LocationManager.NETWORK_PROVIDER);
//
//            if (!isGPSEnabled && !isNetworkEnabled) {
//                // no network provider is enabled
//                //showGPSDisabledAlertToUser();
//            } else {
//                //this.canGetLocation = true;
//                if (isNetworkEnabled) {
//                    // locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
//                    Log.d("Network", "Network Enabled");
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
        dialog = new Dialog(Login.this);
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
                Login.this.startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
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

    @Override
    protected void onResume() {
        super.onResume();

        try {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (!FirebaseApp.getApps(this).isEmpty()) {
            FirebaseMessaging.getInstance().subscribeToTopic("test");
            token = FirebaseInstanceId.getInstance().getToken();
            Log.e("Notification Token", "" + token);
        }

       getLoc();
    }

    public void showCustomDialog() {

        final Dialog dialog = new Dialog(Login.this);
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


                InputMethodManager inputMgr = (InputMethodManager)Login.this.getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMgr.toggleSoftInput(0, 0);
                inputMgr.showSoftInput(emailfetch, InputMethodManager.SHOW_IMPLICIT);
//                try {
//                    InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//                    inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager inputMgr = (InputMethodManager)Login.this.getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMgr.toggleSoftInput(0, 0);
                inputMgr.showSoftInput(emailfetch, InputMethodManager.SHOW_IMPLICIT);

//                try {
//                    InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//                    inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }

                String email = emailfetch.getText().toString();

                if (email.equals("")) {
                    displayAlert(Login.this, "Email can't be empty");
                } else if (!isValidEmail(email)) {
                    displayAlert(Login.this, "Enter a valid Email ID");
                }
                else
                {
                    loginData.setEmailId(email);
                    loginData.setFacebookId(facebook_id);
                    loginData.setPassword("");
                    loginData.setGoogleId("");
                    loginData.setFirstName(fb_firstname);
                    loginData.setLastName(fb_lastname);
                    loginData.setCreateDate(date_format);
                    loginData.setDeviceToken("" + token);
                    loginData.setDeviceType("Android");

                    basicInformation.setPassword("");
                    load_dialog.show();

                    String jsonString = gson.toJson(loginData, LoginData.class).toString();
                    PostApiClient loginApi = new PostApiClient(mOnResultReceived);
                    loginApi.setRequestSource(RequestSource.loginbutton);
                    loginApi.executePostRequest(API.loginApi(), jsonString);
                }
            }
        });

        dialog.show();

    }

}



