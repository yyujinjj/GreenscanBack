package com.io.greenscan.repository;

import com.io.greenscan.entity.Recycling;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecyclingRepository extends JpaRepository<Recycling,Long> {
}
