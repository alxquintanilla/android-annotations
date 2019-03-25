package alxquintanilla.example;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import alxquintanilla.annotations.ActivityView;
import alxquintanilla.annotations.ConfigureAnnotations;
import alxquintanilla.annotations.Inject;
import alxquintanilla.annotations.OnClick;
import alxquintanilla.annotations.R;

/**
 * Example, using androidannotations and running the interpreter class in runtime.
 */
@ActivityView(R.layout.activity_main)
public class MainActivity extends AppCompatActivity {

    @Inject(id=R.id.text_ ,clazz = TextView.class)
    TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ConfigureAnnotations.configure(this);
    }

    @OnClick(idView = R.id.button_)
    void buttonClick(View v){
        Log.d("INFO", "Wow u just click :) I'll show a message real quick ");
        text.setText("Hola mundo!");
    }

}
