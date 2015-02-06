package se.niteco.employee.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletPreferences;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.bind.annotation.ActionMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;

import se.niteco.city.model.City;
import se.niteco.city.service.CityService;
import se.niteco.employee.model.Employee;
import se.niteco.employee.service.EmployeeService;
import senselogic.sitevision.api.Utils;

@Controller("employeeController")
@RequestMapping(value="VIEW")
public class EmployeePortletController{
	
	@Autowired
	@Qualifier("employeeService")
	private EmployeeService service;
	
	@Autowired
	@Qualifier("cityService")
	private CityService cityService;
	
	private static Map<String, String> signal = null;
	
	@RenderMapping
	public String showEmployee(Model model, RenderRequest request, RenderResponse response, PortletPreferences pref){
		
		//Set add url
		PortletURL showAddUrl = response.createRenderURL();
		showAddUrl.setParameter("action", "showAdd");
		model.addAttribute("showAddUrl", showAddUrl);

		//Set edit url
		PortletURL editUrl = response.createRenderURL();
		editUrl.setParameter("action", "showEdit");
		model.addAttribute("editUrl", editUrl);

		//Set remove url
		PortletURL removeUrl = response.createActionURL();
		removeUrl.setParameter("action", "deleteEmployee");
		model.addAttribute("removeUrl", removeUrl);
		
		//Set image url
		PortletURL imageUrl = response.createRenderURL();
		imageUrl.setParameter("action", "showImageForm");
		model.addAttribute("imageUrl", imageUrl);
		
		//Get list of employee
		Utils utils = (Utils) request.getAttribute("sitevision.utils");
		
		//handle signal
		if(signal != null){
			String cityId = signal.get("city");
			service.removeEmployeeByCityId(utils, cityId);
			signal = null;
		}
		
		List<Employee> lst = service.getListEmployee(utils);
		
		List<City> listCity = cityService.getCityList(utils);
		Map<String, String> mapCity = new HashMap<String, String>();
		for( City city : listCity ){
			mapCity.put(city.getId(), city.getCityName());
		}
		
		model.addAttribute("name","Niteco");
		model.addAttribute("lstEmployee", lst);
		model.addAttribute("request", request);
		model.addAttribute("mapCity", mapCity);
		
		String mode = pref.getValue("mode", "View");
		return "employeeList" + mode;
	}
	
	@RenderMapping(params = "action=showAdd")
	public String showAdd(Model model, RenderRequest request, RenderResponse response){
		
		//Set url to model
		PortletURL registUrl = response.createActionURL();
		registUrl.setParameter("action", "insertEmployee");
		
		model.addAttribute("registUrl", registUrl);
		model.addAttribute("homeUrl", response.createRenderURL());
		
		Utils utils = (Utils) request.getAttribute("sitevision.utils");
		List<City> listCity = cityService.getCityList(utils);
		
		Employee emp = new Employee();
		model.addAttribute("emp", emp);
		model.addAttribute("request", request);
		model.addAttribute("errMessage", request.getParameter("errMessage"));
		model.addAttribute("listCity", listCity);
		
		return "employeeForm";
	}
	
	@ActionMapping(params = "action=insertEmployee")
	public void doAdd(ActionRequest request, ActionResponse response) throws Exception{
		
		Employee newEmp = new Employee(request.getParameter("id"), 
				request.getParameter("name"), 
				request.getParameter("dept"), 
				request.getParameter("position"), 
				Integer.valueOf(request.getParameter("salary")));
		
		newEmp.setPicture("");
		newEmp.setAddress(request.getParameter("address"));
		
		Utils utils = (Utils) request.getAttribute("sitevision.utils");
		boolean ret = service.insertEmployee(utils, newEmp);
		
		if ( ret == false ){
			response.setRenderParameter("action", "showAdd");
			response.setRenderParameter("errMessage", "Employee Id is duplicated.");
		}
		else{
			response.setRenderParameter("errMessage", "");
		}
	}
	
	@RenderMapping(params = "action=showEdit")
	public String showEdit(Model model, RenderRequest request, RenderResponse response){

		//Set url to model
		PortletURL updateUrl = response.createActionURL();
		updateUrl.setParameter("action", "updateEmployee");
		
		model.addAttribute("registUrl", updateUrl);
		model.addAttribute("homeUrl", response.createRenderURL());
		
		//Get selected employee
		String id = (String) request.getParameter("id");
		
		Utils utils = (Utils) request.getAttribute("sitevision.utils");
		Employee emp = service.getEmployee(utils, id);
		List<City> listCity = cityService.getCityList(utils);
		
		model.addAttribute("emp", emp);
		model.addAttribute("listCity", listCity);
		model.addAttribute("request", request);
		
		return "employeeForm";
	}
	
	@ActionMapping(params = "action=updateEmployee")
	public void doEdit(ActionRequest request) throws Exception{
		
		Employee updateEmp = new Employee(request.getParameter("id"), 
				request.getParameter("name"), 
				request.getParameter("dept"), 
				request.getParameter("position"), 
				Integer.valueOf(request.getParameter("salary")));
		
		updateEmp.setPicture(null);
		updateEmp.setAddress(request.getParameter("address"));
		
		Utils utils = (Utils) request.getAttribute("sitevision.utils");
		service.updateEmployee(utils, updateEmp);
	}
	
	@ActionMapping(params = "action=deleteEmployee")
	public void doRemove(ActionRequest request) throws Exception{
		
		String id = (String)request.getParameter("id");
		
		Utils utils = (Utils) request.getAttribute("sitevision.utils");
		service.removeEmployee(utils, id);
	}
	
	@RenderMapping(params = "action=showImageForm")
	public String showImageForm(RenderRequest request, RenderResponse response, Model model){
		
		String id = (String) request.getParameter("id");
		Utils utils = (Utils) request.getAttribute("sitevision.utils");
		
		//get current employee
		Employee emp = service.getEmployee(utils, id);
		
		PortletURL homeUrl = response.createRenderURL();
		PortletURL updateImageUrl = response.createActionURL();
		updateImageUrl.setParameter("action", "changeImage");
		
		model.addAttribute("employeeId", id);
		model.addAttribute("homeUrl", homeUrl);
		model.addAttribute("updateImageUrl", updateImageUrl);
		model.addAttribute("request", request);
		model.addAttribute("emp", emp);
		
		return "imageForm";
	}
	
	public void receiveMessage(Map<String, String> message){
		System.out.println("Receive Message:" + message);
		this.signal = message;
	}
}
