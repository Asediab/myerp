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
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

class EcritureComptableDaoImplTest extends ConsumerTestCase {

    private static Date date;
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    private static String annee;
    private static EcritureComptable vEcritureComptable, newEcrit;
    private static List<EcritureComptable> ecritureComptables;
    private static HashMap<Integer,List<LigneEcritureComptable>> ligneEcritureComptables;

    @BeforeAll
    public static void beforeAll() throws ParseException {
        newEcrit = new EcritureComptable();
        date = dateFormat.parse("01/05/2020");

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        annee = sdf.format(date);

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

    @AfterEach
    public void afterEach() {

    }

    @Test
    @Tag("loadListLignEcriture")
    @DisplayName("Load all LigneEcriture of one EcritureComptable")
    void loadListLigneEcriture_returnListLigneEcriture_ofOneEcritureComptable(){

        ligneEcritureComptables.forEach((ecritureId,list) -> {
            vEcritureComptable.setId(ecritureId);
            getDaoProxy().getComptabiliteDao().loadListLigneEcriture(vEcritureComptable);
            Assertions.assertThat(vEcritureComptable.getListLigneEcriture().size()).isEqualTo(list.size());
        });

    }

    @Test
    @Tag("getListEcritureComptable")
    @DisplayName("Get all EcritureComptable")
    void getListEcritureComptable_returnAllEcritureComptable_ofComptabilité(){
        List<EcritureComptable> list = getDaoProxy().getComptabiliteDao().getListEcritureComptable();

        Assertions.assertThat(list.size()).isEqualTo(ecritureComptables.size());
    }


    @Test
    @Tag("getEcritureComptable")
    @DisplayName("Get the right EcritureComptable by Id")
    void getEcritureComptable_returnEcritureComptable_byId(){
        Integer ecritureId = null;
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
    @Tag("getEcritureComptableByRef")
    @DisplayName("Get the right EcritureComptable by Reference")
    void getEcritureComptableByRef_returnEcritureComptable_byReference(){
        String reference = null;

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
    @Tag("getEcritureComptableByRef")
    @DisplayName("Throws NotFoundException if the Reference of EcritureComptable doesn't exist")
    void getEcritureComptableByRef_throwsNotFoundException_OfUnknownReference(){

        assertThrows(NotFoundException.class, () -> {
            getDaoProxy().getComptabiliteDao().getEcritureComptableByRef("oaizz5");
        });
    }

    @Test
    @Tag("getEcritureComptableByRef")
    @DisplayName("Throws NotFoundException if the Reference of EcritureComptable is null")
    void getEcritureComptableByRef_throwsNotFoundException_OfNullReference(){

        assertThrows(NotFoundException.class, () -> {
            getDaoProxy().getComptabiliteDao().getEcritureComptableByRef(null);
        });
    }


    @Test
    @Tag("insertEcritureComptable")
    @DisplayName("Verify that we can insert EcritureComptable")
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
    @Tag("updateEcritureComptable")
    @DisplayName("Assume that we can update an EcritureComptable")
    void updateEcritureComptable() {
        Integer ecritureId = ecritureComptables.get(1).getId();
        vEcritureComptable = newEcrit;
        vEcritureComptable.setId(ecritureId);

        // change EcritureContable with newEC values
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

        // restore EcritureContable with old values
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
    @Tag("deleteEcritureComptable")
    @DisplayName("Verify that we can delete an EcritureComptable")
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
