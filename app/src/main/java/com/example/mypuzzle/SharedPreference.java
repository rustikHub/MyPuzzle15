package com.example.mypuzzle;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class SharedPreference {
    private static SharedPreference preference = null;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private static String nameUser = null;


    private SharedPreference(Context context) {
        sharedPreferences = context.getSharedPreferences("SCORE", 0);

    }

    public static SharedPreference getPreference(Context context) {
        if (preference == null) preference = new SharedPreference(context);
        return preference;
    }


    public void setScore(String playerName, long minutes, int moves, boolean isWinn) {
        setId();
        int id = getID();
        nameUser = nameUser + "#" + id;
        String score = id + "#" + playerName + "#" + minutes + "#" + moves + "#" + isWinn;
        editor = sharedPreferences.edit();
        editor.putString("SCORE" + id, score);
        editor.apply();
    }

    public void setId() {
        editor = sharedPreferences.edit();
        editor.putInt("ID", (1 + getID()));
        editor.apply();
    }

    public int getID() {
        return sharedPreferences.getInt("ID", 0);
    }

    public ArrayList<String> getAllScore() {
        ArrayList<String> sco = new ArrayList<>();
        for (int j = 1; j <= getID(); j++) {
            sco.add(sharedPreferences.getString("SCORE" + j, "0"));
        }

        Collections.sort(sco, new ComparatorOfNomberString());

        return sco;
    }

    public String getById(String id) {
        return sharedPreferences.getString("SCORE" + id, "0");

    }


    public void clearAll(Context context) {
        editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        Toast.makeText(context,"All scores deleted successfully",Toast.LENGTH_SHORT).show();
    }

    public static String getNameUser() {
        return nameUser;
    }

    public static void setNameUser(String nameUser) {
        SharedPreference.nameUser = nameUser;
    }

    class ComparatorOfNomberString implements Comparator<String> {

        @Override
        public int compare(String o1, String o2) {

            String[] info1 = o1.split("#");
            int time1 = Integer.parseInt(info1[2]);
            int moves1 = Integer.parseInt(info1[3]);

            String[] info2 = o2.split("#");
            int time2 = Integer.parseInt(info2[2]);
            int moves2 = Integer.parseInt(info2[3]);

            if (time1 == time2) {
                return moves1 - moves2;
            }
            return time2 - time1;
        }
    }
}



