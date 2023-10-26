-- measurement
INSERT INTO test.measurement(type, unit) VALUES
('heart-rate', 'beats/minute'),
('skin-temperature', 'degrees Celsius'),
('respiratory-rate', 'breaths/minute');

-- observation
INSERT INTO test.observation(measurement_id, date, patient, value) VALUES
(1, '2022-09-06 11:02:44+00', 101, 65),
(2, '2022-09-07 11:23:24+00', 101, 37.2),
(3, '2022-09-06 11:02:44+00', 101, 15);
