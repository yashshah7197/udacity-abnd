package io.yashshah.musicalstructure;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class RecentlyPlayedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recently_played);

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

        // Find the 'Song 1' button
        Button song1 = (Button) findViewById(R.id.button_song_1);

        // Set an onClickListener for the 'Song 1' button
        song1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Display a Toast telling the user that 'Song 1' is now playing.
                String message = getResources().getString(R.string.song_1) + " is now playing.";
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
            }
        });

        // Find the 'Song 2' button
        Button song2 = (Button) findViewById(R.id.button_song_2);

        // Set an onClickListener for the 'Song 2' button
        song2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Display a Toast telling the user that 'Song 2' is now playing.
                String message = getResources().getString(R.string.song_2) + " is now playing.";
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
            }
        });

        // Find the 'Now Playing' button
        Button nowPlaying = (Button) findViewById(R.id.button_now_playing);

        // Set an onClickListener for the 'Now Playing' button
        nowPlaying.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /** Create a new intent to open the {@link NowPlayingActivity} */
                Intent nowPlayingIntent = new Intent(RecentlyPlayedActivity.this, NowPlayingActivity.class);
                startActivity(nowPlayingIntent);
            }
        });
    }
}
