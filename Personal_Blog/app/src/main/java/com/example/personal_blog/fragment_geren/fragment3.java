package com.example.personal_blog.fragment_geren;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.personal_blog.R;

public class fragment3 extends Fragment  {
    Button btn_register;
    TextView textView;
    public fragment3() {
    }

    public static fragment3 newInstance() {
        fragment3 fragment = new fragment3();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
       // Intent intent=new Intent(getActivity(),Info_center.class);
       // startActivity(intent);
      /*  Button register = (Button)getActivity().findViewById(R.id.register);
        final Button login=(Button)getActivity().findViewById(R.id.login);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent3=new Intent(getActivity(),register.class);
                startActivity(intent3);

            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent intent=new Intent(getActivity(),login.class);
               startActivity(intent);


            }
        });*/



    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View geren_Fragment=inflater.inflate(R.layout.geren_fragment,container,false);
       //return super.onCreateView(inflater, container, savedInstanceState);
       // geren_Fragment.setOnClickListener((View.OnClickListener) this);
        return geren_Fragment;
    }
}
