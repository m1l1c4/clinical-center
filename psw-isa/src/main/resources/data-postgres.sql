INSERT INTO korisnik(enabled, email, ime, password, prezime, type, active, first_login) VALUES (true,'admin@gmail.com', 'Dragana', '$2a$10$lQF1FjXTI68iTJDo86eusO59W04bhd9LAKF8oFy.i2MjBPvVnLHV6','Mihajlovic', 'ADMINISTRATOR', true, false);

INSERT INTO korisnik(enabled, email, ime, password, prezime, type, active, first_login) VALUES (true,'doktor@gmail.com', 'Pero', '$2a$10$lQF1FjXTI68iTJDo86eusO59W04bhd9LAKF8oFy.i2MjBPvVnLHV6','Peric', 'DOKTOR', true, false);
INSERT INTO korisnik(enabled, email, ime, password, prezime, type, active, first_login) VALUES (true,'sestra2@gmail.com', 'Ivana', '$2a$10$lQF1FjXTI68iTJDo86eusO59W04bhd9LAKF8oFy.i2MjBPvVnLHV6','Ivanovic', 'MEDICINAR', true, false);
INSERT INTO korisnik(enabled, email, ime, password, prezime, type, active, first_login) VALUES (true,'doktor2@gmail.com', 'Aleksandar', '$2a$10$lQF1FjXTI68iTJDo86eusO59W04bhd9LAKF8oFy.i2MjBPvVnLHV6','Drakul', 'DOKTOR', true, false);
INSERT INTO korisnik(enabled, email, ime, password, prezime, type, active, first_login) VALUES (true,'pacijent@gmail.com', 'Marko', '$2a$10$lQF1FjXTI68iTJDo86eusO59W04bhd9LAKF8oFy.i2MjBPvVnLHV6','Markovic', 'PACIJENT', true, true);
INSERT INTO korisnik(enabled, email, ime, password, prezime, type, active, first_login) VALUES (true,'ccadmin@gmail.com', 'Ognjen', '$2a$10$lQF1FjXTI68iTJDo86eusO59W04bhd9LAKF8oFy.i2MjBPvVnLHV6','Jovanovic', 'CCADMIN', true, false);
INSERT INTO korisnik(enabled, email, ime, password, prezime, type, active, first_login) VALUES (true,'doktor3@gmail.com', 'Dusan', '$2a$10$lQF1FjXTI68iTJDo86eusO59W04bhd9LAKF8oFy.i2MjBPvVnLHV6','Nemanjic', 'DOKTOR', true, false);
INSERT INTO korisnik(enabled, email, ime, password, prezime, type, active, first_login) VALUES (true,'pacijent2@gmail.com', 'Nikola', '$2a$10$lQF1FjXTI68iTJDo86eusO59W04bhd9LAKF8oFy.i2MjBPvVnLHV6','Nikolic', 'PACIJENT', true, true);
INSERT INTO korisnik(enabled, email, ime, password, prezime, type, active, first_login) VALUES (true,'pacijent3@gmail.com', 'Sinica', '$2a$10$lQF1FjXTI68iTJDo86eusO59W04bhd9LAKF8oFy.i2MjBPvVnLHV6','Sinisic', 'PACIJENT', true, true);

INSERT INTO clinic( address, city, clinic_name, rating, description) VALUES ('Stepe Stepanovica', 'Foca', 'Univerzitetska bolnica', 10, 'Klinika je organizovana kao Specijalna bolnica za oftalmolgiju i Centar za refraktivnu hirurgiju.');
INSERT INTO clinic( address, city, clinic_name, rating, description) VALUES ('Nikole Tesle', 'Novi Sad', 'Kosevo', 5, 'Zahvaljujući iskustvu, stručnom kadru, timskom radu, ali prije svega potpunoj predanosti i posvećenosti, obezbjeđujemo visok nivo liječenja i zdravstvene njege svim pacijentima.');

INSERT INTO clinic_administrator(clinic_id, user_id) VALUES (1, 1);
INSERT INTO medical_worker(end_hr, phone, rating, start_hr, type_of_doctor, clinic_id, user_id) VALUES (15, 062547896, 10, 7, 'KARDIOLOSKI', 1, 2);
INSERT INTO medical_worker(end_hr, phone, rating, start_hr, type_of_doctor, clinic_id, user_id) VALUES (20, 064785213, 9, 12, 'UROLOSKI', 1, 3);
INSERT INTO medical_worker(end_hr, phone, rating, start_hr, type_of_doctor, clinic_id, user_id) VALUES (23, 065479826, 9, 15, 'DERMATOLOSKI', 1, 4);
INSERT INTO medical_worker(end_hr, phone, rating, start_hr, type_of_doctor, clinic_id, user_id) VALUES (17, 062457893, 8, 9, 'KARDIOLOSKI', 1, 7);

INSERT INTO patient(address, city, jbo, phone_number, state, user_id) VALUES ('Karadjordjeva', 'Beograd', 123456789101, 065852456, 'Srbija', 5);
INSERT INTO patient(address, city, jbo, phone_number, state, user_id) VALUES ('Mese Selimovica', 'Foca', 3451, 065852456, 'Srbija', 8);
INSERT INTO patient(address, city, jbo, phone_number, state, user_id) VALUES ('Ruzveltova', 'Bijeljina', 3451, 065852456, 'RS', 9);

INSERT INTO clinical_center_administrator(user_id) VALUES (6);

INSERT INTO check_up_type(type_name, type_price) VALUES ('KARDIOLOSKI', 50);
INSERT INTO check_up_type(type_name, type_price) VALUES ('DERMATOLOSKI', 30);

INSERT INTO clinic_and_type(check_up_type_id, clinic_id) VALUES (1, 1);
INSERT INTO clinic_and_type(check_up_type_id, clinic_id) VALUES (2, 2);
INSERT INTO clinic_and_type(check_up_type_id, clinic_id) VALUES (2, 1);

INSERT INTO codebook(name, code, code_type) values ('Aspirin', '1C8F', 'LIJEK');
INSERT INTO codebook(name, code, code_type) values ('Paracetamol', 'G2PS', 'LIJEK');
INSERT INTO codebook(name, code, code_type) values ('Brufen', 'PA3', 'LIJEK');
INSERT INTO codebook(name, code, code_type) values ('Sortis', 'K34F', 'LIJEK');
INSERT INTO codebook(name, code, code_type) values ('Sarkoidoza', 'OE46', 'DIJAGNOZA');
INSERT INTO codebook(name, code, code_type) values ('Diskus hernija', 'DC29', 'DIJAGNOZA');
INSERT INTO codebook(name, code, code_type) values ('Hipertenzija', 'HP94', 'DIJAGNOZA');

INSERT INTO room(first_free_date, is_free, room_name, room_number, type_room, clinic_id)	VALUES ('2019-12-15', true, 'Interno', 102, 'PREGLED', 1);
INSERT INTO room(first_free_date, is_free, room_name, room_number, type_room, clinic_id)	VALUES ('2019-12-15', true, 'Interno', 103, 'PREGLED', 1);
INSERT INTO room(first_free_date, is_free, room_name, room_number, type_room, clinic_id)	VALUES ('2019-12-15', true, 'Sala', 327, 'OPERACIJA', 1);
INSERT INTO room(first_free_date, is_free, room_name, room_number, type_room, clinic_id)	VALUES ('2019-12-15', true, 'Dijagnostika', 215, 'PREGLED', 1);

INSERT INTO medical_record(blood_type, diopter, height, weight, patient_id) VALUES ('0-', 0, 175, 70, 1);


INSERT INTO checkup(date_of_checkup, discount, duration, price, scheduled, time_of_checkup, type, check_up_type_id, clinic_id, patient_id, room_id, pending) VALUES ('2019-12-20', 20, 1, 100, true, '12', 'appointment', 1, 1,  1, 1, false);

INSERT INTO checkup(date_of_checkup, discount, duration, price, scheduled, time_of_checkup, type, check_up_type_id, clinic_id, patient_id, room_id, pending) VALUES ('2019-12-20', 20, 1, 100, true, '12', 'appointment', 1, 1,  1, 1, false);
INSERT INTO checkup(date_of_checkup, discount, duration, price, scheduled, time_of_checkup, type, check_up_type_id, clinic_id, patient_id, room_id, pending) VALUES ('2019-12-20', 0, 1, 100, true, '12', 'appointment', 1, 1,  1, 1, false);
INSERT INTO checkup(date_of_checkup, discount, duration, price, scheduled, time_of_checkup, type, check_up_type_id, clinic_id, patient_id, room_id, pending) VALUES ('2019-12-20', 0, 1, 50, true, '12', 'appointment', 1, 1,  1, 1, false);
INSERT INTO checkup(date_of_checkup, discount, duration, price, scheduled, time_of_checkup, type, check_up_type_id, clinic_id, patient_id, room_id, pending) VALUES ('2019-12-20', 0, 1, 25, true, '12', 'appointment', 1, 1,  1, 1, false);
INSERT INTO checkup(date_of_checkup, discount, duration, price, scheduled, time_of_checkup, type, check_up_type_id, clinic_id, patient_id, room_id, pending) VALUES ('2019-09-20', 0, 1, 76, true, '12', 'appointment', 1, 1,  1, 1, false);
INSERT INTO checkup(date_of_checkup, discount, duration, price, scheduled, time_of_checkup, type, check_up_type_id, clinic_id, patient_id, room_id, pending) VALUES ('2019-09-20', 0, 1, 40, true, '12', 'appointment', 1, 1,  1, 1, false);
INSERT INTO checkup(date_of_checkup, discount, duration, price, scheduled, time_of_checkup, type, check_up_type_id, clinic_id, patient_id, room_id, pending) VALUES ('2019-06-20', 0, 1, 100, true, '12', 'appointment', 1, 1,  1, 1, false);
INSERT INTO checkup(date_of_checkup, discount, duration, price, scheduled, time_of_checkup, type, check_up_type_id, clinic_id, patient_id, room_id, pending) VALUES ('2019-06-20', 0, 1, 70, true, '12', 'appointment', 1, 1,  1, 1, false);
INSERT INTO checkup(date_of_checkup, discount, duration, price, scheduled, time_of_checkup, type, check_up_type_id, clinic_id, patient_id, room_id, pending) VALUES ('2019-06-20', 0, 1, 45, true, '12', 'appointment', 1, 1,  1, 1, false);
INSERT INTO checkup(date_of_checkup, discount, duration, price, scheduled, time_of_checkup, type, check_up_type_id, clinic_id, patient_id, room_id, pending) VALUES ('2019-02-20', 0, 1, 22, true, '12', 'appointment', 1, 1,  1, 1, false);
INSERT INTO checkup(date_of_checkup, discount, duration, price, scheduled, time_of_checkup, type, check_up_type_id, clinic_id, patient_id, room_id, pending) VALUES ('2019-02-20', 0, 1, 56, true, '12', 'appointment', 1, 1,  1, 1, false);
INSERT INTO checkup(date_of_checkup, discount, duration, price, scheduled, time_of_checkup, type, check_up_type_id, clinic_id, patient_id, room_id, pending) VALUES ('2019-01-20', 0, 1, 100, true, '12', 'appointment', 1, 1,  1, 1, false);

INSERT INTO checkup(date_of_checkup, discount, duration, price, scheduled, time_of_checkup, type, check_up_type_id, clinic_id, patient_id, room_id, pending) VALUES ('2019-12-20', 20, 1, 100, true, '12', 'appointment', 1, 1,  1, 1, false);
INSERT INTO checkup(date_of_checkup, discount, duration, price, scheduled, time_of_checkup, type, check_up_type_id, clinic_id, patient_id, room_id, pending) VALUES ('2019-12-20', 20, 1, 100, true, '12', 'appointment', 1, 1,  1, 1, false);
INSERT INTO checkup(date_of_checkup, discount, duration, price, scheduled, time_of_checkup, type, check_up_type_id, clinic_id, patient_id, room_id, pending) VALUES ('2019-12-20', 20, 1, 100, true, '12', 'appointment', 1, 1,  1, 1, false);
INSERT INTO checkup(date_of_checkup, discount, duration, price, scheduled, time_of_checkup, type, check_up_type_id, clinic_id, patient_id, room_id, pending) VALUES ('2019-12-19', 20, 1, 100, true, '12', 'appointment', 1, 1,  1, 1, false);
INSERT INTO checkup(date_of_checkup, discount, duration, price, scheduled, time_of_checkup, type, check_up_type_id, clinic_id, patient_id, room_id, pending) VALUES ('2019-12-18', 20, 1, 100, true, '12', 'appointment', 1, 1,  1, 1, false);
INSERT INTO checkup(date_of_checkup, discount, duration, price, scheduled, time_of_checkup, type, check_up_type_id, clinic_id, patient_id, room_id, pending) VALUES ('2019-12-18', 20, 1, 100, true, '12', 'appointment', 1, 1, 1, 1, false);
INSERT INTO checkup(date_of_checkup, discount, duration, price, scheduled, time_of_checkup, type, check_up_type_id, clinic_id, patient_id, room_id, pending) VALUES ('2019-12-18', 20, 1, 100, true, '12', 'appointment', 1, 1,  1, 1, false);
INSERT INTO checkup(date_of_checkup, discount, duration, price, scheduled, time_of_checkup, type, check_up_type_id, clinic_id, patient_id, room_id, pending) VALUES ('2019-12-17', 20, 1, 100, true, '12', 'appointment', 1, 1,  1, 1, false);


INSERT INTO absence(end_vacation, start_vacation, type_of_absence, clinic_of_absence_id, mw_id, accepted) VALUES ('2019-03-12', '2019-03-01', 'odmor', 1, 1, 'SENT');
INSERT INTO absence(end_vacation, start_vacation, type_of_absence, clinic_of_absence_id, mw_id, accepted) VALUES ('2019-03-10', '2019-03-01', 'odsustvo', 1, 2, 'SENT');




