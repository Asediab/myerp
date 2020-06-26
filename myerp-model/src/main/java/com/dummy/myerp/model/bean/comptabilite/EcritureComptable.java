package com.dummy.myerp.model.bean.comptabilite;


import org.apache.commons.lang3.StringUtils;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;


/**
 * Bean representing a Writing accounting
 */
public class EcritureComptable {

    // ==================== Attributs ====================
    /**
     * The Id.
     */
    private Integer id;
    /**
     * Journal comptable
     */
    @NotNull
    private JournalComptable journal;
    /**
     * The Reference.
     */
    @Pattern(regexp = "\\d{1,5}-\\d{4}/\\d{5}")
    private String reference;
    /**
     * The Date.
     */
    @NotNull
    private Date date;

    /**
     * The Libelle.
     */
    @NotNull
    @Size(min = 1, max = 200)
    private String libelle;

    /**
     * The list of accounting entry lines.
     */
    @Valid
    @Size(min = 2)
    private final List<LigneEcritureComptable> listLigneEcriture = new ArrayList<>();


    // ==================== Getters/Setters ====================
    public Integer getId() {
        return id;
    }

    public void setId(Integer pId) {
        id = pId;
    }

    public JournalComptable getJournal() {
        return journal;
    }

    public void setJournal(JournalComptable pJournal) {
        journal = pJournal;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String pReference) {
        reference = pReference;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date pDate) {
        date = pDate;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String pLibelle) {
        libelle = pLibelle;
    }

    public List<LigneEcritureComptable> getListLigneEcriture() {
        return listLigneEcriture;
    }

    /**
     * Calculate and return the total of the amounts to the debit of the entry lines
     *
     * @return {@link BigDecimal}, {@link BigDecimal#ZERO} if no amount debited
     */
    public BigDecimal getTotalDebit() {
        BigDecimal vRetour = BigDecimal.ZERO;
        for (LigneEcritureComptable vLigneEcritureComptable : listLigneEcriture) {
            if (vLigneEcritureComptable.getDebit() != null) {
                vRetour = vRetour.add(vLigneEcritureComptable.getDebit());
            }
        }
        return vRetour;
    }

    /**
     * Calculate and return the total of the amounts to the credit of the entry lines
     *
     * @return {@link BigDecimal}, {@link BigDecimal#ZERO} if no credit amount
     */
    public BigDecimal getTotalCredit() {
        BigDecimal vRetour = BigDecimal.ZERO;
        for (LigneEcritureComptable vLigneEcritureComptable : listLigneEcriture) {
            if (vLigneEcritureComptable.getCredit() != null) {
                vRetour = vRetour.add(vLigneEcritureComptable.getCredit());
            }
        }
        return vRetour;
    }

    /**
     * Returns if the writing is balanced (TotalDebit = TotalCrédit)
     *
     * @return boolean
     */
    public boolean isEquilibree() {
        boolean vRetour = this.getTotalDebit().equals(getTotalCredit());
        return vRetour;
    }

    // ==================== Méthodes ====================
    @Override
    public String toString() {
        final StringBuilder vStB = new StringBuilder(this.getClass().getSimpleName());
        final String vSEP = ", ";
        vStB.append("{")
                .append("id=").append(id)
                .append(vSEP).append("journal=").append(journal)
                .append(vSEP).append("reference='").append(reference).append('\'')
                .append(vSEP).append("date=").append(date)
                .append(vSEP).append("libelle='").append(libelle).append('\'')
                .append(vSEP).append("totalDebit=").append(this.getTotalDebit().toPlainString())
                .append(vSEP).append("totalCredit=").append(this.getTotalCredit().toPlainString())
                .append(vSEP).append("listLigneEcriture=[\n")
                .append(StringUtils.join(listLigneEcriture, "\n")).append("\n]")
                .append("}");
        return vStB.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EcritureComptable)) return false;
        EcritureComptable that = (EcritureComptable) o;
        return getId().equals(that.getId()) &&
                getJournal().equals(that.getJournal()) &&
                getReference().equals(that.getReference()) &&
                getDate().equals(that.getDate()) &&
                Objects.equals(getLibelle(), that.getLibelle()) &&
                Objects.equals(getListLigneEcriture(), that.getListLigneEcriture());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getJournal(), getReference(), getDate(), getLibelle(), getListLigneEcriture());
    }
}
