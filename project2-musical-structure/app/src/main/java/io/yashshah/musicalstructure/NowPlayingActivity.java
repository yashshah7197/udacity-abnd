package io.yashshah.musicalstructure;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class NowPlayingActivity extends AppCompatActivity {

    boolean playing = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_now_playing);

        // Find the 'Rewind' button
        Button rewind = (Button) findViewById(R.id.button_rewind);

        // Set the onClickListener for the 'Rewind' button
        rewind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Displays a Toast telling the user that the song is now rewinding
                String message = getString(R.string.rewinding);
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
            }
        });

        // Find the 'Play/Pause' button
        final Button playPause = (Button) findViewById(R.id.button_play_pause);

        // Set the onClickListener for the 'Play/Pause' button
        playPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Displays a Toast telling the user that the song is now playing/paused.
                if (playing) {
                    String message = getString(R.string.paused);
                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                    playing = false;
                } else {
                    String message = getString(R.string.playing);
                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                    playing = true;
                }
            }
        });

        // Find the 'Forward' button
        Button forward = (Button) findViewById(R.id.button_forward);

        // Set the onClickListener for the 'Forward' button
        forward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Displays a Toast telling the user that the song is now forwarding
                String message = getString(R.string.forwarding);
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
            }
        });

        // Find the 'Library' button
        Button library = (Button) findViewById(R.id.button_library);

        // Set the onClickListener for the 'Rewind' button
        library.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /** Create a new intent that opens the {@link LibraryActivity} */
                Intent libraryIntent = new Intent(NowPlayingActivity.this, LibraryActivity.class);
                startActivity(libraryIntent);
            }
        });
    }
}
