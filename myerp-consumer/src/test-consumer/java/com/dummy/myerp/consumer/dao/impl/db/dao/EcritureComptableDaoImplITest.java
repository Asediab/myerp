package com.dummy.myerp.consumer.dao.impl.db.dao;

import com.dummy.myerp.model.bean.comptabilite.CompteComptable;
import com.dummy.myerp.model.bean.comptabilite.EcritureComptable;
import com.dummy.myerp.model.bean.comptabilite.JournalComptable;
import com.dummy.myerp.model.bean.comptabilite.LigneEcritureComptable;
import com.dummy.myerp.technical.exception.NotFoundException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.sql.Date;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

class EcritureComptableDaoImplITest extends ConsumerTestCase {

    private static Date date;
    private static String annee;
    private static EcritureComptable vEcritureComptable, newEcrit;
    private static List<EcritureComptable> ecritureComptables;
    private static HashMap<Integer,List<LigneEcritureComptable>> ligneEcritureComptables;

    @BeforeAll
    public static void beforeAll() throws ParseException {
        newEcrit = new EcritureComptable();
        date = Date.valueOf("2020-05-01");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");
        annee = dateFormat.format(date);

        ecritureComptables = getDaoProxy().getComptabiliteDao().getListEcritureComptable();
        ligneEcritureComptables = new HashMap<>();

        for (EcritureComptable e: ecritureComptables) {
            ligneEcritureComptables.put(e.getId(),e.getListLigneEcriture());
        }

        CompteComptable compteComptable1 = getDaoProxy().getComptabiliteDao().getListCompteComptable().get(1);
        CompteComptable compteComptable2 = getDaoProxy().getComptabiliteDao().getListCompteComptable().get(2);
        JournalComptable journalComptable = ecritureComptables.get(1).getJournal();
        newEcrit.setJournal(ecritureComptables.get(1).getJournal());

        newEcrit.setDate(date);
        newEcrit.setReference(journalComptable.getCode() + "-" + annee + "/21");
        newEcrit.setLibelle("EcritureComptable 21");
        newEcrit.getListLigneEcriture().add(new LigneEcritureComptable(compteComptable1,
                "First line EcritureComptable", new BigDecimal(11),
                null));
        newEcrit.getListLigneEcriture().add(new LigneEcritureComptable(compteComptable2,
                "Second ligne EcritureComptable", null,
                new BigDecimal(11)));

    }

    @BeforeEach
    public void beforeEach(){
        vEcritureComptable = new EcritureComptable();
    }


    @Test
    @DisplayName("Load all LigneEcriture")
    void loadListLigneEcriture_returnListLigneEcriture(){

        ligneEcritureComptables.forEach((ecritureId,list) -> {
            vEcritureComptable.setId(ecritureId);
            getDaoProxy().getComptabiliteDao().loadListLigneEcriture(vEcritureComptable);
            Assertions.assertThat(vEcritureComptable.getListLigneEcriture().size()).isEqualTo(list.size());
        });

    }

    @Test
    @DisplayName("Get all EcritureComptable")
    void getListEcritureComptable_returnAllEcritureComptable(){
        List<EcritureComptable> list = getDaoProxy().getComptabiliteDao().getListEcritureComptable();

        Assertions.assertThat(list.size()).isEqualTo(ecritureComptables.size());
    }


    @Test
    @DisplayName("Get the right EcritureComptable by Id")
    void getEcritureComptable_returnEcritureComptable(){
        Integer ecritureId;
        try{
            for (EcritureComptable e: ecritureComptables) {
                ecritureId = e.getId();
                vEcritureComptable = getDaoProxy().getComptabiliteDao().getEcritureComptable(ecritureId);

                Assertions.assertThat(vEcritureComptable).isNotNull();
                Assertions.assertThat(vEcritureComptable.getJournal().getCode()).isEqualTo(e.getJournal().getCode());
                Assertions.assertThat(vEcritureComptable.getReference()).isEqualTo(e.getReference());
                Assertions.assertThat(vEcritureComptable.getDate()).isEqualTo(e.getDate());
                Assertions.assertThat(vEcritureComptable.getLibelle()).isEqualTo(e.getLibelle());
            }
        }catch(Exception ex){

        }

    }

    @Test
    @DisplayName("Get EcritureComptable by Reference")
    void getEcritureComptableByRef(){
        String reference;
        try {
            for (EcritureComptable e: ecritureComptables) {
                reference = e.getReference();
                vEcritureComptable = getDaoProxy().getComptabiliteDao().getEcritureComptableByRef(reference);

                Assertions.assertThat(vEcritureComptable).isNotNull();
                Assertions.assertThat(vEcritureComptable.getJournal().getCode()).isEqualTo(e.getJournal().getCode());
                Assertions.assertThat(vEcritureComptable.getId()).isEqualTo(e.getId());
                Assertions.assertThat(vEcritureComptable.getDate()).isEqualTo(e.getDate());
                Assertions.assertThat(vEcritureComptable.getLibelle()).isEqualTo(e.getLibelle());
            }

        }catch (Exception exception){

        }
    }

    @Test
    @DisplayName("NotFoundException if the Reference doesn't exist")
    void getEcritureComptableByRef_throwsNotFoundException(){

        assertThrows(NotFoundException.class, () -> {
            getDaoProxy().getComptabiliteDao().getEcritureComptableByRef("asedia");
        });
    }

    @Test
    @DisplayName("NotFoundException if the Reference is null")
    void getEcritureComptableByRef_OfNullReference(){
        assertThrows(NotFoundException.class, () -> {
            getDaoProxy().getComptabiliteDao().getEcritureComptableByRef(null);
        });
    }


    @Test
    @DisplayName("Verify we can insert EcritureComptable")
    void insertEcritureComptable(){
        EcritureComptable found = null;
        newEcrit.setId(null);
        getDaoProxy().getComptabiliteDao().insertEcritureComptable(newEcrit);

        Assertions.assertThat(newEcrit.getId()).isNotNull();

        try {
            found = getDaoProxy().getComptabiliteDao().getEcritureComptable(newEcrit.getId());
        }catch (Exception exception){

        }

        Assertions.assertThat(found.getReference()).isEqualTo(newEcrit.getReference());
        Assertions.assertThat(found.getJournal().getCode()).isEqualTo(newEcrit.getJournal().getCode());
        Assertions.assertThat(found.getDate()).isEqualTo(newEcrit.getDate());
        Assertions.assertThat(found.getLibelle()).isEqualTo(newEcrit.getLibelle());

        getDaoProxy().getComptabiliteDao().deleteEcritureComptable(newEcrit.getId());
    }

    @Test
    @DisplayName("Assume we can update an EcritureComptable")
    void updateEcritureComptable() {
        Integer ecritureId = ecritureComptables.get(1).getId();
        vEcritureComptable = newEcrit;
        vEcritureComptable.setId(ecritureId);

        getDaoProxy().getComptabiliteDao().updateEcritureComptable(vEcritureComptable);

        try {
            vEcritureComptable = getDaoProxy().getComptabiliteDao().getEcritureComptable(ecritureId);
        } catch (Exception exception) {

        }

        Assertions.assertThat(vEcritureComptable).isNotNull();
        Assertions.assertThat(vEcritureComptable.getJournal().getCode()).isEqualTo(newEcrit.getJournal().getCode());
        Assertions.assertThat(vEcritureComptable.getReference()).isEqualTo(newEcrit.getReference());
        Assertions.assertThat(vEcritureComptable.getDate()).isEqualTo(newEcrit.getDate());
        Assertions.assertThat(vEcritureComptable.getLibelle()).isEqualTo(newEcrit.getLibelle());

        vEcritureComptable = ecritureComptables.get(1);
        getDaoProxy().getComptabiliteDao().updateEcritureComptable(vEcritureComptable);

        try {
            vEcritureComptable = getDaoProxy().getComptabiliteDao().getEcritureComptable(ecritureId);
        } catch (Exception exception) {

        }

        Assertions.assertThat(vEcritureComptable).isNotNull();
        Assertions.assertThat(vEcritureComptable.getJournal().getCode()).isEqualTo(ecritureComptables.get(1).getJournal().getCode());
        Assertions.assertThat(vEcritureComptable.getReference()).isEqualTo(ecritureComptables.get(1).getReference());
        Assertions.assertThat(vEcritureComptable.getDate()).isEqualTo(ecritureComptables.get(1).getDate());
        Assertions.assertThat(vEcritureComptable.getLibelle()).isEqualTo(ecritureComptables.get(1).getLibelle());
    }

    @Test
    @DisplayName("Verify can delete an EcritureComptable")
    void deleteEcritureComptable(){
        newEcrit.setId(null);
        getDaoProxy().getComptabiliteDao().insertEcritureComptable(newEcrit);
        Integer ecritureId = newEcrit.getId();
        getDaoProxy().getComptabiliteDao().deleteEcritureComptable(ecritureId);

        assertThrows(NotFoundException.class,() -> {
            getDaoProxy().getComptabiliteDao().getEcritureComptable(ecritureId);
        });

    }

}
