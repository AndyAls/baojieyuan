package com.qlckh.purifier.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @author Andy
 * @date 2018/5/20 0:33
 * Desc:
 */
public class Base64Util {

    public static String encode(String paths) {

            //decode to bitmap
        BitmapFactory.Options opt = new BitmapFactory.Options();
        Bitmap bitmap = BitmapFactory.decodeFile(paths,opt);
            //convert to byte array
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 60, baos);
            byte[] bytes = baos.toByteArray();

            //base64 encode
            byte[] encode = Base64.encode(bytes, Base64.DEFAULT);


        return new String(encode);
    }

    public static String ioEncode(File file) {
        String base64 = null;
        InputStream in = null;
        try {
            in = new FileInputStream(file);
            byte[] bytes = new byte[in.available()];
            int length = in.read(bytes);
            base64 = Base64.encodeToString(bytes, 0, length, Base64.DEFAULT);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return base64;

    }

}
