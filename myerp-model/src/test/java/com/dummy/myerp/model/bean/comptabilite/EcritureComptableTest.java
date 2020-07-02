package com.dummy.myerp.model.bean.comptabilite;

import org.apache.commons.lang3.ObjectUtils;
import org.junit.jupiter.api.*;

import java.math.BigDecimal;
import java.sql.Date;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;


class EcritureComptableTest  {
    private static final Integer id = 123;
    private static final String reference = "AC-2020/00011";
    private static final String libelle = "Equilibrée";
    private static final BigDecimal[] debits = {new BigDecimal("200.50"), new BigDecimal("100.50"), null, new BigDecimal("40.00")};
    private static final BigDecimal[] credits = {null, new BigDecimal("33.00"), new BigDecimal("301.00"), new BigDecimal("7.00")};
    private static EcritureComptable ecritureComptable;
    private static JournalComptable journalComptable;
    private static BigDecimal debitSum;
    private static BigDecimal creditSum;


    private LigneEcritureComptable createLigne(Integer pCompteComptableNumero, String pDebit, String pCredit) {
        BigDecimal vDebit = pDebit == null ? null : new BigDecimal(pDebit);
        BigDecimal vCredit = pCredit == null ? null : new BigDecimal(pCredit);
        String vLibelle = ObjectUtils.defaultIfNull(vDebit, BigDecimal.ZERO)
                .subtract(ObjectUtils.defaultIfNull(vCredit, BigDecimal.ZERO)).toPlainString();
        return new LigneEcritureComptable(new CompteComptable(pCompteComptableNumero),
                vLibelle,
                vDebit, vCredit);
    }

    @BeforeAll
    private static void beforeAll() {
        journalComptable = new JournalComptable("BD", "Journal comptable 10");
    }

    @BeforeEach
    private void beforeEach() throws Exception{
        ecritureComptable = new EcritureComptable();
        ecritureComptable.setId(id);
        ecritureComptable.setJournal(journalComptable);
        ecritureComptable.setReference(reference);
        ecritureComptable.setDate(new Date( 2020-05-17));
        ecritureComptable.setLibelle(libelle);
        String debitValue, creditValue;

        for (int i = 0; i < 4; i++) {
            debitValue = debits[i] != null ? debits[i].toString() : null;
            creditValue = credits[i] != null ? credits[i].toString() : null;
            ecritureComptable.getListLigneEcriture().add(this.createLigne(i < 2 ? 1 : 2, debitValue, creditValue));
        }


        debitSum = Arrays.asList(debits).stream().filter(Objects::nonNull).reduce(BigDecimal.ZERO, BigDecimal::add);
        creditSum = Arrays.asList(credits).stream().filter(Objects::nonNull).reduce(BigDecimal.ZERO, BigDecimal::add);

    }

    @Test
    @DisplayName("If  EcritureComptable is balanced than return true")
    void isEquilibree_returnsTrue_() {

        assertThat(creditSum.byteValue()).isEqualTo(debitSum.byteValue());
        assertThat(ecritureComptable.isEquilibree()).isTrue();
    }

    @Test
    @DisplayName("If EcritureComptable is not balanced than return false")
    void isEquilibree_returnsFalse() {
        ecritureComptable.getListLigneEcriture().add(this.createLigne(3, "0", "200"));
        ecritureComptable.getListLigneEcriture().add(this.createLigne(3, "4", "2"));

        assertThat(ecritureComptable.isEquilibree()).isFalse();
    }


    @Test
    @DisplayName("Return total Debit of EcritureComptable")
    void getTotalDebit_returnsTotalDebit() {

        assertThat(ecritureComptable.getTotalDebit()).isEqualTo(debitSum);
    }

    @Test
    @DisplayName("Return total Credit of EcritureComptable")
    void getTotalDebit_returnsTotalCredit() {

        assertThat(ecritureComptable.getTotalCredit()).isEqualTo(creditSum);
    }

    @Test
    @DisplayName("Return id of EcritureComptable")
    void getId() {
        assertThat(ecritureComptable.getId()).isEqualTo(id);

    }

    @Test
    @DisplayName("Change id of EcritureComptable")
    void setId() {
        Integer newId = 005;
        ecritureComptable.setId(newId);

        assertThat(ecritureComptable.getId()).isEqualTo(newId);
    }

    @Test
    @DisplayName("Return JournalComptabler of EcritureComptable")
    void getJournal() {
        assertThat(ecritureComptable.getJournal()).isEqualTo(journalComptable);
    }

    @Test
    @DisplayName("Change JournalComptabler of EcritureComptable")
    void setJournal() {
        JournalComptable newJournalComptable = new JournalComptable("37", "Journal comptable 37");

        ecritureComptable.setJournal(newJournalComptable);

        assertThat(ecritureComptable.getJournal()).isEqualTo(newJournalComptable);
    }

    @Test
    @DisplayName("Return Reference of EcritureComptable")
    void getReference() {
        assertThat(ecritureComptable.getReference()).isEqualTo(reference);
    }

    @Test
    @DisplayName("Change Reference of EcritureComptable")
    void setReference_changeReference_ofEcritureComptableWithGoodRegex() {
        String newReference = "BQ-2016/00001";

        ecritureComptable.setReference(newReference);

        assertThat(ecritureComptable.getReference()).isEqualTo(newReference);
    }


    @Test
    @DisplayName("Return Date of EcritureComptable")
    void getDate() throws ParseException {
        assertThat(ecritureComptable.getDate()).isEqualTo(new Date(2020-05-17));
    }

    @Test
    @DisplayName("Change Date of EcritureComptable")
    void setDate() throws ParseException {
        Date newDate = new Date(2020-05-17);

        ecritureComptable.setDate(newDate);

        assertThat(ecritureComptable.getDate()).isEqualTo(newDate);
    }

    @Test
    @DisplayName("Return Libelle of EcritureComptable")
    void getLibelle() {
        assertThat(ecritureComptable.getLibelle()).isEqualTo(libelle);
    }

    @Test
    @DisplayName("Change Libelle of EcritureComptable")
    void setLibelle() {
        String newLibelle = "Nouvelle ecriture comptable";

        ecritureComptable.setLibelle(newLibelle);

        assertThat(ecritureComptable.getLibelle()).isEqualTo(newLibelle);
    }

    @Test
    @DisplayName("To string")
    void toStringTest() {

        String output = "EcritureComptable{id=123, journal=JournalComptable{code='BD', libelle='Journal comptable 10'}, " +
                "reference='AC-2020/00011', date=" +
                ecritureComptable.getDate() +
                ", libelle='Equilibrée', " +
                "listLigneEcriture=[" +
                "LigneEcritureComptable{compteComptable=CompteComptable{numero=1, libelle='null'}, libelle='200.50', debit=200.50, credit=null}, " +
                "LigneEcritureComptable{compteComptable=CompteComptable{numero=1, libelle='null'}, libelle='67.50', debit=100.50, credit=33.00}, " +
                "LigneEcritureComptable{compteComptable=CompteComptable{numero=2, libelle='null'}, libelle='-301.00', debit=null, credit=301.00}, " +
                "LigneEcritureComptable{compteComptable=CompteComptable{numero=2, libelle='null'}, libelle='33.00', debit=40.00, credit=7.00}" +
                "]}";

        assertThat(ecritureComptable.toString()).isEqualTo(output);
    }

    @Test
    @DisplayName("2 differents EcritureComptable not equals")
    void testEquals_returnFalse() {
        EcritureComptable ecritureComptableEquals = new EcritureComptable();
        ecritureComptableEquals.setId(ecritureComptable.getId());
        ecritureComptableEquals.setDate(ecritureComptable.getDate());
        ecritureComptableEquals.setReference(ecritureComptable.getReference());
        ecritureComptableEquals.setLibelle(ecritureComptable.getLibelle());
        ecritureComptableEquals.setJournal(ecritureComptable.getJournal());

        assertThat(ecritureComptable.equals(ecritureComptableEquals)).isFalse();
    }

    @Test
    @DisplayName("HashCode Ecriture Comptable")
    void testHashCode() {
        assertThat(ecritureComptable.hashCode()).isEqualTo(ecritureComptable.hashCode());
    }

    @Test
    @DisplayName("HashCode Ligne Ecriture Comptable")
    void testHashCodeLigneEcritureComptable() {
        LigneEcritureComptable ligne = this.createLigne(3, "0", "200");
        LigneEcritureComptable ligne2 = this.createLigne(3, "0", "200");

        assertThat(ligne.equals(ligne2)).isTrue();
    }

}
