package com.dummy.myerp.model.bean.comptabilite;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Objects;


/**
 * Bean representing a Journal Comptable
 */
public class JournalComptable {

    // ==================== Attributs ====================
    /**
     * code
     */
    @NotNull
    @Size(min = 1, max = 5)
    private String code;

    /**
     * libelle
     */
    @NotNull
    @Size(min = 1, max = 150)
    private String libelle;


    // ==================== Constructeurs ====================

    /**
     * Instantiates a new Journal comptable.
     */
    public JournalComptable() {
    }

    /**
     * Instantiates a new Journal comptable.
     *
     * @param pCode    the p code
     * @param pLibelle the p libelle
     */
    public JournalComptable(String pCode, String pLibelle) {
        code = pCode;
        libelle = pLibelle;
    }


    // ==================== Getters/Setters ====================
    public String getCode() {
        return code;
    }

    public void setCode(String pCode) {
        code = pCode;
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
        return "JournalComptable{" +
                "code='" + code + '\'' +
                ", libelle='" + libelle + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        JournalComptable that = (JournalComptable) o;

        if (!Objects.equals(code, that.code)) return false;
        return Objects.equals(libelle, that.libelle);
    }

    @Override
    public int hashCode() {
        int result = code != null ? code.hashCode() : 0;
        result = 31 * result + (libelle != null ? libelle.hashCode() : 0);
        return result;
    }


    // ==================== Méthodes STATIC ====================

    /**
     * Renvoie le {@link JournalComptable} de code {@code pCode} s'il est présent dans la liste
     *
     * @param pList la liste où chercher le {@link JournalComptable}
     * @param pCode le code du {@link JournalComptable} à chercher
     * @return {@link JournalComptable} ou {@code null}
     */
    public static JournalComptable getByCode(List<? extends JournalComptable> pList, String pCode) {
        JournalComptable vRetour = null;
        for (JournalComptable vBean : pList) {
            if (vBean != null && Objects.equals(vBean.getCode(), pCode)) {
                vRetour = vBean;
                break;
            }
        }
        return vRetour;
    }
}
