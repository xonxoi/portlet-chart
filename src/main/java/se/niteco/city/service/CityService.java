package se.niteco.city.service;

import java.lang.reflect.Type;
import java.util.LinkedList;
import java.util.List;

import se.niteco.city.model.City;
import senselogic.sitevision.api.Utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public interface CityService {
	
	public final String metadataName = "nitecoCity";
	
	public Gson gson = new Gson();
	
	public Type cityType = new TypeToken<LinkedList<City>>() {}.getType();
	
	public List<City> getCityList(Utils utils);
	
	public City getCity(Utils utils,String id);
	
	public void addNewCity(Utils utils, City input) throws Exception;
	
	public void updateCityInfo(Utils utils, City input) throws Exception;
	
	public void removeCity(Utils utils, String id) throws Exception;

}
