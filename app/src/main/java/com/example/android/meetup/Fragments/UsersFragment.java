package com.example.android.meetup.Fragments;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.meetup.Adapter.UserAdapter;
import com.example.android.meetup.Model.User;
import com.example.android.meetup.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/*
 * A simple {@link Fragment} subclass.
 * Use the {@link UsersFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UsersFragment extends Fragment {
    private RecyclerView recyclerView;
    private UserAdapter userAdapter;
    private List<User> mUsers;
    private ArrayList<Integer> matchpercent;
    EditText search_users;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_users, container, false);
        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mUsers = new ArrayList<>();
        matchpercent= new ArrayList<Integer>();

        readUsers();
        search_users=view.findViewById(R.id.search_users);
        search_users.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                    searchUsers(s.toString().toLowerCase());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        return view;
    }
    private void searchUsers(String s){
        final User user3=new User();
        FirebaseUser fUser=FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference2 = FirebaseDatabase.getInstance().getReference("Users");
        reference2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snapshot1 : snapshot.getChildren() ){
                    User user=snapshot1.getValue(User.class);
                    if (user.getId().equals(fUser.getUid())) {
                        user3.setInterests(user.getInterests());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        Query query=FirebaseDatabase.getInstance().getReference("Users").orderByChild("search").startAt(s).endAt(s+"\uf0ff");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mUsers.clear();
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    User user=dataSnapshot.getValue(User.class);
                    assert user!=null;
                    assert fUser!=null;
                    if (!user.getId().equals(fUser.getUid())){
                        ArrayList<String> u1=user.getInterests();  //Interest array for other users
                        ArrayList<String> u2=user3.getInterests();
                        int n=0;
                        for (String element : u2){
                            for (String ele : u1){
                                if (ele.contains(element)){
                                    n+=1;
                                }
                            }
                        }

                        if (n>=3) {
                            mUsers.add(user);
                        }
                    }
                }


                userAdapter=new UserAdapter(getContext(),mUsers, Collections.singletonList(0),false);
                recyclerView.setAdapter(userAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }



    private void readUsers() {
        final User user2=new User();
        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
        DatabaseReference reference2 = FirebaseDatabase.getInstance().getReference("Users");
        reference2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snapshot1 : snapshot.getChildren() ){
                    User user=snapshot1.getValue(User.class);
                    if (user.getId().equals(firebaseUser.getUid())) {
                        user2.setInterests(user.getInterests());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (search_users.getText().toString().equals("")) {
                mUsers.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    User user = snapshot.getValue(User.class);
                    assert user != null;
                    assert firebaseUser != null;

                    if (!user.getId().equals(firebaseUser.getUid())) {
                        Log.d("COUNTTTT","userrrrr");
                        ArrayList<String> u1=user.getInterests();  //Interest array for other users
                        ArrayList<String> u2=user2.getInterests();
                        int m=u2.size();
                        Log.d("interests diff users",String.valueOf(u1.size())+user.getUsername());
                        Log.d("m value", String.valueOf(m));
                        int n=0;
//                        ArrayList<String> u3=u2;
//                        u3.retainAll(u1);
//                        int n=u3.size();
                        Log.d("number of commons", String.valueOf(n));
                        //Log.d("common interests",u3.toString());
                        for (String element : u2){
                            for (String ele : u1){
                                if (ele.contains(element)){
                                    n+=1;
                                }
                            }
                        }
                        if (n>=3){
                            mUsers.add(user);
                            Log.d("SIZEE OF USERS", String.valueOf(mUsers.size()));
                            matchpercent.add(((n)*100)/m);
                        }


                    }

                }

                userAdapter = new UserAdapter(getContext(), mUsers,matchpercent,true);
                recyclerView.setAdapter(userAdapter);

            }}

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}