package com.hansung.android.soundlogin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {



    ListView listView;
    List fileList = new ArrayList<>();
    ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        String User = intent.getExtras().getString("user_name");
        String USER = User.trim();
        System.out.println("USER1:"+USER);



        Button device_button = (Button) findViewById(R.id.Sound_Login);
        Button record_button = (Button) findViewById(R.id.Record);
        TextView User_text = (TextView) findViewById(R.id.user_text);
        User_text.setText(User+" 님");


        FirebaseDatabase database1 = FirebaseDatabase.getInstance();
        DatabaseReference databaseRef1 = database1.getReference("record");

        listView = (ListView) findViewById(R.id.record_list);


        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, fileList){


            @Override

            public View getView(int position, View convertView, ViewGroup parent)

            {

                View view = super.getView(position, convertView, parent);

                TextView tv = (TextView) view.findViewById(android.R.id.text1);

                tv.setTextColor(Color.BLUE);


                return view;

            }

        };

        listView.setAdapter(adapter);

        device_button.setOnClickListener(new View.OnClickListener(){
         @Override
        public void onClick(View view) {
            Intent intent = new Intent(getApplicationContext(), RegisActivity.class);
            intent.putExtra("user_name", User);
            startActivity(intent);
        }
    });
        record_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                System.out.println("USER2:"+USER);

                databaseRef1.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        for (DataSnapshot fileSnapshot : dataSnapshot.getChildren()) {
                            if(fileSnapshot.exists()) {
                                String name = fileSnapshot.child("name").getValue(String.class);
                                System.out.println("USER3:"+name);
                                if(USER.equals(name.trim())) {
                                    String time = fileSnapshot.child("time").getValue(String.class);
                                    System.out.println("로그인 이력 띄우기: "+time);
                                    fileList.add(time);

                                    adapter.notifyDataSetChanged();

                                }
                            }
                            else{
                                Toast.makeText(MainActivity.this, "최근 로그인 이력이 없습니다.", Toast.LENGTH_SHORT).show();
                            }
                        }
                        adapter.notifyDataSetChanged();

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.w("TAG: ", "Failed to read value", databaseError.toException());
                    }
                });

            }
        });


    }

    @Override
    public void onBackPressed() {
        //초반 플래시 화면에서 넘어갈때 뒤로가기 버튼 못누르게 함
    }
}
