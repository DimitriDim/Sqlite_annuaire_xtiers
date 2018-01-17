package fr.m2i.sqlite_annuaire_xtiers;

import android.app.Activity;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    SQLiteDatabase db;
    EditText etSearch, etId, etName, etTel;
    ListView lv1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etSearch = findViewById(R.id.txtSearch);
        etId = findViewById(R.id.txtId);
        etName = findViewById(R.id.txtName);
        etTel = findViewById(R.id.txtTel);
        etId.setEnabled(false);

        /*
        v11 = findViewById(R.id.maList);
        ArrayList list = new ArrayList(1);
        String columns[]={"id","name","tel"};

        Cursor cursor = db.query("contacts", columns, "", null, null, null, null);
        if(cursor.getCount() > 0){
            cursor.moveToFirst();
            while(cursor.move(1)){
                list.add(cursor.getString(1));
            }
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.activity_liste,R.id.listItem,columns);
        lv1.Adapter(adapter);
        */

        //ouverture de la base de donnée
        DbInit dbInit = new DbInit(this);
        db = dbInit.getWritableDatabase();

        /* Principales méthodes de l'objet SQLiteDatabase:
                ExecSQL()   pour les SQL de mise à jour
                query()     pour les SELECT
                update()
                insert()
                delete()

                // execution en bloc
                beginTransaction()
                endTransaction()
         */
    }

    public void search(View v) {

        Contact contact = new Contact(this);
        try {

            contact.selectByNom(etSearch.getText().toString());
            etId.setText(contact.getId().toString());
            etName.setText(contact.getNom().toString());
            etTel.setText(contact.getTel().toString());


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void clear(View v) {

        etSearch.setText("");
        etId.setText("");
        etName.setText("");
        etTel.setText("");

    }

    public void save(View v) {

        String nom = etName.getText().toString();
        String tel = etTel.getText().toString();
        String id = etId.getText().toString();
        ContentValues value = new ContentValues();

        value.put("name", nom);
        value.put("tel", tel);

        if (id.equals("")) {
            insert(value);
        } else {
            update(value, id);
            Toast.makeText(this, "La personne à été modifié", Toast.LENGTH_LONG).show();
        }

    }

    public void insert(ContentValues values) {

        db.insert("contacts", null, values);
        Toast.makeText(this, "enregistrement ok", Toast.LENGTH_LONG).show();

    }

    public void update(ContentValues values, String id) {

        db.update("contacts", values, "id" + "=" + id, null);
        Toast.makeText(this, "Modification ok", Toast.LENGTH_LONG).show();

    }


    public void delete(View v) {

        Activity act = this;
        AlertDialog.Builder builder = new AlertDialog.Builder(act);
        builder.setMessage("Voulez-vous supprimer ce fichier ?");
        builder.setPositiveButton("Oui je veux supprimer " + etSearch.getText().toString(), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String id = etId.getText().toString();
                db.delete("contacts", "id" + "=" + id, null);
                //Toast.makeText(this, "Suppréssion ok", Toast.LENGTH_LONG).show();
            }
        });

        builder.setNegativeButton("NON", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.show(); // ne retourne pas le choix de l'utilisateur
        // arrivé à la ligne suivante, l'utilisateur n'a peut être pas encore répondu à la question (car appel asynchrone)


    }


    public void listes(View view) {
        //création de l'intent
        Intent i = new Intent(this, listes.class);

        // Lancer la deuxième activité :
        startActivity(i);
    }
}
