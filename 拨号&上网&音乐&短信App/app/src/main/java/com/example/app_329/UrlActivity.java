package com.example.app_329;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

public class UrlActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_url);

        Intent intent=this.getIntent();
        String title=intent.getStringExtra("title");
        this.setTitle(title);
        ImageView btnBrowseUri=(ImageView)findViewById(R.id.btn_browse_url);
        btnBrowseUri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText editUrl=(EditText)findViewById(R.id.edit_url);
                String strUrl=editUrl.getText().toString();

                Uri uri=Uri.parse(strUrl);
                Intent it=new Intent(Intent.ACTION_VIEW,uri);
                startActivity(it);
            }
        });
    }
}
