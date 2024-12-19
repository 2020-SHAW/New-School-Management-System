package com.admin.school.services;

import com.admin.school.data.Role;
import com.admin.school.entity.Attendance;
import com.admin.school.entity.Staff;
import com.admin.school.repository.AttendanceRepository;
import com.admin.school.repository.StaffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class AttendanceService {

    @Autowired
    private StaffRepository staffRepository;

    @Autowired
    private AttendanceRepository attendanceRepository;

    // Fetch staff by role
    public List<Staff> getStaffByRole(Role role) {
        return staffRepository.findByRolesContaining(role);
    }

    // Save attendance
    public void saveAttendance(List<Attendance> attendances) {
        attendanceRepository.saveAll(attendances);
    }

    // Mark absent if not marked present by 10:30 AM
    public void markAbsentIfNotMarked() {
        LocalDate today = LocalDate.now();
        List<Staff> staffList = staffRepository.findAll();
        
        for (Staff staff : staffList) {
            Attendance attendance = attendanceRepository.findByStaffIdAndDate(staff.getId(), today);
            if (attendance == null || !attendance.isPresent()) {
                // Mark absent if not present
                Attendance absentAttendance = new Attendance(staff, false, today);
                attendanceRepository.save(absentAttendance);
            }
        }
    }
}
