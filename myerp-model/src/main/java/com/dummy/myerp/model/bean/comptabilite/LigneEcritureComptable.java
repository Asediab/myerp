package com.dummy.myerp.model.bean.comptabilite;

import com.dummy.myerp.model.validation.constraint.MontantComptable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Objects;


/**
 * Bean representing a Ligne d'écriture comptable.
 */
public class LigneEcritureComptable {

    // ==================== Attributs ====================
    /**
     * Compte Comptable
     */
    @NotNull
    private CompteComptable compteComptable;

    /**
     * The Libelle.
     */
    @Size(max = 200)
    private String libelle;

    /**
     * The Debit.
     */
    @MontantComptable
    private BigDecimal debit;

    /**
     * The Credit.
     */
    @MontantComptable
    private BigDecimal credit;


    // ==================== Constructeurs ====================

    /**
     * Instantiates a new Ligne ecriture comptable.
     */
    public LigneEcritureComptable() {
    }

    /**
     * Instantiates a new Ligne ecriture comptable.
     *
     * @param pCompteComptable the Compte Comptable
     * @param pLibelle         the libelle
     * @param pDebit           the debit
     * @param pCredit          the credit
     */
    public LigneEcritureComptable(CompteComptable pCompteComptable, String pLibelle,
                                  BigDecimal pDebit, BigDecimal pCredit) {
        compteComptable = pCompteComptable;
        libelle = pLibelle;
        debit = pDebit;
        credit = pCredit;
    }


    // ==================== Getters/Setters ====================
    public CompteComptable getCompteComptable() {
        return compteComptable;
    }

    public void setCompteComptable(CompteComptable pCompteComptable) {
        compteComptable = pCompteComptable;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String pLibelle) {
        libelle = pLibelle;
    }

    public BigDecimal getDebit() {
        return debit;
    }

    public void setDebit(BigDecimal pDebit) {
        debit = pDebit;
    }

    public BigDecimal getCredit() {
        return credit;
    }

    public void setCredit(BigDecimal pCredit) {
        credit = pCredit;
    }


    // ==================== Méthodes ====================


    @Override
    public String toString() {
        return "LigneEcritureComptable{" +
                "compteComptable=" + compteComptable +
                ", libelle='" + libelle + '\'' +
                ", debit=" + debit +
                ", credit=" + credit +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LigneEcritureComptable that = (LigneEcritureComptable) o;

        if (!Objects.equals(compteComptable, that.compteComptable))
            return false;
        if (!Objects.equals(libelle, that.libelle)) return false;
        if (!Objects.equals(debit, that.debit)) return false;
        return Objects.equals(credit, that.credit);
    }

    @Override
    public int hashCode() {
        int result = compteComptable != null ? compteComptable.hashCode() : 0;
        result = 31 * result + (libelle != null ? libelle.hashCode() : 0);
        result = 31 * result + (debit != null ? debit.hashCode() : 0);
        result = 31 * result + (credit != null ? credit.hashCode() : 0);
        return result;
    }
}
