package tw.cody.test09;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    private RequestQueue queue;
    private ImageView img;
    private Bitmap bitmap;
    private UIHandler uiHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        queue = Volley.newRequestQueue(this);

        img = findViewById(R.id.img);
        uiHandler = new UIHandler();
    }

    public void test1(View view) {
        new Thread() {
            @Override
            public void run() {
                test11();
            }
        }.start();
    }


    private void test11() {
        try {
            URL url = new URL("http://192.168.1.107/brad01.html");
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.connect();
            InputStream in = conn.getInputStream();
            InputStreamReader inr = new InputStreamReader(in);
            BufferedReader br = new BufferedReader(inr);

            String line;
            while ((line = br.readLine())!=null) {
                Log.v("cody",line);
            }

            in.close();
        } catch (Exception e) {
            Log.v("cody",e.toString());
        }
    }

    public void test2(View view) {
        Response.Listener<String> listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                test12(response);
            }
        } ;
        Response.ErrorListener error = new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.v("cody",error.toString());
            }
        } ;
        StringRequest request = new StringRequest(Request.Method.GET,"http://60.249.210.49/opendata/pub/json/cctv/latest_info",listener,error);

        queue.add(request);
    }


    private void test12(String json) {
        try {
            JSONArray jsonArray = new JSONArray(json);   //JSONObject JSONArray差別
            for (int i = 0;i < jsonArray.length();i++) {
                JSONObject object = jsonArray.getJSONObject(i);
                String 位置 = object.getString("位置");
                Log.v("cody",位置);
            }
        } catch (Exception e) {
            Log.v("cody",e.toString());
        }
    }

    public void test3(View view) {
        new Thread() {
            @Override
            public void run() {
                test13();
            }
        }.start();
    }


    private void test13() {
        try {
            URL url = new URL("https://i.pinimg.com/736x/e8/1a/28/e81a284e1f813f00cf08045a47a4dfd6.jpg");
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.connect();

            bitmap = BitmapFactory.decodeStream(conn.getInputStream());
            uiHandler.sendEmptyMessage(0);
        } catch (Exception e) {
            Log.v("cody",e.toString());
        }
    }

    private class UIHandler extends Handler {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            img.setImageBitmap(bitmap);
        }
    }
}