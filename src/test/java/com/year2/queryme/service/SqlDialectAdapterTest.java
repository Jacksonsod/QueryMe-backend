package com.year2.queryme.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SqlDialectAdapterTest {

    private final SqlDialectAdapter sqlDialectAdapter = new SqlDialectAdapter();

    @Test
    void adaptsCommonMysqlSyntaxToPostgresCompatibleSyntax() {
        String mysqlSql = "SELECT IFNULL(`name`, 'N/A') FROM `students` ORDER BY `id` LIMIT 5, 10";

        String adapted = sqlDialectAdapter.adaptForExecution(mysqlSql);

        assertEquals("SELECT COALESCE(\"name\", 'N/A') FROM \"students\" ORDER BY \"id\" LIMIT 10 OFFSET 5", adapted);
    }

    @Test
    void appendsReturningToFinalDmlStatementWhenMissing() {
        String sql = "UPDATE students SET city = 'Nairobi' WHERE id = 1";

        String adapted = sqlDialectAdapter.ensureFinalStatementReturnsRows(sql);

        assertTrue(adapted.endsWith("RETURNING *"));
    }

    @Test
    void keepsExplicitReturningUnchanged() {
        String sql = "DELETE FROM students WHERE id = 9 RETURNING id";

        String adapted = sqlDialectAdapter.ensureFinalStatementReturnsRows(sql);

        assertEquals(sql, adapted);
    }
}
