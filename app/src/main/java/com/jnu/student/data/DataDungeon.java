
package com.jnu.student.data;

import android.content.Context;
import android.util.Log;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;


public class DataDungeon  {
    final String DATA_FILENAME = "dungeon.data";

    public ArrayList<Dungeon> LoadTasks(Context context) {
        ArrayList<Dungeon> data = new ArrayList<>();
        try {
            FileInputStream fileIn = context.openFileInput(DATA_FILENAME);
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);
            data = (ArrayList<Dungeon>) objectIn.readObject();
            objectIn.close();
            fileIn.close();
            Log.d("Serialization", "Data loaded successfully.item count" + data.size());
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return data;
    }

    public void SaveTasks(Context context, ArrayList<Dungeon> tasks) {
        try {
            FileOutputStream fileOut = context.openFileOutput(DATA_FILENAME, Context.MODE_PRIVATE);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(tasks);
            out.close();
            fileOut.close();
            Log.d("Serialization", "Data is serialized and saved");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}