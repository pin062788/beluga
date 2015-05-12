package org.opencloudengine.garuda.rest;

import org.opencloudengine.garuda.rest.vo.Human;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;

/**
 * Human API
 *
 * @author Sang Wook, Song
 *
 */
@Path("/human")
public class HumanAPI {

	@GET
	@Path("list")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public List<Human> getAllParties() throws Exception
	{
		List<Human> list = new ArrayList<Human>();
		list.add(new Human("Mr. Hong", 20));
		list.add(new Human("Mr. Kim", 30));
		list.add(new Human("Mr. Lee", 40));
		return list;
	}


}
