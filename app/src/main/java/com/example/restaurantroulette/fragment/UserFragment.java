package com.example.restaurantroulette.fragment;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.restaurantroulette.LoginActivity;
import com.example.restaurantroulette.R;
import com.example.restaurantroulette.User;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;

public class UserFragment extends BaseFragment implements View.OnClickListener{
    private Button btLogout;

    TextView tvUsername;
    ImageView ivProfilePhoto;
    public User user = (User) ParseUser.getCurrentUser();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        // Defines the xml file for the fragment
        return inflater.inflate(R.layout.fragment_user, parent, false);
    }

    // This event is triggered soon after onCreateView().
    // Any view setup should occur here.  E.g., view lookups and attaching view listeners.
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // Setup any handles to view objects here
        // EditText etFoo = (EditText) view.findViewById(R.id.etFoo);
        tvUsername = view.findViewById(R.id.tvUsername);
        ivProfilePhoto = view.findViewById(R.id.ivProfilePhoto);
        ivProfilePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //take photo
                launchCamera();
            }
        });

        user.fetchInBackground(new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject object, ParseException e) {
                user = (User) object;
                displayUserInfo();
            }
        });
    }

    public void displayUserInfo(){
        tvUsername.setText(user.getUsername());
        ParseFile profilePhoto = user.getProfilePhoto();
        if(profilePhoto != null){
            Glide.with(getContext()).load(user.getProfilePhoto().getUrl()).circleCrop().into(ivProfilePhoto);
        }else {
            Toast.makeText(getContext(), "Profile Photo does not exist for " + user.getUsername(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                // by this point we have the camera photo on disk
                Bitmap takenImage = BitmapFactory.decodeFile(photoFile.getAbsolutePath());
                // RESIZE BITMAP, see section below
                // Load the taken image into a preview
                Glide.with(getContext()).load(takenImage).circleCrop().into(ivProfilePhoto);
                user.setProfilePhoto(new ParseFile(photoFile));
                user.saveInBackground();
            } else { // Result was a failure
                Toast.makeText(getContext(), "Picture wasn't taken!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onClick(View v) {

    }
//    View view;
//    // Required empty public constructor
//    public UserFragment() {}
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        view = inflater.inflate(R.layout.fragment_user, container, false);
//        btLogout = view.findViewById(R.id.btLogout);
//        //when the user clicks the logout button
//        btLogout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(getContext(), "Logged out!", Toast.LENGTH_SHORT).show();
//                onLogout();
//            }
//        });
//        return view;
//    }
//    private void onLogout() {
//        //logout of account
//        ParseUser.logOut();
//        ParseUser currentUser =ParseUser.getCurrentUser();
//        //redirect to login page
//        Intent i = new Intent(getContext(), LoginActivity.class);
//        startActivity(i);
//    }
//    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstances) {
//        super.onViewCreated(view, savedInstances);
//    }
//    //empty Onclick for onclicklistener implementation
//    @Override
//    public void onClick(View v) {
//    }

}