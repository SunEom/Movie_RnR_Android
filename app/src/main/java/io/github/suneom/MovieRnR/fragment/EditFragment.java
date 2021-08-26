package io.github.suneom.MovieRnR.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.github.suneom.MovieRnR.R;

public class EditFragment extends Fragment {

    View rootView;

    EditText title, rates, overview;

    public String titleValue, ratesValue, overviewValue, genresValue;

    public int movieId;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_edit,container, false);

        initView();
        setData();

        return rootView;
    }

    public void initView(){
        title = rootView.findViewById(R.id.edit_title);
        rates = rootView.findViewById(R.id.edit_rates);
        overview = rootView.findViewById(R.id.edit_overview);
    }

    public void setData(){

        String[] genresArray = genresValue.split(",");
        for(String g : genresArray){
            if(g.trim().equals("Romance")){
                CheckBox checkBox = rootView.findViewById(R.id.checkBox_Romance);
                checkBox.setChecked(true);
            } else if(g.trim().equals("Action")){
                CheckBox checkBox = rootView.findViewById(R.id.checkBox_Action);
                checkBox.setChecked(true);
            } else if(g.trim().equals("Comedy")){
                CheckBox checkBox = rootView.findViewById(R.id.checkBox_Comedy);
                checkBox.setChecked(true);
            } else if(g.trim().equals("Historical")){
                CheckBox checkBox = rootView.findViewById(R.id.checkBox_Historical);
                checkBox.setChecked(true);
            } else if(g.trim().equals("Horror")){
                CheckBox checkBox = rootView.findViewById(R.id.checkBox_Horror);
                checkBox.setChecked(true);
            } else if(g.trim().equals("Sci-Fi")){
                CheckBox checkBox = rootView.findViewById(R.id.checkBox_Sci_Fi);
                checkBox.setChecked(true);
            } else if(g.trim().equals("Thriller")){
                CheckBox checkBox = rootView.findViewById(R.id.checkBox_Thriller);
                checkBox.setChecked(true);
            } else if(g.trim().equals("Mystery")){
                CheckBox checkBox = rootView.findViewById(R.id.checkBox_Mystery);
                checkBox.setChecked(true);
            } else if(g.trim().equals("Animation")){
                CheckBox checkBox = rootView.findViewById(R.id.checkBox_Animation);
                checkBox.setChecked(true);
            } else if(g.trim().equals("Drama")){
                CheckBox checkBox = rootView.findViewById(R.id.checkBox_Drama);
                checkBox.setChecked(true);
            }
        }
        title.setText(titleValue);

        // Regex를 이용하여 rates 값 설정
        String ratesPattern = "⭐ ([\\d\\.]+)/ 10";
        Pattern p = Pattern.compile(ratesPattern);
        Matcher m = p.matcher(ratesValue);
        rates.setText(m.group(1));

        overview.setText(overviewValue);
    }
}
