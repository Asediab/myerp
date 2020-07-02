package com.dummy.myerp.consumer.dao.impl.cache;

import com.dummy.myerp.consumer.dao.impl.db.dao.ConsumerTestCase;
import com.dummy.myerp.model.bean.comptabilite.CompteComptable;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;


class CompteComptableDaoCacheITest extends ConsumerTestCase {
    private final static CompteComptableDaoCache comptableDaoCache = new CompteComptableDaoCache();
    private static List<CompteComptable> compteComptables;

    @BeforeAll
    protected static void beforeAll(){
        compteComptables = getDaoProxy().getComptabiliteDao().getListCompteComptable();
    }

    @Test
    @DisplayName("Verify CompteComptable by number")
    void getByNumero_returnCompteComptable() {
        CompteComptable found = null;

        for(CompteComptable c: compteComptables) {
            found = comptableDaoCache.getByNumero(c.getNumero());
            Assertions.assertThat(found.getNumero()).isEqualTo(c.getNumero());
            Assertions.assertThat(found.getLibelle()).isEqualTo(c.getLibelle());
        }
    }
}
