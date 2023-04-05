package com.athletic.api.dues.repository;

import com.athletic.api.dues.dto.DuesAmountInterface;
import com.athletic.api.dues.entity.Dues;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface DuesRepository extends JpaRepository<Dues, String> {
    @Query(value = "SELECT IN_OUT_CD as inOutCd, SUM(AMOUNT) AS amount FROM DUES WHERE TO_CHAR(CURRENT_TIMESTAMP, 'YYYYMM') BETWEEN TO_CHAR(START_DT, 'YYYYMM') AND TO_CHAR(END_DT, 'YYYYMM') GROUP BY IN_OUT_CD UNION ALL SELECT :rest AS inOutCd, SUM(CASE WHEN IN_OUT_CD = :in THEN AMOUNT WHEN IN_OUT_CD = :out THEN AMOUNT * -1 END) AS amount FROM DUES", nativeQuery = true)
    List<DuesAmountInterface> selectAmountThisMonth(@Param(value = "in") String in, @Param(value = "out") String out, @Param(value = "rest") String rest);

    @Query(value = "SELECT * FROM DUES WHERE CAST(START_DT AS DATE) <= CAST(:endDt AS DATE) AND CAST(END_DT AS DATE) >= CAST(:startDt AS DATE) ORDER BY START_DT, END_DT, AMOUNT", nativeQuery = true)
    List<Dues> findByPeriod(@Param(value = "startDt") LocalDateTime startDt, @Param(value = "endDt") LocalDateTime endDt);
}