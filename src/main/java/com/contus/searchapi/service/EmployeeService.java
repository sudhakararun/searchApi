package com.contus.searchapi.service;

import org.json.simple.JSONObject;

import com.contus.searchapi.api.vo.Employee;

public interface EmployeeService {

	public Employee addEmployee(Employee employee) throws Exception;

	public JSONObject searchEmployee(String empName, Integer deptId, Boolean createdDtSortYn, int page, int size)
			throws Exception;
}
