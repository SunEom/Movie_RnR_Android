package io.github.suneom.MovieRnR.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

import io.github.suneom.MovieRnR.R;
import io.github.suneom.MovieRnR.util.sRequest;

public class PostingFragment extends Fragment implements CompoundButton.OnCheckedChangeListener {

    View rootView;
    EditText title, rates, overviews;
    CheckBox checkBox, checkBox2, checkBox3, checkBox4, checkBox5, checkBox6, checkBox7, checkBox8, checkBox9, checkBox10;
    Button saveButton;

    ArrayList<CheckBox> total_checkboxs = new ArrayList<CheckBox>();
    ArrayList<CheckBox> selected_checkboxs = new ArrayList<CheckBox>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_posting, container, false);

        title = rootView.findViewById(R.id.new_post_title);
        init_checkBox();
        rates = rootView.findViewById(R.id.editText_rates);
        overviews = rootView.findViewById(R.id.editText_overviews);
        saveButton = rootView.findViewById(R.id.new_post_save_button);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sRequest.requestNewPosting(title.getText().toString(),
                        parseArrayItemToString(selected_checkboxs),
                        rates.getText().toString(),
                        overviews.getText().toString(),
                        getContext());
            }
        });

        return rootView;
    }

    public void init_checkBox(){
        
        checkBox = rootView.findViewById(R.id.checkBox_Romance);
        checkBox.setOnCheckedChangeListener(this::onCheckedChanged);
        total_checkboxs.add(checkBox);


        checkBox2 = rootView.findViewById(R.id.checkBox_Action);
        checkBox2.setOnCheckedChangeListener(this::onCheckedChanged);
        total_checkboxs.add(checkBox2);


        checkBox3 = rootView.findViewById(R.id.checkBox_Comedy);
        checkBox3.setOnCheckedChangeListener(this::onCheckedChanged);
        total_checkboxs.add(checkBox3);


        checkBox4 = rootView.findViewById(R.id.checkBox_Historical);
        checkBox4.setOnCheckedChangeListener(this::onCheckedChanged);
        total_checkboxs.add(checkBox4);


        checkBox5 = rootView.findViewById(R.id.checkBox_Horror);
        checkBox5.setOnCheckedChangeListener(this::onCheckedChanged);
        total_checkboxs.add(checkBox5);


        checkBox6 = rootView.findViewById(R.id.checkBox_Sci_Fi);
        checkBox6.setOnCheckedChangeListener(this::onCheckedChanged);
        total_checkboxs.add(checkBox6);


        checkBox7 = rootView.findViewById(R.id.checkBox_Thriller);
        checkBox7.setOnCheckedChangeListener(this::onCheckedChanged);
        total_checkboxs.add(checkBox7);


        checkBox8 = rootView.findViewById(R.id.checkBox_Mystery);
        checkBox8.setOnCheckedChangeListener(this::onCheckedChanged);
        total_checkboxs.add(checkBox8);


        checkBox9 = rootView.findViewById(R.id.checkBox_Animation);
        checkBox9.setOnCheckedChangeListener(this::onCheckedChanged);
        total_checkboxs.add(checkBox9);


        checkBox10 = rootView.findViewById(R.id.checkBox_Drama);
        checkBox10.setOnCheckedChangeListener(this::onCheckedChanged);
        total_checkboxs.add(checkBox10);

    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if(isChecked){
            selected_checkboxs.add((CheckBox)buttonView);
            print_every_item(selected_checkboxs);
        } else {
            selected_checkboxs.remove((CheckBox)buttonView);
            print_every_item(selected_checkboxs);
        }
    }

    public static void print_every_item(ArrayList<CheckBox> checkBoxes){
        for(CheckBox checkBox : checkBoxes){
            Log.d("Posting",parseArrayItemToString(checkBoxes));
        }
    }

    public static String parseArrayItemToString(ArrayList<CheckBox> checkBoxes){
        String result="";

        for(int i=0; i<checkBoxes.size(); i++){
            result += checkBoxes.get(i).getText().toString();
            if(i != checkBoxes.size()-1){
                result+=", ";
            }
        }

        return result;
    }
}
