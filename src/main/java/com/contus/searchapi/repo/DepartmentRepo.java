package com.contus.searchapi.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.contus.searchapi.api.vo.Department;

public interface DepartmentRepo extends JpaRepository<Department, Integer> {

}
