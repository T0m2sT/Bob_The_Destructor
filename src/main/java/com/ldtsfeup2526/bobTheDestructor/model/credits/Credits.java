package com.ldtsfeup2526.bobTheDestructor.model.credits;

public class Credits {
    private final String[] credits;

    public Credits() {
        credits = new String[] {
                "Programmer:         Aléxis Ramos",
                "Programmer: Pedro Tomás Teixeira",
                "Programmer: Rafael Pinho e Silva",
                "Artist:                     Mich"
        };
    }

    public String[] getCredits() {
        return credits;
    }
}
