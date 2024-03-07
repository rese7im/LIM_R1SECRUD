<?php
// Include database connection
require_once('Connection/dbconn.php');

// Check if form submitted
if ($_SERVER["REQUEST_METHOD"] == "POST") {
    $id = $_POST['id'];

    // SQL delete query
    $sql = "DELETE FROM getdatatable WHERE id = $id";

    // Execute the SQL statement
    if ($conn->query($sql) === TRUE) {
        echo "Data Deleted Successfully";
    } else {
        echo "Error Deleting Data: " . $conn->error;
    }
}
?>
