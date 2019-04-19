package com.example.app_329;

//import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

public class call_class extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        final View callLayout=inflater.inflate(R.layout.activity_phone,container,false);
        Intent intent=getActivity().getIntent();
        Bundle bundle=intent.getExtras();
        String title=intent.getStringExtra("title");
        getActivity().setTitle(title);
        ImageView btnStartDial=(ImageView)callLayout.findViewById(R.id.btn_start_dial);
        btnStartDial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText editPhoneNum=(EditText)getActivity().findViewById(R.id.edit_phone_num);
                String phoneNum=editPhoneNum.getText().toString();
                Intent intent=new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+phoneNum));
                startActivity(intent);
            }
        });
         return callLayout;
    }
}
