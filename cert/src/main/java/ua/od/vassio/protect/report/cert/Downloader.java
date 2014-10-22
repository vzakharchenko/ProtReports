package ua.od.vassio.protect.report.cert;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;

import javax.mail.internet.ContentDisposition;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by vzakharchenko on 21.10.14.
 */
public class Downloader {
    private static final String HEADERNAME_SETCOOKIE ="Set-Cookie";
    private static final String HEADERNAME_COOKIE ="Cookie";


    public static InputStream downloadFileToMemory(URL page) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) page.openConnection();

      try {
          urlConnection.setConnectTimeout(10000);
          urlConnection.setReadTimeout(10000);
          InputStream inputStream=urlConnection.getInputStream();
          return new ByteArrayInputStream(IOUtils.toByteArray(inputStream));
      } finally {
          urlConnection.disconnect();
      }

    }

    public static InputStream downloadFileToMemory(URL postPage,String postData, String cookie) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) postPage.openConnection();
        try {
            urlConnection.setConnectTimeout(10000);
            urlConnection.setReadTimeout(10000);
            urlConnection.setDoOutput(true);
            urlConnection.setDoInput(true);
            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty(HEADERNAME_COOKIE,cookie);
            urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            byte[] postByte=postData.getBytes();
            urlConnection.setRequestProperty("Content-Length", Integer.toString(postByte.length));
            IOUtils.write(postByte,urlConnection.getOutputStream());
            System.out.println("Request URL ... " + postPage);
            boolean redirect = false;
            int status = urlConnection.getResponseCode();
            if (status != HttpURLConnection.HTTP_OK) {
                if (status == HttpURLConnection.HTTP_MOVED_TEMP
                        || status == HttpURLConnection.HTTP_MOVED_PERM
                        || status == HttpURLConnection.HTTP_SEE_OTHER)
                    redirect = true;
            }
            System.out.println("Response Code ... " + status);

            if (redirect) {

                // get redirect url from "location" header field
                String newUrl = urlConnection.getHeaderField("Location");

                // get the cookie if need, for login
                String cookies = urlConnection.getHeaderField("Set-Cookie");

                // open the new connnection again
                urlConnection = (HttpURLConnection) new URL(newUrl).openConnection();
                urlConnection.setRequestProperty("Cookie", cookies);
                urlConnection.addRequestProperty("Accept-Language", "en-US,en;q=0.8");
                urlConnection.addRequestProperty("User-Agent", "Mozilla");
                urlConnection.addRequestProperty("Referer", "google.com");

                System.out.println("Redirect to URL : " + newUrl);

            }
            InputStream inputStream=urlConnection.getInputStream();
            byte[] responseData=IOUtils.toByteArray(inputStream);
            return new ByteArrayInputStream(responseData);
        } finally {
            urlConnection.disconnect();
        }
    }

    public static void downloadFileToDisk(URL file,File directory) throws IOException {
        downloadFileToDisk(file, FilenameUtils.getName(file.toString()),directory);
    }
    public static void downloadFileToDisk(URL file,String fileName,File directory) throws IOException {
        FileUtils.copyURLToFile(file, new File(directory, fileName));
    }

    public static String getCookie(URL page) throws IOException {
        URLConnection urlConnection = page.openConnection();
        urlConnection.setConnectTimeout(10000);
        urlConnection.setReadTimeout(10000);
        urlConnection.connect();
        return urlConnection.getHeaderField(HEADERNAME_SETCOOKIE);
    }

    public static String getName(URL link,String cookie) throws Exception {
        HttpURLConnection urlConnection = (HttpURLConnection) link.openConnection();
        urlConnection.setRequestMethod("HEAD");
        urlConnection.setRequestProperty(HEADERNAME_COOKIE,cookie);
        urlConnection.connect();
        ContentDisposition contentDisposition =new ContentDisposition(urlConnection.getHeaderField("Content-Disposition"));
        urlConnection.disconnect();
        return contentDisposition.getParameter("filename");
    }
}
