package com.dummy.myerp.consumer.dao.impl.db.dao;

import com.dummy.myerp.model.bean.comptabilite.JournalComptable;
import com.dummy.myerp.model.bean.comptabilite.SequenceEcritureComptable;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.fail;

class SequenceEcritureComptableDaoImplITest extends ConsumerTestCase {

    private static Integer annee;
    private static String codeJournal;
    private static Integer derniereValeur;
    private static SequenceEcritureComptable vSEC;

    @BeforeEach
    public void beforeEach(){
        vSEC = new SequenceEcritureComptable();
    }

    @Test
    @DisplayName("Verify get the last SequenceEcritureComptable")
    void getSequenceEcritureComptable(){
        annee = 2016;
        codeJournal = "AC";
        derniereValeur = 40;

        vSEC = getDaoProxy().getComptabiliteDao().
                getSequenceEcritureComptable(codeJournal, annee);

        Assertions.assertThat(vSEC).isNotNull();
        Assertions.assertThat(vSEC.getYear()).isEqualTo(annee);
        Assertions.assertThat(vSEC.getLastValue()).isEqualTo(derniereValeur);
    }

    @Test
    @DisplayName("Verify get null if the sequence doesn't exist")
    void getSequenceEcritureComptable_returnNull_ofNewSequenceYear(){
        annee = 1000;
        codeJournal = "AC";

        vSEC = getDaoProxy().getComptabiliteDao().getSequenceEcritureComptable(codeJournal, annee);

        Assertions.assertThat(vSEC).isNull();
    }

    @Test
    @DisplayName("Verify get null if the journal doesn't exist")
    void getSequenceEcritureComptable_returnNull_ofNewSequenceJournal(){
        annee = 2016;
        codeJournal = "VVVV";

        vSEC = getDaoProxy().getComptabiliteDao().getSequenceEcritureComptable(codeJournal, annee);

        Assertions.assertThat(vSEC).isNull();
    }

    @Test
    @DisplayName("Insert SequenceEcritureComptable")
    void insertSequenceEcritureComptable(){
        try{
            annee = 2020;
            codeJournal = "AC";
            derniereValeur = 111;
            JournalComptable journalComptable = new JournalComptable(codeJournal, "Libelle");

            vSEC.setYear(annee);
            vSEC.setLastValue(111);
            vSEC.setJournalComptable(journalComptable);
            getDaoProxy().getComptabiliteDao().insertSequenceEcritureComptable(vSEC);
        }catch(Exception exception){
            fail();
        }

        vSEC = getDaoProxy().getComptabiliteDao().getSequenceEcritureComptable(codeJournal, annee);

        Assertions.assertThat(vSEC.getYear()).isEqualTo(annee);
        Assertions.assertThat(vSEC.getLastValue()).isEqualTo(derniereValeur);

        getDaoProxy().getComptabiliteDao().deleteSequenceEcritureComptable(vSEC);

        vSEC = getDaoProxy().getComptabiliteDao().getSequenceEcritureComptable(codeJournal, annee);
        Assertions.assertThat(vSEC).isNull();
    }

    @Test
    @DisplayName("Update the SequenceEcritureComptable")
    void updateSequenceEcritureComptable(){
        annee = 2010;
        codeJournal = "AC";
        derniereValeur = 123;
        Integer oldValue;

        try{
            vSEC = getDaoProxy().getComptabiliteDao().getSequenceEcritureComptable(codeJournal, annee);
            oldValue = vSEC.getLastValue();
            vSEC.setLastValue(derniereValeur);
            getDaoProxy().getComptabiliteDao().updateSequenceEcritureComptable(vSEC);

            vSEC = getDaoProxy().getComptabiliteDao().getSequenceEcritureComptable(codeJournal, annee);

            Assertions.assertThat(vSEC.getYear()).isEqualTo(annee);
            Assertions.assertThat(vSEC.getLastValue()).isEqualTo(derniereValeur);

            // restore oldvalue
            vSEC.setLastValue(oldValue);
            getDaoProxy().getComptabiliteDao().updateSequenceEcritureComptable(vSEC);

            vSEC = getDaoProxy().getComptabiliteDao().getSequenceEcritureComptable(codeJournal, annee);

            Assertions.assertThat(vSEC.getYear()).isEqualTo(annee);
            Assertions.assertThat(vSEC.getLastValue()).isEqualTo(oldValue);
        }catch (Exception exception){

        }
    }
}
