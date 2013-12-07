<?php
header('Content-type=application/json; charset=utf-8');
$response = array();

if (isset($_GET['login_name']) && isset($_GET['day_no']) && isset($_GET['week_no']) && isset($_GET['year_no']))
{
    $loginname = $_GET['login_name'];
    $dayno = $_GET['day_no'];
    $weekno = $_GET['week_no'];
    $yearno = $_GET['year_no'];

    require_once __DIR__ . '/db_connect.php';

    $db = new DB_CONNECT();

    $selectStr = "SELECT id_proj_time,".
            "description,".
        "login_name,".
        "employee_id,".
        "company_name,".
        "project_id,".
        "monday_date,".
        "from_minutes,".
        "to_minutes,".
        "work_date,".
        "notes,".
        "week_no,".
        "day_no,".
        "year_no,".
        "month_no,".
        "minutes,".
        "entered,".
        "projref,".
        "chargeable,".
        "auth_date".
        " FROM project_management.proj_time t, project_management.project p".
        " WHERE login_name = '$loginname'".
        " AND day_no = $dayno".
        " AND week_no = $weekno".
        " AND year_no = $yearno".
        " AND project_id = id_project".
        " ORDER BY from_minutes ASC;";

    $query = $selectStr;

    $result = odbc_do($db->con, $query);

    if (!empty($result) && (odbc_num_rows($result) > 0))
    {
            $response["proj_time"] = array();

            while($row=odbc_fetch_array($result))
            {
                $proj_time = array();
                $proj_time['id_proj_time'] = $row['id_proj_time'];
                $proj_time['description'] = $row['description'];
                $proj_time['login_name'] = $row['login_name'];
                $proj_time['employee_id'] = $row['employee_id'];
                $proj_time['company_name'] = $row['company_name'];
                $proj_time['project_id'] = $row['project_id'];
                $proj_time['monday_date'] = $row['monday_date'];
                $proj_time['from_minutes'] = $row['from_minutes'];
                $proj_time['to_minutes'] = $row['to_minutes'];
                $proj_time['work_date'] = $row['work_date'];
                $proj_time['notes'] = $row['notes'];
                $proj_time['week_no'] = $row['week_no'];
                $proj_time['day_no'] = $row['day_no'];
                $proj_time['year_no'] = $row['year_no'];
                $proj_time['month_no'] = $row['month_no'];
                $proj_time['minutes'] = $row['minutes'];
                $proj_time['entered'] = $row['entered'];
                $proj_time['projref'] = $row['projref'];
                $proj_time['chargeable'] = $row['chargeable'];
                $proj_time['auth_date'] = $row['auth_date'];
                array_push($response["proj_time"], $proj_time);
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