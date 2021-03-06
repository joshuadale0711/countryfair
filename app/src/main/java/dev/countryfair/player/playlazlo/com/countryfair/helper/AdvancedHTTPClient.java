package dev.countryfair.player.playlazlo.com.countryfair.helper;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Joshua Dray on 3/14/17.
 */

public class AdvancedHTTPClient {

    private static final String TAG = AdvancedHTTPClient.class.getSimpleName();


    private class HTTPGetWithHeader extends AsyncTask<String, Void, JSONObject> {

        Context context;

        HTTPGetWithHeader(Context context) {
            this.context = context;
        }

        @Override
        protected JSONObject doInBackground(String... params) {
            HttpURLConnection conn = null;
            BufferedReader br = null;
            try {
                URL url = new URL(Constants.SERVICE_URL + params[0]);
                conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(15000);
                conn.setConnectTimeout(15000);
                conn.setRequestMethod("GET");
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setRequestProperty("Lazlo-AuthorityLicenseCode", Constants.TOKEN);
                conn.setRequestProperty("Lazlo-PlayerLicenseCode", Constants.PLAYER_TOKEN);
                conn.setRequestProperty("Lazlo-BrandLicenseCode", Constants.BRAND_LISENCE_CODE);


                //Log.d(TAG, "HTTPGetWithHeader: "+url);

                int code = conn.getResponseCode();
                if (code != 200) {
                    br = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
                    String apiOutput = br.readLine();
                    throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
                }

                br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
                String apiOutput = br.readLine();
                return new JSONObject(apiOutput);
            } catch (Exception e) {
                try {
                    Log.i("devolOTest", "getResponseMessage - conn: " + conn.getResponseMessage());
                    final HttpURLConnection finalConn = conn;
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Toast.makeText(context, finalConn.getResponseMessage(), Toast.LENGTH_SHORT).show();
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }
                        }
                    });

                } catch (IOException a) {
                    a.printStackTrace();
                }
//                new Hadler(Looper.getMainLooper())
                //Log.d("json_e-->", e.getMessage());
            } finally {

                AppHelper.close(br);
                if (conn != null) {
                    conn.disconnect();
                }
            }
            return null;
        }
    }

    private class HTTPGetWithBrandHeader extends AsyncTask<String, Void, JSONObject> {

        Context context;

        HTTPGetWithBrandHeader(Context context) {
            this.context = context;
        }

        @Override
        protected JSONObject doInBackground(String... params) {
            HttpURLConnection conn = null;
            BufferedReader br = null;
            try {
                URL url = new URL(Constants.SERVICE_URL + params[0]);
                conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(15000);
                conn.setConnectTimeout(15000);
                conn.setRequestMethod("GET");
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setRequestProperty("Lazlo-AuthorityLicenseCode", Constants.TOKEN);
                conn.setRequestProperty("Lazlo-PlayerLicenseCode", Constants.PLAYER_TOKEN);
                conn.setRequestProperty("Lazlo-BrandLicenseCode", Constants.BRAND_LISENCE_CODE);

                //Log.d(TAG, "HTTPGetWithHeader: "+url);

                int code = conn.getResponseCode();
                if (code != 200) {
                    br = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
                    String apiOutput = br.readLine();
                    throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
                }

                br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
                String apiOutput = br.readLine();
                return new JSONObject(apiOutput);
            } catch (Exception e) {
                try {
                    Log.i("devolOTest", "getResponseMessage - conn: " + conn.getResponseMessage());
                    final HttpURLConnection finalConn = conn;
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Toast.makeText(context, finalConn.getResponseMessage(), Toast.LENGTH_SHORT).show();
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }
                        }
                    });

                } catch (IOException a) {
                    a.printStackTrace();
                }
                //Log.d("json_e-->", e.getMessage());
            } finally {
                AppHelper.close(br);
                if (conn != null) {
                    conn.disconnect();
                }
            }
            return null;
        }
    }

    private class HTTPPostWithBody extends AsyncTask<String, Void, JSONObject> {

        Context context;

        HTTPPostWithBody(Context context) {
            this.context = context;
        }

        @Override
        protected JSONObject doInBackground(String... params) {
            HttpURLConnection conn = null;
            BufferedReader br = null;
            try {
                URL url = new URL(Constants.SERVICE_URL + params[0]);
                conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(15000);
                conn.setConnectTimeout(15000);
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setRequestProperty("Lazlo-AuthorityLicenseCode", Constants.TOKEN);
                conn.setRequestProperty("Lazlo-BrandLicenseCode", Constants.BRAND_LISENCE_CODE);
                conn.setRequestProperty("Lazlo-PlayerLicenseCode", Constants.PLAYER_TOKEN);

                // Send post request
                conn.setDoOutput(true);
                conn.setDoInput(true);

                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                writer.write(params[1]);
                writer.flush();
                writer.close();
                os.close();

                int responseCode = conn.getResponseCode();
                if (responseCode != 200) {
                    br = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
                    throw new Exception("invalid response code:" + responseCode);
                } else {
                    br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                }
                String apiOutput = br.readLine();
                JSONObject obj = new JSONObject(apiOutput);

                return obj;
            } catch (Exception e) {
                //TODO - do what?
                try {
                    Log.i("devolOTest", "getResponseMessage - conn: " + conn.getResponseMessage());
                    final HttpURLConnection finalConn = conn;
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Toast.makeText(context, finalConn.getResponseMessage(), Toast.LENGTH_SHORT).show();
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }
                        }
                    });

                } catch (IOException a) {
                    a.printStackTrace();
                }
            } finally {
                AppHelper.close(br);
                if (conn != null) {
                    conn.disconnect();
                }
            }
            return null;
        }
    }

    private class HTTPPutWithBody extends AsyncTask<String, Void, JSONObject> {

        Context context;

        HTTPPutWithBody(Context context) {
            this.context = context;
        }

        @Override
        protected JSONObject doInBackground(String... params) {
            HttpURLConnection conn = null;
            BufferedReader br = null;
            try {
                URL url = new URL(Constants.SERVICE_URL + params[0]);
                conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(15000);
                conn.setConnectTimeout(15000);
                conn.setRequestMethod("PUT");
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setRequestProperty("Lazlo-AuthorityLicenseCode", Constants.TOKEN);
                conn.setRequestProperty("Lazlo-BrandLicenseCode", Constants.BRAND_LISENCE_CODE);
                conn.setRequestProperty("Lazlo-PlayerLicenseCode", Constants.PLAYER_TOKEN);

                // Send post request
                conn.setDoOutput(true);
                conn.setDoInput(true);

                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                writer.write(params[1]);
                writer.flush();
                writer.close();
                os.close();

                int responseCode = conn.getResponseCode();
                if (responseCode < 200 || responseCode > 300) {
                    br = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
                    String apiOutput = br.readLine();
                    throw new Exception("invalid response code:" + responseCode);
                }
                br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String apiOutput = br.readLine();
                JSONObject obj = new JSONObject();
                return obj;
            } catch (Exception e) {
                try {
                    Log.i("devolOTest", "getResponseMessage - conn: " + conn.getResponseMessage());
                    final HttpURLConnection finalConn = conn;
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Toast.makeText(context, finalConn.getResponseMessage(), Toast.LENGTH_SHORT).show();
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }
                        }
                    });

                } catch (IOException a) {
                    a.printStackTrace();
                }
                return null;
            } finally {
                AppHelper.close(br);
                if (conn != null) {
                    conn.disconnect();
                }
            }
        }
    }

    private class HTTPGetWithActionCode extends AsyncTask<String, Void, JSONObject> {
        Context context;

        HTTPGetWithActionCode(Context context) {
            this.context = context;
        }
        @Override
        protected JSONObject doInBackground(String... params) {
            HttpURLConnection conn = null;
            BufferedReader br = null;
            try {
                URL url = new URL(Constants.SERVICE_URL + params[0]);
                conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(15000);
                conn.setConnectTimeout(15000);
                conn.setRequestMethod("GET");
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setRequestProperty("Lazlo-AuthorityLicenseCode", Constants.TOKEN);
                conn.setRequestProperty("Lazlo-PlayerLicenseCode", Constants.PLAYER_TOKEN);
                conn.setRequestProperty("Lazlo-BrandLicenseCode", Constants.BRAND_LISENCE_CODE);
                conn.setRequestProperty("Lazlo-ActionLicenseCode", params[1]);

                int code = conn.getResponseCode();
                if (code != 200) {
                    br = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
                    String apiOutput = br.readLine();
                    throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
                }

                br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String apiOutput = br.readLine();
                return new JSONObject(apiOutput);
            } catch (Exception e) {
                //Log.d("json_e-->", e.getMessage());
                try {
                    Log.i("devolOTest", "getResponseMessage - conn: " + conn.getResponseMessage());
                    final HttpURLConnection finalConn = conn;
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Toast.makeText(context, finalConn.getResponseMessage(), Toast.LENGTH_SHORT).show();
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }
                        }
                    });

                } catch (IOException a) {
                    a.printStackTrace();
                }
            } finally {
                AppHelper.close(br);
                if (conn != null) {
                    conn.disconnect();
                }
            }
            return null;
        }
    }

    private class HTTPDownloadSocialImage extends AsyncTask<String, Void, Bitmap> {

        Context context;

        HTTPDownloadSocialImage(Context context) {
            this.context = context;
        }
        @Override
        protected Bitmap doInBackground(String... params) {
            HttpURLConnection conn = null;
            BufferedReader br = null;
            try {
                URL url = new URL(Constants.SERVICE_URL + params[0]);
                conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(15000);
                conn.setConnectTimeout(15000);
                conn.setRequestMethod("GET");
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setRequestProperty("Lazlo-AuthorityLicenseCode", Constants.TOKEN);
                conn.setRequestProperty("Lazlo-PlayerLicenseCode", Constants.PLAYER_TOKEN);

                conn.setDoInput(true);
                int code = conn.getResponseCode();
                if (code != 200) {
                    br = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
                    String apiOutput = br.readLine();
                    throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
                }

                InputStream input = conn.getInputStream();
                Bitmap myBitmap = BitmapFactory.decodeStream(input);
                return myBitmap;
            } catch (Exception e) {
                //Log.d("json_e-->", e.getMessage());
                try {
                    Log.i("devolOTest", "getResponseMessage - conn: " + conn.getResponseMessage());
                    final HttpURLConnection finalConn = conn;
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Toast.makeText(context, finalConn.getResponseMessage(), Toast.LENGTH_SHORT).show();
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }
                        }
                    });

                } catch (IOException a) {
                    a.printStackTrace();
                }
            } finally {

                if (conn != null) {
                    conn.disconnect();
                }
            }
            return null;
        }
    }


    public JSONObject httpGetMethod(Context context, String url) throws Exception {
        return new HTTPGetWithHeader(context).execute(url).get();
    }

    public JSONObject httpGetBrandMethod(Context context, String url) throws Exception {
        return new HTTPGetWithBrandHeader(context).execute(url).get();
    }

    public JSONObject httpPostMethod(Context context, String url, String body) throws Exception {
        return new HTTPPostWithBody(context).execute(url, body).get();
    }

    public JSONObject httpGetMethodWithActionCode(Context context, String url, String actionCode) throws Exception {
        return new HTTPGetWithActionCode(context).execute(url, actionCode).get();
    }

    public Bitmap httpDownloadSocialImage(Context context, String url) throws Exception {
        return new HTTPDownloadSocialImage(context).execute(url).get();
    }

    public Bitmap httpGetImageFromRemote(Context context, String url) throws Exception {
        return new HTTPDownloadSocialImage(context).execute(url).get();
    }

    public JSONObject httpPutMethod(Context context, String url, String body) throws Exception {
        return new HTTPPutWithBody(context).execute(url, body).get();
    }

}
