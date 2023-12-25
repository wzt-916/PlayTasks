package com.jnu.student.data;

import android.content.Context;
import android.util.Log;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class DataRewardScore  {
    final String DATA_FILENAME = "rewardscore.data";
    private static final String TAG = "Serialization";

    public int loadScore(Context context) {
        int rewardscore = 0;
        try {
            FileInputStream fileIn = context.openFileInput(DATA_FILENAME);
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);
            rewardscore = objectIn.readInt();
            objectIn.close();
            fileIn.close();
            Log.d(TAG, "Data loaded successfully: score = " + rewardscore);
        } catch (IOException e) {
            Log.d(TAG, "No data found, initializing score to 0");
        }
        return rewardscore;
    }

    public void saveScore(Context context, int rewardscore) {
        try {
            FileOutputStream fileOut = context.openFileOutput(DATA_FILENAME, Context.MODE_PRIVATE);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeInt(rewardscore);
            out.close();
            fileOut.close();
            Log.d(TAG, "Score is saved: score = " + rewardscore);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
