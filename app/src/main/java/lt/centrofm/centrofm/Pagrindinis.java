package lt.centrofm.centrofm;


import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

public class Pagrindinis extends Fragment {

    public Pagrindinis() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.pagrindinis_fragment, container,
                false);

        final ImageView img = (ImageView)rootView.findViewById(R.id.play);
        final ImageView img1 = (ImageView)rootView.findViewById(R.id.pause);
        final Intent serviceIntent = new Intent(getActivity(), BackgroundRadio.class);


        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED)
        {
            img.setVisibility(View.INVISIBLE);
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