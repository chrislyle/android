<?php

/**
 * A class file to connect to database
 */
class DB_CONNECT {

	
	
    // constructor
    function __construct() {
        // connecting to database
        $this->connect();
    }

    // destructor
    function __destruct() {
        // closing db connection
        $this->close();
    }

	
    /**
     * Function to connect with database
     */
    function connect() {
        // import database connection variables
        $db_host        = "lightstar";
		$db_user        = "chris";
		$db_pass        = "zarg2314";
		$db_name        = "pm_dev";
		$dsn =         "DRIVER={MySQL ODBC 5.1 Driver};" . 
		 "CommLinks=tcpip(Host=$db_host);" . 
		 "DatabaseName=$db_name;" . 
		 "uid=$db_user; pwd=$db_pass";

        $this->con = odbc_connect($dsn, $db_user, $db_pass) or die(odbc_error());
		$query = "SELECT * FROM pm_dev.employee;";
		$result = odbc_do($this->con, $query);
		WHILE($row=odbc_fetch_array($result))
		{
			printf("<br/>%s", $row['lastname']);
		}
		odbc_close($this->con); 
        // returning connection cursor
        return $this->con;
    }

    /**
     * Function to close db connection
     */
    function close() {
        // closing db connection
		print "Closed";
    }

}

?>