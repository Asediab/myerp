package com.dummy.myerp.model.bean.comptabilite;


import java.util.List;
import java.util.Objects;

/**
 * Bean representing a sequence for accounting entry references
 */
public class SequenceEcritureComptable {

    // ==================== Attributs ====================

    private JournalComptable journalComptable;
    /**
     * The year
     */
    private Integer year;
    /**
     * The last value used
     */
    private Integer lastValue;

    // ==================== Constructeurs ====================

    /**
     * Constructeur
     */
    public SequenceEcritureComptable() {
    }

//    /**
//     * Constructeur
//     *
//     * @param pYear      -
//     * @param pLastValue -
//     */
    public SequenceEcritureComptable(JournalComptable journalComptable, Integer year, Integer lastValue) {
        this.journalComptable = journalComptable;
        this.year = year;
        this.lastValue = lastValue;
    }


    // ==================== Getters/Setters ====================


    public JournalComptable getJournalComptable() {
        return journalComptable;
    }

    public void setJournalComptable(JournalComptable journalComptable) {
        this.journalComptable = journalComptable;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer pYear) {
        year = pYear;
    }

    public Integer getLastValue() {
        return lastValue;
    }

    public void setLastValue(Integer pLastValue) {
        lastValue = pLastValue;
    }


    // ==================== Méthodes STATIC ====================
    /**
     * Renvoie le {@link SequenceEcritureComptable} de code {@code pCode} s'il est présent dans la liste
     *
     * @param pList la liste où chercher le {@link SequenceEcritureComptable}
     * @param pCode le code du {@link SequenceEcritureComptable} à chercher
     * @param  pYear l'année du {@link SequenceEcritureComptable} à chercher
     * @return {@link SequenceEcritureComptable} ou {@code null}
     */
    public static SequenceEcritureComptable getByCodeAndYear(List<? extends SequenceEcritureComptable> pList, String pCode, Integer pYear) {
        SequenceEcritureComptable vRetour = null;
        for (SequenceEcritureComptable vBean : pList) {
            if (isSequenceEcritureComptableExist( vBean,  pCode, pYear )) {
                vRetour = vBean;
                break;
            }
        }
        return vRetour;
    }

    public static boolean isSequenceEcritureComptableExist(SequenceEcritureComptable vBean, String pCode, Integer pYear){
        return (vBean != null && Objects.equals(vBean.getJournalComptable().getCode(), pCode) && Objects.equals(vBean.getYear(), pYear));
    }


//    @Override
//    public String toString() {
//        return "SequenceEcritureComptable{" +
//                "journalComptable=" + journalComptable +
//                ", year=" + year +
//                ", lastValue=" + lastValue +
//                '}';
//    }
//
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//
//        SequenceEcritureComptable that = (SequenceEcritureComptable) o;
//
//        if (!Objects.equals(journalComptable, that.journalComptable))
//            return false;
//        if (!Objects.equals(year, that.year)) return false;
//        return Objects.equals(lastValue, that.lastValue);
//    }
//
//    @Override
//    public int hashCode() {
//        int result = journalComptable != null ? journalComptable.hashCode() : 0;
//        result = 31 * result + (year != null ? year.hashCode() : 0);
//        result = 31 * result + (lastValue != null ? lastValue.hashCode() : 0);
//        return result;
//    }
}
