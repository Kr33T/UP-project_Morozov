package com.example.up_project_morozov;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.InputStream;
import java.util.List;


public class AdapterFeelings extends RecyclerView.Adapter<AdapterFeelings.ViewHolder> {

    private List<FeelingsModel> feelingsModelList;
    private Context context;

    public AdapterFeelings(List<FeelingsModel> dataModalArrayList, Context context) {
        this.feelingsModelList = dataModalArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public AdapterFeelings.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AdapterFeelings.ViewHolder(LayoutInflater.from(context).inflate(R.layout.feeling_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterFeelings.ViewHolder holder, int position) {
        final FeelingsModel modal = feelingsModelList.get(position);
        holder.title.setText(modal.getTitle());

        if(modal.getImage().equals("null"))
        {
            holder.image.setImageResource(R.drawable.empty);
        }
        else
        {
            new AdapterFeelings.getImage((ImageView) holder.image)
                    .execute(modal.getImage());
        }
    }

    private class getImage extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public getImage(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }

    @Override
    public int getItemCount() {
        return feelingsModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView title;
        private ImageView image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            image = itemView.findViewById(R.id.imageFeeling);
        }
    }
}

