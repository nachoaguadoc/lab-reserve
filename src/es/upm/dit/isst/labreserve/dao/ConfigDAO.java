package es.upm.dit.isst.labreserve.dao;

import java.util.List;

import es.upm.dit.isst.labreserve.model.Config;
import es.upm.dit.isst.labreserve.model.Resource;

public interface ConfigDAO {
	
	public List<Config> listConfig();
	
	public void add (String type, String webmaster, String sessionTime, String opening, String closing);
	
	public void update (String type, String webmaster, String sessionTime, String opensing, String closing);

	public void updateWebmaster (String type, String webmaster);

	public void updateSessionTime (String type, String sessionTime);

	public Config getConfig(String type);

	public void remove (String type);
}
