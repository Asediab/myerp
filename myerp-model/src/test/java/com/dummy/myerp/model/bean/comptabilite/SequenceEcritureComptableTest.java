package com.dummy.myerp.model.bean.comptabilite;

import org.apache.commons.lang3.ObjectUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SequenceEcritureComptableTest {
    private static List<JournalComptable > journalComptableList= new ArrayList<JournalComptable>();

    @BeforeAll
    static void init(){
        journalComptableList.add(new JournalComptable("AC","Achat") );
        journalComptableList.add(new JournalComptable("VE","Vente") );
        journalComptableList.add(new JournalComptable("BQ","Banque") );
        journalComptableList.add(new JournalComptable("OD","Opérations Diverses") );
    }

    @Test
    @DisplayName("Get By Code And Year")
    public void getByCodeAndYear(){
        List<SequenceEcritureComptable> sequenceEcritureComptableList = new ArrayList<SequenceEcritureComptable>();
        SequenceEcritureComptable sequenceEcritureComptable = new SequenceEcritureComptable();

        JournalComptable journal = ObjectUtils.defaultIfNull(
                JournalComptable.getByCode( journalComptableList, "AC" ),
                new JournalComptable( "AC","Achat" ) );

        sequenceEcritureComptable.setJournalComptable( journal );
        sequenceEcritureComptable.setYear(2016);
        sequenceEcritureComptable.setLastValue(1);

        sequenceEcritureComptableList.add( sequenceEcritureComptable );
        assertNotNull(SequenceEcritureComptable.getByCodeAndYear(sequenceEcritureComptableList,"AC",2016) );
        assertEquals(sequenceEcritureComptable.getLastValue(),SequenceEcritureComptable.getByCodeAndYear(sequenceEcritureComptableList,"AC",2016).getLastValue());
        assertNull(SequenceEcritureComptable.getByCodeAndYear(sequenceEcritureComptableList,"AC",2000) );

    }

    @Test
    @DisplayName("Is SequenceEcritureComptable Exist")
    public void isSequenceEcritureComptableExist(){
        JournalComptable journal = ObjectUtils.defaultIfNull(
                JournalComptable.getByCode( journalComptableList, "AC" ),
                new JournalComptable( "AC","Achat" ) );

        SequenceEcritureComptable sequenceEcritureComptable = new SequenceEcritureComptable(journal,2016,1);

        assertTrue(SequenceEcritureComptable.isSequenceEcritureComptableExist(sequenceEcritureComptable,"AC",2016) );

        assertFalse(SequenceEcritureComptable.isSequenceEcritureComptableExist(sequenceEcritureComptable,"A",2016) );
        assertFalse(SequenceEcritureComptable.isSequenceEcritureComptableExist(sequenceEcritureComptable,"AC",2000) );
        assertFalse(SequenceEcritureComptable.isSequenceEcritureComptableExist(null,"AC",2016) );
        assertFalse(SequenceEcritureComptable.isSequenceEcritureComptableExist(null,"AC",2000) );

    }

}