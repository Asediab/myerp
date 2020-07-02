package com.dummy.myerp.model.bean.comptabilite;


import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.sql.Date;
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
    @Pattern(regexp = "[A-Z]{2}-[0-9]{4}/[0-9]{5}")
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
        boolean vRetour = this.getTotalDebit().equals(getTotalCredit()); //compareTo
        return vRetour;
    }


    // ==================== Méthodes ====================


    @Override
    public String toString() {
        return "EcritureComptable{" +
                "id=" + id +
                ", journal=" + journal +
                ", reference='" + reference + '\'' +
                ", date=" + date +
                ", libelle='" + libelle + '\'' +
                ", listLigneEcriture=" + listLigneEcriture +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EcritureComptable that = (EcritureComptable) o;

        if (!Objects.equals(id, that.id)) return false;
        if (!Objects.equals(journal, that.journal)) return false;
        if (!Objects.equals(reference, that.reference)) return false;
        if (!Objects.equals(date, that.date)) return false;
        if (!Objects.equals(libelle, that.libelle)) return false;
        return listLigneEcriture.equals(that.listLigneEcriture);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (journal != null ? journal.hashCode() : 0);
        result = 31 * result + (reference != null ? reference.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (libelle != null ? libelle.hashCode() : 0);
        result = 31 * result + listLigneEcriture.hashCode();
        return result;
    }
}
