package io.yashshah.musicalstructure;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Find the 'Recently Played' button
        final Button recentlyPlayed = (Button) findViewById(R.id.button_recently_played);

        // Set the onClickListener for the 'Recently Played' button
        recentlyPlayed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /** Create a new intent to open the {@link RecentlyPlayedActivity} */
                Intent recentlyPlayedIntent = new Intent(MainActivity.this, RecentlyPlayedActivity.class);
                startActivity(recentlyPlayedIntent);
            }
        });

        // Find the 'Your Playlists' button
        Button yourPlaylists = (Button) findViewById(R.id.button_your_playlists);

        // Set the onClickListener for the 'Your Playlists' button
        yourPlaylists.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /** Create a new intent to open the {@link PlaylistsActivity} */
                Intent yourPlaylistsIntent = new Intent(MainActivity.this, PlaylistsActivity.class);
                startActivity(yourPlaylistsIntent);
            }
        });

        // Find the 'Library' button
        Button library = (Button) findViewById(R.id.button_library);

        // Set the onClickListener for the 'Library' button
        library.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /** Create a new intent to open the {@link LibraryActivity} */
                Intent libraryIntent = new Intent(MainActivity.this, LibraryActivity.class);
                startActivity(libraryIntent);
            }
        });

        // Find the 'Now Playing' button
        Button nowPlaying = (Button) findViewById(R.id.button_now_playing);

        // Set the onClickListener for the 'Now Playing' button
        nowPlaying.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /** Create a new intent to open the {@link NowPlayingActivity} */
                Intent nowPlayingIntent = new Intent(MainActivity.this, NowPlayingActivity.class);
                startActivity(nowPlayingIntent);
            }
        });
    }
}
