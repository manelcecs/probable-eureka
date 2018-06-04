package manpergut.us.crashapp3;

import android.os.Environment;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by manuel on 9/03/18.
 */

public class UtilFichero {

    public static void escribeFichero(String dir, String s) throws IOException{
        File doc = new File(Environment.getExternalStorageDirectory().toString()+"/grafos/");
        doc.setWritable(true);
        doc.mkdir();
        File f = new File(doc.getPath()+"/"+dir);
        if(!f.exists()){
            f.createNewFile();
            f.setWritable(true);
            PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(f.getPath(), true)), true);
            writer.write(s);
            writer.close();
        }else {
            f.setWritable(true);
            PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(f.getPath(), true)), true);
            writer.write(s);
            writer.close();
        }
    }

}
