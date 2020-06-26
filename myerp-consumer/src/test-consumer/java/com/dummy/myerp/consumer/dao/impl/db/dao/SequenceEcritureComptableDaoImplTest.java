package com.dummy.myerp.consumer.dao.impl.db.dao;

import com.dummy.myerp.model.bean.comptabilite.SequenceEcritureComptable;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class SequenceEcritureComptableDaoImplTest extends ConsumerTestCase {

    private static Integer annee;
    private static String codeJournal;
    private static Integer derniereValeur;
    private static SequenceEcritureComptable vSEC;

    @BeforeEach
    public void beforeEach(){
        vSEC = new SequenceEcritureComptable();
    }

    @Test
    @Tag("getLastSeqOfTheYear")
    @DisplayName("Verify that we get the last SequenceEcritureComptable")
    void getLastSeqOfTheYear(){
        annee = 2016;
        codeJournal = "AC";
        derniereValeur = 40;

        vSEC = getDaoProxy().getComptabiliteDao().
                getLastSeqOfTheYear(annee, codeJournal);

        Assertions.assertThat(vSEC).isNotNull();
        Assertions.assertThat(vSEC.getYear()).isEqualTo(annee);
        Assertions.assertThat(vSEC.getLastValue()).isEqualTo(derniereValeur);
    }

    @Test
    @Tag("getLastSeqOfTheYear")
    @DisplayName("Verify that we get null if the sequence year doesn't exist")
    void getLastSeqOfTheYear_returnNull_ofNewSequenceYear(){
        annee = 2025;
        codeJournal = "AC";

        vSEC = getDaoProxy().getComptabiliteDao().getLastSeqOfTheYear(annee, codeJournal);

        Assertions.assertThat(vSEC).isNull();
    }

    @Test
    @Tag("getLastSeqOfTheYear")
    @DisplayName("Verify that we get null if the journal doesn't exist")
    void getLastSeqOfTheYear_returnNull_ofNewSequenceJournal(){
        annee = 2016;
        codeJournal = "ZZ";

        vSEC = getDaoProxy().getComptabiliteDao().getLastSeqOfTheYear(annee, codeJournal);

        Assertions.assertThat(vSEC).isNull();
    }

    @Test
    @Tag("insertSequenceEcritureComptable")
    void insertSequenceEcritureComptable(){
        try{
            annee = 2020;
            codeJournal = "AC";
            derniereValeur = 111;

            vSEC.setYear(annee);
            vSEC.setLastValue(111);
            getDaoProxy().getComptabiliteDao().insertSequenceEcritureComptable(vSEC, codeJournal);
        }catch(Exception exception){

        }

        vSEC = getDaoProxy().getComptabiliteDao().getLastSeqOfTheYear(annee, codeJournal);

        Assertions.assertThat(vSEC.getYear()).isEqualTo(annee);
        Assertions.assertThat(vSEC.getLastValue()).isEqualTo(derniereValeur);

        getDaoProxy().getComptabiliteDao().deleteSequenceEcritureComptable(vSEC, codeJournal);

        // remove inserted SequenceEcritureComptable
        vSEC = getDaoProxy().getComptabiliteDao().getLastSeqOfTheYear(annee, codeJournal);
        Assertions.assertThat(vSEC).isNull();
    }

    @Test
    @Tag("updateSequenceEcritureComptable")
    @DisplayName("Verify that we update the SequenceEcritureComptable")
    void updateSequenceEcritureComptable(){
        annee = 2016;
        codeJournal = "AC";
        derniereValeur = 555;
        Integer oldValue;

        try{
            vSEC = getDaoProxy().getComptabiliteDao().getLastSeqOfTheYear(annee, codeJournal);
            oldValue = vSEC.getLastValue();
            vSEC.setLastValue(derniereValeur);
            getDaoProxy().getComptabiliteDao().updateSequenceEcritureComptable(vSEC, codeJournal);

            vSEC = getDaoProxy().getComptabiliteDao().getLastSeqOfTheYear(annee, codeJournal);

            Assertions.assertThat(vSEC.getYear()).isEqualTo(annee);
            Assertions.assertThat(vSEC.getLastValue()).isEqualTo(derniereValeur);

            // restore oldvalue
            vSEC.setLastValue(oldValue);
            getDaoProxy().getComptabiliteDao().updateSequenceEcritureComptable(vSEC, codeJournal);

            vSEC = getDaoProxy().getComptabiliteDao().getLastSeqOfTheYear(annee, codeJournal);

            Assertions.assertThat(vSEC.getYear()).isEqualTo(annee);
            Assertions.assertThat(vSEC.getLastValue()).isEqualTo(oldValue);
        }catch (Exception exception){

        }
    }
}
