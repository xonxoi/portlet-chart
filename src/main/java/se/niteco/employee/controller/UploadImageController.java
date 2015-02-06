package se.niteco.employee.controller;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.jcr.Node;
import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.bind.annotation.ActionMapping;

import se.niteco.employee.model.Employee;
import se.niteco.employee.service.EmployeeService;
import senselogic.sitevision.api.Utils;
import senselogic.sitevision.api.render.ImageRenderer;
import senselogic.sitevision.api.resource.ResourceLocatorUtil;
import senselogic.sitevision.api.webresource.ImageUtil;

@Controller
@RequestMapping(value = "VIEW")
public class UploadImageController {
	
	@Autowired
	@Qualifier("employeeService")
	private EmployeeService service;
	
	private final String UPLOAD_FOLDER = "/home/xonmai/Desktop/ImageData";
	
	@ActionMapping(params = "action=changeImage")
	public void updateImage(ActionRequest request, ActionResponse response) 
			throws Exception{
		
		String id = (String) request.getParameter("id");
		
		File inputFile = (File) request.getAttribute("file");
		File outputFile = new File(UPLOAD_FOLDER + "/" + id + ".jpg");
		
		if(inputFile == null){
			return;
		}

		BufferedImage img = ImageIO.read(inputFile);
		ImageIO.write(img, "jpg", outputFile);
		
		Utils utils = (Utils) request.getAttribute("sitevision.utils");
		
		
		//Upload image to sitevision
		ImageUtil imageUtil = utils.getImageUtil();
		ResourceLocatorUtil rlUtil = utils.getResourceLocatorUtil();
		Node imgNode = rlUtil.getImageRepository();
		
		boolean hasNode = imgNode.hasNode(id + ".jpg");
		if(hasNode == false){
			imageUtil.createImage(imgNode, id + ".jpg", "file://" + outputFile.getPath());
		}
		else{
			imageUtil.updateBinaryContent(imgNode.getNode(id + ".jpg"), "file://" + outputFile.getPath());
		}
		
		Node imgLink = imgNode.getNode(id + ".jpg");
		
		ImageRenderer imgRender = utils.getImageRenderer();
		imgRender.setImage(imgLink);
		imgRender.setDescription(id);
		
		//Update image to employee
		Employee emp = service.getEmployee(utils, id);
		emp.setPicture(imgRender.render());		
		service.updateEmployee(utils, emp);
		
		outputFile.delete();
	}
}
