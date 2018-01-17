package fr.m2i.sqlite_annuaire_xtiers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Administrateur on 16/01/2018.
 */
            /* couche DAL */


//Cette classe prendra en charge la création de la base de données
// et les modifications de structure lors des évolutions.
public class DbInit extends SQLiteOpenHelper {

    // La classe DbInit peut etre créée comme un singleton
    //pour éviter les connexions multiples à la bdd
    // - Constructeur privée
    //- Une méthode static getInstance()

    // variable instance pour mémoriser la premiere création
    private static DbInit instance;

    private DbInit(Context ctx) {
        //curseur factory pour parcourir les listes de données si neccessaire
        //non utilisé dans cet appli (null)
        super(ctx, "annuaire", null, 1);
    }

    public static DbInit getInstance(Context ctxt) {

        if (instance == null) {
            instance = new DbInit(ctxt);
        }
        return instance;
    }

    //Cette méthode aura pour rôle de créer la base de données lors de la première exécution.
    //Elle sera constituée essentiellement d'une série d'instructions db.execSQL(sql);
    //Chacune de ces instructions créant une table, définissant les index, les relations,etc.
    @Override
    public void onCreate(SQLiteDatabase db) {

        //création de la Table contacts
        String sql = "CREATE TABLE contacts (" +
                " id INTEGER PRIMARY KEY NOT NULL" +
                ", name TEXT NOT NULL" +
                ", tel TEXT" +
                ")";
        db.execSQL(sql);

    }

    //Cette méthode s'occupera des mises à jour de la structure de la base lorsque cela sera
    //nécessaire, c'est à dire lors d'un changement de version.
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {


    }
}
