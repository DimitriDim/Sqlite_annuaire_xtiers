package fr.m2i.sqlite_annuaire_xtiers;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

                        /* couche IHM */


public class Listes extends AppCompatActivity {
    private SQLiteDatabase db;
    private ListView lv1;

    private ArrayList<String> list = new ArrayList<String>(1);
    private String columns[] = {"id", "name", "tel"};
    private String nomDoublon;
    private boolean doublon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listes);

        //ouverture de la base de donnée (singleton)
        DbInit dbInit = DbInit.getInstance(this);
        db = dbInit.getWritableDatabase();
        lv1 = findViewById(R.id.listeAnnuaire);
        Intent intentRecup = getIntent();
        nomDoublon = intentRecup.getStringExtra("nomDoublon");
        doublon = intentRecup.getBooleanExtra("doublon", false);
        liste();
    }

    public void liste() {

        if (doublon == false) {
            //lecture pas a pas dans la bdd
            Cursor cursor = db.query("contacts", columns, "", null, null, null, null);
            if (cursor.getCount() > 1) {
                while (cursor.moveToNext()) {
                    list.add("Id : " + cursor.getString(0) + "   Nom : " + cursor.getString(1) + "   Tel : " + cursor.getString(2));
                }
            }
        } else {
            String where = "name = '" + nomDoublon + "'";

            //lecture pas a pas dans la bdd
            Cursor cursor = db.query("contacts", columns, where, null, null, null, null);
            if (cursor.getCount() > 1) {
                while (cursor.moveToNext()) {
                    list.add("Id : " + cursor.getString(0) + "   Nom : " + cursor.getString(1) + "   Tel : " + cursor.getString(2));
                }
            }
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, list);
        lv1.setAdapter(adapter);

    }


    public void selection(View view) {

    }
}
