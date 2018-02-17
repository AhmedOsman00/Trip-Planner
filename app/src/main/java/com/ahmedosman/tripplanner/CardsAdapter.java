package com.ahmedosman.tripplanner;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


public class CardsAdapter extends RecyclerView.Adapter<CardsAdapter.ViewHolder> {

    private Trip[] mDataset;
    private TextView txtView;
    private CardView cardView;

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
                    intent.putExtra("currentTrip",currentTrip);
                    view.getContext().startActivity(intent);
                }
            });
            view.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    Toast.makeText(v.getContext(), currentTrip.getTripName(), Toast.LENGTH_SHORT).show();
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
        BitmapDrawable drawable = new BitmapDrawable(bitmap);
        cardView.setBackground(drawable);
    }

    @Override
    public int getItemCount() {
        return mDataset.length;
    }
}