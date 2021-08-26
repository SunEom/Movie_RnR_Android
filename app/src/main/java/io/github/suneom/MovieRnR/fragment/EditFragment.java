package io.github.suneom.MovieRnR.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.github.suneom.MovieRnR.R;
import io.github.suneom.MovieRnR.util.sRequest;

public class EditFragment extends Fragment implements CompoundButton.OnCheckedChangeListener {

    View rootView;

    EditText title, rates, overview;
    Button save_button, cancel_button;

    CheckBox checkBox, checkBox2, checkBox3, checkBox4, checkBox5, checkBox6, checkBox7, checkBox8, checkBox9, checkBox10;
    ArrayList<CheckBox> selected_checkboxs = new ArrayList<CheckBox>();


    public String titleValue, ratesValue, overviewValue, genresValue;

    public int movieId;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_edit,container, false);

        initView();
        init_checkBox();
        setData();
        setButtonListener();

        return rootView;
    }

    public void initView(){
        title = rootView.findViewById(R.id.edit_title);
        rates = rootView.findViewById(R.id.edit_rates);
        overview = rootView.findViewById(R.id.edit_overview);

        save_button = rootView.findViewById(R.id.edit_save_button);
        cancel_button = rootView.findViewById(R.id.edit_cancel_button);
    }

    public void setData(){

        String[] genresArray = genresValue.split(",");
        for(String g : genresArray){
            if(g.trim().equals("Romance")){
                CheckBox checkBox = rootView.findViewById(R.id.checkBox_Romance);
                checkBox.setChecked(true);
                selected_checkboxs.add(checkBox);
            } else if(g.trim().equals("Action")){
                CheckBox checkBox = rootView.findViewById(R.id.checkBox_Action);
                checkBox.setChecked(true);
                selected_checkboxs.add(checkBox);
            } else if(g.trim().equals("Comedy")){
                CheckBox checkBox = rootView.findViewById(R.id.checkBox_Comedy);
                checkBox.setChecked(true);
                selected_checkboxs.add(checkBox);
            } else if(g.trim().equals("Historical")){
                CheckBox checkBox = rootView.findViewById(R.id.checkBox_Historical);
                checkBox.setChecked(true);
                selected_checkboxs.add(checkBox);
            } else if(g.trim().equals("Horror")){
                CheckBox checkBox = rootView.findViewById(R.id.checkBox_Horror);
                checkBox.setChecked(true);
                selected_checkboxs.add(checkBox);
            } else if(g.trim().equals("Sci-Fi")){
                CheckBox checkBox = rootView.findViewById(R.id.checkBox_Sci_Fi);
                checkBox.setChecked(true);
                selected_checkboxs.add(checkBox);
            } else if(g.trim().equals("Thriller")){
                CheckBox checkBox = rootView.findViewById(R.id.checkBox_Thriller);
                checkBox.setChecked(true);
                selected_checkboxs.add(checkBox);
            } else if(g.trim().equals("Mystery")){
                CheckBox checkBox = rootView.findViewById(R.id.checkBox_Mystery);
                checkBox.setChecked(true);
                selected_checkboxs.add(checkBox);
            } else if(g.trim().equals("Animation")){
                CheckBox checkBox = rootView.findViewById(R.id.checkBox_Animation);
                checkBox.setChecked(true);
                selected_checkboxs.add(checkBox);
            } else if(g.trim().equals("Drama")){
                CheckBox checkBox = rootView.findViewById(R.id.checkBox_Drama);
                checkBox.setChecked(true);
                selected_checkboxs.add(checkBox);
            }
        }
        title.setText(titleValue);
        rates.setText(ratesValue.split(" ")[1].split("/")[0]);

        overview.setText(overviewValue);
    }

    public void setButtonListener(){
        save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String titleValue = title.getText().toString();
                String genresValue = parseArrayItemToString(selected_checkboxs);
                String ratesValue = rates.getText().toString();
                String overviewValue = overview.getText().toString();

                sRequest.requestPatchPosting(titleValue, genresValue, ratesValue, overviewValue, movieId, getActivity().getSupportFragmentManager());
            }
        });

        cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DetailFragment detailFragment = new DetailFragment();
                detailFragment.movieId = movieId;
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, detailFragment).commit();
            }
        });
    }


    public void init_checkBox(){

        checkBox = rootView.findViewById(R.id.checkBox_Romance);
        checkBox.setOnCheckedChangeListener(this::onCheckedChanged);


        checkBox2 = rootView.findViewById(R.id.checkBox_Action);
        checkBox2.setOnCheckedChangeListener(this::onCheckedChanged);


        checkBox3 = rootView.findViewById(R.id.checkBox_Comedy);
        checkBox3.setOnCheckedChangeListener(this::onCheckedChanged);


        checkBox4 = rootView.findViewById(R.id.checkBox_Historical);
        checkBox4.setOnCheckedChangeListener(this::onCheckedChanged);


        checkBox5 = rootView.findViewById(R.id.checkBox_Horror);
        checkBox5.setOnCheckedChangeListener(this::onCheckedChanged);


        checkBox6 = rootView.findViewById(R.id.checkBox_Sci_Fi);
        checkBox6.setOnCheckedChangeListener(this::onCheckedChanged);


        checkBox7 = rootView.findViewById(R.id.checkBox_Thriller);
        checkBox7.setOnCheckedChangeListener(this::onCheckedChanged);


        checkBox8 = rootView.findViewById(R.id.checkBox_Mystery);
        checkBox8.setOnCheckedChangeListener(this::onCheckedChanged);


        checkBox9 = rootView.findViewById(R.id.checkBox_Animation);
        checkBox9.setOnCheckedChangeListener(this::onCheckedChanged);


        checkBox10 = rootView.findViewById(R.id.checkBox_Drama);
        checkBox10.setOnCheckedChangeListener(this::onCheckedChanged);

    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if(isChecked){
            selected_checkboxs.add((CheckBox)buttonView);
        } else {
            selected_checkboxs.remove((CheckBox)buttonView);
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
