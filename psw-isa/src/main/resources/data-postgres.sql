INSERT INTO korisnik(email, ime, password, prezime, type, active) VALUES ('email', 'Dragana', 'sifra','Mihajlovic', 'ADMINISTRATOR', true);
INSERT INTO clinic_administrator(clinic, user_id) VALUES ('Bolnica', 1);
INSERT INTO korisnik(email, ime, password, prezime, type, active) VALUES ('email2', 'Milica', 'sifra1','Skipina', 'DOKTOR', true);
INSERT INTO clinic( address, city, clinic_name, rating) VALUES ('Stepe Stepanovica', 'Foca', 'Univerzitetska bolnica', 10);
INSERT INTO medical_worker(end_hr, phone, rating, start_hr, type_of_doctor, clinic_id, user_id) VALUES (17, 123456, 9, 8, 'UROLOG', 1, 2);
