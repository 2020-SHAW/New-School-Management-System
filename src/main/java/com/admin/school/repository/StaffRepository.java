package com.admin.school.repository;

import com.admin.school.data.Role;
import com.admin.school.entity.Staff;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StaffRepository extends JpaRepository<Staff, String> {
    // You can add custom queries here if needed
	List<Staff> findByRolesContaining(Role role);
}
