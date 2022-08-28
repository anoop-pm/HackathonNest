package com.example.usermxmessageservices.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.usermxmessageservices.entity.MxMessage;




@Repository
public interface MxRepository extends JpaRepository<MxMessage ,Long> {
	
	
	@Query(value = "select count(message) from mxmessage where id=:ids ", nativeQuery = true)
	Integer countMessage(int ids);
	
	@Query(value = "select message from mxmessage where id=:ids ", nativeQuery = true)
	String selectMessage(int ids);
	
	@Transactional
	@Modifying
	@Query("UPDATE MxMessage SET message = :message WHERE  id = 1")
	Integer updatemessage(String message );
	
	
	
	

}
