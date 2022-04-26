package com.piatnitsa.dao;

import com.piatnitsa.entity.GiftCertificateColumn;
import com.piatnitsa.entity.TagColumn;
import com.piatnitsa.exception.DaoException;
import com.piatnitsa.exception.DaoExceptionMessageCodes;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;

import static com.piatnitsa.dao.SortingParameter.*;

/**
 * This class is designed to create a selection condition in SQL queries.
 *
 * @author Vlad Piatnitsa
 * @version 1.0
 */
@Component
public class QueryBuilder {

    /**
     * Builds a query with filter parameters.
     * @param basicQuery basic query that contains DML command and table name.
     * @param filterParams parameters by which the selection is filtered.
     * @return a string SQL query.
     * @throws DaoException if parameters contain incorrect filter name.
     */
    public String buildQueryWithFilters(String basicQuery, Map<String, String> filterParams) throws DaoException {
        StringBuilder query = new StringBuilder(basicQuery);
        for (Map.Entry<String, String> entry : filterParams.entrySet()) {
            String param = entry.getKey().toLowerCase();
            switch (param) {
                case GiftCertificateColumn.NAME:
                case GiftCertificateColumn.DESCRIPTION:
                case TagColumn.TAG_NAME: {
                    addPartParameter(query, param, entry.getValue());
                    break;
                }
                case DATE_SORT_PARAMETER: {
                    addSortParameter(query, GiftCertificateColumn.CREATE_DATE, entry.getValue());
                    break;
                }
                case NAME_SORT_PARAMETER: {
                    addSortParameter(query, GiftCertificateColumn.NAME, entry.getValue());
                    break;
                }
                case TAG_NAME_SORT_PARAMETER: {
                    addSortParameter(query, TagColumn.TAG_NAME, entry.getValue());
                    break;
                }
                default: throw new DaoException(DaoExceptionMessageCodes.NO_ENTITIES_WITH_PARAMETERS);
            }
        }
        return query.toString();
    }

    /**
     * Builds a query with updated parameters.
     * @param basicQuery basic query that contains DML command and table name.
     * @param updatableParams updated parameters.
     * @return a string SQL query.
     */
    public String buildUpdateQuery(String basicQuery, Map<String, String> updatableParams) {
        StringBuilder updateQuery = new StringBuilder(basicQuery);

        String id = updatableParams.get(GiftCertificateColumn.ID);
        updatableParams.remove(GiftCertificateColumn.ID);

        Set<Map.Entry<String, String>> entries = updatableParams.entrySet();
        for (Map.Entry<String, String> entry : entries) {
            updateQuery.append(entry.getKey())
                    .append("=")
                    .append('\'').append(entry.getValue()).append('\'')
                    .append(", ");
        }
        updateQuery.deleteCharAt(updateQuery.length() - 2);
        updateQuery.append("where id=").append(id);

        return updateQuery.toString();
    }

    private void addPartParameter(StringBuilder query, String param, String value) {
        if (query.toString().contains("where")) {
            query.append(" and ");
        } else {
            query.append(" where ");
        }
        query.append(param).append(" ilike '%").append(value).append("%' ");
    }

    private void addSortParameter(StringBuilder query, String param, String value) {
        if (query.toString().contains("order by")) {
            query.append(", ");
        } else {
            query.append(" order by ");
        }
        query.append(param).append(" ").append(value);
    }
}
