package com.example.usermtmessageservices.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.example.usermtmessageservices.entity.ReceiverBank;



public interface BankRepository extends JpaRepository<ReceiverBank ,Long>{
	
	@Query(value = "select count(accountnumber) from receiverbank where accountnumber=:accountnumber ", nativeQuery = true)
	Integer accountcount(String accountnumber);
	
	@Query(value = "select accountbalance from receiverbank where accountnumber=:accountnumber ", nativeQuery = true)
	Integer accountBalance(String accountnumber);
	
	@Transactional
	@Modifying
	@Query("UPDATE ReceiverBank SET accountbalance = :deposit WHERE  accountnumber = :accountnumber")
	Integer updatebalance(int deposit, String accountnumber);

}
