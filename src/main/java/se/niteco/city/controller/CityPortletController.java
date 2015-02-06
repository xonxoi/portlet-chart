package se.niteco.city.controller;

import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletSession;
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
import se.niteco.jms.CitySender;
import senselogic.sitevision.api.Utils;

@Controller
@RequestMapping(value="VIEW")
public class CityPortletController {
	
	@Autowired
	@Qualifier("cityService")
	private CityService service;
	
	@Autowired
	@Qualifier("citySender")
	private CitySender citySender;
	
	@RenderMapping //Default
	public String showCityList(RenderResponse response, RenderRequest request, Model model) throws Exception{
		
		Utils utils = (Utils) request.getAttribute("sitevision.utils");
		List<City> cityList = service.getCityList(utils);
		
		PortletURL cityFormURL = response.createRenderURL();
		cityFormURL.setParameter("action", "showCityForm");
		
		//Render edit link
		PortletURL showEditURL = response.createRenderURL();
		showEditURL.setParameter("action", "showEdit");
		
		//Render remove link
		PortletURL removeURL = response.createActionURL();
		removeURL.setParameter("action", "removeCity");
		
		model.addAttribute("cityList", cityList);
		model.addAttribute("request", request);
		model.addAttribute("cityFormURL", cityFormURL);
		model.addAttribute("editURL", showEditURL);
		model.addAttribute("removeURL", removeURL);
		
		return "cityList";
	}
	
	@RenderMapping(params="action=showCityForm")
	public String showCityForm(RenderRequest request, RenderResponse response, Model model){
		
		PortletURL homeURL = response.createRenderURL();
		PortletURL addCityURL = response.createActionURL();
		addCityURL.setParameter("action", "addCity");
		
		model.addAttribute("request", request);
		model.addAttribute("homeURL", homeURL);
		model.addAttribute("addCityURL", addCityURL);
		
		return "cityForm";
	}
	
	@ActionMapping(params="action=addCity")
	public void addCity(ActionRequest request, ActionResponse response) throws Exception{
		
		City newCity = new City();
		newCity.setId((String) request.getParameter("cityId"));
		newCity.setCityName((String) request.getParameter("cityName"));
		
		Utils utils = (Utils) request.getAttribute("sitevision.utils");
		
		service.addNewCity(utils, newCity);
	}
	
	@ActionMapping(params="action=removeCity")
	public void removeCity(ActionRequest request, ActionResponse response) throws Exception{
		
		Utils utils = (Utils) request.getAttribute("sitevision.utils");
		String id = (String) request.getParameter("id");
		
		service.removeCity(utils, id);
		
		citySender.sendMessage("delete", id);
	}
	
	@RenderMapping(params="action=showEdit")
	public String showEditForm(RenderRequest request, RenderResponse response, Model model){
		
		String id = (String) request.getParameter("id");
		Utils utils = (Utils) request.getAttribute("sitevision.utils");
		
		City city = service.getCity(utils, id);
		
		PortletURL homeURL = response.createRenderURL();
		
		PortletURL updateURL = response.createActionURL();
		updateURL.setParameter("action", "editCity");
		
		model.addAttribute("city", city);
		model.addAttribute("request", request);
		model.addAttribute("addCityURL", updateURL);
		model.addAttribute("homeURL", homeURL);
		
		return "cityForm";
	}
	
	@ActionMapping(params="action=editCity")
	public void updateCity(ActionRequest request, ActionResponse response, Model model) throws Exception{
		
		City info = new City();
		info.setId(request.getParameter("cityId"));
		info.setCityName(request.getParameter("cityName"));
		
		Utils utils = (Utils) request.getAttribute("sitevision.utils");
		service.updateCityInfo(utils, info);
	}
	
}
