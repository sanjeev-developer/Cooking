package com.saavor.user.activity;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.multidex.MultiDex;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.saavor.user.Model.*;
import com.saavor.user.Model.Delivery;
import com.saavor.user.R;
import com.saavor.user.adapter.FilterDefault;
import com.saavor.user.backend.OnResultReceived;
import com.google.gson.Gson;
import com.saavor.user.backend.RequestSource;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class BaseActivity extends AppCompatActivity  implements OnResultReceived{

    public SharedPreferences mDatabase;
    public SharedPreferences.Editor mTabel;
    public SharedPreferences sharedPrefs;
    public SharedPreferences.Editor editor;
    public Gson gson;
    public ImageView navpic;
    public BookmarkViewModel bookmarkViewModel;
    public String mSignupDatabase, mSignupReturn, deliverydetails;
    public String mLoginDatabase;
    public String session;
    public String mFilterdefault;
    public String mDeliveryDetails;
    public String mtimingslots;
    public String maddondata,mnoncustomize;
    public int mSocialAccStatus;
    public AddModel addModel;
    public Delivery delivery;
    public References references;
    public LoginDataReturn loginDataReturn;
    public SignupReturn signupReturn;
    public DeliveryTypeDetails deliveryTypeDetails;
    public BookmarkModel bookmarkModel;
    public SaveAll saveAll;
    public BasicInformation basicInformation;
    public DashApiHit dashApiHit;
    public String mBasicInformation;
    public Calendar calander;
    public AddressListt addressListt;
    public String udibase64, pdibase64;
    public static String Date;
    public String date_format;
    public SimpleDateFormat simpledateformat;
    public VerificationImg verificationImg;
    public ArrayList<VerificationImg> imgobject;
    public ProgressDialog mProgressDialog;
    public ArrayList<References> refobj;
    public RefrenceSend refrenceSend;
    public DrawerLayout drawer;
    public SignUpData signUpData;
    public KitchenSearch kitchenSearch;
    public DashitReturn dashitReturn;
    public FilterDefault filterDefault;
    public KitchenInfoHit kitchenInfoHit;
    public KitchenInfoReturn kitchenInfoReturn;
    public KitchenSearchReturn kitchenSearchReturn;
    public CardModel cardModel;
    public CusineHit cusineHit;
    public FavDishInsert favDishInsert;
    public CustomizeHit customizeHit;
    public CustomizeReturn customizeReturn;
    public CustomDishItem customDishItem;
    public FavouriteReturn favouriteReturn;
    public RefferalModel refferalModel;
    public MenuCartModel menuCartModel;
    public Bestoffermodel bestoffermodel;
    public BasicKitchenInfo basicKitInfo;
    public BestOfferNodelReturn bestOfferNodelReturn;
    public Intent intent;
    public TodayReturn todayReturn;
    public ProfileUpdateModel profileUpdateModel;
    public TodayMenuHit todayMenuHit;
    public ChangePasswordModel changePasswordModel;
    public OnResultReceived mOnResultReceived;
    public LinearLayout navigationlayout;
    public FeedbackModel feedbackModel;
    public Dialog dialog;
    public Dialog load_dialog;
    public static Dialog ulyx_rating;
    ProgressBar progressBar;
    public ArrayList<String> homeaddress = new ArrayList<String>();
    public ArrayList<String> workaddress = new ArrayList<String>();
    public TextView navhome, navbook, navfavdish, navorder, navrefferal, navfreemeal, navbadges, navacc, navnotif,
            navaddress, navpayment, navfaq, navfeedback, navrate, navabout, navlogout, navusername, navnotcount,txt_nav_BookingHistory;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MultiDex.install(this);

        mDatabase = getSharedPreferences("MyPREFERENCES", Context.MODE_PRIVATE);
        mTabel = mDatabase.edit();
        gson = new Gson();

        //intiliazing shared
        sharedPrefs = getSharedPreferences("ADDPREFERENCES", Context.MODE_PRIVATE);
        editor = sharedPrefs.edit();

        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setCancelable(false);
        mProgressDialog.setMessage("Please wait ...");


        load_dialog = new Dialog(BaseActivity.this);
        load_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        load_dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        load_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        load_dialog.setCancelable(false);
        load_dialog.setContentView(R.layout.loading_dialog);

        dashApiHit = new DashApiHit();
        filterDefault= new FilterDefault();
        bookmarkModel = new BookmarkModel();
        bookmarkViewModel = new BookmarkViewModel();
        addressListt = new AddressListt();
        feedbackModel = new FeedbackModel();
        profileUpdateModel = new ProfileUpdateModel();
        cardModel = new CardModel();
        refferalModel = new RefferalModel();
        addModel = new AddModel();
        changePasswordModel = new ChangePasswordModel();
        signUpData = new SignUpData();
        delivery = new Delivery();
        references = new References();
        loginDataReturn = new LoginDataReturn();
        basicInformation = new BasicInformation();
        signupReturn = new SignupReturn();
        saveAll = new SaveAll();
        verificationImg = new VerificationImg();
        imgobject = new ArrayList<VerificationImg>();
        refobj = new ArrayList<References>();
        refrenceSend = new RefrenceSend();
        kitchenSearch = new KitchenSearch();
        kitchenSearchReturn= new KitchenSearchReturn();
        dashitReturn = new DashitReturn();
        bestoffermodel=new Bestoffermodel();
        bestOfferNodelReturn=new BestOfferNodelReturn();
        cusineHit=new CusineHit();
        kitchenInfoHit=new KitchenInfoHit();
        kitchenInfoReturn = new KitchenInfoReturn();
        deliveryTypeDetails= new DeliveryTypeDetails();
        todayMenuHit=new TodayMenuHit();
        todayReturn= new TodayReturn();
        customizeHit=new CustomizeHit();
        customizeReturn=new CustomizeReturn();
        favDishInsert = new FavDishInsert();
        customDishItem= new CustomDishItem();
        favouriteReturn= new FavouriteReturn();
      //  addToCartSave=new AddToCartSave();
        menuCartModel=new MenuCartModel();
        basicKitInfo=new BasicKitchenInfo();

        //getting date
        calander = Calendar.getInstance();
        simpledateformat = new SimpleDateFormat("MMM dd,yyyy hh:mm aa", Locale.ENGLISH);
        Date = simpledateformat.format(calander.getTime());
        Log.e("dateeeee", "" + Date);
        date_format = Date.replace("a.m", "AM").replace("p.m", "PM");
        if (date_format.contains(".")) {
            date_format = date_format.substring(0, date_format.length() - 1);
        }

        Log.e("string", "" + date_format);


        try {
            mLoginDatabase = mDatabase.getString("LoginDatabase", "");
            loginDataReturn = gson.fromJson(mLoginDatabase, LoginDataReturn.class);
        } catch (Exception e) {

        }
    }

    public  void displayAlert(Context mContext, String strMessage) {

        //dialog intialization
        dialog = new Dialog(mContext);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.alert_diag);

        Button okplaced = (Button) dialog.findViewById(R.id.ok_alert);
        TextView alertext= (TextView) dialog.findViewById(R.id.text_alert);

        alertext.setText(strMessage);

        okplaced.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                dialog.cancel();

            }
        });

        dialog.show();
    }

//    public  void showdialog(Context mContext) {
//
//        //dialog intialization
//        load_dialog = new Dialog(BaseActivity.this);
//        load_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        load_dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
//        load_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        load_dialog.setContentView(R.layout.loading_dialog);
//        load_dialog.show();
//        load_dialog.cancel();
//    }


    public void redirect(Context mContext, String strMessage) {

        AlertDialog.Builder mDialog = new AlertDialog.Builder(mContext);
        mDialog.setTitle("Oh dear!");
        mDialog.setMessage(strMessage)
                .setCancelable(false)
                .setPositiveButton("Settings", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();
                                startActivityForResult(new Intent(android.provider.Settings.ACTION_SETTINGS), 0);

                            }
                        }).show();

    }

    public void basicfetch() {
        mBasicInformation = mDatabase.getString("basicinformation", "");
        basicInformation = gson.fromJson(mBasicInformation, BasicInformation.class);
    }

    public void delievryfetch() {
        deliverydetails = mDatabase.getString("deliverydetails", "");
        deliveryTypeDetails = gson.fromJson(deliverydetails, DeliveryTypeDetails.class);
    }


    public void navintial() {

        navigationlayout = (LinearLayout) findViewById(R.id.ll_nav_view);
        navpic = (ImageView) navigationlayout.findViewById(R.id.img_user_nav);
        navusername = (TextView) navigationlayout.findViewById(R.id.txt_nav_username);
        navhome = (TextView) navigationlayout.findViewById(R.id.txt_nav_home);
        navbook = (TextView) navigationlayout.findViewById(R.id.txt_nav_bookmark);
        navfavdish = (TextView) navigationlayout.findViewById(R.id.txt_nav_dishes);
        navorder = (TextView) navigationlayout.findViewById(R.id.txt_nav_order);
        navrefferal = (TextView) navigationlayout.findViewById(R.id.txt_nav_refferal);
        //navfreemeal = (TextView) navigationlayout.findViewById(R.id.txt_nav_meal);
        navbadges = (TextView) navigationlayout.findViewById(R.id.txt_nav_badges);
        navacc = (TextView) navigationlayout.findViewById(R.id.txt_nav_acc);
        navnotif = (TextView) navigationlayout.findViewById(R.id.txt_nav_notifi);
        navaddress = (TextView) navigationlayout.findViewById(R.id.txt_nav_address);
        navpayment = (TextView) navigationlayout.findViewById(R.id.txt_nav_payment);
        navfaq = (TextView) navigationlayout.findViewById(R.id.txt_nav_faq);
        navrate = (TextView) navigationlayout.findViewById(R.id.txt_nav_rate);
        navabout = (TextView) navigationlayout.findViewById(R.id.txt_nav_about);
        navfeedback = (TextView) navigationlayout.findViewById(R.id.txt_nav_feed);
        navlogout = (TextView) navigationlayout.findViewById(R.id.txt_nav_logout);
        navnotcount = (TextView) navigationlayout.findViewById(R.id.txt_notcount);
        txt_nav_BookingHistory = (TextView) navigationlayout.findViewById(R.id.txt_nav_BookingHistory);
    }

    public void settingdatanav() {
        try {
            String name = basicInformation.getFirstName().toString();
            String cap = name.substring(0, 1).toUpperCase() + name.substring(1);

            String lname = basicInformation.getLastName().toString();
            String lcap = lname.substring(0, 1).toUpperCase() + lname.substring(1);

            navusername.setText(cap + " " + lcap);
            System.out.println("new set text: " + cap + " " + lcap);

        } catch (Exception e) {

        }
        try {

            String internetUrl = "http://saavorapi.parkeee.net/" + basicInformation.getUserprofile().toString();

            Glide.with(getApplicationContext()).load(internetUrl).error( R.drawable.usericonpd ).into(navpic);

        } catch (Exception e) {
            navpic.setImageResource(R.drawable.usericonpd);
        }
    }

    public void defaultfilter()
    {
        KitchenSearch kitchenSearch= new KitchenSearch();
        kitchenSearch.setCostForOne(""+0);
        kitchenSearch.setDeliveryFrom(""+0);
        kitchenSearch.setDeliveryTo(""+60);
        kitchenSearch.setDistance(""+5);
        kitchenSearch.setStartIndex(""+0);
        kitchenSearch.setEndIndex(""+4);
        kitchenSearch.setIsBookMarked(""+0);
        kitchenSearch.setIsDiscount(""+0);
        kitchenSearch.setIsVegetarian(""+0);
        kitchenSearch.setMinimumOrder(""+0);
        kitchenSearch.setServiceType("");
        kitchenSearch.setKitchenType("");
        kitchenSearch.setSortBy("Distance");
        kitchenSearch.setDeliveryDate(date_format);
        kitchenSearch.setCuisineList("");

        mFilterdefault = gson.toJson(kitchenSearch);
        mTabel.putString("defaultfilter", mFilterdefault);
        mTabel.commit();
    }

    public static void hideSoftKeyboard(Activity activity) {



        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(
                activity.getCurrentFocus().getWindowToken(), 0);
    }


//    public static void show_ulyx_rating(Context context)
//    {
//        Intent intent = new Intent(context, Ulyx_Rating.class);
//        context.startActivity(intent);
//    }

    @Override
    public void dispatchString(RequestSource from, String what) {

    }
}
