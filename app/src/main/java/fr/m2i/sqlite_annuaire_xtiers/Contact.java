package fr.m2i.sqlite_annuaire_xtiers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Administrateur on 17/01/2018.
 */
                /* Couche BLL */

public class Contact {

    final String TABLE_NAME = "contacts";
    final String COLUMNS[] = {"id", "name", "tel"};

    private Integer id;
    private String nom;
    private String tel;
    private boolean nomExistant;

    SQLiteDatabase db;


    public Contact(Context ctxt) {

        //DbInit est singleton: pas de création direct via le constructeur de DbInit (private car singleton)
        //passage pas la methode static getInstance qui va créer l'objet
        DbInit dbInit = DbInit.getInstance(ctxt);

        // constructeur à utiliser si DbInit n'était pas singleton
        // DbInit dbInit = new DbInit(ctxt);
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

    public void selectById(Integer id) throws Exception {

        String where = "id = ' " + id + "'";
        select(where);
    }

    public void selectByNom(String nom) throws Exception {

        String where = "name = '" + nom + "'";
        select(where);


    }

    //throws car cette méthode peux renvoyer une exception
    private void select(String where) throws Exception {

        //Cursor cursor = db.query(TABLE_NAME, COLUMNS, where,"", null, null, null,null);
        Cursor cursor = db.query("contacts", COLUMNS, where, null, null, null, null);
        //cursor.getCount() est le nombre d'éléments trouvé lors du query
        //si supérieur à 0 donc trouvé,

        if (cursor.getCount() == 1) {
            cursor.moveToFirst();
            this.id = cursor.getInt(0);
            this.nom = cursor.getString(1);
            this.tel = cursor.getString(2);
            nomExistant=true;
        } else if (cursor.getCount() > 1) {
            //création d'une exception personnalisé suite plusieurs contacts  trouvé lors du query
            throw new ContactMultiplesException();
        } else {
            nomExistant=false;
            //création d'une exception personnalisé suite contact non trouvé lors du query
            //throw new ContactNotFoundException();
        }

    }

    public void update() throws Exception {
        if (this.nom.equals("")) {
            throw new ContacNameInvalidException();
        }
        //ContentValues tableau de clé - valeur
        ContentValues values = new ContentValues();
        values.put("name", this.nom);
        values.put("tel", this.tel);
        String where = "name = '" + this.nom + "'";
        db.update(TABLE_NAME, values, where, null);
    }

    public void insert() throws Exception {

        if (this.nom.equals("")) {
            throw new ContacNameInvalidException();
        }
        //ContentValues tableau de clé - valeur
        ContentValues values = new ContentValues();
        values.put("name", this.nom);
        values.put("tel", this.tel);
        db.insert(TABLE_NAME, null, values);
    }


    public void delete() throws Exception {

        String where = "id ='" + this.id + "'";
        db.delete(TABLE_NAME, where, null);

    }

    public boolean isNomExistant() {
        return nomExistant;
    }


    //création des classes d'exception interne car ne sera utilisé que par la class Contact

    public class ContactNotFoundException extends Exception {

        public ContactNotFoundException() {

            //on passe un String en paramètre au constructeur de la classe mère Exception
            super("Contact non trouvé");
        }

    }

    public class ContactMultiplesException extends Exception {

        public ContactMultiplesException() {

            //on passe un String en paramètre au constructeur de la classe mère Exception
            super("Plusieurs contacts trouvés");
        }
    }

    public class ContacNameInvalidException extends Exception {

        public ContacNameInvalidException() {

            //on passe un String en paramètre au constructeur de la classe mère Exception
            super("vide");
        }

    }


}
