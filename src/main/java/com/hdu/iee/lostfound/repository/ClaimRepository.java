package com.hdu.iee.lostfound.repository;

import com.hdu.iee.lostfound.entity.Claim;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClaimRepository extends JpaRepository<Claim, Long> {
}


