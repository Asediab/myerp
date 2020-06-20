package com.dummy.myerp.model.bean.comptabilite;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class CompteComptableTest {
    private List<CompteComptable> compteComptables = new ArrayList<>();
    private final CompteComptable compte1 = new CompteComptable(20, "compte20");
    private final CompteComptable compte2 = new CompteComptable(45, "compte45");


    @BeforeEach
    public void init() {
        compteComptables.add(compte1);
        compteComptables.add(compte2);
    }

    @Test
    public void getByNumber_returnsTheRightCompteComptable() {
        Assertions.assertEquals(compte1, CompteComptable.getByNumero(compteComptables, 2));
        Assertions.assertEquals(compte2, CompteComptable.getByNumero(compteComptables, 5));
    }

    @Test
    public void getByNumber_returnsNull_() {
        Assertions.assertNull(CompteComptable.getByNumero(compteComptables, 3));
    }
}
