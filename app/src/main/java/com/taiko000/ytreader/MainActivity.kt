package com.taiko000.ytreader

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.webkit.WebViewClient
import android.widget.Toast
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerView
import kotlinx.android.synthetic.activity_main.webView
import kotlinx.android.synthetic.activity_main.youtube_view


public class MainActivity : YouTubeBaseActivity(), YouTubePlayer.OnInitializedListener
{
    val RECOVERY_DIALOG_REQUEST = 10;
    val API_KEY = "AIzaSyBEH3_UJG3XIAfOBU7BQ2vMcR5L1I3jQFg";
    var ytv : YouTubePlayerView? = null
    var mPlayer : YouTubePlayer? = null

    private var videoId: String? = "tttG6SdnCd4" // some default video

    override fun onInitializationSuccess(p0: YouTubePlayer.Provider?, player: YouTubePlayer, wasRestored: Boolean)
    {
        if (!wasRestored) {

            mPlayer = player;
            player.cueVideo(videoId);
            player.setPlaybackEventListener(PlaybackEventListener());
        }

    }

    override fun onInitializationFailure(p0: YouTubePlayer.Provider?, errorReason: YouTubeInitializationResult)
    {
        if (errorReason.isUserRecoverableError()) {
            errorReason.getErrorDialog(this, RECOVERY_DIALOG_REQUEST).show();
        } else {
            val errorMessage = "Youtube Error: " + errorReason.toString()
            Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show();
        }

    }

    override fun onCreate(bundle: Bundle?) {
        super.onCreate(bundle)
        setContentView(R.layout.activity_main)
        ytv = youtube_view as YouTubePlayerView

        if (Intent.ACTION_SEND.equals(intent.action) && intent.type != null) {
            if ("text/plain".equals(intent.type)) {
                val sharedText = intent.getStringExtra(Intent.EXTRA_TEXT);
                videoId = sharedText?.split("/")?.last()
            }

            //setSupportActionBar(toolbar)

            //        fab.setOnClickListener( {view ->
            //            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show()  })


            ytv?.initialize(API_KEY, this)


            webView.setWebViewClient(WebViewClient())
            webView.loadUrl("https://www.google.com/ncr")


        }
    }

    override fun onBackPressed() {

        if (webView.canGoBack()) {
            webView.goBack()
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onPause()
    {
        super.onPause()

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent) {
        if (requestCode == RECOVERY_DIALOG_REQUEST) {
            // Retry initialization if user performed a recovery action
            ytv?.initialize(API_KEY, this);
        }

    }
}
