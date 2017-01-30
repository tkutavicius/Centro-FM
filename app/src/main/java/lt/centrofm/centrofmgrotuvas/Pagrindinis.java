package lt.centrofm.centrofmgrotuvas;


import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Random;

public class Pagrindinis extends Fragment {

    public Pagrindinis() {
    }

    String loginURL="http://centrofm.lt/json/groja-json.php";
    String data = "";
    RequestQueue requestQueue;

    String reklamos[] = {"Internto svetainė - www.centrofm.lt","Apsilankykite mūsų Facebook puslapyje!","Klausykite CentroFM 106.1 MHz dažniu Kėdainių rajone!", "Klausykite Lietuvos bei pasaulio žinių kiekvienos valandos pradžioje!", "CentroFM - tai mūsų radijas!", "CentroFM - Kėdainių regioninė radijo stotis!", "Laidų įrašus galite rasti www.centrofm.lt", "Radijo programą galite rasti www.centrofm.lt", "Centro FM - tiesiai į ausį!"};
    final Random rand = new Random();
    int reiksme = rand.nextInt(9);

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.pagrindinis_fragment, container,
                false);

        final ImageView img = (ImageView)rootView.findViewById(R.id.play);
        final ImageView img1 = (ImageView)rootView.findViewById(R.id.pause);
        final TextView output = (TextView) rootView.findViewById(R.id.textView);
        requestQueue = Volley.newRequestQueue(getActivity());
        final Intent serviceIntent = new Intent(getActivity(), BackgroundRadio.class);

        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED)
        {
            JsonObjectRequest jor = new JsonObjectRequest(Request.Method.GET, loginURL, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {

                            try{

                                JSONArray ja = response.getJSONArray("songs");

                                for(int i=0; i < 1; i++){

                                    JSONObject jsonObject = ja.getJSONObject(i);

                                    // int id = Integer.parseInt(jsonObject.optString("id").toString());
                                    String artist = jsonObject.getString("artist");
                                    String track = jsonObject.getString("track");

                                    data = artist +" - "+ track +"";
                                }
                                output.setText(data);
                            }catch(JSONException e){
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            output.setText(reklamos[reiksme]);
                            Log.e("Volley","Error");

                        }
                    }
            );
            requestQueue.add(jor);
            img.setVisibility(View.INVISIBLE);
            getActivity().startService(serviceIntent);
            Toast.makeText(getActivity(),
                    "Kraunama...", Toast.LENGTH_LONG).show();
            final Handler refresh = new Handler();
            refresh.postDelayed(new Runnable() {
                @Override
                public void run() {
                    reiksme = rand.nextInt(9);
                    JsonObjectRequest jor = new JsonObjectRequest(Request.Method.GET, loginURL, null,
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {

                                    try{

                                        JSONArray ja = response.getJSONArray("songs");

                                        for(int i=0; i < 1; i++){

                                            JSONObject jsonObject = ja.getJSONObject(i);

                                            // int id = Integer.parseInt(jsonObject.optString("id").toString());
                                            String artist = jsonObject.getString("artist");
                                            String track = jsonObject.getString("track");

                                            data = artist +" - "+ track +"";
                                        }
                                        output.setText(data);
                                    }catch(JSONException e){
                                        e.printStackTrace();
                                    }
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Log.e("Volley","Error");
                                    output.setText(reklamos[reiksme]);

                                }
                            }
                    );
                    requestQueue.add(jor);
                    refresh.postDelayed(this, 10000);
                }
            }, 10000);
        }
        else
        {
            img1.setVisibility(View.INVISIBLE);
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("Perspėjimas!");
            builder.setMessage("Prašome prisijungti prie interneto!");
            AlertDialog alert = builder.create();
            alert.show();
        }
        img1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                img.setVisibility(View.VISIBLE);
                img1.setVisibility(View.INVISIBLE);
                getActivity().stopService(serviceIntent);
            }
        });

        img.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
                if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                        connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED)
                    {
                        img.setVisibility(View.INVISIBLE);
                        img1.setVisibility(View.VISIBLE);
                        getActivity().startService(serviceIntent);
                        Toast.makeText(getActivity(),
                                "Kraunama...", Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        img1.setVisibility(View.INVISIBLE);
                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                        builder.setTitle("Perspėjimas!");
                        builder.setMessage("Prašome prisijungti prie interneto!");
                        AlertDialog alert = builder.create();
                        alert.show();
                    }
            }
        });

        return rootView;
    }

}