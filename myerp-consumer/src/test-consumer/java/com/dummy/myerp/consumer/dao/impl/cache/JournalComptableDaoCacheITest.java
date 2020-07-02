package com.dummy.myerp.consumer.dao.impl.cache;


import com.dummy.myerp.consumer.dao.impl.db.dao.ConsumerTestCase;
import com.dummy.myerp.model.bean.comptabilite.JournalComptable;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class JournalComptableDaoCacheITest extends ConsumerTestCase {
    private final static JournalComptableDaoCache journalComptableDaoCache = new JournalComptableDaoCache();
    private static List<JournalComptable> journalComptables = new ArrayList<>();

    @BeforeAll
    protected static void beforeAll(){
        journalComptables = getDaoProxy().getComptabiliteDao().getListJournalComptable();
    }

    @Test
    @DisplayName("Verify journalComptable by code")
    void getByCode_returnjournalComptable_byHisCode() {
        JournalComptable found = null;

        for(JournalComptable j: journalComptables) {
            found = journalComptableDaoCache.getByCode(j.getCode());
            Assertions.assertThat(found.getCode()).isEqualTo(j.getCode());
            Assertions.assertThat(found.getLibelle()).isEqualTo(j.getLibelle());
        }

    }
}