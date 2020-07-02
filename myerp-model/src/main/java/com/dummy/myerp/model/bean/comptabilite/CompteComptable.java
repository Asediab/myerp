package com.dummy.myerp.model.bean.comptabilite;


import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Objects;


/**
 * Bean représentant un Compte Comptable
 */
public class CompteComptable {
    // ==================== Attributs ====================
    /**
     * The Numero.
     */
    @NotNull
    private Integer numero;

    /**
     * The Libelle.
     */
    @NotNull
    @Size(min = 1, max = 150)
    private String libelle;


    // ==================== Constructeurs ====================

    /**
     * Instantiates a new Compte comptable.
     */
    public CompteComptable() {
    }

    /**
     * Instantiates a new Compte comptable.
     *
     * @param pNumero the numero
     */
    public CompteComptable(Integer pNumero) {
        numero = pNumero;
    }

    /**
     * Instantiates a new Compte comptable.
     *
     * @param pNumero  the numero
     * @param pLibelle the libelle
     */
    public CompteComptable(Integer pNumero, String pLibelle) {
        numero = pNumero;
        libelle = pLibelle;
    }


    // ==================== Getters/Setters ====================
    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer pNumero) {
        numero = pNumero;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String pLibelle) {
        libelle = pLibelle;
    }


    // ==================== Méthodes ====================


    @Override
    public String toString() {
        return "CompteComptable{" +
                "numero=" + numero +
                ", libelle='" + libelle + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CompteComptable that = (CompteComptable) o;

        if (!Objects.equals(numero, that.numero)) return false;
        return Objects.equals(libelle, that.libelle);
    }

    @Override
    public int hashCode() {
        int result = numero != null ? numero.hashCode() : 0;
        result = 31 * result + (libelle != null ? libelle.hashCode() : 0);
        return result;
    }


    // ==================== Méthodes STATIC ====================

    /**
     * Renvoie le {@link CompteComptable} de numéro {@code pNumero} s'il est présent dans la liste
     *
     * @param pList   la liste où chercher le {@link CompteComptable}
     * @param pNumero le numero du {@link CompteComptable} à chercher
     * @return {@link CompteComptable} ou {@code null}
     */
    public static CompteComptable getByNumero(List<? extends CompteComptable> pList, Integer pNumero) {
        CompteComptable vRetour = null;
        for (CompteComptable vBean : pList) {
            if (vBean != null && Objects.equals(vBean.getNumero(), pNumero)) {
                vRetour = vBean;
                break;
            }
        }
        return vRetour;
    }
}
