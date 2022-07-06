package com.example.gmusicplayer;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;


public class AdapterClass extends RecyclerView.Adapter<AdapterClass.ViewHolder> {

    private String[] localDataSet;
    Onclick call;

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView textView;
        Onclick oc;

        public ViewHolder(View view, Onclick oc) {
            super(view);
            // Define click listener for the ViewHolder's View

            textView = (TextView) view.findViewById(R.id.textView);
            this.oc = oc;
            view.setOnClickListener(this);
        }

        public TextView getTextView() {
            return textView;
        }


        @Override
        public void onClick(View v) {
            oc.onclick(getAdapterPosition());

        }
    }


    public AdapterClass(String[] dataSet, Onclick oc) {
        localDataSet = dataSet;
        this.call = oc;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.temp, viewGroup, false);

        return new ViewHolder(view, call);                                          // recall
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.getTextView().setText(localDataSet[position]);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return localDataSet.length;
    }

    public interface Onclick {
        void onclick(int position);
    }
}

