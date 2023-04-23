package com.contus.searchapi.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.contus.searchapi.api.vo.Employee;

public interface EmployeeRepo extends JpaRepository<Employee, Integer> {
	@Query(nativeQuery = true, value = "SELECT E.ID AS employeeId, E.NAME AS employeeName, D.NAME AS deptName FROM EMPLOYEE E INNER JOIN DEPARTMENT D ON E.DEPARTMENT=D.ID WHERE ((LOWER(E.NAME) LIKE :name AND :name IS NOT NULL) OR  :name IS NULL) AND ((D.ID = :departmentId AND :departmentId IS NOT NULL)OR :departmentId IS NULL )", 
			countQuery = "SELECT count(*) FROM EMPLOYEE E INNER JOIN DEPARTMENT D ON E.DEPARTMENT=D.ID WHERE ((LOWER(E.NAME) LIKE :name AND :name IS NOT NULL) OR  :name IS NULL) AND ((D.ID = :departmentId AND :departmentId IS NOT NULL)OR :departmentId IS NULL )")
	public Page<Object[]> employeeSerachApi(@Param("name") String name, @Param("departmentId") Integer departmentId,
			Pageable pageable);

}

