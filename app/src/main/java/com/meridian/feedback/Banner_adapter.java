package com.meridian.feedback;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by libin on 6/14/2017.
 */

public class Banner_adapter extends RecyclerView.Adapter<Banner_adapter.ViewHolder> {


    List<Action_Services_Model> asm;
    Context context;



    public Banner_adapter(ArrayList<Action_Services_Model> asm, Context context) {
        this.asm = asm;
        this.context = context;

    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView count, on_time, on_type, dept, evntnam;
        String imag;

        ViewHolder(View itemView) {
            super(itemView);

            count=   (TextView) itemView.findViewById(R.id.count);
            on_time=   (TextView) itemView.findViewById(R.id.on_time);
            on_type=   (TextView) itemView.findViewById(R.id.on_type);

        }
    }


    @Override
    public int getItemCount() {
        return asm.size();
    }



    @Override
    public Banner_adapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_pateinttype_count, viewGroup, false);
        Banner_adapter.ViewHolder pvh = new Banner_adapter.ViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(final Banner_adapter.ViewHolder personViewHolder, final int i) {



        personViewHolder.count.setText(asm.get(i).getCount());
        personViewHolder.on_time.setText(asm.get(i).getOntime());
       // personViewHolder.on_type.setText("Inpatient Reviewed");

        if(asm.get(i).getType().equalsIgnoreCase("ip")){
            personViewHolder.on_type.setText("Inpatient Reviewed");

        }else {
            personViewHolder.on_type.setText("Outpatient Reviewed");

        }

    }


    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }



}
