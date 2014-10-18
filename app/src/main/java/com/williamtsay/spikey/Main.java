package com.williamtsay.spikey;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.Ack;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;


public class Main extends Activity {
    ImageView card0,card1,card2,card3,card4,card5,card6,card7,card8,card9,card10,card11;
    TextView mainText;
    Socket socket;
    Helper helper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        helper.getCard();
        mainText = (TextView) findViewById(R.id.main_text);
        card0 = (ImageView) findViewById(R.id.card_0);
        card1 = (ImageView) findViewById(R.id.card_1);
        card2 = (ImageView) findViewById(R.id.card_2);
        card3 = (ImageView) findViewById(R.id.card_3);
        card4 = (ImageView) findViewById(R.id.card_4);
        card5 = (ImageView) findViewById(R.id.card_5);
        card6 = (ImageView) findViewById(R.id.card_6);
        card7 = (ImageView) findViewById(R.id.card_7);
        card8 = (ImageView) findViewById(R.id.card_8);
        card9 = (ImageView) findViewById(R.id.card_9);
        card10 = (ImageView) findViewById(R.id.card_10);
        card11 = (ImageView) findViewById(R.id.card_11);

        try {
            socket = IO.socket("http://192.168.1.3:8080/");
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        socket.connect();
        socket.on("baz", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mainText.setText("holy smokes");
                    }
                });
            }
        });
        socket.emit("foo", "bar");
        // game flow is: #1 get a board - have the server generate a board and set it back
        socket.emit("getBoard");
        socket.on("receiveBoard", new Emitter.Listener(){
            @Override
            public void call(Object... args) {
                String jsonString = (String) args[0];
                JSONObject jsonObj = null;
                try {
                    jsonObj = new JSONObject(jsonString);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                String cardValue = null;
                try {
                    cardValue = jsonObj.getString("card1");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                Log.d("jsonTest", cardValue);
//                int cardId1 = getResources().getIdentifier("something","drawable", getPackageName());

//                card1.setImageResource(cardId1);
//                card2.setImageResource();
//                card3.setImageResource();
//                card4.setImageResource();
//                card5.setImageResource();
//                card6.setImageResource();
//                card7.setImageResource();
//                card8.setImageResource();
//                card9.setImageResource();
//                card10.setImageResource();
//                card11.setImageResource();

            }
        });




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
