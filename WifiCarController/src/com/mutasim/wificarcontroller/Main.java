package com.mutasim.wificarcontroller;

import com.mutasim.wificarcontroller.JoystickMovedListener;
import com.mutasim.wificarcontroller.JoystickView;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SlidingDrawer;
import android.widget.SlidingDrawer.OnDrawerCloseListener;
import android.widget.SlidingDrawer.OnDrawerOpenListener;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class Main extends Activity {
	ToggleButton tb;
	Button buttonConnect;
	TextView txtX, txtY, TextVieX, TextVieY,lig;
    JoystickView joystick;
	Boolean connected=false;
	ConnectivityManager connectivity;
	NetworkInfo wifiInfo;
	Button slideButton; 
	SlidingDrawer slidingDrawer;
	DataOutputStream dataOutputStream = null;
	Socket socket = null;

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        TextVieX=(TextView)findViewById(R.id.TextVieX);
        TextVieY=(TextView)findViewById(R.id.TextVieY);
        txtX = (TextView)findViewById(R.id.TextViewX);
        txtY = (TextView)findViewById(R.id.TextViewY);
        lig = (TextView)findViewById(R.id.lig);

        tb = (ToggleButton) findViewById(R.id.tb);
        joystick = (JoystickView)findViewById(R.id.joystickView);
	    buttonConnect = (Button)findViewById(R.id.connect);
		slideButton = (Button) findViewById(R.id.handle);
		slidingDrawer = (SlidingDrawer) findViewById(R.id.slidingDrawer);
	    changeConnectionStatus(false);
	    buttonConnect.setOnClickListener(buttonConnectOnClickListener);
        joystick.setOnJostickMovedListener(_listener);
    	slidingDrawer.setOnDrawerOpenListener(new OnDrawerOpenListener() {
    		@Override 
    		public void onDrawerOpened() 
    		{slideButton.setBackgroundResource(R.drawable.down);} });
    	slidingDrawer.setOnDrawerCloseListener(new OnDrawerCloseListener() {
    		@Override 
    		public void onDrawerClosed() 
    		{slideButton.setBackgroundResource(R.drawable.up);} }); 
        tb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tb.isChecked()) {if(connected){
        	 	    	try {dataOutputStream.writeBytes("L");}
        	 	    	catch (UnknownHostException e) {} 
        	 	    	catch (IOException e) {}}} 
                else {if(connected){
        	 	    	try {dataOutputStream.writeBytes("l");}
        	 	    	catch (UnknownHostException e) {} 
        	 	    	catch (IOException e) {}}}}});	
    }

	@Override
	 public boolean onCreateOptionsMenu(Menu menu) {
	 	MenuInflater inflater = getMenuInflater();
	 	inflater.inflate(R.menu.menu, menu);
	 	return true;
	 }
	
	@Override
	 public boolean onOptionsItemSelected(MenuItem item) {
	 switch (item.getItemId()) {
	 case R.id.about:
     Intent intent = new Intent(Main.this, About.class);
     Main.this.startActivity(intent);	 
	 break;
	 case R.id.exit:
	 this.finish();
	 }
	 return false;
	 }
    
    private JoystickMovedListener _listener = new JoystickMovedListener() {
        @Override
        public void OnMoved(int pan, int tilt) {
        	if(connected){
	 	    	try {dataOutputStream.writeBytes((pan+90)+" A");}
	 	    	catch (UnknownHostException e) {} 
	 	    	catch (IOException e) {}
	 	    }
        	if(connected){
	 	    	try {dataOutputStream.writeBytes((tilt+90)+" B");}
	 	    	catch (UnknownHostException e) {} 
	 	    	catch (IOException e) {}
	 	    }
                txtX.setText(Integer.toString(pan*(-1)));
                txtY.setText(Integer.toString(tilt));
        }

        @Override
        public void OnReleased() {
        	if(connected){
	 	    	try {dataOutputStream.writeBytes(90+" A");}
	 	    	catch (UnknownHostException e) {} 
	 	    	catch (IOException e) {}
	 	    }
        	if(connected){
	 	    	try {dataOutputStream.writeBytes(90+" B");}
	 	    	catch (UnknownHostException e) {} 
	 	    	catch (IOException e) {}
	 	    }
                txtX.setText("0");
                txtY.setText("0");
        }
        
        public void OnReturnedToCenter() {
        	if(connected){
	 	    	try {dataOutputStream.writeBytes(90+" A");}
	 	    	catch (UnknownHostException e) {} 
	 	    	catch (IOException e) {}
	 	    }
        	if(connected){
	 	    	try {dataOutputStream.writeBytes(90+" B");}
	 	    	catch (UnknownHostException e) {} 
	 	    	catch (IOException e) {}	 	    	
	 	    }
                txtX.setText("0");
                txtY.setText("0");
        };
}; 
 	   

    
    Button.OnClickListener buttonConnectOnClickListener = new Button.OnClickListener(){
		@Override
		public void onClick(View arg0) {
			connectivity = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
			wifiInfo = connectivity.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
			if (wifiInfo.isConnected()) {
			if(!connected){
				 try {socket = new Socket("192.168.1.120", 55555);
				 Toast msg = Toast.makeText(Main.this,
                 "Connected Successfully", Toast.LENGTH_LONG);
				 msg.show();
					  dataOutputStream = new DataOutputStream(socket.getOutputStream());
					  changeConnectionStatus(true);} 
				 catch (UnknownHostException e) {changeConnectionStatus(false);} 
				 catch (IOException e) {changeConnectionStatus(false);}}
			else{
				try {socket.close();
				Toast msg = Toast.makeText(Main.this,
		        "Disconnected Successfully", Toast.LENGTH_LONG);
				msg.show();
					  changeConnectionStatus(false);} 
				catch (UnknownHostException e) {changeConnectionStatus(false);}
				catch (IOException e) {changeConnectionStatus(false);}}}
			else {Toast msg = Toast.makeText(Main.this,
			        "Wifi is not connected !", Toast.LENGTH_LONG);
					msg.show();}
	}};
	
	public void changeConnectionStatus(Boolean isConnected) {
		connected=isConnected;
		if(isConnected){
			buttonConnect.setBackgroundResource(R.drawable.discon);
			joystick.setVisibility(View.VISIBLE);
			slidingDrawer.setVisibility(View.VISIBLE);
			slideButton.setVisibility(View.VISIBLE);
			TextVieX.setVisibility(View.VISIBLE);
			TextVieY.setVisibility(View.VISIBLE);
			txtX.setVisibility(View.VISIBLE);
			txtY.setVisibility(View.VISIBLE);
			tb.setVisibility(View.VISIBLE);
			lig.setVisibility(View.VISIBLE);
}
		else{buttonConnect.setBackgroundResource(R.drawable.con);
		joystick.setVisibility(View.INVISIBLE);
		slidingDrawer.setVisibility(View.INVISIBLE);
		slideButton.setVisibility(View.INVISIBLE);
		TextVieX.setVisibility(View.INVISIBLE);
		TextVieY.setVisibility(View.INVISIBLE);
		txtX.setVisibility(View.INVISIBLE);
		txtY.setVisibility(View.INVISIBLE);
		tb.setVisibility(View.INVISIBLE);
		lig.setVisibility(View.INVISIBLE);
		}}
}

