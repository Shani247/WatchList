package com.example.watchlist;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
public abstract class BaseActivity extends AppCompatActivity {
    // מכריזים על מחלקה בשם BaseActivity שהיא abstract.
    // abstract אומר שמחלקה זו **לא ניתנת להופעלה ישירות**, אלא משמשת "תבנית" למחלקות אחרות.

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        // "מפציץ" (inflate) את קובץ ה-XML של התפריט (menu_main.xml) לתוך האובייקט Menu.
        // כלומר, התפריט מוגדר בקובץ XML ונטען כאן.

        return true;
        // מחזיר true כדי לאותת ל-Android שהתפריט נוצר וניתן להציגו.
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_home) {
            Intent intent = new Intent(this, Home.class);
            startActivity(intent);
            return true;
            // מחזיר true כדי לאותת ל-Android שהאירוע טופל.
        }
        else if (id == R.id.action_watched) {
            Intent intent = new Intent(this, Watched.class);
            startActivity(intent);
            return true;
        }
        else if (id == R.id.action_action) {
            Intent intent = new Intent(this, Action.class);
            startActivity(intent);
            return true;
        }

        else if (id == R.id.action_comedy) {
            Intent intent = new Intent(this, Comedy.class);
            startActivity(intent);
            return true;
        }

        else if (id == R.id.action_romance) {
            Intent intent = new Intent(this, Romance.class);
            startActivity(intent);
            return true;
        }
        else if (id == R.id.action_add) {
            Intent intent = new Intent(this, Add_Movie.class);
            startActivity(intent);
            return true;
        }
        else if (id == R.id.action_exit) {
            finishAffinity();
            return true;
        }

        return super.onOptionsItemSelected(item);
        // אם הפריט לא טופל כאן, מעביר את האירוע למחלקת האב (AppCompatActivity) כדי לטפל בו.
    }
}
