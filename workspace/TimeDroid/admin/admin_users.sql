#DROP USER 'kanteen';
CREATE USER 'kanteen'@'localhost' IDENTIFIED BY 'kanteen'; 
GRANT SELECT,INSERT,UPDATE,DELETE ON *.* TO 'kanteen'@'localhost' WITH GRANT OPTION;

#DROP USER 'chrisl';
CREATE USER 'chris'@'localhost' IDENTIFIED BY 'zarg2314'; 
GRANT SELECT,INSERT,UPDATE,DELETE ON *.* TO 'chris'@'localhost' WITH GRANT OPTION;