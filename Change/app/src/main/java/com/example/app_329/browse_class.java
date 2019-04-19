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

public class browse_class extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
         View browseLayout=inflater.inflate(R.layout.activity_url,container,false);
         super.onActivityCreated(savedInstanceState);
         Intent intent=getActivity().getIntent();
         String title=intent.getStringExtra("title");
         getActivity().setTitle(title);
         ImageView imageView=(ImageView)browseLayout.findViewById(R.id.btn_browse_url);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText editUrl=(EditText)getActivity().findViewById(R.id.edit_url);
                String strUrl=editUrl.getText().toString();

                Uri uri=Uri.parse(strUrl);
                Intent it=new Intent(Intent.ACTION_VIEW,uri);
                startActivity(it);
            }
        });
         return browseLayout;

  }

//    @Overrid
//    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//        Intent intent=this.getIntent();
//        String title=intent.getStringExtra("title");
//        this.setTitle(title);
//        ImageView imageView=(ImageView)getActivity().findViewById(R.id.btn_browse_url);
//    }

}
