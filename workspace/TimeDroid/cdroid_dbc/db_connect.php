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
        $db_host        = "10.0.3.0";
	$db_user        = "chrisl";
	$db_pass        = "chrisl";
	$db_name        = "project_management";
	$dsn =         "DRIVER={MySQL ODBC 5.2 ANSI Driver};" .
	 "CommLinks=tcpip(Host=$db_host);" .
	 "DatabaseName=$db_name;" .
	 "uid=$db_user; pwd=$db_pass";

        $this->con = odbc_connect($dsn, $db_user, $db_pass) or die(odbc_error());

        return $this->con;
    }

    /**
     * Function to close db connection
     */
    function close() {
        // closing db connection
		odbc_close($this->con);
    }

}

?>