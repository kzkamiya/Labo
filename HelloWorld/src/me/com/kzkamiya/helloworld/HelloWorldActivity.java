package me.com.kzkamiya.helloworld;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;

public class HelloWorldActivity extends Activity 
implements View.OnClickListener {
	Button button;
	int touchCount;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        button = new Button(this);
        button.setText("Touchd me!");
        button.setOnClickListener(this);
        setContentView(button);
    }
	@Override
	public void onClick(View v) {
		touchCount++;
		button.setText("Touched me" + touchCount + "time(s)");
		
	}
}