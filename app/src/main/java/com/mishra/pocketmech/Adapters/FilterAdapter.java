package com.mishra.pocketmech.Adapters;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.slider.LabelFormatter;
import com.google.android.material.slider.Slider;
import com.mishra.pocketmech.R;

public class FilterAdapter extends BottomSheetDialogFragment {
    private BottomSheetListener mListener;
    TextView bike;
    TextView car;
    TextView submit;

    String selected ;
    int limit;

    //
    String type;

    public FilterAdapter(String type) {
        this.type = type;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.filter_pop_up, container, false);
        bike = v.findViewById(R.id.two_wheeler);
        selected = bike.getText().toString();
        car = v.findViewById(R.id.four_wheeler);

        if (type.equalsIgnoreCase("car")){
            car.setBackgroundDrawable(getResources().getDrawable(R.drawable.rounded_rectangle_white_no_border));
            car.setTextColor(getResources().getColor(R.color.colorPrimary));
            bike.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            bike.setTextColor(getResources().getColor(R.color.white));
        }

        else {
            bike.setBackgroundDrawable(getResources().getDrawable(R.drawable.rounded_rectangle_white_no_border));
            bike.setTextColor(getResources().getColor(R.color.colorPrimary));
            car.setTextColor(getResources().getColor(R.color.white));
            car.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        }

        car.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                car.setBackgroundDrawable(getResources().getDrawable(R.drawable.rounded_rectangle_white_no_border));
                car.setTextColor(getResources().getColor(R.color.colorPrimary));
                bike.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                bike.setTextColor(getResources().getColor(R.color.white));
                selected = "Car";
            }
        });

        bike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bike.setBackgroundDrawable(getResources().getDrawable(R.drawable.rounded_rectangle_white_no_border));
                bike.setTextColor(getResources().getColor(R.color.colorPrimary));
                car.setTextColor(getResources().getColor(R.color.white));
                car.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                selected = "Bike";
            }
        });

        submit = v.findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    if (selected.equalsIgnoreCase("Two Wheeler")){
                        mListener.onItemClicked("", limit);

                    }else{
                        mListener.onItemClicked(selected.trim(), limit);
                    }
                dismiss();
            }
        });

        return v;
    }

    public void show(FragmentManager fragmentManager, String abc) {
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.add(this, abc);
        ft.commit();
    }

    public interface BottomSheetListener {
        void onItemClicked(String text, int limit);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (BottomSheetListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement BottomSheetListener");
        }
    }

}
