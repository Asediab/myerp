package com.dummy.myerp.consumer.dao.impl.db.rowmapper.comptabilite;

import com.dummy.myerp.consumer.dao.impl.cache.JournalComptableDaoCache;
import com.dummy.myerp.model.bean.comptabilite.SequenceEcritureComptable;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SequenceEcritureComptableRM implements RowMapper<SequenceEcritureComptable> {

    private final JournalComptableDaoCache journalComptableDaoCache = new JournalComptableDaoCache();

    @Override
    public SequenceEcritureComptable mapRow(ResultSet resultSet, int i) throws SQLException {
        SequenceEcritureComptable vBean = new SequenceEcritureComptable();
        vBean.setJournalComptable(journalComptableDaoCache.getByCode( resultSet.getString("journal_code") ) );
        vBean.setYear(resultSet.getInt("annee"));
        vBean.setLastValue(resultSet.getInt("derniere_valeur"));

        return vBean;
    }
}
