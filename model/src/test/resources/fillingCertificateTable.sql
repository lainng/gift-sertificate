/* --------------filling in the table "gift_certificates"---------------------*/
INSERT INTO gift_certificate (id, name, description, price, duration, create_date, last_update_date)
VALUES ('1', 'giftCertificate1', 'description1', 99.9, 1, '2020-10-20T07:20:15.156', '2020-10-20T07:20:15.156');

INSERT INTO gift_certificate (id, name, description, price, duration, create_date, last_update_date)
VALUES ('2', 'giftCertificate3', 'description3', 100.99, 3, '2019-10-20T07:20:15.156', '2019-10-20T07:20:15.156');

INSERT INTO gift_certificate (id, name, description, price, duration, create_date, last_update_date)
VALUES ('3', 'giftCertificate2', 'description2', 999.99, 2, '2018-10-20T07:20:15.156', '2018-10-20T07:20:15.156');

/* --------------filling in the table "gift_certificates_tags"---------------------*/

INSERT INTO gift_certificate_with_tags (gift_certificate_id, tag_id)
VALUES (1, 2);

INSERT INTO gift_certificate_with_tags (gift_certificate_id, tag_id)
VALUES (2, 2);

INSERT INTO gift_certificate_with_tags (gift_certificate_id, tag_id)
VALUES (1, 1);