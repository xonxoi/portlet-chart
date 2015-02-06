package se.niteco.employee.service;

import java.lang.reflect.Type;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import se.niteco.employee.model.Employee;
import senselogic.sitevision.api.Utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


public interface EmployeeService {
	
	public final String metadataName = "nitecoEmployee";
	
	public Type employeeType =  new TypeToken<LinkedList<Employee>>() {}.getType();
	
	public Gson gson = new Gson();
	
	/**
	 * @return List of employee
	 */
	public List<Employee> getListEmployee(Utils utils);
	
	/**
	 * @param newEmp
	 */
	public boolean insertEmployee(Utils utils, Employee newEmp) throws Exception;
	
	/**
	 * @param id
	 * @return employee
	 */
	public Employee getEmployee(Utils utils, String id);
	
	/**
	 * @param info
	 */
	public void updateEmployee(Utils utils, Employee info) throws Exception;
	
	/**
	 * @param id
	 */
	public void removeEmployee(Utils utils, String id) throws Exception;
	
	public void removeEmployeeByCityId(Utils utils, String cityId);
}
