package com.dummy.myerp.model.bean.comptabilite;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;

class CompteComptableTest {
    private static final List<CompteComptable> compteComptables = new ArrayList<>();
    private static final Integer numero1 = 1;
    private static final String libelle1 = "compte2";
    private static final CompteComptable compte1 = new CompteComptable(numero1, libelle1);
    private static final CompteComptable compte2 = new CompteComptable(8, "compte8");
    private static final CompteComptable compte3 = new CompteComptable(20, "compte20");


    @BeforeAll
    private static void beforeAll() {
        compteComptables.add(compte1);
        compteComptables.add(compte2);
        compteComptables.add(compte3);
    }

    @AfterEach
    private void afterEach() {
        compte1.setNumero(numero1);
        Assertions.assertThat(compte1.getNumero()).isEqualTo(numero1);
        compte1.setLibelle(libelle1);
        Assertions.assertThat(compte1.getLibelle()).isEqualTo(libelle1);
    }

    @Test
    @DisplayName("We can retrieve one by Numero")
    void getByNumero_returnsTheRightCompteComptable() {
        Assertions.assertThat(CompteComptable.getByNumero(compteComptables, numero1)).isEqualTo(compte1);
        Assertions.assertThat(CompteComptable.getByNumero(compteComptables, compte2.getNumero())).isEqualTo(compte2);
        Assertions.assertThat(CompteComptable.getByNumero(compteComptables, compte3.getNumero())).isEqualTo(compte3);
    }

    @Test
    @DisplayName("null if a CompteComptable Numero doesn't exist")
    void getByNumero_returnsNull() {
        Assertions.assertThat(CompteComptable.getByNumero(compteComptables, 33)).isNull();
    }

    @Test
    @DisplayName("we get the right Numero CompteComptable")
    void getNumero_returnTheNumero() {
        Assertions.assertThat(compte1.getNumero()).isEqualTo(numero1);
    }

    @Test
    @DisplayName("we can change the Numero CompteComptable")
    void setNumero() {
        Integer numero2 = 46;

        compte1.setNumero(numero2);
        Assertions.assertThat(compte1.getNumero()).isEqualTo(numero2);
    }

    @Test
    @DisplayName("we get the Libelle CompteComptable")
    void getLibelle_returnTheLibelle() {
        Assertions.assertThat(compte1.getLibelle()).isEqualTo(libelle1);
    }

    @Test
    @DisplayName("we can change the Libelle CompteComptable")
    void setLibelle() {
        String libelle2 = "newLibelle";

        compte1.setLibelle(libelle2);
        Assertions.assertThat(compte1.getLibelle()).isEqualTo(libelle2);

    }

    @Test
    @DisplayName("To string")
    void toString_returnString() {
        String expected = "CompteComptable{numero=1, libelle='compte2'}";

        Assertions.assertThat(compte1.toString()).isEqualTo(expected);
    }

}
