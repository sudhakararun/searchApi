package com.contus.searchapi.controller;

import javax.validation.Valid;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.contus.searchapi.api.vo.Department;
import com.contus.searchapi.api.vo.Employee;
import com.contus.searchapi.service.DepartmentService;
import com.contus.searchapi.service.EmployeeService;

@RestController
@RequestMapping("/searchApi")
public class ApiController {
	@Autowired
	private EmployeeService employeeService;
	@Autowired
	private DepartmentService departmentService;
	Logger logger = LoggerFactory.getLogger(ApiController.class);

	/**
	 * This service we are using to save and update Department Entity
	 * 
	 * @param Department Entity
	 * @throws Exception 
	 * 
	 */
	@SuppressWarnings("unchecked")
	@PostMapping("/saveDepartment")
	public ResponseEntity<Object> saveDepartment(@Valid @RequestBody Department department) throws Exception {
		logger.info("save Department Request :{}" + department);
		JSONObject resultJson = new JSONObject();
		Department updDepartment = departmentService.saveDepartment(department);
		resultJson.put("data", updDepartment);
		resultJson.put("statusCode", HttpStatus.OK);
		resultJson.put("isError", false);
		resultJson.put("message", "success");
		return new ResponseEntity<Object>(resultJson, HttpStatus.CREATED);

	}

	/**
	 * This service we are using to save and update Employee Entity
	 * 
	 * @param Employee Entity
	 * 
	 */
	@SuppressWarnings("unchecked")
	@PostMapping("/saveEmployee")
	public ResponseEntity<Object> saveEmployee(@Valid @RequestBody Employee employee) throws Exception {
		JSONObject resultJson = new JSONObject();
		logger.info("save Employee Request :{}" + employee);
		Employee updEmployee = employeeService.addEmployee(employee);
		resultJson.put("data", updEmployee);
		resultJson.put("statusCode", HttpStatus.OK);
		resultJson.put("isError", false);
		resultJson.put("message", "success");
		return new ResponseEntity<Object>(resultJson, HttpStatus.CREATED);
	}

	/**
	 * This service we are using fetch record from {@link Employee} table using
	 * below Criteria 1. Search using name 2. Order by created_at 3. filter using
	 * department id 4. Select employee id, employee name and department name using
	 * join and with pagination
	 * 
	 * @param empName         =>Employee Name
	 * @param departmentId    => Department Name
	 * @param createdDtSortYn => Base on this Flag we are doing sorting using
	 *                        createdDate
	 * @param page=>          Page param we are using for Pagination Page
	 * @param size=>          Based on size record will show in Pagination Page
	 * 
	 * 
	 */
	@SuppressWarnings("unchecked")
	@GetMapping("/searchEmployee")
	public ResponseEntity<JSONObject> getSearchEmployee(
			@RequestParam(name = "empName", required = false) String empName,
			@RequestParam(name = "departmentId", required = false) Integer departmentId,
			@RequestParam(name = "createdDtSortYn", required = false) Boolean createdDtSortYn,
			@RequestParam(name = "page", defaultValue = "0") Integer page,
			@RequestParam(name = "size", defaultValue = "5") Integer size) throws Exception {
		logger.info("empName :{},departmentId:{},createdDtSortYn:{},page:{},size:{}:", empName, departmentId,
				createdDtSortYn, page, size);
		JSONObject resultJson = employeeService.searchEmployee(empName, departmentId, createdDtSortYn, page, size);
		resultJson.put("statusCode", HttpStatus.OK);
		resultJson.put("isError", false);
		resultJson.put("message", "success");
		return new ResponseEntity<JSONObject>(resultJson, HttpStatus.OK);

	}

}
