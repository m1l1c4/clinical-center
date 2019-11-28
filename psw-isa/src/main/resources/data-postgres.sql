INSERT INTO korisnik(enabled, email, ime, password, prezime, type, active, first_login) VALUES (true,'email@gmail.com', 'Dragana', '$2a$10$lQF1FjXTI68iTJDo86eusO59W04bhd9LAKF8oFy.i2MjBPvVnLHV6','Mihajlovic', 'ADMINISTRATOR', true, false);
INSERT INTO korisnik(enabled, email, ime, password, prezime, type, active, first_login) VALUES (true,'draganamihajlovic55@yahoo.com', 'Dragana', '$2a$10$lQF1FjXTI68iTJDo86eusO59W04bhd9LAKF8oFy.i2MjBPvVnLHV6','Mihajlovic', 'DOKTOR', true, false);
INSERT INTO clinic( address, city, clinic_name, rating, description) VALUES ('Stepe Stepanovica', 'Foca', 'Univerzitetska bolnica', 10, 'Opis klinike');
INSERT INTO clinic_administrator(clinic_id, user_id) VALUES (1, 1);
INSERT INTO medical_worker(end_hr, phone, rating, start_hr, type_of_doctor, clinic_id, user_id) VALUES (15, 12345, 9, 8, 'UROLOG', 1, 2);
 