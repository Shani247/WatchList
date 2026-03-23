package com.example.watchlist;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Add_Movie extends BaseActivity {

    SharedPreferences sp;
    Button btnSubmit;
    RadioButton selected;
    EditText movieName;
    RadioGroup category;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_movie);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        initViews();

    }

    private void initViews() {
        movieName = findViewById(R.id.movieName);
        category = findViewById(R.id.category_radio_group);
        btnSubmit = findViewById(R.id.btnSubmit);
        sp = getSharedPreferences("saveMovies", 0);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btnSubmit == v) {
                    int selectedId = category.getCheckedRadioButtonId();
                    if (selectedId != -1) {
                        selected = findViewById(selectedId);
                        SharedPreferences.Editor editor = sp.edit();
                        String categoryName = selected.getText().toString();
                        String movie = movieName.getText().toString();
                        editor.putString("movie", movie);
                        editor.putString("category", categoryName);
                        editor.commit();
                        String key = "";

                        if (categoryName.equals("Romance")) {
                            key = "movies_romance";
                            Intent intent = new Intent(Add_Movie.this, Romance.class);
                            startActivity(intent);
                        } else if (categoryName.equals("Comedy")) {
                            key = "movies_comedy";
                            Intent intent = new Intent(Add_Movie.this, Comedy.class);
                            startActivity(intent);
                        } else if (categoryName.equals("Action")) {
                            key = "movies_action";
                            Intent intent = new Intent(Add_Movie.this, Action.class);
                            startActivity(intent);
                        }

                        String oldMovies = sp.getString(key, "");

                        if (oldMovies.equals("")) {
                            sp.edit().putString(key, movie).apply();
                        } else {
                            String[] moviesArray = oldMovies.split(","); // split - מפריד בין התווים המסומנים בפסיק ומכניס כל אחד לתא במערך.

                            boolean exists = false;

                            for (int i = 0; i < moviesArray.length && !exists; i++){
                                if (moviesArray[i].equals(movie))
                                    exists = true;
                            }
                            if (!exists) {
                                String newMovies = oldMovies + "," + movie;
                                sp.edit().putString(key, newMovies).apply();
                            }
                            else
                                Toast.makeText(Add_Movie.this, "Movie already exists", Toast.LENGTH_SHORT).show();

                        }
                    }
                }
            }
        });
    }
}
