<?php
header('Content-type=application/json; charset=utf-8');
$response = array();

if (isset($_GET['lastname']))
{
    $last_name = $_GET['lastname'];

    require_once __DIR__.'/db_connect.php';

    $db = new DB_CONNECT();

    $selectStr = "SELECT id_employee,".
    "lastname,".
    "firstname,".
    "section,".
    "emp_type,".
    "login,".
    "sage_dept_code,".
    "manager,".
    "company,".
    "weekly_contract_hours,".
    "mandatory_holidays_days,".
    "holiday_allowance_days,".
    "status FROM project_management.employee".
    " WHERE lastname = '$last_name';";

    $query = $selectStr;

    $result = odbc_do($db->con, $query);

    if (!empty($result) && (odbc_num_rows($result) > 0))
    {
        $response["employee"] = array();

        while($row=odbc_fetch_array($result))
        {
            $employee = array();
            $employee['id_employee'] = $row['id_employee'];
            $employee['lastname'] = $row['lastname'];
            $employee['firstname'] = $row['firstname'];
            $employee['section'] = $row['section'];
            $employee['emp_type'] = $row['emp_type'];
            $employee['login'] = $row['login'];
            $employee['sage_dept_code'] = $row['sage_dept_code'];
            $employee['manager'] = $row['manager'];
            $employee['company'] = $row['company'];
            $employee['weekly_contract_hours'] = $row['weekly_contract_hours'];
            $employee['mandatory_holidays_days'] = $row['mandatory_holidays_days'];
            $employee['holiday_allowance_days'] = $row['holiday_allowance_days'];
            $employee['status'] = $row['status'];
            array_push($response["employee"], $employee);
        }
        $response["success"] = "1";
    }
    else
    {
        $response["success"] = "0";
        $response["message"] = "No time sheet entries found for that period";
    }
    printf("%s", json_encode($response));
}
?>