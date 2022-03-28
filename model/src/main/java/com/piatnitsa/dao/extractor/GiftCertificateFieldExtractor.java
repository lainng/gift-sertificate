package com.piatnitsa.dao.extractor;

import com.piatnitsa.entity.GiftCertificate;

import java.util.HashMap;
import java.util.Map;

public class GiftCertificateFieldExtractor {

    public Map<String, String> extractData(GiftCertificate certificate) {
        Map<String, String> fields = new HashMap<>();

        if (certificate.getName() != null && !certificate.getName().isEmpty()) {
            fields.put("name", certificate.getName());
        }

        if (certificate.getDescription() != null && !certificate.getDescription().isEmpty()) {
            fields.put("description", certificate.getDescription());
        }

        if (certificate.getPrice() != null) {
            fields.put("price", certificate.getPrice().toString());
        }

        if (certificate.getDuration() != 0) {
            fields.put("duration", String.valueOf(certificate.getDuration()));
        }
        fields.put("last_update_date", certificate.getLastUpdateDate());

        return fields;
    }
}
