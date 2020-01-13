package com.saavor.user.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chauthai.swipereveallayout.SwipeRevealLayout;
import com.chauthai.swipereveallayout.ViewBinderHelper;
import com.saavor.user.Model.CardList;
import com.saavor.user.R;
import com.saavor.user.activity.CardBook;
import com.saavor.user.activity.CompletePayment;

import java.util.ArrayList;


    public class CardBookAdapter extends RecyclerView.Adapter<com.saavor.user.adapter.CardBookAdapter.MyViewHolder> {

        private ArrayList<CardList> cardLists;
        private Activity context;
        int count = 0 ;
        private final ViewBinderHelper viewBinderHelper = new ViewBinderHelper();
        Dialog dialog;

        public CardBookAdapter(Activity contexts, ArrayList<CardList> cardtype)
        {
            this.cardLists=cardtype;
            this.context=contexts;
        }

        @Override
        public com.saavor.user.adapter.CardBookAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardlistlayout, parent, false);
            return new com.saavor.user.adapter.CardBookAdapter.MyViewHolder(itemView);

        }

        @Override
        public void onBindViewHolder(final com.saavor.user.adapter.CardBookAdapter.MyViewHolder holder, final int position)
        {
            // Save/restore the open/close state.
            // You need to provide a String id which uniquely defines the data object.
            viewBinderHelper.bind(holder.swipeRevealLayout, String.valueOf(cardLists.get(position)));

            holder.cardnum.setText(cardLists.get(position).getCardNumber());
            holder.cardtype.setText(cardLists.get(position).getCardType());

            holder.swipeRevealLayout.setSwipeListener(new SwipeRevealLayout.SwipeListener() {
                @Override
                public void onClosed(SwipeRevealLayout swipeRevealLayout) {
                  //  Toast.makeText(context, "onclosed", Toast.LENGTH_LONG).show();
                }

                @Override
                public void onOpened(SwipeRevealLayout swipeRevealLayout) {
                   // Toast.makeText(context, "onopen", Toast.LENGTH_LONG).show();

                    //dialog intialization


                    if(dialog.isShowing())
                    {

                    }
                    else
                    {

                        Button yes = (Button) dialog.findViewById(R.id.yes_cancel);
                        Button no = (Button) dialog.findViewById(R.id.not_cancel);
                        TextView Title = (TextView) dialog.findViewById(R.id.cancel_title);
                        ImageView image = (ImageView) dialog.findViewById(R.id.cancel_image);
                        TextView sub_title = (TextView) dialog.findViewById(R.id.text_empty);

                        Title.setText("Delete");
                        image.setImageResource(R.drawable.delete_add);

                        sub_title.setText("Are you sure you want to remove this card?");

                        yes.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialog.cancel();
                                ((CardBook) context).hitdelete(cardLists.get(position).getCardId(), cardLists.get(position).getCustomerId(), cardLists.get(position).getStripeCardId());

                            }
                        });

                        no.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                holder.swipeRevealLayout.close(true);
                                dialog.cancel();
                                dialog.dismiss();
                            }
                        });
                        dialog.show();
                    }


                }

                @Override
                public void onSlide(SwipeRevealLayout swipeRevealLayout, float v) {
                  //  Toast.makeText(context, "onslide", Toast.LENGTH_LONG).show();


                }
            });

            holder.LL_card_deatils.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(CardBook.select)
                    {
                        CompletePayment.placeorderdebit(cardLists.get(position).getCardId(),cardLists.get(position).getStripeCardId());
                        ((CardBook) context).finishit();


                    }
                    else
                    {
//                        Intent intent = new Intent(context, PaymentMethod.class);
//                        intent.putExtra("cardid",cardLists.get(position).getCardId());
//                        intent.putExtra("detailschoice", 1);
//                        context.startActivity(intent);
                    }

                }
            });
        }

        @Override
        public int getItemCount()
        {
            return cardLists.size();
        }
        public class MyViewHolder extends RecyclerView.ViewHolder{

            TextView cardnum,cardtype;
            ImageView Delete_card;
            SwipeRevealLayout swipeRevealLayout;
            LinearLayout LL_card_deatils;

            public MyViewHolder(View view) {
                super(view);
               // Delete_card =(ImageView)view.findViewById(R.id.delete_card);
                cardnum=(TextView)view.findViewById(R.id.txt_cardnum);
                cardtype=(TextView)view.findViewById(R.id.txt_cardtype);
                LL_card_deatils= (LinearLayout) view.findViewById(R.id.ll_card_details);
                swipeRevealLayout= (SwipeRevealLayout) view.findViewById(R.id.swipe_layout);
                dialog = new Dialog(context);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                context.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.setContentView(R.layout.cancel_diag);
                dialog.setCancelable(true);
            }
        }
    }
