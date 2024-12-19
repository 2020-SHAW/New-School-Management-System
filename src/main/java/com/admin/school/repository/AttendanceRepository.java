package com.admin.school.repository;

import com.admin.school.entity.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
    // Find attendance for a given date
    List<Attendance> findByDate(LocalDate date);
    
    // Find attendance for a specific staff on a specific date
    Attendance findByStaffIdAndDate(String staffId, LocalDate date);
}
