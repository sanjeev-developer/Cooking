
package com.saavor.user.Classes;

        import android.os.Bundle;
        import android.support.v4.app.DialogFragment;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.view.Window;
        import android.view.WindowManager;
        import android.widget.Button;
        import android.widget.TextView;
        import com.saavor.user.R;


public class DialogSearch extends DialogFragment  {

    Button delivery,pickup,address;
    TextView mAppId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.searchpopup, container, false);

       // getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                Window window = getDialog().getWindow();
                lp.copyFrom(window.getAttributes());
//This makes the dialog take up the full width
                lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
                window.setAttributes(lp);

//        delivery= (Button) layout.findViewById(R.id.but_search_del);
//        pickup= (Button) layout.findViewById(R.id.but_search_pick);
//        address= (Button) layout.findViewById(R.id.btn_addressbook);
//
//        delivery.setOnClickListener(this);
//        pickup.setOnClickListener(this);
//        address.setOnClickListener(this);
//
//        delivery.setBackgroundColor(getResources().getColor(R.color.darkgrey));

        return layout;
    }


//    @Override
//    public void onClick(View v)
//    {
//        switch (v.getId()) {
//
//            case R.id.but_search_del:
//                delivery.setBackgroundColor(getResources().getColor(R.color.darkgrey));
//                pickup.setBackgroundColor(getResources().getColor(R.color.buttonback));
//
//                break;
//
//            case R.id.but_search_pick:
//
//                delivery.setBackgroundColor(getResources().getColor(R.color.buttonback));
//                pickup.setBackgroundColor(getResources().getColor(R.color.darkgrey));
//
//                break;
//
//            case R.id.btn_addressbook:
//
//                Intent intent = new Intent(getActivity(), AddAddress.class);
//                this.startActivity(intent);
//                break;
//
//
//        }
}
