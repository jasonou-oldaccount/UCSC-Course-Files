INSERT INTO mg_medicines(medicine_id, name, price) SELECT h.medicine_id, h.name, h.price FROM h_medicines h;
CREATE SEQUENCE mg_medicines_seq START 80;
ALTER TABLE mg_medicines ALTER COLUMN medicine_id SET DEFAULT NEXTVAL ('mg_medicines_seq');
INSERT INTO mg_medicines(name, price) SELECT new_medicines.name, new_medicines.price FROM new_medicines WHERE new_medicines.name NOT IN (SELECT h_medicines.name FROM h_medicines);