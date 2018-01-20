package fr.m2i.sqlite_annuaire_xtiers;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

                        /* couche IHM */


public class listes extends AppCompatActivity {
    SQLiteDatabase db;
    ListView lv1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listes);

        //ouverture de la base de donnée (singleton)
        DbInit dbInit = DbInit.getInstance(this);
        db = dbInit.getWritableDatabase();
        lv1 = findViewById(R.id.listeAnnuaire);

        ArrayList<String> list = new ArrayList<String>(1);
        String columns[] = {"id", "name", "tel"};

        //lecture pas a pas dans la bdd

        Cursor cursor = db.query("contacts", columns, "", null, null, null, null);
        if (cursor.getCount() > 1) {
            cursor.moveToFirst();
            while (cursor.moveToNext()) {
                list.add("Id : " + cursor.getString(0) + "   Nom : " + cursor.getString(1) + "   Tel : " + cursor.getString(2));
            }
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, list);
        lv1.setAdapter(adapter);
    }

}
