package com.example.messengerapp.Fragments;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.messengerapp.Modals.Status;
import com.example.messengerapp.Modals.User;
import com.example.messengerapp.Modals.UserStatus;
import com.example.messengerapp.R;
import com.example.messengerapp.StatusAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class storyFragment3 extends Fragment {


    public storyFragment3() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    StatusAdapter statusAdapter;
    ArrayList<UserStatus> statusArrayList;
    RecyclerView recyclerView;
    FloatingActionButton camerastatusBtn;
    ProgressDialog progressDialog;
    DatabaseReference databaseReference;
    FirebaseAuth auth;
    User user;
    private final static int Img_Req_code = 2;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_story3, container, false);

        recyclerView = view.findViewById(R.id.statusrecyclerView_id);
        camerastatusBtn = view.findViewById(R.id.statusflotingBtn_id);

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Uploading Image....");
        progressDialog.setCancelable(false);

        databaseReference = FirebaseDatabase.getInstance().getReference();
        auth = FirebaseAuth.getInstance();

        databaseReference.child("Users").child(auth.getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        user = snapshot.getValue(User.class);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(getActivity(), "Data get failed" + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        camerastatusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent();
                intent.setType("video/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, Img_Req_code);
            }
        });

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        statusArrayList = new ArrayList<>();
        statusAdapter = new StatusAdapter(getContext(), statusArrayList);

        recyclerView.setAdapter(statusAdapter);

        databaseReference.child("Status").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists()) {
                    statusArrayList.clear();
                    for (DataSnapshot snapshot1 : snapshot.getChildren()) {

                        UserStatus userStatus = new UserStatus();
                        userStatus.setProfile_name(snapshot1.child("profile_name").getValue(String.class));
                        userStatus.setProfile_image(snapshot1.child("profile_image").getValue(String.class));
                        userStatus.setLastUpdated(snapshot1.child("lastUpdated").getValue(String.class));
                        statusArrayList.add(userStatus);

                        ArrayList<Status> statuses = new ArrayList<>();

                        for (DataSnapshot statusSnapshot : snapshot1.child("statuses").getChildren()) {

                            Status status1 = statusSnapshot.getValue(Status.class);
                            statuses.add(status1);

                        }
                        userStatus.setStatuses(statuses);
                    }
                    statusAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "error" + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data != null) {
            if (data.getData() != null) {

                progressDialog.show();
                FirebaseStorage storage = FirebaseStorage.getInstance();
                DateFormat format = new SimpleDateFormat("h:mm a");
                String time = format.format(Calendar.getInstance().getTime());

                StorageReference reference = storage.getReference().child("UsersStatus").child(time);
                reference.putFile(data.getData()).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {

                        if (task.isSuccessful()) {

                            reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {

                                    UserStatus userStatus = new UserStatus();
                                    userStatus.setProfile_name(user.getProfilename());
                                    userStatus.setProfile_image(user.getProfileimg());
                                    userStatus.setLastUpdated(time);

                                    HashMap<String, Object> map = new HashMap<>();
                                    map.put("profile_name", userStatus.getProfile_name());
                                    map.put("profile_image", userStatus.getProfile_image());
                                    map.put("lastUpdated", userStatus.getLastUpdated());

                                    String statusUrl = uri.toString();
                                    Status status = new Status(statusUrl, userStatus.getLastUpdated());

                                    databaseReference.child("Status").child(auth.getUid())
                                            .updateChildren(map);

                                    databaseReference.child("Status")
                                            .child(auth.getUid())
                                            .child("statuses")
                                            .push()
                                            .setValue(status);

                                    progressDialog.dismiss();
                                }
                            });
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext(), "Status not Update", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
    }
}