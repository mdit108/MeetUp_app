//package com.example.android.meetup;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import android.os.Bundle;
//
//public class Interests extends AppCompatActivity {
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_interests);
//    }
//}
package com.example.android.meetup;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.chip.Chip;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

//import example.android.chip.R;

public class Interests extends AppCompatActivity {

    private Chip chip4,chip5,chip6,chip7,chip8,chip9,chip10,chip11,chip12,chip13,chip14,chip15,chip16,chip17,chip18,chip19,chip20,chip21,chip22,chip23,chip24,chip25,chip26,chip27;
    //FirebaseFirestore fstore;
    DatabaseReference reference;
    FirebaseAuth fAuth;
    Button button;
    private ArrayList<String> selectedchips;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interests);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        fAuth= FirebaseAuth.getInstance();
        // fstore=FirebaseFirestore.getInstance();
        FirebaseUser firebaseUser = fAuth.getCurrentUser();
        chip4=findViewById(R.id.chip4);
        chip5=findViewById(R.id.chip5);
        chip6=findViewById(R.id.chip6);
        chip7=findViewById(R.id.chip7);
        chip8=findViewById(R.id.chip8);
        chip9=findViewById(R.id.chip9);
        chip10=findViewById(R.id.chip10);
        chip11=findViewById(R.id.chip11);
        chip12=findViewById(R.id.chip12);
        chip13=findViewById(R.id.chip13);
        chip14=findViewById(R.id.chip14);
        chip15=findViewById(R.id.chip15);
        chip16=findViewById(R.id.chip16);
        chip17=findViewById(R.id.chip17);
        chip18=findViewById(R.id.chip18);
        chip19=findViewById(R.id.chip19);
        chip20=findViewById(R.id.chip20);
        chip21=findViewById(R.id.chip21);
        chip22=findViewById(R.id.chip22);
        chip23=findViewById(R.id.chip23);
        chip24=findViewById(R.id.chip24);
        chip25=findViewById(R.id.chip25);
        chip26=findViewById(R.id.chip26);
        chip27=findViewById(R.id.chip27);

        selectedchips = new ArrayList<>();
        int i=0;
        int n=0;

        CompoundButton.OnCheckedChangeListener checkedChangeListener = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    selectedchips.add(buttonView.getText().toString());
                    //i++;
                }
                else{
                    selectedchips.remove(buttonView.getText().toString());
                    //i--;
                }
//                if(i<5){
//                    Log.d("Error");
//                }
                for ( String singleRecord : selectedchips)
                {
                    Log.d("Records:Button--", singleRecord.toString());
                }
            }


        };
//        n=selectedchips.size();
//
//        String str[] = new String[n];
//        for(int j=0;j<n;j++){
//            str[j]=selectedchips.get(j);
//
//        }

        chip4.setOnCheckedChangeListener(checkedChangeListener);
        chip5.setOnCheckedChangeListener(checkedChangeListener);
        chip6.setOnCheckedChangeListener(checkedChangeListener);
        chip7.setOnCheckedChangeListener(checkedChangeListener);
        chip8.setOnCheckedChangeListener(checkedChangeListener);
        chip9.setOnCheckedChangeListener(checkedChangeListener);
        chip10.setOnCheckedChangeListener(checkedChangeListener);
        chip11.setOnCheckedChangeListener(checkedChangeListener);
        chip12.setOnCheckedChangeListener(checkedChangeListener);
        chip13.setOnCheckedChangeListener(checkedChangeListener);
        chip14.setOnCheckedChangeListener(checkedChangeListener);
        chip15.setOnCheckedChangeListener(checkedChangeListener);
        chip16.setOnCheckedChangeListener(checkedChangeListener);
        chip17.setOnCheckedChangeListener(checkedChangeListener);
        chip18.setOnCheckedChangeListener(checkedChangeListener);
        chip19.setOnCheckedChangeListener(checkedChangeListener);
        chip20.setOnCheckedChangeListener(checkedChangeListener);
        chip21.setOnCheckedChangeListener(checkedChangeListener);
        chip22.setOnCheckedChangeListener(checkedChangeListener);
        chip23.setOnCheckedChangeListener(checkedChangeListener);
        chip24.setOnCheckedChangeListener(checkedChangeListener);
        chip25.setOnCheckedChangeListener(checkedChangeListener);
        chip26.setOnCheckedChangeListener(checkedChangeListener);
        chip27.setOnCheckedChangeListener(checkedChangeListener);

        button = (Button) findViewById(R.id.done_bt);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int n=selectedchips.size();
                if(n<5) {
                    Toast.makeText(Interests.this, "Select minimum 5 interests ", Toast.LENGTH_SHORT).show();
                }
                else {
                    userID = firebaseUser.getUid();
                    reference = FirebaseDatabase.getInstance().getReference("Users").child(userID);
                    Map<String,Object> user = new HashMap<>();
                    user.put("interests",selectedchips);
                    reference.updateChildren(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.v("tag","Interests added for "+userID);
                        }
                    });
                    openNewActivity();
                }
            }

        });



    }

    public void openNewActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        //intent.putExtra("key", selectedchips);
        startActivity(intent);
    }
}