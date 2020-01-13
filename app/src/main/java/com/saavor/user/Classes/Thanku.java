//package com.saavor.user.Classes;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.support.v4.app.DialogFragment;
//import android.support.v7.widget.DefaultItemAnimator;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Button;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import com.saavor.user.Model.LoginDataReturn;
//import com.saavor.user.R;
//
//import com.saavor.user.adapter.AboutRegistrationAdapter;
//
///**
// * Created by a123456 on 24/04/17.
// */
//
//public class Thanku extends DialogFragment implements View.OnClickListener  {
//
//    Button close;
//    TextView mAppId;
//    int mApplicationID;
//
//    public void Receive(Integer appid) {
//
//        mApplicationID=appid;
//    }
//
//
//
//    @Override
//        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//            View layout = inflater.inflate(R.layout.custom_dialog_thanku, container, false);
//
//             close= (Button) layout.findViewById(R.id.thanku_close);
//             mAppId= (TextView) layout.findViewById(R.id.txt_appid);
//
//
//             System.out.println(">>>>"+mApplicationID);
//             mAppId.setText(""+mApplicationID);
//
//             close.setOnClickListener(this);
//             return layout;
//        }
//
//
//    @Override
//    public void onClick(View v) {
//        dismiss();
//        Intent intent = new Intent(getActivity(), Welcome.class);
//        getActivity().startActivity(intent);
//    }
//}
