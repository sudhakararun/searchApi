package com.contus.searchapi.serviceimpl;

import java.util.Date;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.contus.searchapi.ExceptionHandler.ContusException;
import com.contus.searchapi.api.vo.Employee;
import com.contus.searchapi.repo.EmployeeRepo;
import com.contus.searchapi.service.EmployeeService;

@Service
public class EmployeeServiceImpl implements EmployeeService {
	@Autowired
	private EmployeeRepo employeeRepo;
	Logger logger = LoggerFactory.getLogger(EmployeeServiceImpl.class);

	@Override
	public Employee addEmployee(Employee employee) throws Exception {
		try {
			if (employee.getCreatedAt() == null || employee.getEmpId() == null) {
				employee.setCreatedAt(new Date(System.currentTimeMillis()));
			} else {
				employee.setUpdatedAt(new Date(System.currentTimeMillis()));
			}
			employee = employeeRepo.save(employee);
			if (employee == null) {
				throw new ContusException("Error Occured While Saving");
			}
		} catch (final InvalidDataAccessApiUsageException exception) {
			logger.error("Exception: {}" + exception.toString());
			throw new InvalidDataAccessApiUsageException("InvalidDataAccessApiUsageException");
		} catch (final ContusException exception) {
			logger.error("Exception: {} " + exception.getErrorMessage());
			throw new ContusException(exception.getErrorMessage());
		} catch (final Exception exception) {
			logger.error("Exception: {} " + exception.toString());
			throw new Exception("GenericException");
		}

		return employee;
	}

	@SuppressWarnings("unchecked")
	@Override
	public JSONObject searchEmployee(String empName, Integer deptId, Boolean createdDtSortYn, int page, int size)
			throws Exception {
		JSONObject resultJson = new JSONObject();
		JSONObject dataJson = new JSONObject();
		Pageable pageable;
		try {

			if (size == 0) {
				throw new ContusException("Size should not Be less than 1");
			}
			if (createdDtSortYn != null && createdDtSortYn == true) {
				pageable = PageRequest.of(page, size, Sort.by("created_at").ascending());
			} else {
				pageable = PageRequest.of(page, size);
			}
			Page<Object[]> empList;
			empList = employeeRepo.employeeSerachApi((empName != null ? "%" + empName.toLowerCase() + "%" : empName),
					deptId, pageable);
			dataJson.put("totalPages", empList.getTotalPages());
			dataJson.put("currentPage", page);
			dataJson.put("pageSize", size);
			JSONArray empJsonArray = new JSONArray();
			if (empList.isEmpty()) {
				throw new ContusException("No Data Found");
			}
			for (Object[] empDate : empList) {
				JSONObject empJson = new JSONObject();
				empJson.put("empId", empDate[0].toString());
				empJson.put("empName", empDate[1].toString());
				empJson.put("deptName", empDate[2].toString());
				empJsonArray.add(empJson);
			}
			dataJson.put("paginationData", empJsonArray);
			resultJson.put("data", dataJson);
		} catch (final InvalidDataAccessApiUsageException exception) {
			logger.error("Exception: {}" + exception.toString());
			throw new InvalidDataAccessApiUsageException("InvalidDataAccessApiUsageException");
		} catch (final ContusException exception) {
			logger.error("Exception: {} " + exception.getErrorMessage());
			throw new ContusException(exception.getErrorMessage());
		} catch (final Exception exception) {
			logger.error("Exception: {} " + exception.toString());
			throw new Exception("GenericException");
		}
		return resultJson;
	}

}
