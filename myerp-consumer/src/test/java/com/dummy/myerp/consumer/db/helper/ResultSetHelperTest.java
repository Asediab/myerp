package com.dummy.myerp.consumer.db.helper;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

@ExtendWith(MockitoExtension.class)
class ResultSetHelperTest {
    private static final Integer valueInteger = 1;
    private static final Long valueLong = 123456789L;
    private static final Date valueDate = new Date(2020-05-14);
    private static final String INTEGER = "labelInteger";
    private static final String LONG = "labelLong";
    private static final String DATE = "labelDate";

    @Mock
    ResultSet resultSet;

    @BeforeEach
    protected void beforeEach() throws SQLException {
        Mockito.lenient().when(resultSet.getInt(INTEGER)).thenReturn(valueInteger);
        Mockito.lenient().when(resultSet.getLong(LONG)).thenReturn(valueLong);
        Mockito.lenient().when(resultSet.getDate(DATE)).thenReturn(valueDate);
    }

    @Test
    @Tag("getInteger")
    @DisplayName("Verify that getInteger return Integer value")
    void getInteger_returnIntegerValue_ofIntegerColumn() throws SQLException {

        Assertions.assertThat(ResultSetHelper.getInteger(resultSet,INTEGER)).isEqualTo(valueInteger);
    }


    @Test
    @Tag("getLong")
    @DisplayName("Verify that getLong return Long value")
    void getLong_returnLongValue_ofLongColumn() throws SQLException{
        Assertions.assertThat(ResultSetHelper.getLong(resultSet,LONG)).isEqualTo(valueLong);
    }

    @Test
    @Tag("getDate")
    @DisplayName("Verify that getDate return Date value")
    void getDate_returnDateValue_ofDateColumn()throws SQLException {
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        Assertions.assertThat(ResultSetHelper.getDate(resultSet,DATE)).isEqualTo(formatter.format(valueDate));
    }
}
