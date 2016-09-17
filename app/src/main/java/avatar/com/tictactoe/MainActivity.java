package avatar.com.tictactoe;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private View mContentView;
    private final Runnable mHidePart2Runnable = new Runnable() {
        @SuppressLint("InlinedApi")
        @Override
        public void run() {
            // Delayed removal of status and navigation bar

            // Note that some of these constants are new as of API 16 (Jelly Bean)
            // and API 19 (KitKat). It is safe to use them, as they are inlined
            // at compile-time and do nothing on earlier devices.
            mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        }
    };

    private View mControlsView;
    private boolean mVisible;
    private Button randomBtn, easyBtn, hardBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        mVisible = true;
        mControlsView = findViewById(R.id.fullscreen_content_controls);
        mContentView = findViewById(R.id.fragment_container);
        randomBtn = (Button) findViewById(R.id.random);
        easyBtn = (Button) findViewById(R.id.easy);
//        hardBtn = (Button) findViewById(R.id.hard);
//
        randomBtn.setOnClickListener(this);
        easyBtn.setOnClickListener(this);
//        hardBtn.setOnClickListener(this);
//
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }

    @Override
    public void onClick(View v) {
        String type = null;
        if (v.getId() == R.id.random) {
            type = "random";
        } else if (v.getId() == R.id.easy) {
            type = "easy";
        }
//        else if (v.getId() == R.id.hard) {
//            type = "hard";
//        }

        getFragmentManager().executePendingTransactions();
        GameFragment gameFragment = GameFragment.newInstance(type);
        getFragmentManager().beginTransaction().remove(gameFragment).commit();
        getFragmentManager().beginTransaction().add(R.id.fragment_container, gameFragment).commit();
    }
}
