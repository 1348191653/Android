package com.example.test;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;
public class MainActivity extends AppCompatActivity {
    private EditText editInput;
    private RadioButton ceslsiusBtton;
    private RadioButton fahrenheitButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editInput=(EditText)findViewById(R.id.editInput);
        ceslsiusBtton=(RadioButton)findViewById(R.id.rbtnCelsius);
        fahrenheitButton=(RadioButton)findViewById(R.id.rbtnFahren);
    }
    private float convertFtoC(float fathrenheit){
        return ((fathrenheit-32.0f)/1.8f);
    }
    private float convertCtoF(float celsius){
        return (celsius * 1.8f)+32.0f;
    }
    public void onClick(View view){
        switch (view.getId()){
            case R.id.button:
            if(editInput.getText().length()==0){
               Toast.makeText(this,"enter a valid number",Toast.LENGTH_LONG).show();
               return;
            }
            float inputValue=Float.parseFloat(editInput.getText().toString());
            if(ceslsiusBtton.isChecked()){
                float celValue = convertCtoF(inputValue);
                editInput.setText(String.valueOf(celValue));
                ceslsiusBtton.setChecked(false);
                fahrenheitButton.setChecked(true);
                return ;
            }
            if(fahrenheitButton.isChecked()){
                float fahValue =convertFtoC(inputValue);
                editInput.setText(String.valueOf(fahValue));
                fahrenheitButton.setChecked(false);
                ceslsiusBtton.setChecked(true);
            }
            break;

        }
    }
}
