package com.ahmedosman.tripplanner.home;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.widget.CardView;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ahmedosman.tripplanner.R;
import com.ahmedosman.tripplanner.models.Trip;
import com.ahmedosman.tripplanner.viewtrip.ViewTrip;


public class CardsAdapter extends RecyclerView.Adapter<CardsAdapter.ViewHolder> {

    private Trip[] mDataset;
    private TextView txtView;
    private CardView cardView;
    public static final String CURRENT_TRIP = "current_trip";

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public View view;
        public Trip currentTrip;

        public ViewHolder(View v) {
            super(v);
            view = v;
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(view.getContext(),ViewTrip.class);
                    intent.putExtra(CURRENT_TRIP,currentTrip);
                    view.getContext().startActivity(intent);
                }
            });
            view.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    CardsAdapter.showPopUp(v,currentTrip);
                    return true;
                }
            });
        }
    }

    public CardsAdapter(Trip[] mDataset) {
        this.mDataset = mDataset;
    }

    @Override
    public CardsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                      int viewType) {
        View v = (View) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view, parent, false);
        ViewHolder vh = new ViewHolder(v);
        txtView = (TextView) v.findViewById(R.id.trip_name);
        cardView = (CardView) v.findViewById(R.id.card_view);
        return vh;
    }



    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.currentTrip = mDataset[position];
        txtView.setText(mDataset[position].getTripName());
        Bitmap bitmap = null ;
        //BitmapDrawable drawable = new BitmapDrawable(R.mipmap.TripBack);
        cardView.setBackgroundResource(R.mipmap.trip);
    }

    @Override
    public int getItemCount() {
        return mDataset.length;
    }

    public static void showPopUp(View view,Trip currentTrip){
        PopupMenu popupMenu=new PopupMenu(view.getContext(),view);
        MenuInflater menuInflater=popupMenu.getMenuInflater();
        PopUpMenuEventHandle popUpMenuEventHandle=new PopUpMenuEventHandle(view.getContext(),currentTrip);
        popupMenu.setOnMenuItemClickListener(popUpMenuEventHandle);
        menuInflater.inflate(R.menu.popup_menu,popupMenu.getMenu());
        popupMenu.show();
    }
}