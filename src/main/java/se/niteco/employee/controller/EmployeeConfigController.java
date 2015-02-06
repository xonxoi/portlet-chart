package se.niteco.employee.controller;

import java.io.IOException;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletContext;
import javax.portlet.PortletException;
import javax.portlet.PortletMode;
import javax.portlet.PortletPreferences;
import javax.portlet.PortletSession;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.WindowState;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.bind.annotation.ActionMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;

@Controller
@RequestMapping(value="CONFIG")
public class EmployeeConfigController {
	
	@RenderMapping
	public String doConfig(RenderRequest request, RenderResponse response, Model model, PortletPreferences pref) 
			throws PortletException, IOException{
		System.out.println("Configuration Mode");

		//Set configuration URL
		PortletURL configUrl = response.createActionURL();
		model.addAttribute("configUrl", configUrl);
		configUrl.setWindowState(WindowState.MINIMIZED);
		
		return "configView";
	}
	
	@ActionMapping
	public void saveConfig(ActionRequest request, ActionResponse response, PortletSession session, PortletPreferences pref)
			throws PortletException, IOException{
		
		pref.setValue("mode", request.getParameter("mode"));
		pref.store();

		response.setPortletMode(PortletMode.VIEW);
	}
}
