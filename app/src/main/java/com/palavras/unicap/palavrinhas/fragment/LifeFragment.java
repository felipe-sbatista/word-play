package com.palavras.unicap.palavrinhas.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import com.palavras.unicap.palavrinhas.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class LifeFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private List<String> vidas = new ArrayList(Arrays.asList("vida1", "vida2", "vida3"));

    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public LifeFragment() {}

    public static LifeFragment newInstance(String param1, String param2) {
        LifeFragment fragment = new LifeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_life, container, false);
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    public void reduzir() {
        String vida = vidas.get(0);
        vidas.remove(0);
        int id = getResources().getIdentifier(vida, "id", getActivity().getPackageName());
        ImageView imageView = getView().findViewById(id);
        imageView.setVisibility(View.GONE);
        Vibrator vibrator = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(VibrationEffect.createOneShot(100, VibrationEffect.DEFAULT_AMPLITUDE));
    }

    public boolean isFinished() {
        return vidas.size() < 1;
    }

    public void restoreLife() {
        this.vidas.add("vida1");
        int id = getResources().getIdentifier(this.vidas.get(0), "id", getActivity().getPackageName());
        ImageView imageView = getView().findViewById(id);
        imageView.setVisibility(View.VISIBLE);
    }

}
