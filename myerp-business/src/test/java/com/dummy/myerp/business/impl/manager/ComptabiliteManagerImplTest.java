package com.dummy.myerp.business.impl.manager;

import com.dummy.myerp.model.bean.comptabilite.*;
import com.dummy.myerp.technical.exception.FunctionalException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.sql.Date;
import java.text.ParseException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class ComptabiliteManagerImplTest {


    private static ComptabiliteManagerImpl manager;
    private EcritureComptable vEcritureComptable;
    private  JournalComptable journal;
    private static Date date;

    @Mock
    public ComptabiliteManagerImpl managerMock = mock(ComptabiliteManagerImpl.class);


    @BeforeAll
    private static void beforeAll() throws ParseException {
        manager = new ComptabiliteManagerImpl();
        date = Date.valueOf("2020-05-01");
    }

    @BeforeEach
    private void beforeEach() throws ParseException {
        journal = new JournalComptable( "AC","Achat" );
        vEcritureComptable = new EcritureComptable();
        vEcritureComptable.setJournal(journal);
        vEcritureComptable.setDate(date);
        vEcritureComptable.setLibelle("Libelle");
        vEcritureComptable.setReference("AC-2020/00001");
    }

    @Test
    void setReference(){
        SequenceEcritureComptable sequenceEcritureComptable = new SequenceEcritureComptable(journal,2019,1);
        assertEquals("AC-2019/00001",manager.setReference( sequenceEcritureComptable ) );
        assertNotEquals("AL-2019/0001",manager.setReference( sequenceEcritureComptable ) );

        sequenceEcritureComptable = new SequenceEcritureComptable(journal,2016,1);
        assertNotEquals("AV-2019/00001",manager.setReference( sequenceEcritureComptable ) );


    }


    @Test
    @DisplayName("Verify that no exception if the EcritureComptable is correct")
    void checkEcritureCompt() throws FunctionalException {
        //GIVEN
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
                null, new BigDecimal(123),
                null));
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(2),
                null, null,
                new BigDecimal(123)));

        //THEN-WHEN
        Assertions.assertThatCode(() -> manager.checkEcritureComptableUnit(vEcritureComptable))
                .doesNotThrowAnyException();

    }

    @Test
    @DisplayName("Verify that null checkEcritureComptableUnit thrown FunctionalException")
    void checkEcritureComptableUnit() {
        assertThrows(FunctionalException.class, () -> {
            manager.checkEcritureComptableUnit(vEcritureComptable);
        });
    }


    @Test
    @DisplayName("When total Debit is greater than total Credit checkEcritureComptableUnit thrown FunctionalException")
    void checkEcritureComptableRG2()   {
        //GIVEN
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
                null, new BigDecimal(12345),
                null));
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(2),
                null, null,
                new BigDecimal(1234)));

        //THEN-WHEN
        assertThrows(FunctionalException.class, () -> {
            manager.checkEcritureComptableRG2(vEcritureComptable);
        });

    }

    @Test
    @DisplayName("When total Debit is less than total Credit checkEcritureComptableUnit thrown FunctionalException")
    void checkEcritureComptableRG2_throwFunctionalException(){
        //GIVEN
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
                null, new BigDecimal(123),
                null));
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(2),
                null, null,
                new BigDecimal(1234)));

        //THEN-WHEN
        assertThrows(FunctionalException.class, () -> {
            manager.checkEcritureComptableRG2(vEcritureComptable);
        });

    }

    @Test
    @DisplayName("When there are only one Debit LigneEcritureComptable checkEcritureComptableUnit thrown FunctionalException")
    void checkEcritureComptableRG3_ifOnlyOneDebitLigneEcritureComptable() {
        vEcritureComptable.getListLigneEcriture().add(
                new LigneEcritureComptable(new CompteComptable(1),
                        null,
                        new BigDecimal(123),
                        null));

        //THEN-WHEN
        assertThrows(FunctionalException.class, () -> {
            manager.checkEcritureComptableRG3(vEcritureComptable);
        });
    }

    @Test
    @DisplayName("When there are only one Credit LigneEcritureComptable checkEcritureComptableUnit throw FunctionalException")
    void checkEcritureComptableRG3_ifOnlyOneCrediLigneEcritureComptable() {
        //GIVEN
        vEcritureComptable.getListLigneEcriture().add(
                new LigneEcritureComptable(new CompteComptable(1),
                        null,
                        null,
                        new BigDecimal(123)));

        //THEN-WHEN
        assertThrows(FunctionalException.class, () -> {
            manager.checkEcritureComptableRG3(vEcritureComptable);
        });
    }

    @Test
    @DisplayName("Accept negative values")
    void checkEcritureComptableRG4_returnNoException(){
        //GIVEN
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
                null, new BigDecimal(-123),
                null));
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(2),
                null, null,
                new BigDecimal(-123)));

        //THEN-WHEN
        Assertions.assertThatCode(() -> manager.checkEcritureComptableUnit(vEcritureComptable))
                .doesNotThrowAnyException();

    }

    @Test
    @DisplayName("Bad Reference Journal Code throw FunctionalException")
    void checkEcritureComptableRG5_throwFunctionalException_ofCode() {
        //GIVEN
        vEcritureComptable.setReference("BQ-2016/00001");
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
                null, new BigDecimal(123),
                null));
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(2),
                null, null,
                new BigDecimal(123)));

        //THEN-WHEN
        assertThrows(FunctionalException.class, () -> {
            manager.checkEcritureComptableRG5(vEcritureComptable);
        });
    }

    @Test
    @DisplayName("Bad Reference Journal Year throw FunctionalException")
    void checkEcritureComptableRG5_throwFunctionalException_ofYear() {
        //GIVEN
        vEcritureComptable.setReference("AC-2018/00001");
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
                null, new BigDecimal(123),
                null));
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(2),
                null, null,
                new BigDecimal(123)));

        //THEN-WHEN
        assertThrows(FunctionalException.class, () -> {
            manager.checkEcritureComptableRG5(vEcritureComptable);
        });

    }

    @Test
    @DisplayName("The LigneEcritureComptable Debit amout can't exceed 2 decimals")
    void checkEcritureComptableRG7_ofLigneEcritureComptableWith3DecimalDebitAmount() {
        //GIVEN
        vEcritureComptable.setReference("AC-2018/00001");
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
                null, new BigDecimal(123.123),
                null));
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(2),
                null, null,
                new BigDecimal(123.12)));

        //THEN-WHEN
        assertThrows(FunctionalException.class, () -> {
            manager.checkEcritureComptableRG7(vEcritureComptable);
        });
    }

    @Test
    @DisplayName("The LigneEcritureComptable Credit amout can't exceed 2 decimals")
    void checkEcritureComptableRG7_ofLigneEcritureComptableWith3DecimalCrebitAmount() throws Exception {
        //GIVEN
        vEcritureComptable.setReference("AC-2018/00001");
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
                null,
                new BigDecimal(123.12),
                null));
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(2),
                null, null,
                new BigDecimal(-123.125)));

        //THEN-WHEN
        assertThrows(FunctionalException.class, () -> {
            manager.checkEcritureComptableRG7(vEcritureComptable);
        });
    }
}
