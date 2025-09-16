package com.multiGenesys.mt_resources.repository;

import com.multiGenesys.mt_resources.entity.Resources;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResourceRepository extends JpaRepository<Resources, Long> {
}
