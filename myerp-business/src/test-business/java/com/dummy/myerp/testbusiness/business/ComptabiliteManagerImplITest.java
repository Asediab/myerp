package com.dummy.myerp.testbusiness.business;

import com.dummy.myerp.business.impl.manager.ComptabiliteManagerImpl;
import com.dummy.myerp.model.bean.comptabilite.CompteComptable;
import com.dummy.myerp.model.bean.comptabilite.EcritureComptable;
import com.dummy.myerp.model.bean.comptabilite.JournalComptable;
import com.dummy.myerp.model.bean.comptabilite.LigneEcritureComptable;
import com.dummy.myerp.technical.exception.FunctionalException;
import com.dummy.myerp.technical.exception.NotFoundException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.dummy.myerp.testbusiness.business.BusinessTestCase.getBusinessProxy;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ComptabiliteManagerImplITest {

    private static ComptabiliteManagerImpl manager;
    private EcritureComptable vEcritureComptable;
    private static CompteComptable compteComptable1;
    private static CompteComptable compteComptable2;
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    private static Date date;


    @BeforeAll
    private static void beforeAll() throws ParseException {
        compteComptable1 = getBusinessProxy().getComptabiliteManager().getListCompteComptable().get(1);
        compteComptable2 = getBusinessProxy().getComptabiliteManager().getListCompteComptable().get(2);
        manager = new ComptabiliteManagerImpl();
        date = dateFormat.parse("01/05/2020");
    }

    @BeforeEach
    private void beforeEach() {
        vEcritureComptable = new EcritureComptable();
        vEcritureComptable.setJournal(new JournalComptable("AC", "Achat"));
        vEcritureComptable.setDate(date);
        vEcritureComptable.setLibelle("Libelle");
    }

    @Test
    @Tag("updateEcritureComptable")
    @DisplayName("Verify that we can update the libelle of an Ecriture comptable")
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
    @Tag("updateEcritureComptable")
    @DisplayName("Verify that we can update the reference of an Ecriture comptable")
    void updateEcritureComptable_returnNewReference_ofReferenceUpdating() throws ParseException {
        String newLibelle = "Nouveau libelle";
        String newReference = "AC-2016/00055";
        Date newDate = dateFormat.parse("01/05/2005");

        EcritureComptable ecritureComptable = getBusinessProxy().getComptabiliteManager().getListEcritureComptable().get(1);
        Integer id = ecritureComptable.getId();
        String oldLibelle = ecritureComptable.getLibelle();
        String oldReference = ecritureComptable.getReference();
        Date oldDate = (java.sql.Date)ecritureComptable.getDate();

        ecritureComptable.setLibelle(newLibelle);
        ecritureComptable.setReference(newReference);
        ecritureComptable.setDate(newDate);
        try {
            manager.updateEcritureComptable(ecritureComptable);
            ecritureComptable = manager.getEcritureComptableById(id);
            Assertions.assertThat(ecritureComptable.getLibelle()).isEqualTo(newLibelle);
            Assertions.assertThat(ecritureComptable.getReference()).isEqualTo(newReference);
            Assertions.assertThat(ecritureComptable.getDate()).isEqualTo(newDate);

            ecritureComptable.setLibelle(oldLibelle);
            ecritureComptable.setReference(oldReference);
            ecritureComptable.setDate(oldDate);
            manager.updateEcritureComptable(ecritureComptable);
            ecritureComptable = manager.getEcritureComptableById(id);
            Assertions.assertThat(ecritureComptable.getLibelle()).isEqualTo(oldLibelle);
            Assertions.assertThat(ecritureComptable.getReference()).isEqualTo(oldReference);
            Assertions.assertThat(ecritureComptable.getDate()).isEqualTo(oldDate);
        } catch (FunctionalException | NotFoundException exception) {

        }
    }

    @Test
    @Tag("updateEcritureComptable")
    @DisplayName("Verify that we can update the Date of an Ecriture comptable")
    void updateEcritureComptable_returnNewDate_ofDateUpdating() throws ParseException {
        Date newDate = dateFormat.parse("01/05/2005");

        EcritureComptable ecritureComptable = getBusinessProxy().getComptabiliteManager().getListEcritureComptable().get(1);
        Integer id = ecritureComptable.getId();
        Date oldDate = (java.sql.Date) ecritureComptable.getDate();

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
    @Tag("deleteEcritureComptable")
    @DisplayName("Verify that we can delete ecriture comptable")
    void deleteEcritureComptable_returnNotFoundException_ofDeletedEcritureComptable() throws ParseException {
        Integer ecritureId;

        vEcritureComptable.setReference("AC-2016/00055");
        vEcritureComptable.setDate(dateFormat.parse("01/06/2016"));
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

//    @Test
//    @Tag("insertEcritureComptable")
//    @DisplayName("Verify tha Ecriture comptable is inserted with his LigneEcritureComptable")
//    void insertEcritureComptable_insertTheRightEcritureComptable_ofEcritureComptable() throws ParseException {
//        EcritureComptable vFound = null;
//
//        vEcritureComptable.setReference("AC-2016/00051");
//        vEcritureComptable.setDate(dateFormat.parse("31/11/2016"));
//        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(compteComptable1,
//                "0",
//                new BigDecimal(123),
//                new BigDecimal(3)));
//        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(compteComptable2,
//                "25", new BigDecimal(3),
//                new BigDecimal(123)));
//
//        try {
//            manager.insertEcritureComptable(vEcritureComptable);
//            vFound = manager.getEcritureComptableById(vEcritureComptable.getId());
//
//        } catch (FunctionalException | NotFoundException exception) {
//        }
//
//        Assertions.assertThat(found.equals(vEcritureComptable)).isTrue();
//        manager.deleteEcritureComptable(vEcritureComptable.getId());
//    }

//    @Test
//    @Tag("checkEcritureComptableRG6")
//    @DisplayName("Reference duplication Year throw FunctionalException")
//    void checkEcritureComptableRG6_throwFunctionalException_ofReferenceDuplication() throws Exception {
//
//        // all EcritureComptable to try to insert them
//        List<EcritureComptable> ecritureComptables = manager.getListEcritureComptable();
//
//        for (EcritureComptable e : ecritureComptables) {
//            assertThrows(FunctionalException.class, () -> {
//                manager.checkEcritureComptableRG6(e);
//            });
//        }
//    }
}
