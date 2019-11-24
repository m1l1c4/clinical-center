INSERT INTO korisnik(enabled, email, ime, password, prezime, type, active, first_login) VALUES (true,'email', 'Dragana', 'sifra','Mihajlovic', 'ADMINISTRATOR', true, false);
INSERT INTO clinic_administrator(clinic, user_id) VALUES ('Bolnica', 1);
INSERT INTO korisnik(enabled, email, ime, password, prezime, type, active, first_login) VALUES (true,'draganamihajlovic55@yahoo.com', 'Dragana', 'sifra1','Mihajlovic', 'DOKTOR', true, false);
INSERT INTO clinic( address, city, clinic_name, rating) VALUES ('Stepe Stepanovica', 'Foca', 'Univerzitetska bolnica', 10);
INSERT INTO medical_worker(end_hr, phone, rating, start_hr, type_of_doctor, clinic_id, user_id) VALUES (15, 12345, 9, 8, 'UROLOG', 1, 2);
