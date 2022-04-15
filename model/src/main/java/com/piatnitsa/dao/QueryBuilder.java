package com.piatnitsa.dao;

import com.piatnitsa.exception.DaoException;
import com.piatnitsa.exception.DaoExceptionMessageCodes;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;

@Component
public class QueryBuilder {

    public String buildQueryWithFilters(String basicQuery, Map<String, String> filterParams) throws DaoException {
        StringBuilder query = new StringBuilder(basicQuery);
        for (Map.Entry<String, String> entry : filterParams.entrySet()) {
            String param = entry.getKey().toLowerCase();
            switch (param) {
                case "name":
                case "description":
                case "tag_name": {
                    addPartParameter(query, param, entry.getValue());
                    break;
                }
                case "date_sort": {
                    addSortParameter(query, "create_date", entry.getValue());
                    break;
                }
                case "name_sort": {
                    addSortParameter(query, "name", entry.getValue());
                    break;
                }
                case "tag_name_sort": {
                    addSortParameter(query, "tag_name", entry.getValue());
                    break;
                }
                default: throw new DaoException(DaoExceptionMessageCodes.NO_ENTITIES_WITH_PARAMETERS);
            }
        }
        return query.toString();
    }

    public String buildUpdateQuery(String basicQuery, Map<String, String> updatableParams) {
        StringBuilder updateQuery = new StringBuilder(basicQuery);

        Set<Map.Entry<String, String>> entries = updatableParams.entrySet();
        for (Map.Entry<String, String> entry : entries) {
            updateQuery.append(entry.getKey())
                    .append("=")
                    .append('\'').append(entry.getValue()).append('\'')
                    .append(", ");
        }
        updateQuery.deleteCharAt(updateQuery.length() - 2);
        updateQuery.append(" where id=").append(updatableParams.get("id"));

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
