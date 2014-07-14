package com.atomu.timetrace.analyze;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by Atomu on 2014/7/14.
 * fetch data from databases and export data into files
 */
public class FetchData {
    private Context context;
    private String path;
    private String fileName;

    public FetchData(Context context){
        this.context = context;

        // package/files
        if (context.getFilesDir() != null){
            path = context.getFilesDir().toString();
            Log.d("fetch", path);
        }
        fileName = "data";
    }

    public void fetch(){
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(path+fileName);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        if (fos != null){
            // write
        }
    }

}
