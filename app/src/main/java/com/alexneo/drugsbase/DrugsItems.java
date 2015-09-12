package com.alexneo.drugsbase;

/**
 * Created by Alex Neo on 09.09.2015.
 */
public class DrugsItems {

    private static String nameDrugs;

    public DrugsItems(String nameDrugs) {
        this.nameDrugs = nameDrugs;
    }

    public static String getNameDrugs() {
        return nameDrugs;
    }

    public void setNameDrugs(String nameDrugs) {
        this.nameDrugs = nameDrugs;
    }
}