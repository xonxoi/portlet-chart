package se.niteco.city.service;

import java.util.ArrayList;
import java.util.List;

import javax.jcr.Node;

import org.springframework.stereotype.Service;

import se.niteco.city.model.City;
import senselogic.sitevision.api.Utils;
import senselogic.sitevision.api.context.PortletContextUtil;
import senselogic.sitevision.api.metadata.MetadataUtil;
import senselogic.sitevision.api.property.PropertyUtil;

@Service(value="cityService")
public class CityServiceImpl implements CityService {

	public List<City> getCityList(Utils utils) {
		List<City> ret = new ArrayList<City>();
		
		PropertyUtil propertyUtil = utils.getPropertyUtil();
		PortletContextUtil ctxUtil = utils.getPortletContextUtil();
		Node currentPage = ctxUtil.getCurrentPage();
		
		String cityJSON = propertyUtil.getString(currentPage, CityService.metadataName);
		
		if(cityJSON != null && !cityJSON.isEmpty()){
			ret = gson.fromJson(cityJSON, CityService.cityType);
		}
		
		return ret;
	}

	public City getCity(Utils utils, String id) {
		City ret = null;
		List<City> cityData = new ArrayList<City>();
		
		PropertyUtil propertyUtil = utils.getPropertyUtil();
		PortletContextUtil ctxUtil = utils.getPortletContextUtil();
		Node currentPage = ctxUtil.getCurrentPage();
		
		String cityJSON = propertyUtil.getString(currentPage, CityService.metadataName);
		
		cityData = gson.fromJson(cityJSON, CityService.cityType);
		
		if(cityData != null && cityData.size() > 0){
			for(City city : cityData){
				if(city.getId().equalsIgnoreCase(id)){
					return city;
				}
			}
		}
		
		return ret;
	}

	public void addNewCity(Utils utils, City input) throws Exception{
		
		//Check whether the city is exist or not
		City exist = getCity(utils, input.getId());
		
		if(exist == null){
			List<City> cityData = null;
			
			PropertyUtil propertyUtil = utils.getPropertyUtil();
			PortletContextUtil ctxUtil = utils.getPortletContextUtil();
			Node currentPage = ctxUtil.getCurrentPage();
			
			String cityJSON = propertyUtil.getString(currentPage, CityService.metadataName);
			
			cityData = gson.fromJson(cityJSON, CityService.cityType);
			
			if(cityData == null){
				cityData = new ArrayList<City>();
			}	
			cityData.add(input);
			
			MetadataUtil dataUtil = utils.getMetadataUtil();
			dataUtil.setMetadataPropertyValue(currentPage, CityService.metadataName, gson.toJson(cityData));
		}
	}

	public void updateCityInfo(Utils utils, City input) throws Exception{
		
		List<City> cityData = new ArrayList<City>();

		PropertyUtil propertyUtil = utils.getPropertyUtil();
		PortletContextUtil ctxUtil = utils.getPortletContextUtil();
		Node currentPage = ctxUtil.getCurrentPage();

		String cityJSON = propertyUtil.getString(currentPage,
				CityService.metadataName);

		cityData = gson.fromJson(cityJSON, CityService.cityType);
		
		for(City city : cityData){
			if(city.getId().equalsIgnoreCase(input.getId())){
				city.setCityName(input.getCityName());
			}
		}
		
		MetadataUtil dataUtil = utils.getMetadataUtil();
		dataUtil.setMetadataPropertyValue(currentPage, CityService.metadataName, gson.toJson(cityData));
		
	}

	public void removeCity(Utils utils, String id) throws Exception{
		
		List<City> cityData = new ArrayList<City>();
		List<City> newList = new ArrayList<City>();

		PropertyUtil propertyUtil = utils.getPropertyUtil();
		PortletContextUtil ctxUtil = utils.getPortletContextUtil();
		Node currentPage = ctxUtil.getCurrentPage();

		String cityJSON = propertyUtil.getString(currentPage,
				CityService.metadataName);

		cityData = gson.fromJson(cityJSON, CityService.cityType);
		
		for(City city : cityData){
			if(!city.getId().equalsIgnoreCase(id)){
				newList.add(city);
			}
		}
		
		MetadataUtil dataUtil = utils.getMetadataUtil();
		dataUtil.setMetadataPropertyValue(currentPage, CityService.metadataName, gson.toJson(newList));
		
	}

}
