package fr.m2i.sqlite_annuaire_xtiers;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Administrateur on 17/01/2018.
 */
                /* Couche BLL */

public class Contact {

    final String TABLE_NAME = "contacts";
    final String COLUMNS[] = {"id", "nom", "tel"};

    private Integer id;
    private String nom;
    private String tel;


    SQLiteDatabase db;


    public Contact(Context ctxt) {

        DbInit dbInit = new DbInit(ctxt);
        db = dbInit.getWritableDatabase();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }


    //On définit les methodes pour réaliser les actions possibles
    //sur les contacts:

    //throws ContactNotFoundException  car cette méthode peux renvoyer une exception
    public void selectById(Integer id) throws ContactNotFoundException {

        String where = "id ' " + id + "'";
        Cursor cursor = db.query(TABLE_NAME, COLUMNS, where, null, null, null, null);

        //cursor.getCount() est le nombre d'éléments trouvé lors du query
        //si supérieur à 0 donc trouvé,
        if (cursor.getCount() > 0) {
            this.id = cursor.getInt(0);
            this.nom = cursor.getString(1);
            this.tel = cursor.getString(2);
        } else {
            //création d'une exception personnalisé suite contact non trouvé lors du query
            throw new ContactNotFoundException();
        }

    }

    public void selectByNom(Integer nom) throws ContactNotFoundException {

        String where = "name ' " + nom + "'";
        Cursor cursor = db.query(TABLE_NAME, COLUMNS, where, null, null, null, null);

        //cursor.getCount() est le nombre d'éléments trouvé lors du query
        //si supérieur à 0 donc trouvé,
        if (cursor.getCount() > 0) {
            this.id = cursor.getInt(0);
            this.nom = cursor.getString(1);
            this.tel = cursor.getString(2);
        } else {
            //création d'une exception personnalisé suite contact non trouvé lors du query
            throw new ContactNotFoundException();
        }

    }

    //création d'une classe d'exception interne car ne sera utilisé que par la class Contact

    public class ContactNotFoundException extends Exception {

        public ContactNotFoundException() {

            //on passe un String en paramètre au constructeur de la classe mère Exception
            super("Contact non trouvé");
        }

    }

}
