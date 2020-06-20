package com.dummy.myerp.model.bean.comptabilite;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class JournalComptableTest {
    private final List<JournalComptable> journalComptables = new ArrayList<>();
    private JournalComptable journalComptable1;
    private JournalComptable journalComptable2;


    @BeforeAll
    public void init() {
        journalComptable1 = new JournalComptable("1", "JournCompt 1");
        journalComptable2 = new JournalComptable("300", "JournCompt 45");

        journalComptables.add(journalComptable1);
        journalComptables.add(journalComptable2);
    }

    @Test
    public void getByCode_returnsTheRightJournalComptable_ofListAndAxistingCode() {
        Assertions.assertEquals(journalComptable1, JournalComptable.getByCode(journalComptables, journalComptable1.getCode()));
        Assertions.assertEquals(journalComptable2, JournalComptable.getByCode(journalComptables, journalComptable2.getCode()));
    }

    @Test
    public void getByCode_returnsNull_ofListAndNonExistingCode() {
        Assertions.assertNull(JournalComptable.getByCode(journalComptables, "70"));
        Assertions.assertNull(JournalComptable.getByCode(journalComptables, "-70"));
        Assertions.assertNull(JournalComptable.getByCode(journalComptables, "sdf"));
    }
}
