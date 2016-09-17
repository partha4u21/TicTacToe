package avatar.com.tictactoe;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.Map;

/**
 * Created by partha on 9/15/16.
 */
public class GameFragment extends android.app.Fragment implements View.OnClickListener {

    int one, two, three, four, five, six, seven, eight, nine;
    int clickCount = 0;
    int[] viewArray;
    int[] movesArray = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0};
    GameLogic logic;

    public static GameFragment newInstance(String gameType) {
        GameFragment frag = new GameFragment();
        Bundle bundle = new Bundle();
        bundle.putString("gameType", gameType);
        frag.setArguments(bundle);
        return frag;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.game_layout, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        DisplayMetrics displaymetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int width = displaymetrics.widthPixels;

        one = R.id.one;
        two = R.id.two;
        three = R.id.three;
        four = R.id.four;
        five = R.id.five;
        six = R.id.six;
        seven = R.id.seven;
        eight = R.id.eight;
        nine = R.id.nine;

        logic = new GameLogic();

        viewArray = new int[]{one, two, three, four, five, six, seven, eight, nine};

        for (int i = 0; i < viewArray.length; i++) {
            view.findViewById(viewArray[i]).setOnClickListener(this);
        }

        setFrameSize(width, width);
        super.onViewCreated(view, savedInstanceState);
    }

    private void setFrameSize(int width, int height) {

        int paddingWidth = 40;
        int cellWidth = (width / 3) - paddingWidth;

        ViewGroup.LayoutParams params = (ViewGroup.LayoutParams) getView().findViewById(one).getLayoutParams();
        params.width = cellWidth;
        params.height = cellWidth;

        for (int i = 0; i < viewArray.length; i++) {
            getView().findViewById(viewArray[i]).setLayoutParams(params);
        }
    }

    @Override
    public void onClick(View v) {
        if (clickCount <= 9) {
            ImageView img = ((ImageView) getView().findViewById(v.getId()));
            for (int i = 0; i < viewArray.length; i++) {
                if (v.getId() == viewArray[i]) {
                    // save 1 for user
                    movesArray[i] = 1;
                    img.setImageResource(R.drawable.ic_circle);
                    img.setClickable(false);
                    img.setFocusable(false);
                    if (clickCount < 8) {
                        nextMove();
                    } else {
                        clickCount = 0;
                        movesArray = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0};
                        Snackbar.make(getView(), "All moves complete", Snackbar.LENGTH_SHORT).show();
                    }
                }
            }
            clickCount++;
            Map winArray = logic.winwin(movesArray);
            if (winArray.size() > 0) {
                Snackbar.make(getView(), (boolean) winArray.get("win") ? "Congrats !! You won this round :) " : "Try again :( ", Snackbar.LENGTH_SHORT).show();
                int[] combination = (int[]) winArray.get("combination");
                for (int i = 0; i < combination.length; i++) {
                    ((ImageView) getView().findViewById(viewArray[combination[i]])).setBackgroundColor(Color.parseColor("#3CB371"));
                }
                ImageView image;
                for (int i = 0; i < viewArray.length; i++) {
                    image = ((ImageView) getView().findViewById(viewArray[i]));
                    image.setClickable(false);
                    image.setFocusable(false);
                }
            }
        } else {
            //reset all values
            clickCount = 0;
            movesArray = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0};
            Snackbar.make(getView(), "All moves complete", Snackbar.LENGTH_SHORT).show();
        }

    }

    private void nextMove() {
        clickCount++;
        int viewId = logic.algo(getArguments().getString("gameType"), movesArray, viewArray);
        for (int i = 0; i < movesArray.length; i++) {
            if (viewArray[i] == viewId) {
                movesArray[i] = 2;
            }
        }
        ImageView img = (ImageView) getView().findViewById(viewId);
        img.setImageResource(R.drawable.ic_cross);
        img.setClickable(false);
        img.setFocusable(false);
    }
}
