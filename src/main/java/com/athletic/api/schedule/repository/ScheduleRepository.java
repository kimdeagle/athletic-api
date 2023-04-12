package com.athletic.api.schedule.repository;

import com.athletic.api.schedule.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, String>, ScheduleRepositoryCustom {
}
