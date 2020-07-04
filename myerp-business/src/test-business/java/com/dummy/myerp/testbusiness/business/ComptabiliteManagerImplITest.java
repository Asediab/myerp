package com.dummy.myerp.testbusiness.business;

import com.dummy.myerp.business.impl.TransactionManager;
import com.dummy.myerp.business.impl.manager.ComptabiliteManagerImpl;
import com.dummy.myerp.consumer.dao.impl.db.dao.ComptabiliteDaoImpl;
import com.dummy.myerp.model.bean.comptabilite.*;
import com.dummy.myerp.technical.exception.FunctionalException;
import com.dummy.myerp.technical.exception.NotFoundException;
import org.apache.commons.lang3.ObjectUtils;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.transaction.TransactionStatus;

import java.math.BigDecimal;
import java.sql.Date;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import static com.dummy.myerp.testbusiness.business.BusinessTestCase.getBusinessProxy;
import static org.junit.jupiter.api.Assertions.*;

class ComptabiliteManagerImplITest {

    private static ComptabiliteManagerImpl manager;
    private EcritureComptable vEcritureComptable;
    private static CompteComptable compteComptable1;
    private static CompteComptable compteComptable2;
    private static Date date;
    private static List<JournalComptable> journalComptableList= new ArrayList<>();
    private JournalComptable journal;
    private TransactionManager trManager = TransactionManager.getInstance();


    @BeforeAll
    private static void beforeAll() {
        journalComptableList = getBusinessProxy().getComptabiliteManager().getListJournalComptable();
        compteComptable1 = getBusinessProxy().getComptabiliteManager().getListCompteComptable().get(1);
        compteComptable2 = getBusinessProxy().getComptabiliteManager().getListCompteComptable().get(2);
        manager = new ComptabiliteManagerImpl();
        date = Date.valueOf("2016-05-01");
    }

    @BeforeEach
    private void beforeEach() {
        journal = ObjectUtils.defaultIfNull(
                JournalComptable.getByCode( journalComptableList, "AC" ),
                new JournalComptable( "AC","Achat" ) );
        vEcritureComptable = new EcritureComptable();
        vEcritureComptable.setJournal(journal);
        vEcritureComptable.setDate(date);
        vEcritureComptable.setLibelle("Libelle");
    }

    @Test
    @DisplayName("Add reference")
    void addReference() throws FunctionalException {
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(compteComptable1,
                null,
                new BigDecimal(123),
                null));
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(compteComptable2,
                null, null,
                new BigDecimal(123)));
        vEcritureComptable.setReference("AC-2016/00040");
        assertEquals(vEcritureComptable.getReference() ,manager.setReference(ComptabiliteDaoImpl.getInstance().getSequenceEcritureComptable("AC", 2016) ) );

        SequenceEcritureComptable pSeq = ComptabiliteDaoImpl.getInstance().getSequenceEcritureComptable("AC",2016);

       assertEquals( vEcritureComptable.getReference(), manager.setReference(pSeq) );
       assertEquals("AC-2016/00040", manager.setReference(pSeq) );
    }

    @Test
    @DisplayName("Verify that we can update the LIBELLE of an EcritureComptable")
    void updateEcritureComptable_returnNewLibelle_ofLibelleUpdating() {
        String newLibelle = "Nouveau libelle";

        EcritureComptable ecritureComptable = getBusinessProxy().getComptabiliteManager().getListEcritureComptable().get(1);
        Integer id = ecritureComptable.getId();
        String oldLibelle = ecritureComptable.getLibelle();

        ecritureComptable.setLibelle(newLibelle);
        try {
            manager.updateEcritureComptable(ecritureComptable);
            ecritureComptable = manager.getEcritureComptableById(id);
            Assertions.assertThat(ecritureComptable.getLibelle()).isEqualTo(newLibelle);

            ecritureComptable.setLibelle(oldLibelle);
            manager.updateEcritureComptable(ecritureComptable);
            ecritureComptable = manager.getEcritureComptableById(id);
            Assertions.assertThat(ecritureComptable.getLibelle()).isEqualTo(oldLibelle);
        } catch (FunctionalException | NotFoundException exception) {
        }
    }


    @Test
    @DisplayName("Verify that we can update the Date of an Ecriture comptable")
    void updateEcritureComptable_returnNewDate_ofDateUpdating() throws ParseException {
        Date newDate = Date.valueOf("2020-05-01");

        EcritureComptable ecritureComptable = getBusinessProxy().getComptabiliteManager().getListEcritureComptable().get(1);
        Integer id = ecritureComptable.getId();
        Date oldDate = ecritureComptable.getDate();

        ecritureComptable.setDate(newDate);
        try {
            manager.updateEcritureComptable(ecritureComptable);
            ecritureComptable = manager.getEcritureComptableById(id);
            Assertions.assertThat(ecritureComptable.getDate()).isEqualTo(newDate);

            ecritureComptable.setDate(oldDate);
            manager.updateEcritureComptable(ecritureComptable);
            ecritureComptable = manager.getEcritureComptableById(id);
            Assertions.assertThat(ecritureComptable.getDate()).isEqualTo(oldDate);
        } catch (FunctionalException | NotFoundException exception) {

        }
    }

    @Test
    @DisplayName("Verify we can delete")
    void deleteEcritureComptable_returnNotFoundException_ofDeletedEcritureComptable() throws ParseException {
        Integer ecritureId;

        vEcritureComptable.setReference("AC-2016/00055");
        vEcritureComptable.setDate(Date.valueOf("2016-06-01"));
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(compteComptable1,
                null,
                new BigDecimal(123),
                null));
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(compteComptable2,
                null, null,
                new BigDecimal(123)));

        try {
            manager.insertEcritureComptable(vEcritureComptable);
            ecritureId = vEcritureComptable.getId();
            manager.deleteEcritureComptable(ecritureId);

            assertThrows(NotFoundException.class, () -> {
                manager.getEcritureComptableById(ecritureId);
            });

        } catch (FunctionalException exception) {

        }
    }

    @Test
    @DisplayName("Verify Ecriture comptable is inserted ")
    void insertEcritureComptable_insertTheRightEcritureComptable_ofEcritureComptable() throws ParseException {
        EcritureComptable vFound = null;

        vEcritureComptable.setReference("AC-2016/00051");
        vEcritureComptable.setDate(Date.valueOf("2016-11-31"));
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(compteComptable1,
                "0",
                new BigDecimal("123.00"),
                new BigDecimal("3.00")));
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(compteComptable2,
                "25", new BigDecimal("3.00"),
                new BigDecimal("123.00")));

        try {
            manager.insertEcritureComptable(vEcritureComptable);
            vFound = manager.getEcritureComptableById(vEcritureComptable.getId());

        } catch (FunctionalException | NotFoundException exception) {
        }

        manager.deleteEcritureComptable(vEcritureComptable.getId());
        assert vFound != null;
        Assertions.assertThat(vFound).isEqualTo(vEcritureComptable);
    }

    @Test
    @DisplayName("Reference duplication")
    void checkEcritureComptableRG6_throwFunctionalException_ofReferenceDuplication() throws Exception {
        List<EcritureComptable> ecritureComptables = manager.getListEcritureComptable();

        for (EcritureComptable e : ecritureComptables) {
            assertThrows(FunctionalException.class, () -> {
                manager.checkEcritureComptableRG6(e);
            });
        }
    }

    @Test
    void transactionManagerStatus(){
        TransactionStatus vTS = null;
        try {
            trManager.commitMyERP(vTS);
            assertNull( vTS );
        } finally {
            vTS = trManager.beginTransactionMyERP();
            assertNotNull( vTS );
            trManager.rollbackMyERP(vTS);
        }

        vTS = trManager.beginTransactionMyERP();
        try {
            trManager.commitMyERP(vTS);
            assertNotNull( vTS );
            vTS = null;
        } finally {
            assertNull( vTS );
            trManager.rollbackMyERP(vTS);
        }

    }
}
