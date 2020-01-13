package com.saavor.user.activity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Handler;
import android.provider.MediaStore;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.saavor.user.Classes.Preferences;
import com.saavor.user.Model.InsertReview;
import com.saavor.user.Model.ProfileUpdateModel;
import com.saavor.user.Model.SignupReturn;
import com.saavor.user.R;
import com.saavor.user.backend.API;
import com.saavor.user.backend.OnResultReceived;
import com.saavor.user.backend.RequestSource;
import com.saavor.user.processor.PostApiClient;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Personal_Details extends BaseActivity implements View.OnClickListener, OnResultReceived {

    private EditText mFirstname, mMi, mLastname, mPhone, countrycode;
    private Button mSave, mCancel;
    private ImageView userpic;
    int REQUEST_CODE = 0;
    boolean userupload = false;
    boolean datepick = false;
    private DatePicker datePicker;
    private Calendar calendar;
    private java.util.Date Datee;
    private TextView dateView,staticemail;
    private int Year, Month, Day;
    private ImageView pd_back;
    private String monthname;
    private Button img_upload_pic;
    private String dob = "";

    private int myear;
    private int mmonth;
    private int mday;
    public Calendar c;
    static final int DATE_DIALOG_ID = 999;
    ProgressBar progress_personaldetails;
    Handler handler;
    String profilepic = "";
    private boolean isedit=false;
    private Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_details);

        //fettching user basic information
        basicfetch();

        countrycode = (EditText) findViewById(R.id.countrycodepd);
       // countrycode.setOnClickListener(this);

        mFirstname = (EditText) findViewById(R.id.et_firstname_pd);
        mMi = (EditText) findViewById(R.id.et_mi_pd);
        mLastname = (EditText) findViewById(R.id.et_lastname_pd);
        mPhone = (EditText) findViewById(R.id.et_mobileno_pd);
        userpic = (ImageView) findViewById(R.id.img_user_pd);
        pd_back = (ImageView) findViewById(R.id.tool_back_pd);
        img_upload_pic = (Button) findViewById(R.id.img_upload_pic);
        progress_personaldetails = (ProgressBar) findViewById(R.id.progressBar_personaldetails);
        img_upload_pic.setOnClickListener(this);

        isedit= getIntent().getBooleanExtra("fromedit", false);

        mFirstname.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mFirstname.setFocusableInTouchMode(true);
                return false;
            }
        });


        mMi.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mMi.setFocusableInTouchMode(true);
                return false;
            }
        });

        mLastname.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mLastname.setFocusableInTouchMode(true);
                return false;
            }
        });

        mPhone.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mPhone.setFocusableInTouchMode(true);
                return false;
            }
        });


        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                progress_personaldetails.setVisibility(View.GONE);

            }
        }, 4000);


        dateView = (TextView) findViewById(R.id.txt_selectdate_pd);
        staticemail = (TextView) findViewById(R.id.txt_static_email);
        dateView.setOnClickListener(this);
        calendar = Calendar.getInstance();

        Year = calendar.get(Calendar.YEAR);
        Month = calendar.get(Calendar.MONTH);
        Day = calendar.get(Calendar.DAY_OF_MONTH);


        mOnResultReceived = this;

        mSave = (Button) findViewById(R.id.btn_save_pd);
        mCancel = (Button) findViewById(R.id.btn_cancel_pd);

        mSave.setOnClickListener(this);
        mCancel.setOnClickListener(this);
        pd_back.setOnClickListener(this);

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


        try {

            String fname = "";
            String lname = "";
            String mi = "";
            String mobileno = "";

            fname = basicInformation.getFirstName().toString();
            lname = basicInformation.getLastName().toString();
            mi = basicInformation.getMI().toString();
            mobileno = basicInformation.getMobileNumber().toString();
            dob = basicInformation.getDateOfBirth().toString();
            staticemail.setText(basicInformation.getEmail().toString());

            profilepic = basicInformation.getUserprofile().toString();

            //setting profileimage
            if (profilepic.isEmpty() || profilepic == "" || profilepic == null) {

            } else {
//                String d = "http://saavorapi.parkeee.net/" + profilepic;
//
//                Glide.with(Personal_Details.this).load(Uri.parse(d)).error(R.drawable.usericonpd).into(new SimpleTarget<GlideDrawable>() {
//                    @Override
//                    public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
//                        userpic.setImageDrawable(resource);
//                    }
//                });


                try {

                    String internetUrl = "http://saavorapi.parkeee.net/" + profilepic;

                    Glide.with(this).load(internetUrl).listener(new RequestListener<String, GlideDrawable>() {
                        @Override
                        public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                            progress_personaldetails.setVisibility(View.GONE);
                            userpic.setImageResource(R.drawable.usericonpd);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                            progress_personaldetails.setVisibility(View.GONE);
                            return false;
                        }
                    })
                            .into(userpic);


                } catch (Exception e) {
                    progress_personaldetails.setVisibility(View.GONE);
                    userpic.setImageResource(R.drawable.usericonpd);
                }


                userupload = true;
            }





            //setting first name
            if (fname.isEmpty() || fname == "" || fname == null) {

            } else {
                mFirstname.setText(fname);
            }


            //setting last name
            if (lname.isEmpty() || lname == "" || lname == null) {

            } else {
                mLastname.setText(lname);
            }

            //setting mi

            if (mi.isEmpty() || mi == "" || mi == null) {

            } else {
                mMi.setText(fname);
            }

            //setting mobile no
            if (mobileno.isEmpty() || mobileno == "" || mobileno == null) {

            } else
                {
                String filename = mobileno.split(" ")[0];
                String extension = mobileno.split(" ")[1];
                String detention = mobileno.split(" ")[2];

                    countrycode.setText(""+filename);

                mPhone.setText(extension+" "+detention);
            }


            //setting dob
            String myString = dob;

            if (myString.equals("")) {

            } else {
                if (myString.contains("-")) {
                    String month = "", date = "", year = "";

                    String[] aSplit = myString.split("-");

                    month = aSplit[0];
                    date = aSplit[1];
                    year = aSplit[2];

                    profileUpdateModel.setDateOfBirth(month + " " + date + "," + year);

                    dateView.setText(month + " " + date + ", " + year);

                } else {
                    dateView.setText("" + dob);
                }
            }


        } catch (Exception e) {
            System.out.println(">>>>." + e);
        }


    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.btn_save_pd:

                try {
                    InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                if (mFirstname.getText().toString().equals("")) {
                    displayAlert(this, "First name Can't be blank");
                } else if (mLastname.getText().toString().equals("")) {
                    displayAlert(this, "Last name Can't be blank");
                } else if (mPhone.getText().toString().equals("")) {
                    displayAlert(this, "Mobile Number Can't be blank");
                } else if ((mPhone.getText().length() < 14)) {
                    displayAlert(this, "Mobile Number must be 10 digit");
                } else {
                    load_dialog.show();
                    profileUpdateModel.setFirstName(mFirstname.getText().toString());
                    profileUpdateModel.setLastName(mLastname.getText().toString());

                    Log.i("Saavor", "dob " + dob);
                    if (dateView.getText().toString().isEmpty() || dateView.getText().toString() == "" || dateView.getText().toString() == null || dateView.getText().toString().equals("Select Date")) {
                        profileUpdateModel.setDateOfBirth("");
                    } else {
                        profileUpdateModel.setDateOfBirth(dateView.getText().toString());
                    }

                    if (!userupload) {
                        profileUpdateModel.setImageBase64String("");
                    } else {
                        profileUpdateModel.setImageBase64String(pdibase64);
                    }
                    profileUpdateModel.setLatitude("31.0182739");
                    profileUpdateModel.setLongitude("75.401169");
                    profileUpdateModel.setMI(mMi.getText().toString());
                    profileUpdateModel.setModifyDate(date_format);

                    String mobile = mPhone.getText().toString().replace("(", "");
                    mobile = mobile.replace(")", "");
                    mobile = mobile.replace(" ", "");
                    mobile = mobile.replace("-", "");
                    mobile = countrycode.getText().toString() + "-" + mobile;

                    profileUpdateModel.setMobileNumber(countrycode.getText().toString() + " " + mPhone.getText().toString());
                    profileUpdateModel.setUserId(basicInformation.getUserId().toString());
                    profileUpdateModel.setSessionToken(basicInformation.getSessionToken().toString());

                    String jsonString = gson.toJson(profileUpdateModel, ProfileUpdateModel.class).toString();
                    PostApiClient oChangePsswordApi = new PostApiClient(mOnResultReceived);
                    oChangePsswordApi.executePostRequest(API.pupdate(), jsonString);
                }


                break;

            case R.id.btn_cancel_pd:

                try {
                    InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                finish();

                break;

            case R.id.txt_selectdate_pd:

                try {
                    InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //showDialog(999);
                showDialog(DATE_DIALOG_ID);

                break;


            case R.id.img_upload_pic:

                try {
                    InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                } catch (Exception e) {
                    e.printStackTrace();
                }


                showchooser();

                break;

            case R.id.tool_back_pd:

                try {
                    InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                finish();

                break;

            case R.id.countrycodepd:

                Intent intent1 = new Intent(Personal_Details.this, CountrylistActivity.class);
                intent1.putExtra("class_name", "Personal_Details");
                startActivity(intent1);
                break;


        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Preferences.readString(Personal_Details.this, Preferences.personaldetails_cc, "1").equals(""))
            countrycode.setText("+1");
        else
            countrycode.setText("+" + Preferences.readString(Personal_Details.this, Preferences.personaldetails_cc, "1"));
    }

//    public void openDialog() {
//        AlertDialog.Builder builder = new AlertDialog.Builder(Personal_Details.this);
//        builder.setTitle("Choose Profile Picture")
//                .setItems(R.array.option, new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int which) {
//                        // The 'which' argument contains the index position
//                        // of the selected item
//                        switch (which) {
//                            case 0: {
//                                System.out.println("CAMERA >>>>>> ");
//                                Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                                startActivityForResult(i, REQUEST_CODE);
//
//                                break;
//                            }
//
//                            case 1: {
//                                Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                                startActivityForResult(intent, 2);
//                                break;
//                            }
//                        }
//                    }
//                });
//        builder.setCancelable(true);
//        builder.show();
//    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == this.RESULT_OK) {
            if (requestCode == REQUEST_CODE && null != data) {

                Bundle extras = data.getExtras();
                Bitmap imageBitmap = (Bitmap) extras.get("data");
                final int THUMBSIZE = 100;
                Bitmap ThumbImage = ThumbnailUtils.extractThumbnail(imageBitmap, THUMBSIZE, THUMBSIZE);
                userpic.setImageBitmap(ThumbImage);
                pdibase64 = encodeTobase64(ThumbImage);
                userupload = true;
            } else if (requestCode == 2) {
                Uri selectedImage = data.getData();
                String picturePath = getRealPathFromURI(selectedImage);

                final int THUMBSIZE = 100;
                Bitmap ThumbImage = ThumbnailUtils.extractThumbnail(BitmapFactory.decodeFile(picturePath), THUMBSIZE, THUMBSIZE);

                userpic.setImageBitmap(ThumbImage);
                pdibase64 = encodeTobase64(ThumbImage);
                userupload = true;

            } else {

            }
        }
    }

    private String getRealPathFromURI(Uri contentURI) {
        String result;
        Cursor cursor = getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) { // Source is Dropbox or other similar local file path
            result = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }
        return result;
    }


    // method for bitmap to base64
    public static String encodeTobase64(Bitmap image) {
        Bitmap immage = image;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        immage.compress(Bitmap.CompressFormat.PNG, 40, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        String imageEncoded = Base64.encodeToString(byteArray, Base64.DEFAULT);

        Log.d("Image Log:", imageEncoded);
        return imageEncoded;

    }

    // method for base64 to bitmap
    public static Bitmap decodeBase64(String input) {
        byte[] decodedByte = Base64.decode(input, 0);
        return BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_DIALOG_ID:
                // set date picker as current date

                setCurrentDateOnView();

                DatePickerDialog _date =   new DatePickerDialog(this, datePickerListener, myear,mmonth, mday)
                {
                    @Override
                    public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth)
                    {

                        Calendar cal = Calendar.getInstance();
                        cal.add(Calendar.YEAR, -3);
                        System.out.println("Date = "+ cal.getTime());

                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        Date date1 = null;

                        try {
                            date1 = sdf.parse(""+year+"-"+(monthOfYear+1)+"-"+dayOfMonth);
                        } catch (java.text.ParseException e) {
                            e.printStackTrace();
                        }

                        if (year > myear)
                        {
                            view.updateDate(myear, mmonth, mday);
                            Toast.makeText(getApplicationContext(), "Choose a valid date", Toast.LENGTH_LONG).show();
                        }

                        if (monthOfYear > mmonth && year == myear)
                        {
                            view.updateDate(myear, mmonth, mday);
                            Toast.makeText(getApplicationContext(), "Choose a valid date", Toast.LENGTH_LONG).show();
                        }

                        if (dayOfMonth > mday && year == myear && monthOfYear == mmonth)
                        {
                            view.updateDate(myear, mmonth, mday);
                            Toast.makeText(getApplicationContext(), "Choose a valid date", Toast.LENGTH_LONG).show();
                        }

                        if (date1.after(cal.getTime())) {
                            System.out.println("Date1 is after Date2");
                            view.updateDate(myear, mmonth, mday);
                            Toast.makeText(getApplicationContext(), "Choose a valid date", Toast.LENGTH_LONG).show();
                        }

                    }
                };

                _date.show();
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {

        // when dialog box is closed, below method will be called.
        public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay)
        {
            myear = selectedYear;
            mmonth = selectedMonth;
            mday = selectedDay;

            switch (mmonth+1) {
            case 1:
                monthname = "Jan";

                break;

            case 2:
                monthname = "Feb";

                break;

            case 3:
                monthname = "Mar";

                break;

            case 4:
                monthname = "Apr";

                break;

            case 5:
                monthname = "May";

                break;

            case 6:
                monthname = "Jun";

                break;

            case 7:
                monthname = "Jul";

                break;

            case 8:
                monthname = "Aug";

                break;

            case 9:
                monthname = "Sep";
                break;

            case 10:
                monthname = "Oct";
                break;

            case 11:
                monthname = "Nov";
                break;

            case 12:
                monthname = "Dec";

                break;
        }
        dob = monthname + " " + mday + ", " + myear;
        dateView.setText(dob);
        datepick = true;
        }
    };


//    @Override
//    protected Dialog onCreateDialog(int id) {
//        // TODO Auto-generated method stub
//        if (id == 999) {
//            return new DatePickerDialog(this,
//                    myDateListener, Year, Month, Day);
//        }
//        return null;
//    }
//
//    private DatePickerDialog.OnDateSetListener myDateListener = new
//            DatePickerDialog.OnDateSetListener() {
//                @Override
//                public void onDateSet(DatePicker arg0,
//                                      int arg1, int arg2, int arg3) {
//                    // TODO Auto-generated method stub
//                    // arg1 = year
//                    // arg2 = month
//                    // arg3 = day
//                    showDate(arg1, arg2 + 1, arg3);
//                }
//            };
//
//    private void showDate(int year, int month, int day) {
//
//        //  String monthn = calendar.getDisplayName(month, Calendar.LONG, Locale.getDefault());
//
//        switch (month) {
//            case 1:
//                monthname = "Jan";
//
//                break;
//
//            case 2:
//                monthname = "Feb";
//
//                break;
//
//            case 3:
//                monthname = "Mar";
//
//                break;
//
//            case 4:
//                monthname = "Apr";
//
//                break;
//
//            case 5:
//                monthname = "May";
//
//                break;
//
//            case 6:
//                monthname = "Jun";
//
//                break;
//
//            case 7:
//                monthname = "Jul";
//
//                break;
//
//            case 8:
//                monthname = "Aug";
//
//                break;
//
//            case 9:
//                monthname = "Sep";
//                break;
//
//            case 10:
//                monthname = "Oct";
//                break;
//
//            case 11:
//                monthname = "Nov";
//                break;
//
//            case 12:
//                monthname = "Dec";
//
//                break;
//        }
//        dob = monthname + " " + day + ", " + year;
//        dateView.setText(dob);
//        datepick = true;


    @Override
    public void dispatchString(RequestSource from, String what) {


        if (what.equals("-3")) {

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    load_dialog.cancel();
                    displayAlert(Personal_Details.this, "Sorry! The process failed due to some technical error. Please try after some time.");
                }});
        }
        else
        {
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

                        load_dialog.cancel();
                        //saving basic information
                        savingbasic();

                        try {
                            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        submitdiag();

                    }
                });
            } else if (check.equals("0")) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        load_dialog.cancel();
                        displayAlert(Personal_Details.this, message);

                    }
                });


            } else if (check.equals("-1")) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        load_dialog.cancel();
                        displayAlert(Personal_Details.this, message);
                    }
                });
            }

            else if (check.equals("5")) {
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
                  //  redirect(Personal_Details.this, "No internet access. Please turn on cellular data or use wifi.");
                    nointernet();
                }
            });
        }

        Log.e("response", "" + what);
    }}


    public void savingbasic() {
        basicInformation.setFirstName(profileUpdateModel.getFirstName().toString());
        basicInformation.setLastName(profileUpdateModel.getLastName().toString());
        basicInformation.setMobileNumber(profileUpdateModel.getMobileNumber().toString());
        basicInformation.setDateOfBirth(profileUpdateModel.getDateOfBirth().toString());
        basicInformation.setMI(profileUpdateModel.getMI().toString());

        if(signupReturn.getProfilePicPath().toString().equals(""))
        {
            basicInformation.setUserprofile(profilepic);
        }
        else
        {
            basicInformation.setUserprofile(signupReturn.getProfilePicPath().toString());
        }



        mBasicInformation = gson.toJson(basicInformation);
        mTabel.putString("basicinformation", mBasicInformation);
        mTabel.commit();
    }


    @Override
    public void onBackPressed() {
        finish();
    }

    public void setCurrentDateOnView()
    {
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        } catch (Exception e) {
            e.printStackTrace();
        }

        c = Calendar.getInstance();
        c.add(Calendar.YEAR, -3);
        myear = c.get(c.YEAR);
        mmonth = c.get(c.MONTH);
        mday = c.get(c.DAY_OF_MONTH);
    }

    public void showchooser()
    {
        //dialog intialization
        dialog = new Dialog(Personal_Details.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.choose_c_g);
        dialog.setCancelable(true);

        LinearLayout camera_choose=(LinearLayout)dialog.findViewById(R.id.camera_picker);
        LinearLayout gallery_choose=(LinearLayout)dialog.findViewById(R.id.gallery_picker);
        ImageView cnacel=(ImageView) dialog.findViewById(R.id.chooser_clear);

        camera_choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("CAMERA >>>>>> ");
                Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(i, REQUEST_CODE);
                dialog.dismiss();
            }
        });

        gallery_choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 2);
                dialog.dismiss();
            }
        });

        cnacel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               dialog.dismiss();
            }
        });

        dialog.show();
}

    public void submitdiag()
    {
        //dialog intialization
        dialog = new Dialog(Personal_Details.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.submit_diag);
        dialog.setCancelable(false);

        Button okplaced = (Button) dialog.findViewById(R.id.ok_submit);

        okplaced.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                dialog.cancel();

                intent = new Intent(Personal_Details.this, Account.class);
                Personal_Details.this.startActivity(intent);

            }
        });

        dialog.show();
    }


    public void nointernet()
    {
        //dialog intialization
        dialog = new Dialog(Personal_Details.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.no_internet);
        dialog.setCancelable(false);

        Button settings=(Button)dialog.findViewById(R.id.not_settings);
        Button retry=(Button)dialog.findViewById(R.id.not_retry);

        retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
                try {
                    InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                if (mFirstname.getText().toString().equals("")) {
                    displayAlert(Personal_Details.this, "First name Can't be blank");
                } else if (mLastname.getText().toString().equals("")) {
                    displayAlert(Personal_Details.this, "Last name Can't be blank");
                } else if (mPhone.getText().toString().equals("")) {
                    displayAlert(Personal_Details.this, "Mobile Number Can't be blank");
                } else if ((mPhone.getText().length() < 14)) {
                    displayAlert(Personal_Details.this, "Mobile Number must be 10 digit");
                } else {
                    load_dialog.show();
                    profileUpdateModel.setFirstName(mFirstname.getText().toString());
                    profileUpdateModel.setLastName(mLastname.getText().toString());

                    Log.i("Saavor", "dob " + dob);
                    if (dateView.getText().toString().isEmpty() || dateView.getText().toString() == "" || dateView.getText().toString() == null || dateView.getText().toString().equals("Select Date")) {
                        profileUpdateModel.setDateOfBirth("");
                    } else {
                        profileUpdateModel.setDateOfBirth(dateView.getText().toString());
                    }

                    if (!userupload) {
                        profileUpdateModel.setImageBase64String("");
                    } else {
                        profileUpdateModel.setImageBase64String(pdibase64);
                    }
                    profileUpdateModel.setLatitude("31.0182739");
                    profileUpdateModel.setLongitude("75.401169");
                    profileUpdateModel.setMI(mMi.getText().toString());
                    profileUpdateModel.setModifyDate(date_format);

                    String mobile = mPhone.getText().toString().replace("(", "");
                    mobile = mobile.replace(")", "");
                    mobile = mobile.replace(" ", "");
                    mobile = mobile.replace("-", "");
                    mobile = countrycode.getText().toString() + "-" + mobile;

                    profileUpdateModel.setMobileNumber(countrycode.getText().toString() + " " + mPhone.getText().toString());
                    profileUpdateModel.setUserId(basicInformation.getUserId().toString());
                    profileUpdateModel.setSessionToken(basicInformation.getSessionToken().toString());

                    String jsonString = gson.toJson(profileUpdateModel, ProfileUpdateModel.class).toString();
                    PostApiClient oChangePsswordApi = new PostApiClient(mOnResultReceived);
                    oChangePsswordApi.executePostRequest(API.pupdate(), jsonString);
                }


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


}
