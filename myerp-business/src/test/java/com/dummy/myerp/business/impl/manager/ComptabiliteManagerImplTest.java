package com.dummy.myerp.business.impl.manager;

import com.dummy.myerp.model.bean.comptabilite.CompteComptable;
import com.dummy.myerp.model.bean.comptabilite.EcritureComptable;
import com.dummy.myerp.model.bean.comptabilite.JournalComptable;
import com.dummy.myerp.model.bean.comptabilite.LigneEcritureComptable;
import com.dummy.myerp.technical.exception.FunctionalException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertThrows;

class ComptabiliteManagerImplTest {

    private static ComptabiliteManagerImpl manager;
    private EcritureComptable vEcritureComptable;
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    private static Date date;


    @BeforeAll
    private static void beforeAll() throws ParseException {
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
    @Tag("checkEcritureComptUnit")
    @DisplayName("Verify that no exception is thrown if the EcritureComptable is correct")
    void checkEcritureComptableUnit() throws FunctionalException {
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
                null, new BigDecimal(123),
                null));
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(2),
                null, null,
                new BigDecimal(123)));

        Assertions.assertThatCode(() -> manager.checkEcritureComptableUnit(vEcritureComptable))
                .doesNotThrowAnyException();

    }

    @Test
    @Tag("checkEcritureComptUnit")
    @DisplayName("Verify that null checkEcritureComptableUnit thrown FunctionalException")
    void checkEcritureComptableUnitViolation() {
        assertThrows(FunctionalException.class, () -> {
            manager.checkEcritureComptableUnit(vEcritureComptable);
        });
    }


    @Test
    @Tag("checkEcritureComptRG2")
    @DisplayName("When total Debit is greater than total Credit checkEcritureComptableUnit thrown FunctionalException")
    void checkEcritureComptableRG2_throwFunctionalException_ofDebitGreaterThanCredit() throws Exception {
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
                null, new BigDecimal(1234),
                null));
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(2),
                null, null,
                new BigDecimal(123)));

        assertThrows(FunctionalException.class, () -> {
            manager.checkEcritureComptableRG2(vEcritureComptable);
        });

    }

    @Test
    @Tag("checkEcritureComptRG2")
    @DisplayName("When total Debit is less than total Credit checkEcritureComptableUnit thrown FunctionalException")
    void checkEcritureComptableRG2_throwFunctionalException_ofDebitLessThanCredit() throws Exception {
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
                null, new BigDecimal(123),
                null));
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(2),
                null, null,
                new BigDecimal(1234)));

        assertThrows(FunctionalException.class, () -> {
            manager.checkEcritureComptableRG2(vEcritureComptable);
        });

    }

    @Test
    @Tag("checkEcritureComptRG3")
    @DisplayName("When there are only one Debit LigneEcritureComptable checkEcritureComptableUnit thrown FunctionalException")
    void checkEcritureComptableRG3_returnFunctionalException_ofOnlyOneDebitLigneEcritureComptable() throws Exception {
        vEcritureComptable.getListLigneEcriture().add(
                new LigneEcritureComptable(new CompteComptable(1),
                        null,
                        new BigDecimal(123),
                        null));

        assertThrows(FunctionalException.class, () -> {
            manager.checkEcritureComptableRG3(vEcritureComptable);
        });
    }

    @Test
    @Tag("checkEcritureComptRG3")
    @DisplayName("When there are only one Credit LigneEcritureComptable checkEcritureComptableUnit throw FunctionalException")
    void checkEcritureComptableRG3_returnFunctionalException_ofOnlyOneCrediLigneEcritureComptable() throws Exception {
        vEcritureComptable.getListLigneEcriture().add(
                new LigneEcritureComptable(new CompteComptable(1),
                        null,
                        null,
                        new BigDecimal(123)));

        assertThrows(FunctionalException.class, () -> {
            manager.checkEcritureComptableRG3(vEcritureComptable);
        });
    }

    @Test
    @Tag("checkEcritureComptUnitRG4")
    @DisplayName("Accept negative values")
    void RG4_returnNoException_ofSameCreditAndDebitNegativeValues() throws Exception {
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
                null, new BigDecimal(-123),
                null));
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(2),
                null, null,
                new BigDecimal(-123)));

        Assertions.assertThatCode(() -> manager.checkEcritureComptableUnit(vEcritureComptable))
                .doesNotThrowAnyException();

    }


    @Test
    @Tag("checkEcritureComptRG5")
    @DisplayName("Bad Reference Journal Code throw FunctionalException")
    void checkEcritureComptableRG5_throwFunctionalException_ofBadJournalCodeInReference() throws Exception {
        vEcritureComptable.setReference("BQ-2016/00001");
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
                null, new BigDecimal(123),
                null));
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(2),
                null, null,
                new BigDecimal(123)));

        assertThrows(FunctionalException.class, () -> {
            manager.checkEcritureComptableRG5(vEcritureComptable);
        });
    }

    @Test
    @Tag("checkEcritureComptRG5")
    @DisplayName("Bad Reference Journal Year throw FunctionalException")
    void checkEcritureComptableRG5_throwFunctionalException_ofBadJournalYearInReference() throws Exception {
        vEcritureComptable.setReference("AC-2018/00001");
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
                null, new BigDecimal(123),
                null));
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(2),
                null, null,
                new BigDecimal(123)));

        assertThrows(FunctionalException.class, () -> {
            manager.checkEcritureComptableRG5(vEcritureComptable);
        });

    }





    @Test
    @Tag("checkEcritureComptableRG7")
    @DisplayName("The LigneEcritureComptable Debit amout can't exceed 2 decimals")
    void checkEcritureComptableRG7_throwFunctionalException_ofLigneEcritureComptableWith3DecimalDebitAmount() throws Exception {
        vEcritureComptable.setReference("AC-2018/00001");
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
                null, new BigDecimal(123.123),
                null));
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(2),
                null, null,
                new BigDecimal(123.12)));

        assertThrows(FunctionalException.class, () -> {
            manager.checkEcritureComptableRG7(vEcritureComptable);
        });
    }

    @Test
    @Tag("checkEcritureComptableRG7")
    @DisplayName("The LigneEcritureComptable Credit amout can't exceed 2 decimals")
    void checkEcritureComptableRG7_throwFunctionalException_ofLigneEcritureComptableWith3DecimalCrebitAmount() throws Exception {
        vEcritureComptable.setReference("AC-2018/00001");
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
                null,
                new BigDecimal(123.12),
                null));
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(2),
                null, null,
                new BigDecimal(-123.125)));

        assertThrows(FunctionalException.class, () -> {
            manager.checkEcritureComptableRG7(vEcritureComptable);
        });
    }

}
