package com.taskManagement.repository;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.taskManagement.entity.Role;


@Repository

public interface RoleRepository extends JpaRepository<Role, Long> {

}
