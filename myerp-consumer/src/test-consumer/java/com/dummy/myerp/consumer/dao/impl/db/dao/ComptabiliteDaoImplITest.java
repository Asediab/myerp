package com.dummy.myerp.consumer.dao.impl.db.dao;

import com.dummy.myerp.model.bean.comptabilite.CompteComptable;
import com.dummy.myerp.model.bean.comptabilite.JournalComptable;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

class ComptabiliteDaoImplITest extends ConsumerTestCase {

    private final static Map<Integer,String> mapCompteComptables = new HashMap<>();
    private final static Map<String,String> mapJournalComptable = new HashMap<>();

    @BeforeAll
    public static void beforeAll(){
        for(CompteComptable c: getDaoProxy().getComptabiliteDao().getListCompteComptable()) {
            mapCompteComptables.put(c.getNumero(),c.getLibelle());
        }

        for(JournalComptable j: getDaoProxy().getComptabiliteDao().getListJournalComptable()){
            mapJournalComptable.put(j.getCode(),j.getLibelle());
        }
    }


    @Test
    @DisplayName("Verify we get all CompteComptable")
    void getListCompteComptables(){
        List<CompteComptable> compteComptables = getDaoProxy().getComptabiliteDao().getListCompteComptable();

        Assertions.assertThat(compteComptables.size()).isEqualTo(mapCompteComptables.size());
        for(CompteComptable c: compteComptables) {
            Assertions.assertThat(c.getLibelle()).isEqualTo(mapCompteComptables.get(c.getNumero()));
        }
    }

    @Test
    @DisplayName("Verify we get all JournalComptable")
    void getListJournalComptables(){
        List<JournalComptable> journalComptables = getDaoProxy().getComptabiliteDao().getListJournalComptable();

        Assertions.assertThat(journalComptables.size()).isEqualTo(mapJournalComptable.size());
        for(JournalComptable j: journalComptables) {
            Assertions.assertThat(j.getLibelle()).isEqualTo(mapJournalComptable.get(j.getCode()));
        }
    }

}
