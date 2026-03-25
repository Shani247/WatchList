package com.example.watchlist;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Comedy extends BaseActivity {
    TextView listC;
    SharedPreferences sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_comedy);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        initViews();
    }
    private void initViews() {
        listC = findViewById(R.id.listComedy);
        sp = getSharedPreferences("saveMovies", 0);
        String content = sp.getString("movies_comedy", "");
        String [] tempArr = content.split(",");
        content = "";

        for (int i = 0; i < tempArr.length; i++)
            content += tempArr[i] + "\n";

        listC.setText(content);

        SpannableString spannable = new SpannableString(content);
        int start = 0;

        for (int i = 0; i < tempArr.length; i++) {
            String movie = tempArr[i];
            int end = start + movie.length();

            spannable.setSpan(new ClickableSpan() {
                @Override
                public void onClick(View v) {
                    showMovieDialog(movie);
                }

                @Override
                public void updateDrawState(android.text.TextPaint ds) {
                    super.updateDrawState(ds);
                    ds.setColor(getResources().getColor(R.color.baby_pink)); // שומר את הצבע ורוד
                    ds.setUnderlineText(false); // מבטל קו תחתון
                }
            }, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

            start = end + 1;
        }

        listC.setText(spannable);
        listC.setMovementMethod(LinkMovementMethod.getInstance()); // חובה כדי שהלחיצות יעבדו

        TextView watched = findViewById(R.id.watched);
        SharedPreferences spWatched = getSharedPreferences("movies_status", 0);
        String contectW = "";
        for (int i = 0; i < tempArr.length; i++){
            String temp = tempArr[i] + "_watched";
            if (spWatched.getBoolean(temp, false))
                contectW += "Seen" + "\n";
            else
                contectW += "Unseen" + "\n";
        }
        watched.setText(contectW);
    }
    private void showMovieDialog(String movieName) {
        Dialog d = new Dialog(this);
        d.setContentView(R.layout.dialog);
        d.getWindow().setLayout(
                WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT
        );

        d.setContentView(R.layout.dialog);
        d.setTitle("Movie Options");
        d.setCancelable(true);

        TextView tvMovie = d.findViewById(R.id.tvMovieName);
        Button btnWatched = d.findViewById(R.id.btnMarkWatched);
        Button btnPhoto = d.findViewById(R.id.btnTakePhoto);

        tvMovie.setText(movieName);
        SharedPreferences sp = getSharedPreferences("movies_status", 0);

        btnWatched.setOnClickListener(v -> {
            sp.edit().putBoolean(movieName + "_watched", true).apply();
            d.dismiss();
            initViews();
        });

        btnPhoto.setOnClickListener(v -> {
            Intent intent = new Intent(Comedy.this, posters.class);
            startActivity(intent);
        });
        d.show();
    }
}