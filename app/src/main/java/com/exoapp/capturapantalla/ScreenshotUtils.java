package com.exoapp.capturapantalla;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Environment;
import android.util.Log;
import android.view.View;

import java.io.File;
import java.io.FileOutputStream;

public class ScreenshotUtils {
    /*  Method which will return Bitmap after taking screenshot. We have to pass the view which we want to take screenshot.  */
    public static Bitmap getScreenShot(View view) {
        //getDrawingCache esta deprecado y puede devolver null: se dibuja la vista directamente en un Bitmap
        View screenView = view.getRootView();
        Bitmap bitmap = Bitmap.createBitmap(screenView.getWidth(), screenView.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawColor(androidx.core.content.ContextCompat.getColor(view.getContext(), com.exoapp.R.color.fondo_app)); //Color de fondo del modo activo (el JPEG no tiene transparencia)
        screenView.draw(canvas);
        return bitmap;
    }


    /*  Create Directory where screenshot will save for sharing screenshot  */
    public static File getMainDirectoryName(Context context) {
        //Here we will use getExternalFilesDir and inside that we will make our Demo folder
        //benefit of getExternalFilesDir is that whenever the app uninstalls the images will get deleted automatically.
        File mainDir = new File(
                context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "Captura");

        //If File is not present create directory
        if (!mainDir.exists()) {
            if (mainDir.mkdir())
                Log.e("Crear directorio", "Directorio principal creado : " + mainDir);
        }
        return mainDir;
    }


    /*  Store taken screenshot into above created path  */
    public static File store(Bitmap bm, String fileName, File saveFilePath) {
        File dir = new File(saveFilePath.getAbsolutePath());
        if (!dir.exists())
            dir.mkdirs();
        File file = new File(saveFilePath.getAbsolutePath(), fileName);
        try {
            FileOutputStream fOut = new FileOutputStream(file);
            bm.compress(Bitmap.CompressFormat.JPEG, 85, fOut);
            fOut.flush();
            fOut.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file;
    }
}
