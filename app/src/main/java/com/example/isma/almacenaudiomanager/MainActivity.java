package com.example.isma.almacenaudiomanager;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    Context context;
    private ImageView iv1;
    private SeekBar sb1,sb2;
    private TextView tvvolumengeneral,tvvolumenmultimedia;
    int volumengeneral;
    int volumenmultimedia;
    private int valorbarrasonidogeneral;
    private int valorbarrasonidomultimedia;
    AudioManager audioManager;
    private int nivelmaximosonidogeneral,nivelmaximosonidomultimedia;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setVariables();
        setVistas();
        setListernes();
    }
    private void setVariables()
    {
        context=getApplicationContext();
        ////////////////////////////////////////////////////////////////////////////////////////////
        //Pruebas volumen sistema y volumen multimedia
        //Obtener el volumen...
        //Tenemos variso AudioManager.STREAM_---- para cada tipo de sonido. STREAM_ALARM,STREAM_DTMF,STREAM_MUSIC,STREAM_NOTIFICATION,STREAM_RING,STREAM_SYSTE,STREAM_VOICE_CALL

        //Metodos getStreamMaxVolume(STREAM_TYPE), setStreamVolume(STREAM_TYPE,int index,int flags)

        audioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
        //Obtenemos el nivel maximo de sonido del dispositivo...
        nivelmaximosonidogeneral=audioManager.getStreamMaxVolume(audioManager.STREAM_SYSTEM);
        nivelmaximosonidomultimedia=audioManager.getStreamMaxVolume(audioManager.STREAM_MUSIC);
    }
    private void setVistas()
    {
        this.iv1=(ImageView)findViewById(R.id.mainiv1);
        this.tvvolumengeneral=(TextView)findViewById(R.id.maintvvolumengeneral);
        this.tvvolumenmultimedia=(TextView)findViewById(R.id.maintvvolumenmultimedia);
        this.sb1=(SeekBar)findViewById(R.id.seekBar);
        this.sb2=(SeekBar)findViewById(R.id.seekBar2);
        //ajustamos el maximo de las barras con estos valores...
        sb1.setMax(nivelmaximosonidogeneral);
        sb2.setMax(nivelmaximosonidomultimedia);
    }
    private void setListernes()
    {
        sb1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
            {
                //obtenemos el nuevo valor...
                valorbarrasonidogeneral = (int) (sb1.getProgress());
                audioManager.setStreamVolume(AudioManager.STREAM_SYSTEM, valorbarrasonidogeneral, AudioManager.FLAG_PLAY_SOUND);
                volumengeneral = audioManager.getStreamVolume(AudioManager.STREAM_SYSTEM);
                tvvolumengeneral.setText("" + volumengeneral);
                }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        sb2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                //obtenemos el nuevo valor...
                valorbarrasonidomultimedia = sb2.getProgress();
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, valorbarrasonidomultimedia, AudioManager.FLAG_PLAY_SOUND);//Hay diferentes Flags...
                volumenmultimedia = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
                tvvolumenmultimedia.setText("" + volumenmultimedia);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
    public void MostrarBitmapenTextView()
    {
        Bitmap image = BitmapFactory.decodeResource(getResources(), R.drawable.volume0bmp);
        iv1.setImageBitmap(image);
    }
    public void MostrarPngDrawableenTextView(int Rimagen)
    {
       iv1.setImageResource(Rimagen);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onResume()
    {
        super.onResume();
        volumengeneral=audioManager.getStreamVolume(AudioManager.STREAM_SYSTEM);
        volumenmultimedia= audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        tvvolumengeneral.setText("" + volumengeneral);
        tvvolumenmultimedia.setText("" + volumenmultimedia);
        //ponemos las barras con los valores obtenidos del sistema...
        sb1.setProgress(volumengeneral);
        sb2.setProgress(volumenmultimedia);
    }
}
