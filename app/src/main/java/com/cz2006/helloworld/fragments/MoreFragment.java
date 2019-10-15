package com.cz2006.helloworld.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import com.cz2006.helloworld.R;
import com.cz2006.helloworld.activities.FaqActivity;
import com.cz2006.helloworld.activities.FeedbackActivity;
import com.cz2006.helloworld.activities.LeaderboardActivity;
import com.cz2006.helloworld.activities.ProfileActivity;
import com.cz2006.helloworld.managers.SessionManager;

/**
 * Represents More Fragment linking from BottomNavigation Activity
 *
 * @author Rosario Gelli Ann
 *
 */
public class MoreFragment extends Fragment implements View.OnClickListener {

    private AppCompatButton profileBtn;
    private AppCompatButton leaderboardBtn;
    private AppCompatButton feedbackBtn;
    private AppCompatButton faqBtn;
    private AppCompatButton signoutBtn;

    private SessionManager sessionManager;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public MoreFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MoreFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MoreFragment newInstance(String param1, String param2) {
        MoreFragment fragment = new MoreFragment();
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
        View v = inflater.inflate(R.layout.fragment_more, container, false);

        //Initialize Variables
        init(v);

        profileBtn.setOnClickListener(this);
        leaderboardBtn.setOnClickListener(this);
        feedbackBtn.setOnClickListener(this);
        faqBtn.setOnClickListener(this);
        signoutBtn.setOnClickListener(this);

        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    // TODO: Rename method, update argument and hook method into UI event
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

    // OnClickListener implementation
    @Override
    public void onClick(View v) {

        Intent intent = null;
        switch (v.getId()) {
            case R.id.btnProfile:
                intent = new Intent(getActivity(), ProfileActivity.class);
                break;
            case R.id.btnLeaderboard:
                intent = new Intent(getActivity(), LeaderboardActivity.class);
                break;
            case R.id.btnFeedback:
                intent = new Intent(getActivity(), FeedbackActivity.class);
                break;
            case R.id.btnFAQ:
                intent = new Intent(getActivity(), FaqActivity.class);
                break;
            case R.id.btnSignOut:
                sessionManager.checkLogin(); // Check if user is logged in
                sessionManager.logoutUser(); // Log out user
                Toast.makeText(getContext(), "Logged out successfully!", Toast.LENGTH_SHORT).show();
                break;

        }

        if(intent != null)
            startActivity(intent);
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    /**
     *
     * Methods
     *
     */
    public void init(View v){
        // Initialize variables
        sessionManager = new SessionManager(getContext());

        profileBtn = v.findViewById(R.id.btnProfile);
        leaderboardBtn = v.findViewById(R.id.btnLeaderboard);
        feedbackBtn = v.findViewById(R.id.btnFeedback);
        faqBtn = v.findViewById(R.id.btnFAQ);
        signoutBtn = v.findViewById(R.id.btnSignOut);


    }



}
