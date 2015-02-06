package se.niteco.chart.controller;

import java.util.List;

import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;
import org.springframework.web.portlet.bind.annotation.ResourceMapping;
import org.springframework.web.servlet.ModelAndView;

import se.niteco.employee.model.Employee;
import se.niteco.employee.service.EmployeeService;
import senselogic.sitevision.api.Utils;

@Controller
@RequestMapping(value="VIEW")
public class ChartController {
	
	@Autowired
	@Qualifier("employeeService")
	private EmployeeService service;
	
	@RenderMapping //default
	public String displayChart(RenderRequest req, RenderResponse res, Model model){
		
		Utils utils = (Utils) req.getAttribute("sitevision.utils");
		List<Employee> listEmp = service.getListEmployee(utils);
		
		model.addAttribute("request", req);
		model.addAttribute("listEmp", listEmp);
		
		return "showChart";
	}
	
	@ResourceMapping(value="")
	public ModelAndView updateChart(ResourceRequest req, ResourceResponse res, Model model) 
			throws Exception{
		System.out.println("Calling AJAX...");
		
		return new ModelAndView();
	}
}
