package org.vedibarta.app.network;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import io.reactivex.Observable;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okio.BufferedSink;
import okio.BufferedSource;
import okio.Okio;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Streaming;

import static org.vedibarta.app.ParashotHelper.PARASHOT_FOLDER;

/**
 * Created by Yaakov Shahak on 28/05/2017.
 */

public class RetrofitHelper {
    private static final String TAG = RetrofitHelper.class.getSimpleName();

    public final static String BASE_URL_VEDIBARTA = "http://www.vedibarta.org/";

    public interface SendFeedbackService{
        @FormUrlEncoded
        @POST("guestbook/save.asp")
        @Headers({
                "Proxy-Connection: keep-alive",
                "Cache-Control: max-age=0",
                "Accept-Encoding: gzip,deflate",
                "Content-Type: application/x-www-form-urlencoded;charset=UTF-8"
        })
        Observable<ResponseBody> sendFeedback(@Field("NAME") String name,
                                        @Field("EMAIL") String email,
                                        @Field("MESSAGE") String message);
    }

    public interface ApiService {
        @Streaming
        @GET("{par}")
            Observable<Response<ResponseBody>> download(@Path("par") String par);
    }



    public static Observable<File> saveFile(Context context, Response<ResponseBody> response, String parName, String trackUrl) {
        return Observable.create(subscriber -> {
            try {
                File parFolder = new File(context.getFilesDir() + File.separator + PARASHOT_FOLDER, parName);
                if (!parFolder.exists()) parFolder.mkdirs();

                File file = new File(parFolder, trackUrl);

                BufferedSink sink = Okio.buffer(Okio.sink(file));
                // you can access body of response
                sink.writeAll(response.body().source());
                sink.close();
                subscriber.onNext(file);
//                subscriber.onComplete();
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

    public static void downloadWithOkHttp(Context context, String url, String iconName) {
        final int DOWNLOAD_CHUNK_SIZE = 2048; //Same as Okio Segment.SIZE

        try {
            okhttp3.Request request = new okhttp3.Request.Builder().url(url).build();
            OkHttpClient client = new OkHttpClient();

            okhttp3.Response response = client.newCall(request).execute();
            ResponseBody body = response.body();
            BufferedSource source = body.source();
            String fileName = (iconName.endsWith(".zip")) ? iconName : iconName + ".zip";
            File zipFolder = new File(context.getFilesDir() + File.separator + PARASHOT_FOLDER);
            zipFolder.mkdirs();
            File tempZipfile = new File(zipFolder, fileName);
            BufferedSink sink = Okio.buffer(Okio.sink(tempZipfile));
            long totalRead = 0;
            long read;
            while ((read = source.read(sink.buffer(), DOWNLOAD_CHUNK_SIZE)) != -1) {
                totalRead += read;
            }
            sink.writeAll(source);
            sink.flush();
            sink.close();
            if (iconName.contains(".zip")) {
                iconName = iconName.substring(0, iconName.indexOf(".zip"));
            }
            unzipIcon(context, tempZipfile, iconName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}