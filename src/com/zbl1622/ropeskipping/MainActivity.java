package com.zbl1622.ropeskipping;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends Activity {
	
	private DiagramView diagramView;
	
	private SensorManager mSensorManager;  
    private Sensor mSensor;
	
	private boolean change_flag=false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		diagramView=(DiagramView) findViewById(R.id.view_diagram);
		mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);  
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mSensorManager.registerListener(sensorEventListener, mSensor,  
                SensorManager.SENSOR_DELAY_FASTEST);
        
//		startChangeValue();
	}
	
	private SensorEventListener sensorEventListener=new SensorEventListener() {
		
		@Override
		public void onSensorChanged(SensorEvent event) {
			if (event.sensor == null) {  
	            return;  
	        }  
	  
	        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {  
	            float x = event.values[0];  
	            float y = event.values[1];  
	            float z = event.values[2];
	            double a=Math.sqrt(x*x+y*y+z*z);
	            diagramView.setValue((float) (a*10f));
	        }
			
		}
		
		@Override
		public void onAccuracyChanged(Sensor sensor, int accuracy) {
		}
	};
	
	@Override
	protected void onResume() {
		super.onResume();
		diagramView.startRender();
	}
	
	@Override
	protected void onStop() {
		super.onStop();
		diagramView.stopRender();
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		change_flag=false;
	}
	
	private void startChangeValue(){
		change_flag=true;
		final long startTime=System.currentTimeMillis();
		new Thread(){
			@Override
			public void run() {
				while(change_flag){
					long time=System.currentTimeMillis()-startTime;
					float x=((float)time)/1000f;
					diagramView.setValue((float) (300*Math.sin(x)));
				}
			}
		}.start();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
