<?php

require_once("dbconfig.php");

class DAL {
  private static $dbh;

  /*
   * Connection function
   */
  public static function connect() {
    if (!self::$dbh) {
      try {
		$host = HOST;
	   	$db_name = DB_NAME;
		$username = USERNAME;
		$password = PASSWORD;
		
        // Establish the connection
        self::$dbh = new PDO("mysql:host=".$host.";dbname=".$db_name, $username, $password);
        self::$dbh->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
      }
      catch(PDOException $e) {
        echo ("Error: " . $e->getMessage());
        return false;
      }
    }
    return (self::$dbh != NULL);
  }

  /*
   * isConnected function
   */
  public static function isConnected() {
    if (self::$dbh == NULL)
      return false;
    return true;
  }

  /*
   * disconnect function
   */
  public static function disconnect() {
    self::$dbh = NULL;
  }

  /*
   * Inserts user 
   */
  public static function insert_user($user_id, $name, $email="") {
    try {
	  $data = array($user_id, $name, $email);
      $sql = "INSERT INTO Users (user_id, name, email) VALUES (?, ?, ?) ON DUPLICATE KEY UPDATE user_id=?, name=?, email=?";

	  $query = self::$dbh->prepare($sql);
	  $isSuccessful = $query->execute(array_merge($data, $data));
	  return $isSuccessful;
    }
    catch(PDOException $e) {
      echo ("Error: " . $e->getMessage());
    }
    return false;
  }

  /*
   * Retrieves all of the users in the Users table.
   */
  public static function get_users($user_id = "") {
    try {
      $sql = "SELECT * FROM Users";
	  if (!empty($user_id))
	  	$sql .= " WHERE user_id=:user_id";
	
      $query = self::$dbh->prepare($sql);
	  if (!empty($user_id))
	  	$query->bindParam(":user_id", $user_id, PDO::PARAM_STR, 255);
	  
      $query->execute();
	  return $query->fetchAll(PDO::FETCH_ASSOC);
    }
    catch (PDOException $e) {
      echo ("Error: " . $e->getMessage());
    }
    return NULL;
  }
  
  public static function is_user_exist($user_id) {
	try {
		$query = self::$dbh->prepare("SELECT count(*) FROM Users WHERE user_id=:user_id");
		$query->bindParam(":user_id", $user_id, PDO::PARAM_STR, 255);
		$query->execute();
		if ($query->fetchColumn() > 0)
			return true;
	}
	catch(PDOException $e) {
		echo ("Error: " . $e->getMessage());
	}
	return false; 
  }
  
  /*
   * Deletes a user when specifying the user_id.
   */
  public static function delete_users($user_id) {
    try {
      $sql = "DELETE FROM Users WHERE user_id=:user_id";
      $query = self::$dbh->prepare($sql);
	  $query->bindParam(":user_id", $user_id, PDO::PARAM_STR, 255);
      $isSuccessful = $query->execute();
      return $isSuccessful;
    }
    catch(PDOEXception $e) {
      echo ("Error: " . $e->getMessage());
    }
    return false;
  }
  
  public static function get_privilege($user_id) {
	try {
      $sql = "SELECT privilege FROM Users WHERE user_id=:user_id";
      $query = self::$dbh->prepare($sql);
	  $query->bindParam(":user_id", $user_id, PDO::PARAM_STR, 255);
      $query->execute();
	  return $query->fetchAll(PDO::FETCH_ASSOC);
    }
    catch (PDOException $e) {
      echo ("Error: " . $e->getMessage());
    }
    return -1;
  }

  /*
   * Inserts a reservation using the user_id, court_id, and the reserve_time.
   * @return reservation id;
   */
  public static function insert_reservation($user_id, $court_id, $reserve_time) {
    try {
	  if ($court_id < 1 || $court_id > 5)
	  	return;	
	
      // Building the SQL statement
      $sql = "INSERT INTO Reservations(user_id, court_id, reserve_time) VALUES (:user_id, :court_id, :reserve_time)";
	  $query = self::$dbh->prepare($sql);
	  $query->bindParam(":user_id", $user_id, PDO::PARAM_STR, 255);
	  $query->bindParam(":court_id", $court_id, PDO::PARAM_INT);
	  $query->bindParam(":reserve_time", $reserve_time);
	  $isSuccessful = $query->execute();
	  $reservation_id = self::$dbh->lastInsertId(); 
	  return $reservation_id;
    }
    catch(PDOException $e) {
      echo ("Error: " . $e->getMessage());
    }
    return -1;
  }

  public static function get_reservations($court_id, $reserve_time, $interval=0) {
    try {	
		$end_time = new DateTime($reserve_time);
		$end_time->modify("+$interval hours");
		$start_time = $reserve_time;
		$end_time = $end_time->format("Y-m-d H:i:s");
		
		$sql = "";
		if ($court_id >= 1 && $court_id <=5)
		{
			$sql = "SELECT * FROM Reservations WHERE court_id=:court_id "; 
			if ($interval > 0)
				$sql .= " AND reserve_time BETWEEN :start_time AND :end_time";
			else
				$sql .= " AND reserve_time >= :start_time";
			$sql .= " ORDER BY reserve_time ASC";
		}
		else
		{
			$sql = "SELECT * FROM Reservations WHERE "; 
			if ($interval > 0)
				$sql .= " reserve_time BETWEEN :start_time AND :end_time";
			else
				$sql .= " reserve_time >= :start_time";
			$sql .= " ORDER BY reserve_time ASC, court_id ASC";
		}
		
		$query = self::$dbh->prepare($sql);
		if ($court_id >= 1 && $court_id <=5)
			$query->bindParam(":court_id", $court_id, PDO::PARAM_INT);
		$query->bindParam(":start_time", $start_time);
		if ($interval > 0)
			$query->bindParam(":end_time", $end_time);
		
		$query->execute();
		return $query->fetchAll(PDO::FETCH_ASSOC);
	}
	catch(PDOException $e) {
		echo ("Error: " . $e->getMessage());
	}
	return NULL;
  }
  
  public static function get_reservations_by_user_id($user_id) {
    try {
		$num_of_court = 5;
		$sql = "SELECT * FROM Reservations WHERE user_id=:user_id ORDER BY reserve_time ASC, court_id ASC";
		$query = self::$dbh->prepare($sql);
		$query->bindParam(":user_id", $user_id, PDO::PARAM_STR, 255);
		$query->execute();
		return $query->fetchAll(PDO::FETCH_ASSOC);
	}
	catch(PDOException $e) {
		echo ("Error: " . $e->getMessage());
	}
	return NULL;
  }

  public static function delete_reservation_by_id($reservation_id) {
    try {
      $sql = "DELETE FROM Reservations WHERE reserve_id=:reserve_id";
      $query = self::$dbh->prepare($sql);
	  $query->bindParam(":reserve_id", $reservation_id, PDO::PARAM_INT);
      $isSuccessful = $query->execute();
	  return $isSuccessful;
    }
    catch(PDOException $e) {
      echo ("Error: " . $e->getMessage());
    }
    return false; 
  }
  
  public static function delete_reservation_by_time($court_number, $reserve_time, $interval=0) {
    try {
	    $end_time = new DateTime($reserve_time);
		$end_time->modify("+$interval hours");
		$start_time = $reserve_time;
		$end_time = $end_time->format("Y-m-d H:i:s");
		
		// Select the reservation that will be deleted
		$sql = "SELECT u.user_id, u.name, u.email FROM Users u, Reservations r WHERE "; 
		if ($interval > 0)
			$sql .= " r.reserve_time BETWEEN :start_time AND :end_time";
		else
			$sql .= " r.reserve_time >= :start_time";
		$sql .= " AND r.user_id = u.user_id";
		
		if ($court_number > 0)
			$sql .= " AND r.court_id=:court_number";
			
		$query = self::$dbh->prepare($sql);
	  	$query->bindParam(":start_time", $start_time);
		if ($court_number > 0)
			$query->bindParam(":court_number", $court_number);
	  	if ($interval > 0)
			$query->bindParam(":end_time", $end_time);
		$query->execute();
		$result = $query->fetchAll(PDO::FETCH_ASSOC);
		
		// Delete reservation
		$sql = "DELETE FROM Reservations WHERE "; 
		if ($interval > 0)
			$sql .= " reserve_time BETWEEN :start_time AND :end_time";
		else
			$sql .= " reserve_time >= :start_time";	
			
		if ($court_number > 0)
			$sql .= " AND r.court_id=:court_number";
		
     	$query = self::$dbh->prepare($sql);
	  	$query->bindParam(":start_time", $start_time);
		if ($court_number > 0)
			$query->bindParam(":court_number", $court_number);
	  	if ($interval > 0)
			$query->bindParam(":end_time", $end_time);
      	$isSuccessful = $query->execute();
	  	if ($isSuccessful)
	  		return $result;
    }
    catch(PDOException $e) {
      echo ("Error: " . $e->getMessage());
    }
    return NULL; 
  }
  
  public static function delete_reservation($user_id, $court_id, $reserve_time) {
    try {
      $sql = "DELETE FROM Reservations WHERE user_id=:user_id AND court_id=:court_id AND reserve_time=:reserve_time";
      $query = self::$dbh->prepare($sql);
	  $query->bindParam(":user_id", $user_id, PDO::PARAM_STR, 255);
	  $query->bindParam(":court_id", $court_id, PDO::PARAM_INT);
	  $query->bindParam(":reserve_time", $reserve_time);
      $isSuccessful = $query->execute();
	  return $isSuccessful;
    }
    catch(PDOException $e) {
      echo ("Error: " . $e->getMessage());
    }
    return false; 
  }
}
?>
