package app.vir.com.jsonhandler.controller;

import android.content.Context;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import app.vir.com.jsonhandler.AppController;
import app.vir.com.jsonhandler.model.api.GsonRequest;
import app.vir.com.jsonhandler.model.pojo.Album;


public class Controller {

    private String url = "https://jsonplaceholder.typicode.com/albums";
    private CallbackListener mListener;
    private Context mcontext;

    public Controller(CallbackListener listener, Context context) {
        mListener = listener;
        mcontext = context;
    }

    public void startFetching() {

        GsonRequest<Album[]> getPersons =
                new GsonRequest<Album[]>(url, Album[].class,null,

                        new Response.Listener<Album[]>() {
                            @Override
                            public void onResponse(Album[] response) {
                                List<Album> albumList = Arrays.asList(response);
                                mListener.onFetchComplete(albumList);
                            }

                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        mListener.onFetchFailed();
                    }
                });


        AppController.getInstance().addToRequestQueue(getPersons);

    }

      public interface CallbackListener {

        void onFetchComplete(List<Album> album);

        void onFetchFailed();
    }
}
