package com.aritra.mobioticspoc.presentation.ui.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.aritra.mobioticspoc.R;
import com.aritra.mobioticspoc.domain.executor.impl.ThreadExecutor;
import com.aritra.mobioticspoc.domain.model.PiResponse;
import com.aritra.mobioticspoc.presentation.adapters.VideoListAdapter;
import com.aritra.mobioticspoc.presentation.presenters.VideoListPresenter;
import com.aritra.mobioticspoc.presentation.presenters.impl.VideoListPresenterImpl;
import com.aritra.mobioticspoc.threading.MainThreadImpl;

import java.util.concurrent.atomic.AtomicBoolean;

public class VideoListFragment extends Fragment implements VideoListPresenter.View {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private RecyclerView videoRecyclerView;
    private VideoListPresenter videoListPresenter;
    private static final String TAG = "VideoListFragment";
    private AtomicBoolean isLoaded = new AtomicBoolean(false);

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    ProgressDialog dialogLoading;
    private PiResponse mPiResponse;
    private OnFragmentInteractionListener mListener;
    private Context mContext;
    private TextView header;

    public VideoListFragment() {

    }


    public static VideoListFragment newInstance(String param1, String param2) {
        VideoListFragment fragment = new VideoListFragment();
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
        videoListPresenter = new VideoListPresenterImpl(ThreadExecutor.getInstance(), MainThreadImpl.getInstance(), mContext, this);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_video_list, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        videoRecyclerView = (RecyclerView) getActivity().findViewById(R.id.video_rv);
        header = (TextView) getActivity().findViewById(R.id.main_header);

    }

    @Override
    public void onResume() {
        super.onResume();

            if(isLoaded.get()==false){
                showProgress(true);
                videoListPresenter.getAllCurrencies(checkNetworkConnection());
            }
    }

    private Boolean checkNetworkConnection() {
        ConnectivityManager connectivityManager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnectedOrConnecting()) {
            return true;
        }
        return false;
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
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
        mContext = null;
    }

    @Override
    public void onCurrenciesLoaded(PiResponse piResponse) {
        showProgress(false);
        mPiResponse = piResponse;
        updateUI();
    }

    public void showProgress(boolean show) {
        if (show && dialogLoading == null) {
            dialogLoading = ProgressDialog.show(getContext(), "", getString(R.string.loading_video_dialog), true);
            dialogLoading.setCancelable(false);
            dialogLoading.show();
        } else if (dialogLoading != null) {
            dialogLoading.dismiss();
            dialogLoading = null;
        }
    }

    private void updateUI() {

        try {
            if (mPiResponse != null && mPiResponse.getResult()!=null && !mPiResponse.getResult().isEmpty()) {
                header.setText(R.string.list_of_currency_header);
                VideoListAdapter videoListAdapter = new VideoListAdapter(mPiResponse.getResult(),mContext);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
                videoRecyclerView.setNestedScrollingEnabled(false);
                videoRecyclerView.setLayoutManager(linearLayoutManager);
                videoRecyclerView.setAdapter(videoListAdapter);
                isLoaded.getAndSet(true);

            } else {
                header.setText(R.string.no_data_loaded);
            }
        } catch (Exception e) {
            Log.d(TAG, e.getMessage());
            header.setText(R.string.no_data_loaded);

        }
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
