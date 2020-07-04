package com.dummy.myerp.business.impl.manager;

import com.dummy.myerp.business.contrat.manager.ComptabiliteManager;
import com.dummy.myerp.business.impl.AbstractBusinessManager;
import com.dummy.myerp.model.bean.comptabilite.*;
import com.dummy.myerp.technical.exception.FunctionalException;
import com.dummy.myerp.technical.exception.NotFoundException;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.transaction.TransactionStatus;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;
import java.util.Set;


/**
 * Comptabilite manager implementation.
 */
public class ComptabiliteManagerImpl extends AbstractBusinessManager implements ComptabiliteManager {

    // ==================== Attributs ====================


    // ==================== Constructeurs ====================

    /**
     * Instantiates a new Comptabilite manager.
     */
    public ComptabiliteManagerImpl() {
    }


    // ==================== Getters/Setters ====================
    @Override
    public List<CompteComptable> getListCompteComptable() {
        return getDaoProxy().getComptabiliteDao().getListCompteComptable();
    }


    @Override
    public List<JournalComptable> getListJournalComptable() {
        return getDaoProxy().getComptabiliteDao().getListJournalComptable();
    }

    @Override
    public void insertSequenceEcritureComptable(SequenceEcritureComptable sequenceEcritureComptable) {
        getDaoProxy().getComptabiliteDao().insertSequenceEcritureComptable( sequenceEcritureComptable );
    }

    @Override
    public void updateSequenceEcritureComptable(SequenceEcritureComptable sequenceEcritureComptable) {
        getDaoProxy().getComptabiliteDao().updateSequenceEcritureComptable( sequenceEcritureComptable);
    }

    @Override
    public  SequenceEcritureComptable getSequenceEcritureComptable(String pJournalCode, Integer pAnnee){
        return getDaoProxy().getComptabiliteDao().getSequenceEcritureComptable( pJournalCode,pAnnee );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<EcritureComptable> getListEcritureComptable() {
        return getDaoProxy().getComptabiliteDao().getListEcritureComptable();
    }

    /**
     * @param id  de l'écriture comptable
     * @return {@link EcritureComptable}
     */
    @Override
    public EcritureComptable getEcritureComptableById(Integer id) throws NotFoundException {
        return getDaoProxy().getComptabiliteDao().getEcritureComptable(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public synchronized void addReference(EcritureComptable pEcritureComptable) throws FunctionalException {
        Integer annee = Integer.parseInt(new SimpleDateFormat("yyyy").format(pEcritureComptable.getDate()));
        SequenceEcritureComptable pSeq = getSequenceEcritureComptable( pEcritureComptable.getJournal().getCode(),annee);
        if ( pSeq != null){
            pSeq.setLastValue( pSeq.getLastValue() + 1 );
            updateSequenceEcritureComptable( pSeq );
        }else{
            pSeq = new SequenceEcritureComptable(pEcritureComptable.getJournal() ,annee,1);
            insertSequenceEcritureComptable( pSeq );
        }
        pEcritureComptable.setReference( setReference( pSeq ) );
    }

    /**
     * @param sequenceEcritureComptable
     * @return
     */
    public String setReference(SequenceEcritureComptable sequenceEcritureComptable){
        String reference = sequenceEcritureComptable.getJournalComptable().getCode() + "-";
        reference += sequenceEcritureComptable.getYear()  + "/";
        reference +=String.format("%05d", sequenceEcritureComptable.getLastValue() );
        return  reference;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void checkEcritureComptable(EcritureComptable pEcritureComptable) throws FunctionalException {
        this.checkEcritureComptableUnit(pEcritureComptable);
        this.checkEcritureComptableRG2(pEcritureComptable);
        this.checkEcritureComptableRG3(pEcritureComptable);
        this.checkEcritureComptableRG5(pEcritureComptable);
        this.checkEcritureComptableRG6(pEcritureComptable);
        this.checkEcritureComptableRG7(pEcritureComptable);
    }


    /**
     * Vérifie que l'Ecriture comptable respecte les règles de gestion unitaires,
     * c'est à dire indépendemment du contexte (unicité de la référence, exercie comptable non cloturé...)
     *
     * @param pEcritureComptable -
     * @throws FunctionalException Si l'Ecriture comptable ne respecte pas les règles de gestion
     */

    public void checkEcritureComptableUnit(EcritureComptable pEcritureComptable) throws FunctionalException {
        // ===== Vérification des contraintes unitaires sur les attributs de l'écriture
        Set<ConstraintViolation<EcritureComptable>> vViolations = getConstraintValidator().validate(pEcritureComptable);
        if (!vViolations.isEmpty()) {
            throw new FunctionalException("L'écriture comptable ne respecte pas les règles de gestion.",
                    new ConstraintViolationException(
                            "L'écriture comptable ne respecte pas les contraintes de validation",
                            vViolations));
        }
    }

    public void checkEcritureComptableRG2(EcritureComptable pEcritureComptable) throws FunctionalException {
        // ===== RG_Compta_2 : Pour qu'une écriture comptable soit valide, elle doit être équilibrée
        if (!pEcritureComptable.isEquilibree()) {
            throw new FunctionalException("L'écriture comptable n'est pas équilibrée.");
        }

    }

    public void checkEcritureComptableRG3(EcritureComptable pEcritureComptable) throws FunctionalException {
        // ===== RG_Compta_3 : une écriture comptable doit avoir au moins 2 lignes d'écriture (1 au débit, 1 au crédit)
        Boolean hasCreditLine = Boolean.FALSE;
        Boolean hasDebitLine = Boolean.FALSE;
        int lineCounter = 0;

        for (LigneEcritureComptable vLigneEcritureComptable : pEcritureComptable.getListLigneEcriture()) {
            lineCounter++;
            if (BigDecimal.ZERO.compareTo(ObjectUtils.defaultIfNull(vLigneEcritureComptable.getCredit(),
                    BigDecimal.ZERO)) != 0) {
                hasCreditLine = Boolean.TRUE;
            }
            if (BigDecimal.ZERO.compareTo(ObjectUtils.defaultIfNull(vLigneEcritureComptable.getDebit(),
                    BigDecimal.ZERO)) != 0) {
                hasDebitLine = Boolean.TRUE;
            }
        }

        if (!hasCreditLine.booleanValue() || !hasDebitLine.booleanValue() || lineCounter<2) {
            throw new FunctionalException(
                    "The entry must have two lines: a debit line and a credit line.");
        }
    }

    public void checkEcritureComptableRG5(EcritureComptable pEcritureComptable) throws FunctionalException {
        // ===== RG_Compta_5 : Format et contenu de la référence
        // vérifier que l'année dans la référence correspond bien à la date de l'écriture, idem pour le code journal...
        if (pEcritureComptable.getJournal()!=null && pEcritureComptable.getReference()!=null) {
            String code = pEcritureComptable.getReference().split("-")[0];

            if (!code.equals(pEcritureComptable.getJournal().getCode())) {
                throw new FunctionalException(
                        "The reference of the accounting entry must correspond to the journal.");
            }

            String year = pEcritureComptable.getReference().split("-|/")[1];
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(pEcritureComptable.getDate());
            String dateYear = String.valueOf(calendar.get(Calendar.YEAR));

            if (!year.equals(dateYear)) {
                throw new FunctionalException(
                        "The reference of the accounting entry must correspond to the journal.");
            }
        }
    }

    public void checkEcritureComptableRG6(EcritureComptable pEcritureComptable) throws FunctionalException {
        // ===== RG_Compta_6 : La référence d'une écriture comptable doit être unique
        if (StringUtils.isNoneEmpty(pEcritureComptable.getReference())) {
            try {
                // Recherche d'une écriture ayant la même référence
                EcritureComptable vECRef = getDaoProxy().getComptabiliteDao().getEcritureComptableByRef(pEcritureComptable.getReference());

                // Si l'écriture à vérifier est une nouvelle écriture (id == null),
                // ou si elle ne correspond pas à l'écriture trouvée (id != idECRef),
                // c'est qu'il y a déjà une autre écriture avec la même référence
                if (pEcritureComptable.getId() != null || Objects.equals(pEcritureComptable.getId(), vECRef.getId())) {
                    // id n'est pas null ou la même référence existe
                    throw new FunctionalException("Another accounting entry already exists with the same reference.");
                }
            } catch (NotFoundException vEx) {
                // on n'a aucune autre écriture avec la même référence.
            }

        }
    }

    public void checkEcritureComptableRG7(EcritureComptable pEcritureComptable) throws FunctionalException {
        List<LigneEcritureComptable> ligneEcritureComptables = pEcritureComptable.getListLigneEcriture();

        for(LigneEcritureComptable ligneEcritureComptable : ligneEcritureComptables) {
            if(ligneEcritureComptable.getCredit() != null && ligneEcritureComptable.getCredit().scale() > 2) {
                throw new FunctionalException("Cannot have more than two digits after the decimal point");
            }
            if(ligneEcritureComptable.getDebit() != null && ligneEcritureComptable.getDebit().scale() > 2) {
                throw new FunctionalException("Cannot have more than two digits after the decimal point");
            }
        }

    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void insertEcritureComptable(EcritureComptable pEcritureComptable) throws FunctionalException {
        this.checkEcritureComptable(pEcritureComptable);
        TransactionStatus vTS = getTransactionManager().beginTransactionMyERP();
        try {
            getDaoProxy().getComptabiliteDao().insertEcritureComptable(pEcritureComptable);
            getTransactionManager().commitMyERP(vTS);
            vTS = null;
        } finally {
            getTransactionManager().rollbackMyERP(vTS);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateEcritureComptable(EcritureComptable pEcritureComptable) throws FunctionalException {
        this.checkEcritureComptable(pEcritureComptable);
        TransactionStatus vTS = getTransactionManager().beginTransactionMyERP();
        try {
            getDaoProxy().getComptabiliteDao().updateEcritureComptable(pEcritureComptable);
            getTransactionManager().commitMyERP(vTS);
            vTS = null;
        } finally {
            getTransactionManager().rollbackMyERP(vTS);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteEcritureComptable(Integer pId) {
        TransactionStatus vTS = getTransactionManager().beginTransactionMyERP();
        try {
            getDaoProxy().getComptabiliteDao().deleteEcritureComptable(pId);
            getTransactionManager().commitMyERP(vTS);
            vTS = null;
        } finally {
            getTransactionManager().rollbackMyERP(vTS);
        }
    }
}
