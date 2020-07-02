package com.dummy.myerp.model.bean.comptabilite;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;

class JournalComptableTest {
    private static final List<JournalComptable> journalComptables = new ArrayList<>();
    private static JournalComptable journalComptable;
    private static final String[] code = {"1", "20", "10", "201"};
    private static final String[] libelle = {"first Journal", "Journal 20", "Journal 10", "Journal 201"};

    @BeforeAll
    private static void beforeAll() {
        for (int i=0; i<4; i++) {
            journalComptables.add(new JournalComptable(code[i], libelle[i]));
        }

    }

    @BeforeEach
    private void beforeEach() {
        journalComptable = new JournalComptable(code[1], libelle[1]);
    }

    @Test
    @Tag("getByCode")
    @DisplayName("In a JournalComptable list we can retreive one by his Code")
    void getByCode_returnsTheRightJournalComptable_ofListAndAxistingCode() {
        for (int i=0; i<4; i++) {
            Assertions.assertThat(JournalComptable.getByCode(journalComptables, code[i])).isEqualTo(journalComptables.get(i));
        }
    }

    @Test
    @Tag("getByCode")
    @DisplayName("In a JournalComptable list we get null if Code doesn't exist")
    void getByCode_returnsNull_ofListAndNonExistingCode() {
        Assertions.assertThat(JournalComptable.getByCode(journalComptables,"4")).isNull();
    }

    @Test
    @Tag("getByCode")
    @DisplayName("In a JournalComptable list we get null if Code is negative value")
    void getByCode_returnsNull_ofListAndNegativeCode() {
        Assertions.assertThat(JournalComptable.getByCode(journalComptables,"-4")).isNull();
    }

    @Test
    @Tag("getByCode")
    @DisplayName("In a JournalComptable list we get null if Code is null")
    void getByCode_returnsNull_ofListAndNullCode() {
        Assertions.assertThat(JournalComptable.getByCode(journalComptables,null)).isNull();
    }

    @Test
    @Tag("getByCode")
    @DisplayName("In a JournalComptable list we get null if Code format doesn't match")
    void getByCode_returnsNull_ofListAndBadFormatCode() {
        Assertions.assertThat(JournalComptable.getByCode(journalComptables,"sdfsd")).isNull();
    }


    @Test
    @Tag("getByCode")
    @DisplayName("In a JournalComptable list we get null if list is empty")
    void getByCode_returnsNull_ofEmptyList() {
        List<JournalComptable> emptyList = new ArrayList<>();

        Assertions.assertThat(JournalComptable.getByCode(emptyList,"2")).isNull();
    }


    @Test
    @Tag("getCode")
    @DisplayName("Return the Code of a JournalComptable")
    void getCode_returnCode_ofJournalComptable() {
        Assertions.assertThat(journalComptable.getCode()).isEqualTo(code[1]);
    }

    @Test
    @Tag("setCode")
    @DisplayName("We can change the Code of a JournalComptable")
    void setCode_returnNewCode_ofJournalComptableCodeChanged() {
        journalComptable.setCode(code[2]);
        Assertions.assertThat(journalComptable.getCode()).isEqualTo(code[2]);
    }

    @Test
    @Tag("getLibelle")
    @DisplayName("Return the Libelle of a JournalComptable")
    void getLibelle_returnLibelle_ofJournalComptable() {
        Assertions.assertThat(journalComptable.getLibelle()).isEqualTo(libelle[1]);
    }

    @Test
    @Tag("setLibelle")
    @DisplayName("We can change the Libelle of a JournalComptable")
    void setLibelle_returnNewLibelle_ofJournalComptableLibelleChanged() {
        journalComptable.setLibelle(libelle[2]);
        Assertions.assertThat(journalComptable.getLibelle()).isEqualTo(libelle[2]);
    }

    @Test
    @Tag("toString")
    @DisplayName("testTostring")
    void testToStringreturnTheString_ofJournalComptable() {
        String journalComptableString = "JournalComptable{code='20', libelle='Journal 20'}";

        Assertions.assertThat(journalComptables.get(1).toString()).isEqualTo(journalComptableString);
    }

    @Test
    @Tag("hashCode")
    @DisplayName("Verify that hashCode is always the same")
    void testHashCode() {
        Assertions.assertThat(journalComptable.hashCode()).isEqualTo(journalComptable.hashCode());
    }
}
