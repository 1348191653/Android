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

public class message_class extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View messageLayout=inflater.inflate(R.layout.activity_sms,container,false);
        Intent intent=getActivity().getIntent();
        String title=intent.getStringExtra("title");
        getActivity().setTitle(title);
        ImageView btnStartSend=(ImageView)messageLayout.findViewById(R.id.btn_start_send);
        btnStartSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText smsPhoneNum=(EditText)getActivity().findViewById(R.id.edit_sms_num);
                EditText editSmsContent=(EditText)getActivity().findViewById(R.id.edit_sms_content);
                String smsNum=smsPhoneNum.getText().toString();
                String smsContent=editSmsContent.getText().toString();

                Uri uri=Uri.parse("smsto:"+smsNum);
                Intent intent=new Intent(Intent.ACTION_SENDTO,uri);
                intent.putExtra("sms_body",smsContent);
                startActivity(intent);
            }
        });
         return messageLayout;
    }
}
