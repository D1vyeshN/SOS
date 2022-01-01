package com.example.de;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;

public class MyAdapter extends BaseAdapter implements Filterable {

    CustomFilter cs;

    Context c;

    ArrayList<SingleRow> originalArray, tempArray;

    public MyAdapter(Context c, ArrayList<SingleRow> originalArray){

        this.c = c;

        this.originalArray = originalArray;

        this.tempArray = originalArray;

    }

    @Override
    public Object getItem(int position) {
        return originalArray.get(position);
    }

    public int findView(String val){

        int pos = 0;

        for(int i = 0; i < tempArray.size(); i++){

            if(tempArray.get(i).getName().equals(val)){

                pos =  i;

            }

        }

        return pos;

    }

    public ArrayList<SingleRow> getExactAdapter(CharSequence constraint){

        ArrayList<SingleRow> filters = new ArrayList<>();

        if(constraint != null && constraint.length() > 0) {

            constraint = constraint.toString().toUpperCase();

            for (int i = 0; i < tempArray.size(); i++) {

                if (tempArray.get(i).getName().toUpperCase().contains(constraint)) {

                    SingleRow singleRow = new SingleRow(tempArray.get(i).getName(), tempArray.get(i).getPos());

                    filters.add(singleRow);

                }

            }
        }

        return filters;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View row = inflater.inflate(R.layout.custom_layout, null);

        TextView textOne = (TextView) row.findViewById(R.id.myTextView);

            @SuppressLint("UseSwitchCompatOrMaterialCode")
            Switch switchOne = (Switch) row.findViewById(R.id.mySwitch);

//            Toast.makeText(c, "in", Toast.LENGTH_SHORT).show();

        textOne.setText(originalArray.get(position).getName());

        if(originalArray.get(position).getPos().equals("true")){

            switchOne.setChecked(true);

        }
        else{

            switchOne.setChecked(false);
        }

        return row;
    }

    public View getV(int position) {

        LayoutInflater inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View row = inflater.inflate(R.layout.custom_layout, null);

        TextView textOne = (TextView) row.findViewById(R.id.myTextView);

        @SuppressLint("UseSwitchCompatOrMaterialCode")
        Switch switchOne = (Switch) row.findViewById(R.id.mySwitch);

//            Toast.makeText(c, "in", Toast.LENGTH_SHORT).show();

        textOne.setText(tempArray.get(position).getName());

        if(tempArray.get(position).getPos().equals("true")){

            switchOne.setChecked(true);

        }
        else{

            switchOne.setChecked(false);
        }

        return row;
    }

    @Override
    public int getCount() {
        return originalArray.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Filter getFilter() {

        if(cs == null){

            cs = new CustomFilter();

        }

        return cs;
    }

    class CustomFilter extends Filter{

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            FilterResults results = new FilterResults();

            if(constraint != null && constraint.length() > 0) {

                constraint = constraint.toString().toUpperCase();

                ArrayList<SingleRow> filters = new ArrayList<>();

                for (int i = 0; i < tempArray.size(); i++) {

                    if (tempArray.get(i).getName().toUpperCase().contains(constraint)) {

                        SingleRow singleRow = new SingleRow(tempArray.get(i).getName(), tempArray.get(i).getPos());

                        filters.add(singleRow);

                    }

                }

                results.count = filters.size();

                results.values = filters;

            }
            else{

                results.count = tempArray.size();

                results.values = tempArray;

            }

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

            originalArray = (ArrayList<SingleRow>) results.values;

            notifyDataSetChanged();

        }
    }



}
