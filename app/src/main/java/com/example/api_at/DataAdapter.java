package com.example.api_at;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;


class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> {

    private ArrayList<AndroidVersion> android_versions;
    private Context mContext;

    public DataAdapter(Context con, ArrayList<AndroidVersion> data) {
        this.android_versions = data;
        this.mContext = con;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list,viewGroup,false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.tv1.setText(android_versions.get(i).getUserId());
        viewHolder.tv2.setText(android_versions.get(i).getId());
        viewHolder.tv3.setText(android_versions.get(i).getTitle());
        viewHolder.tv4.setText(android_versions.get(i).getBody());

    }

    @Override
    public int getItemCount() {
        return android_versions.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tv1,tv2,tv3,tv4;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv1=itemView.findViewById(R.id.tv1);
            tv2=itemView.findViewById(R.id.tv2);
            tv3=itemView.findViewById(R.id.tv3);
            tv4=itemView.findViewById(R.id.tv4);

        }
    }
}