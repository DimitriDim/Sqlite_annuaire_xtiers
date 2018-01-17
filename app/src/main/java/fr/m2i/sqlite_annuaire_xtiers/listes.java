package fr.m2i.sqlite_annuaire_xtiers;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class listes extends AppCompatActivity {
    SQLiteDatabase db;
    ListView lv1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listes);

        //ouverture de la base de donnée
        DbInit dbInit = new DbInit(this);
        db = dbInit.getWritableDatabase();

        lv1 = findViewById(R.id.listeAnnuaire);

        ArrayList<String> list = new ArrayList<String>(1);
        String columns[] = {"id", "name", "tel"};

        //lecture pas a pas dans la bdd
        Cursor cursor = db.query("contacts", columns, "", null, null, null, null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            while (cursor.moveToNext()) {
                list.add(cursor.getString(1));
            }
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, list);
        lv1.setAdapter(adapter);
    }
}
