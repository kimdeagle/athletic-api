package com.athletic.api.dues.repository;

import com.athletic.api.dues.dto.DuesAmountInterface;
import com.athletic.api.dues.entity.Dues;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DuesRepository extends JpaRepository<Dues, String> {
    @Query(value = "SELECT IN_OUT as inOut, SUM(AMOUNT) AS amount FROM DUES WHERE TO_CHAR(CURRENT_TIMESTAMP, 'YYYYMM') BETWEEN TO_CHAR(START_DT, 'YYYYMM') AND TO_CHAR(END_DT, 'YYYYMM') GROUP BY IN_OUT UNION ALL SELECT 'REST' AS inOut, SUM(CASE WHEN IN_OUT = ?1 THEN AMOUNT ELSE AMOUNT * -1 END) AS amount FROM DUES", nativeQuery = true)
    List<DuesAmountInterface> selectAmountThisMonth(String in);
}