<?php
include 'Connection/dbconn.php';

// Check if 'id', 'name', and 'email' fields are set in the POST data
if(isset($_POST['id']) && isset($_POST['name']) && isset($_POST['email'])) {
    // Sanitize input data to prevent SQL injection
    $id = mysqli_real_escape_string($conn, $_POST['id']);
    $name = mysqli_real_escape_string($conn, $_POST['name']);
    $email = mysqli_real_escape_string($conn, $_POST['email']);

    // SQL query to update data in the 'getdatatable' table
    $Sql_Query = "UPDATE `getdatatable` SET `name`='$name', `email`='$email' WHERE `id`='$id'";

    // Execute the query and check if update was successful
    if(mysqli_query($conn, $Sql_Query)) {
        echo 'Data Updated Successfully';
    } else {
        echo 'Error: ' . mysqli_error($conn);
    }
} else {
    echo 'Error: Missing data (id, name, or email)';
}

// Close the database connection
mysqli_close($conn);
?>
