INSERT INTO korisnik(enabled, email, ime, password, prezime, type, active, first_login) VALUES (true,'email@gmail.com', 'Dragana', '$2a$10$lQF1FjXTI68iTJDo86eusO59W04bhd9LAKF8oFy.i2MjBPvVnLHV6','Mihajlovic', 'ADMINISTRATOR', true, false);
INSERT INTO korisnik(enabled, email, ime, password, prezime, type, active, first_login) VALUES (true,'draganamihajlovic55@yahoo.com', 'Dragana', '$2a$10$lQF1FjXTI68iTJDo86eusO59W04bhd9LAKF8oFy.i2MjBPvVnLHV6','Mihajlovic', 'DOKTOR', true, false);
INSERT INTO clinic( address, city, clinic_name, rating, description) VALUES ('Stepe Stepanovica', 'Foca', 'Univerzitetska bolnica', 10, 'Opis klinike');
INSERT INTO clinic_administrator(clinic_id, user_id) VALUES (1, 1);
INSERT INTO medical_worker(end_hr, phone, rating, start_hr, type_of_doctor, clinic_id, user_id) VALUES (15, 12345, 9, 8, 'KARDIOLOSKI', 1, 2);

INSERT INTO clinic( address, city, clinic_name, rating, description) VALUES ('Nikole Tesle', 'Novi Sad', 'Institut', 5, 'Opis klinike');
INSERT INTO korisnik(enabled, email, ime, password, prezime, type, active, first_login) VALUES (true,'doktor@gmail.com', 'Milica', '$2a$10$lQF1FjXTI68iTJDo86eusO59W04bhd9LAKF8oFy.i2MjBPvVnLHV6','Skipina', 'DOKTOR', true, false);
INSERT INTO check_up_type(type_name) VALUES ('KARDIOLOSKI');
INSERT INTO check_up_type(type_name) VALUES ('DERMATOLOSKI');

INSERT INTO clinic_and_type(check_up_type_id, clinic_id) VALUES (1, 1);
INSERT INTO clinic_and_type(check_up_type_id, clinic_id) VALUES (2, 2);
INSERT INTO korisnik(enabled, email, ime, password, prezime, type, active, first_login) VALUES (true,'doktor2@gmail.com', 'Doktor', '$2a$10$lQF1FjXTI68iTJDo86eusO59W04bhd9LAKF8oFy.i2MjBPvVnLHV6','DoktorPrezime', 'DOKTOR', true, false);
INSERT INTO medical_worker(end_hr, phone, rating, start_hr, type_of_doctor, clinic_id, user_id) VALUES (15, 12345, 9, 8, 'UROLOG', 1, 3);
INSERT INTO medical_worker(end_hr, phone, rating, start_hr, type_of_doctor, clinic_id, user_id) VALUES (15, 12345, 9, 8, 'DERMATOLOSKI', 2, 4);
INSERT INTO codebook(name, code, code_type) values ('prvi', '124', 'LIJEK');
INSERT INTO codebook(name, code, code_type) values ('drugi', '345', 'LIJEK');
INSERT INTO codebook(name, code, code_type) values ('treci', '567', 'DIJAGNOZA');

INSERT INTO room(is_free, room_name, room_number, type, clinic_id)	VALUES (true, 'Soba1', 3, 'nekitip', 1);
INSERT INTO room(is_free, room_name, room_number, type, clinic_id)	VALUES (true, 'Soba2', 5, 'nekitip2', 1);



INSERT INTO korisnik(enabled, email, ime, password, prezime, type, active, first_login) VALUES (true,'pacijent@gmail.com', 'Marko', '$2a$10$lQF1FjXTI68iTJDo86eusO59W04bhd9LAKF8oFy.i2MjBPvVnLHV6','Markovic', 'PACIJENT', true, true);
INSERT INTO patient(address, city, jbo, phone_number, state, clinic_id, user_id) VALUES ('Karadjordjeva', 'Beograd', 468, 963, 'Srbija', 1, 5);
INSERT INTO medical_record(blood_type, diopter, height, weight, patient_id) VALUES ('0-', 0, 175, 70, 1);
INSERT INTO checkup(date_of_checkup, discount, duration, price, scheduled, time_of_checkup, type, check_up_type_id, clinic_id, medical_worker_id, patient_id, room_id) VALUES ('2019-12-20', 20, 1, 100, true, '12', 'appointment', 1, 1, 2, 1, 1);