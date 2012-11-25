<?php

require_once("dbconfig.php");

class DAL 
{
	private static $dbh;

	/**
	 * Basic DB Handler functions
	 */
	public static function connect() 
	{
		if (!self::$dbh) 
		{
			try 
			{
				$host = HOST;
				$db_name = DB_NAME;
				$username = USERNAME;
				$password = PASSWORD;
		
				// Establish the connection
				self::$dbh = new PDO("mysql:host=".$host.";dbname=".$db_name, $username, $password);
				self::$dbh->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
			}
			catch(PDOException $e) 
			{
				echo ("Error: " . $e->getMessage());
				return false;
			}
		}
		return (self::$dbh != NULL);
	}
	
	public static function isConnected() 
	{
		if (self::$dbh == NULL)
			return false;
		return true;
	}

	public static function disconnect() 
	{
		self::$dbh = NULL;
	}

	/*
	 * User 
	 */
	public static function login($username, $password)
	{
		try
		{
			$sql = "SELECT username FROM oUsers WHERE username=:username AND password=SHA2(:password, 256)";
			
			$query = self::$dbh->prepare($sql);
			$query->bindParam(":username", $username, PDO::PARAM_STR, 64);
			$query->bindParam(":password", $password, PDO::PARAM_STR, 128);
			$query->execute();
			return $query->fetchAll(PDO::FETCH_ASSOC);
		}
		catch(PDOException $e) 
		{
			echo ("Error: " . $e->getMessage());
		}
		return NULL;
	}
	
	public static function update_user_account($username, $password) 
	{
		try 
		{
			$sql = "INSERT INTO oUsers (username, password) VALUES (:username, SHA2(:password, 256))";
			$sql .= " ON DUPLICATE KEY UPDATE password=SHA2(:u_password, 256)";
			
			$query = self::$dbh->prepare($sql);
			$query->bindParam(":username", $username, PDO::PARAM_STR, 64);
			$query->bindParam(":password", $password, PDO::PARAM_STR, 128);
			$query->bindParam(":u_password", $password, PDO::PARAM_STR, 128);
			return $query->execute();
		}
		catch(PDOException $e) 
		{
			echo ("Error: " . $e->getMessage());
		}
		return false;
	}
	
	public static function update_user_profile($username, $name, $street_address, $city, 
											   $state, $zipcode, $website="", $email, $about)
	{
		try 
		{
			$sql = "UPDATE oUsers SET";
			$sql .= " name=:name, ";
			$sql .= " street_address=:street_address,";
			$sql .= " city=:city,";
			$sql .= " state=:state,";
			$sql .= " zipcode=:zipcode,";
			$sql .= " website=:website,";
			$sql .= " email=:email,";
			$sql .= " about=:about";
			$sql .= " WHERE username=:username";
			
			$query = self::$dbh->prepare($sql);
			$query->bindParam(":username", $username, PDO::PARAM_STR, 64);
			$query->bindParam(":name", $name, PDO::PARAM_STR, 64);
			$query->bindParam(":street_address", $street_address, PDO::PARAM_STR, 64);
			$query->bindParam(":city", $city, PDO::PARAM_STR, 64);
			$query->bindParam(":state", $state, PDO::PARAM_STR, 64);
			$query->bindParam(":zipcode", $zipcode, PDO::PARAM_STR, 64);
			$query->bindParam(":website", $website, PDO::PARAM_STR, 64);
			$query->bindParam(":email", $email, PDO::PARAM_STR, 64);		
			$query->bindParam(":about", $about, PDO::PARAM_STR, 4096);			
			return $query->execute();
		}
		catch(PDOException $e) 
		{
			echo ("Error: " . $e->getMessage());
		}
		return false;
	}
	
	public static function get_user_profile($username)
	{
		try 
		{
			$sql = "SELECT username, name, street_address, city, state, zipcode,";
			$sql .= " website, email, about";
			$sql .= " FROM oUsers WHERE username=:username";
			$query = self::$dbh->prepare($sql);
			$query->bindParam(":username", $username, PDO::PARAM_STR, 64);
			$query->execute();
			return $query->fetchAll(PDO::FETCH_ASSOC);
		}
		catch(PDOException $e) 
		{
			echo ("Error: " . $e->getMessage());
		}
		return NULL;
	}
	
	/**
	 * Announcement
	 */
	public static function insert_announcement($username, $title, $type, $highlights, $fine_print="", 
											   $street_address, $city, $state, $zipcode, $radius,
											   $latitude, $longitude,
											   $regular_price=-1, $promotional_price=-1, 
											   $from, $to, $url="", $category)
	{
		try 
		{
			$sql = "INSERT INTO oAdvertisements (username, title, type, highlights, fine_print,";
			$sql .= " street_address, city, state, zipcode, radius, latitude, longitude,";
			$sql .= " regular_price, promotional_price, from_date, to_date, url, category)"; 
			$sql .= " VALUES (:username, :title, :type, :highlights, :fine_print,";
			$sql .= " :street_address, :city, :state, :zipcode, :radius, :latitude, :longitude,";
			$sql .= " :regular_price, :promotional_price, :from, :to, :url, :category)"; 

			$query = self::$dbh->prepare($sql);
			$query->bindParam(":username", $username, PDO::PARAM_STR, 64);
			$query->bindParam(":title", $title, PDO::PARAM_STR, 64);
			$query->bindParam(":type", $type, PDO::PARAM_INT);
			$query->bindParam(":highlights", $highlights, PDO::PARAM_STR, 140);
			$query->bindParam(":fine_print", $fine_print, PDO::PARAM_STR, 140);
			$query->bindParam(":street_address", $street_address, PDO::PARAM_STR, 64);
			$query->bindParam(":city", $city, PDO::PARAM_STR, 64);
			$query->bindParam(":state", $state, PDO::PARAM_STR, 64);
			$query->bindParam(":zipcode", $zipcode, PDO::PARAM_STR, 64);
			$query->bindParam(":radius", $radius, PDO::PARAM_INT);
			$query->bindParam(":latitude", $latitude);
			$query->bindParam(":longitude", $longitude);
			$query->bindParam(":regular_price", $regular_price);
			$query->bindParam(":promotional_price", $promotional_price);
			$query->bindParam(":from", $from);
			$query->bindParam(":to", $to);
			$query->bindParam(":url", $url, PDO::PARAM_STR, 64);
			$query->bindParam(":category", $category, PDO::PARAM_STR, 64);
			return $query->execute();
		}
		catch(PDOException $e) 
		{
			echo ("Error: " . $e->getMessage());
		}
		return false;
	}
	
	public static function update_announcement($id, $username, $title, $type, $highlights, $fine_print="", 
											   $street_address, $city, $state, $zipcode, $radius,
											   $latitude, $longitude,
											   $regular_price=-1, $promotional_price=-1, 
											   $from, $to, $url="", $category)
	{
		try 
		{
			$sql = "UPDATE oAdvertisements SET";
			$sql .= " title=:u_title, type=:u_type, highlights=:u_highlights, fine_print=:u_fine_print,";
			$sql .= " street_address=:u_street_address, city=:u_city, state=:u_state,";
			$sql .= " zipcode=:u_zipcode, radius=:u_radius, latitude=:u_latitude, longitude=:u_longitude,";
			$sql .= " regular_price=:u_regular_price, promotional_price=:u_promotional_price,"; 
			$sql .= " from_date=:u_from, to_date=:u_to, url=:u_url, category=:u_category";
			$sql .= " WHERE id=:id AND username=:username";

			$query = self::$dbh->prepare($sql);
			$query->bindParam(":id", $id, PDO::PARAM_INT);
			$query->bindParam(":username", $username, PDO::PARAM_STR, 64);
			$query->bindParam(":u_title", $title, PDO::PARAM_STR, 64);
			$query->bindParam(":u_type", $type, PDO::PARAM_INT);
			$query->bindParam(":u_highlights", $highlights, PDO::PARAM_STR, 140);
			$query->bindParam(":u_fine_print", $fine_print, PDO::PARAM_STR, 140);
			$query->bindParam(":u_street_address", $street_address, PDO::PARAM_STR, 64);
			$query->bindParam(":u_city", $city, PDO::PARAM_STR, 64);
			$query->bindParam(":u_state", $state, PDO::PARAM_STR, 64);
			$query->bindParam(":u_zipcode", $zipcode, PDO::PARAM_STR, 64);
			$query->bindParam(":u_radius", $radius, PDO::PARAM_INT);
			$query->bindParam(":u_latitude", $latitude);
			$query->bindParam(":u_longitude", $longitude);
			$query->bindParam(":u_regular_price", $regular_price);
			$query->bindParam(":u_promotional_price", $promotional_price);
			$query->bindParam(":u_from", $from);
			$query->bindParam(":u_to", $to);
			$query->bindParam(":u_url", $url, PDO::PARAM_STR, 64);
			$query->bindParam(":u_category", $category, PDO::PARAM_STR, 64);
			return $query->execute();
		}
		catch(PDOException $e) 
		{
			echo ("Error: " . $e->getMessage());
		}
		return false;
	}
	
	public static function delete_announcement($id)
	{
		try 
		{
			$sql = "DELETE FROM oAdvertisements WHERE id=:id";
			$query = self::$dbh->prepare($sql);
			$query->bindParam(":id", $id, PDO::PARAM_INT);
			return $query->execute();
		}
		catch(PDOException $e) 
		{
			echo ("Error: " . $e->getMessage());
		}
		return false;
	}
	
	public static function get_announcement($username)
	{
		try 
		{
			$sql = "SELECT * FROM oAdvertisements WHERE username=:username ORDER BY type ASC LIMIT 20";
			$query = self::$dbh->prepare($sql);
			$query->bindParam(":username", $username, PDO::PARAM_STR, 64);
			$query->execute();
			return $query->fetchAll(PDO::FETCH_ASSOC);
		}
		catch(PDOException $e) 
		{
			echo ("Error: " . $e->getMessage());
		}
		return NULL;
	}
	
	public static function get_announcement_by_type($username="", $type)
	{
		try 
		{
			$sql = "";
			$query = NULL;
			
			if (strlen($username) == 0)
			{
				$sql = "SELECT * FROM oAdvertisements WHERE type=:type LIMIT 20";
				$query = self::$dbh->prepare($sql);
				$query->bindParam(":type", $type, PDO::PARAM_INT);
			}
			else
			{
				$sql = "SELECT * FROM oAdvertisements WHERE username=:username AND type=:type  LIMIT 20";
				$query = self::$dbh->prepare($sql);
				$query->bindParam(":username", $username, PDO::PARAM_STR, 64);
				$query->bindParam(":type", $type, PDO::PARAM_INT);
			}
			
			$query->execute();
			return $query->fetchAll(PDO::FETCH_ASSOC);
		}
		catch(PDOException $e) 
		{
			echo ("Error: " . $e->getMessage());
		}
		return NULL;
	}
	
	public static function get_announcement_by_id($id)
	{
		try 
		{
			$sql = "SELECT * FROM oAdvertisements WHERE id=:id";
			$query = self::$dbh->prepare($sql);
			$query->bindParam(":id", $id, PDO::PARAM_INT);
			$query->execute();
			return $query->fetchAll(PDO::FETCH_ASSOC);
		}
		catch(PDOException $e) 
		{
			echo ("Error: " . $e->getMessage());
		}
		return NULL;
	}
	
	/**
	 * DEVICES
	 */
	public static function register_device($gcm_id)
	{
		
	}
	
	public static function unregister_device($gcm_id)
	{
		
	}
	
	public static function update_location($gcm_id)
	{
		
	}
}
?>
