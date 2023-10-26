-- measurement
INSERT INTO measurement(type, unit) VALUES
('heart-rate', 'beats/minute'),
('skin-temperature', 'degrees Celsius'),
('respiratory-rate', 'breaths/minute');

-- observation
INSERT INTO observation(measurement_id, date, patient, value) VALUES
(1, '2023-09-06 11:02:44+00', 101, 65),
(2, '2023-09-07 11:23:24+00', 101, 37.2),
(3, '2023-09-06 11:02:44+00', 101, 15),
(1, '2023-09-04 08:54:33+00', 102, 76),
(3, '2023-09-04 08:54:33+00', 102, 18),
(2, '2023-09-05 15:12:23+00', 103, 37.8);