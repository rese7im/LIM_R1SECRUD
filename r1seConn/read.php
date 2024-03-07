<?php
// Include database connection
require_once('database.php');

// Read users
$users = $user->read();
echo "<pre>";
print_r($users);
echo "</pre>";
?>
