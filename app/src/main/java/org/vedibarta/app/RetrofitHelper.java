package org.vedibarta.app;

import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import org.vedibarta.app.model.Par;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import okio.BufferedSink;
import okio.Okio;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Yaakov Shahak on 28/05/2017.
 */

public class RetrofitHelper {
    public static final String BASE_URL_ZIP = "http://www.vedibarta.org/Rashi_Tora_ZIP/";
    private static final String TAG = RetrofitHelper.class.getSimpleName();
    private static final Object PARASHOT_FOLDER = "PARASHOT";

    public interface ApiService {
        @GET("{par}")
        Observable<Response<ResponseBody>> download(@Path("par") String par);
    }



    public static Observable<File> saveFile(Response<ResponseBody> response, Par par) {
        return Observable.create(subscriber -> {
            try {
                // you can access headers of response
                Log.d(TAG, response.message());
                File tempZipFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsoluteFile(), par.getParTitle() + ".zip");

                BufferedSink sink = Okio.buffer(Okio.sink(tempZipFile));
                // you can access body of response
                sink.writeAll(response.body().source());
                sink.close();
                subscriber.onNext(tempZipFile);
                subscriber.onComplete();
            } catch (IOException e) {
                e.printStackTrace();
                subscriber.onError(e);
            }
        });
    }

    /**
     * unzip content of zip file with icons
     * @param context to use
     * @param parTitle to determine the folder name of the icon set
     * @param zipFile point to the icon zip file
     */
    public static void unzipIcon(Context context, File zipFile, String parTitle) {
        try {
            File parFolder = new File(context.getFilesDir() + File.separator + PARASHOT_FOLDER, parTitle);
            if (!parFolder.exists()) parFolder.mkdirs();
            boolean zipFinishedSuccessfully = unzip(zipFile, parFolder);
            boolean deleted = zipFile.delete();
            Log.d(TAG, "zip file deleted = " + deleted);
            if (zipFinishedSuccessfully) {
                Log.d(TAG, "finished unzipping from " + zipFile.getAbsolutePath() + " to " + parFolder.getPath());
            } else {
                Log.w(TAG, "unzipping of " + parTitle + " failed, zero files, check file permission!");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static boolean unzip(File zipFile, File targetDirectory) throws IOException {
        ZipInputStream zis = new ZipInputStream(
                new BufferedInputStream(new FileInputStream(zipFile)));
        return unzip(zis, targetDirectory);
    }

    public static boolean unzip(ZipInputStream zis, File targetDirectory) throws IOException {
        try {
            ZipEntry ze;
            int count;
            byte[] buffer = new byte[8192];
            while ((ze = zis.getNextEntry()) != null) {
                File file = new File(targetDirectory, Uri.parse(ze.getName()).getLastPathSegment());//if the zip file contain folder we want to get rid of the internal folder
                File dir = ze.isDirectory() ? file : file.getParentFile();
                if (!dir.isDirectory() && !dir.mkdirs())
                    throw new FileNotFoundException("Failed to ensure directory: " +
                            dir.getAbsolutePath());
                if (ze.isDirectory()) {
                    dir.delete();
                    continue;
                }
                try (FileOutputStream fout = new FileOutputStream(file)) {
                    while ((count = zis.read(buffer)) != -1)
                        fout.write(buffer, 0, count);
                    Log.d(TAG, "finished to unzip:" + file.getPath());
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

        } catch (Exception e){
            e.printStackTrace();
        }
        finally {
            zis.close();
        }
        return targetDirectory.listFiles().length > 0;

    }

    public static boolean deleteIcon(Context context, String iconName){
        File iconFolder = new File(context.getFilesDir() + File.separator + PARASHOT_FOLDER , iconName);
        File[] files = iconFolder.listFiles();
        if (files != null) {
//            MyLog.d("icons dir Size: "+ files.length);
            for (File file : files) {
//                MyLog.d("deleting icon:" + file.getName() + " : " + file.delete());
            }
        }
        return iconFolder.delete();
    }


}