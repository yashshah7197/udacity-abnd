package io.yashshah.musicalstructure;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class PlaylistsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlists);

        // Find the 'Playlist 1' button
        Button playlist1 = (Button) findViewById(R.id.button_playlist_1);

        // Set an onClickListener for the 'Playlist 1' button
        playlist1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Display a Toast to the user telling him/her that 'Playlist 1' is now playing
                String message = getString(R.string.playlist_1) + " is now playing.";
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
            }
        });

        // Find the 'Playlist 2' button
        Button playlist2 = (Button) findViewById(R.id.button_playlist_2);

        // Set an onClickListener for the 'Playlist 2' button
        playlist2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Display a Toast to the user telling him/her that 'Playlist 2' is now playing
                String message = getString(R.string.playlist_2) + " is now playing.";
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
            }
        });

        // Find the 'Now Playing' button
        Button nowPlaying = (Button) findViewById(R.id.button_now_playing);

        // Set an onClickListener for the 'Now Playing' button
        nowPlaying.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /** Create a new intent that opens the {@link NowPlayingActivity} */
                Intent nowPlayingIntent = new Intent(PlaylistsActivity.this, NowPlayingActivity.class);
                startActivity(nowPlayingIntent);
            }
        });
    }
}
