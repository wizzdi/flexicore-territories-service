package com.flexicore.territories.rest;

import com.flexicore.interceptors.SecurityImposer;
import com.flexicore.interceptors.DynamicResourceInjector;
import com.flexicore.annotations.plugins.PluginInfo;
import com.flexicore.annotations.OperationsInside;
import javax.interceptor.Interceptors;
import com.flexicore.interfaces.RestServicePlugin;
import javax.ws.rs.Path;

import com.flexicore.model.territories.Street;
import com.flexicore.model.territories.Zip;
import com.flexicore.territories.service.ZipToStreetService;
import javax.inject.Inject;
import com.flexicore.security.SecurityContext;
import java.util.List;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import javax.ws.rs.HeaderParam;
import io.swagger.v3.oas.annotations.Operation;
import com.flexicore.annotations.IOperation;
import com.flexicore.territories.data.request.ZipToStreetCreationContainer;
import javax.ws.rs.core.Context;
import com.flexicore.model.territories.ZipToStreet;
import javax.ws.rs.DELETE;
import javax.ws.rs.PathParam;
import com.flexicore.territories.data.request.ZipToStreetFiltering;
import com.flexicore.territories.data.request.ZipToStreetUpdateContainer;
import io.swagger.v3.oas.annotations.tags.Tag;

@PluginInfo(version = 1)
@OperationsInside
@Interceptors({SecurityImposer.class, DynamicResourceInjector.class})
@Path("plugins/ZipToStreet")
@Tag(name = "ZipToStreet")

public class ZipToStreetRESTService implements RestServicePlugin {

	@Inject
	@PluginInfo(version = 1)
	private ZipToStreetService service;

	@POST
	@Produces("application/json")
	@Path("/createZipToStreet")
	@Operation(summary = "createZipToStreet", description = "Creates ZipToStreet")
	@IOperation(Name = "createZipToStreet", Description = "Creates ZipToStreet")
	public ZipToStreet createZipToStreet(
			@HeaderParam("authenticationKey") String authenticationKey,
			ZipToStreetCreationContainer creationContainer,
			@Context SecurityContext securityContext) {
		Zip leftside = service.getByIdOrNull(creationContainer.getZipId(),
				Zip.class, null, securityContext);
		if (leftside == null) {
			throw new BadRequestException("no Zip with id "
					+ creationContainer.getZipId());
		}
		creationContainer.setZip(leftside);
		Street rightside = service.getByIdOrNull(
				creationContainer.getStreetId(), Street.class, null,
				securityContext);
		if (rightside == null) {
			throw new BadRequestException("no Street with id "
					+ creationContainer.getStreetId());
		}
		creationContainer.setStreet(rightside);
		return service.createZipToStreet(creationContainer, securityContext);
	}

	@DELETE
	@Operation(summary = "deleteZipToStreet", description = "Deletes ZipToStreet")
	@IOperation(Name = "deleteZipToStreet", Description = "Deletes ZipToStreet")
	@Path("deleteZipToStreet/{id}")
	public void deleteZipToStreet(
			@HeaderParam("authenticationKey") String authenticationKey,
			@PathParam("id") String id, @Context SecurityContext securityContext) {
		service.deleteZipToStreet(id, securityContext);
	}

	@POST
	@Produces("application/json")
	@Operation(summary = "listAllZipToStreets", description = "Lists all ZipToStreets Filtered")
	@IOperation(Name = "listAllZipToStreets", Description = "Lists all ZipToStreets Filtered")
	@Path("listAllZipToStreets")
	public List<ZipToStreet> listAllZipToStreets(
			@HeaderParam("authenticationKey") String authenticationKey,
			ZipToStreetFiltering filtering,
			@Context SecurityContext securityContext) {
		return service.listAllZipToStreets(securityContext, filtering);
	}

	@POST
	@Produces("application/json")
	@Path("/updateZipToStreet")
	@Operation(summary = "updateZipToStreet", description = "Updates ZipToStreet")
	@IOperation(Name = "updateZipToStreet", Description = "Updates ZipToStreet")
	public ZipToStreet updateZipToStreet(
			@HeaderParam("authenticationKey") String authenticationKey,
			ZipToStreetUpdateContainer updateContainer,
			@Context SecurityContext securityContext) {
		ZipToStreet zipToStreet = service.getByIdOrNull(
				updateContainer.getId(), ZipToStreet.class, null,
				securityContext);
		if (zipToStreet == null) {
			throw new BadRequestException("no ZipToStreet with id "
					+ updateContainer.getId());
		}
		updateContainer.setZipToStreet(zipToStreet);
		Zip leftside = service.getByIdOrNull(updateContainer.getZipId(),
				Zip.class, null, securityContext);
		if (leftside == null) {
			throw new BadRequestException("no Zip with id "
					+ updateContainer.getZipId());
		}
		updateContainer.setZip(leftside);
		Street rightside = service.getByIdOrNull(
				updateContainer.getStreetId(), Street.class, null,
				securityContext);
		if (rightside == null) {
			throw new BadRequestException("no Street with id "
					+ updateContainer.getStreetId());
		}
		updateContainer.setStreet(rightside);
		return service.updateZipToStreet(updateContainer, securityContext);
	}
}