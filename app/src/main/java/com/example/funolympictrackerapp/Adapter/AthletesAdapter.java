package com.example.funolympictrackerapp.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.funolympictrackerapp.Helper.Users;
import com.example.funolympictrackerapp.R;

import java.util.List;

public class AthletesAdapter extends ArrayAdapter {

    private Activity mContext;
    List<Users> athletesLists;

    public AthletesAdapter(Activity mContext, List<Users> athletesLists){

        super(mContext, R.layout.athletes_list_item);
        this.mContext = mContext;
        this.athletesLists = athletesLists;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = mContext.getLayoutInflater();
        View athletesView = inflater.inflate(R.layout.athletes_list_item, null, true);

        TextView ath_name = athletesView.findViewById(R.id.name);
        TextView ath_surname = athletesView.findViewById(R.id.surname);
        TextView ath_email = athletesView.findViewById(R.id.email);

        Users users = athletesLists.get(position);

        ath_name.setText(users.getAthlete_name());
        ath_surname.setText(users.getAthlete_surname());
        ath_email.setText(users.getAthlete_email());

        return athletesView;

    }
}
