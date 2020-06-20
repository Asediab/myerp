package com.dummy.myerp.model.bean.comptabilite;


/**
 * Bean representing a sequence for accounting entry references
 */
public class SequenceEcritureComptable {

    // ==================== Attributs ====================
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

    /**
     * Constructeur
     *
     * @param pYear      -
     * @param pLastValue -
     */
    public SequenceEcritureComptable(Integer pYear, Integer pLastValue) {
        year = pYear;
        lastValue = pLastValue;
    }


    // ==================== Getters/Setters ====================
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


    // ==================== Méthodes ====================
    @Override
    public String toString() {
        final StringBuilder vStB = new StringBuilder(this.getClass().getSimpleName());
        final String vSEP = ", ";
        vStB.append("{")
                .append("annee=").append(year)
                .append(vSEP).append("derniereValeur=").append(lastValue)
                .append("}");
        return vStB.toString();
    }
}
