package com.contus.searchapi.serviceimpl;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.stereotype.Service;

import com.contus.searchapi.ExceptionHandler.ContusException;
import com.contus.searchapi.api.vo.Department;
import com.contus.searchapi.repo.DepartmentRepo;
import com.contus.searchapi.service.DepartmentService;

@Service
public class DepartmentServiceImpl implements DepartmentService {

	@Autowired
	private DepartmentRepo departmentRepo;

	@Override
	public Department saveDepartment(Department department) throws Exception {
		Logger logger = LoggerFactory.getLogger(DepartmentServiceImpl.class);

		try {
			if (department.getCreatedAt() == null || department.getDeptId() == null) {
				department.setCreatedAt(new Date(System.currentTimeMillis()));
			} else {
				department.setUpdatedAt(new Date(System.currentTimeMillis()));
			}
			if (department.getDeptName() == null) {
				throw new ContusException("Department Name should be Mandatory");
			}
			department = departmentRepo.save(department);// save or update Entity Based on existence

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
		return department;
	}

}
