package com.juandavid.springboot.challengebackend.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.juandavid.springboot.challengebackend.entity.CallHistory;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.Pageable;

/**
 * Repository interface for CallHistory entity.
 * This interface extends JpaRepository to provide CRUD operations for CallHistory.
 */

@Repository
public interface CallHistoryRepository extends JpaRepository<CallHistory, Long> {
    //Page<CallHistory> findAll(Pageable pageable);


}
