package com.athletic.api.dues.repository;

import com.athletic.api.dues.entity.Dues;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DuesRepository extends JpaRepository<Dues, String>, DuesRepositoryCustom {
}