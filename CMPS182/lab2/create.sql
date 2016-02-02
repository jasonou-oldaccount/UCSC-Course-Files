CREATE TYPE specialty AS ENUM('D', 'OBG', 'OPH', 'PD', 'OS');
CREATE TABLE h_patients(patient_id int, name varchar(250), address varchar(250), email varchar(250), doctor_id int, admitted boolean);
CREATE TABLE h_medicines(medicine_id int, name varchar(250), price int);
CREATE TABLE h_doctors(doctor_id int, name varchar(250), location varchar(250), specialty specialty);
CREATE TABLE h_prescriptions(prescription_id int, doctor_id int, medicine_id int, patient_id int, prescription_date date);
CREATE TABLE new_medicines(name varchar(250), price int);
CREATE TABLE mg_medicines(medicine_id int, name varchar(250), price int);