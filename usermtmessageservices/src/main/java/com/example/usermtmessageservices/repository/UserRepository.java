package com.example.usermtmessageservices.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.usermtmessageservices.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User ,Long>{
	
	@Query(value = "select count(accountnumber) from user where accountnumber=:accountnumber ", nativeQuery = true)
	Integer accountcount(String accountnumber);
	
	@Query(value = "select accountbalance from user where accountnumber=:accountnumber ", nativeQuery = true)
	Integer accountBalance(String accountnumber);
	
	@Transactional
	@Modifying
	@Query("UPDATE User SET accountbalance = :deposit WHERE  accountnumber = :accountnumber")
	Integer updatebalance(int deposit, String accountnumber);
	
	


}
