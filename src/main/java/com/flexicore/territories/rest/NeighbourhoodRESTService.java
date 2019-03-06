package com.flexicore.territories.rest;

import com.flexicore.annotations.IOperation;
import com.flexicore.annotations.OperationsInside;
import com.flexicore.annotations.plugins.PluginInfo;
import com.flexicore.data.jsoncontainers.PaginationResponse;
import com.flexicore.interceptors.DynamicResourceInjector;
import com.flexicore.interceptors.SecurityImposer;
import com.flexicore.interfaces.RestServicePlugin;
import com.flexicore.model.territories.City;
import com.flexicore.model.territories.Neighbourhood;
import com.flexicore.model.territories.Street;
import com.flexicore.security.SecurityContext;
import com.flexicore.territories.data.request.NeighbourhoodCreationContainer;
import com.flexicore.territories.data.request.NeighbourhoodFiltering;
import com.flexicore.territories.data.request.NeighbourhoodUpdateContainer;
import com.flexicore.territories.service.NeighbourhoodService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import javax.inject.Inject;
import javax.interceptor.Interceptors;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;

@PluginInfo(version = 1)
@OperationsInside
@Interceptors({SecurityImposer.class, DynamicResourceInjector.class})
@Path("plugins/Neighbourhood")

@Tag(name = "Neighbourhood")
public class NeighbourhoodRESTService implements RestServicePlugin {

    @Inject
    @PluginInfo(version = 1)
    private NeighbourhoodService service;

    @POST
    @Produces("application/json")
    @Path("/updateNeighbourhood")
    @Operation(summary = "updateNeighbourhood", description = "Updates Neighbourhood")
    @IOperation(Name = "updateNeighbourhood", Description = "Updates Neighbourhood")
    public Neighbourhood updateNeighbourhood(
            @HeaderParam("authenticationKey") String authenticationKey,
            NeighbourhoodUpdateContainer updateContainer,
            @Context SecurityContext securityContext) {
        Neighbourhood neighbourhood = service.getByIdOrNull(updateContainer.getId(), Neighbourhood.class, null, securityContext);
        if (neighbourhood == null) {
            throw new BadRequestException("no Neighbourhood with id " + updateContainer.getId());
        }
        updateContainer.setNeighbourhood(neighbourhood);
        service.validate(updateContainer, securityContext);
        return service.updateNeighbourhood(updateContainer, securityContext);
    }


    @POST
    @Produces("application/json")
    @Operation(summary = "listAllNeighbourhoodes", description = "Lists all Neighbourhoodes Filtered")
    @IOperation(Name = "listAllNeighbourhoodes", Description = "Lists all Neighbourhoodes Filtered")
    @Path("listAllNeighbourhoodes")
    public PaginationResponse<Neighbourhood> listAllNeighbourhoodes(
            @HeaderParam("authenticationKey") String authenticationKey,
            NeighbourhoodFiltering filtering, @Context SecurityContext securityContext) {
        return service.listAllNeighbourhoodes(securityContext, filtering);
    }

    @POST
    @Produces("application/json")
    @Path("/createNeighbourhood")
    @Operation(summary = "createNeighbourhood", description = "Creates Neighbourhood")
    @IOperation(Name = "createNeighbourhood", Description = "Creates Neighbourhood")
    public Neighbourhood createNeighbourhood(
            @HeaderParam("authenticationKey") String authenticationKey,
            NeighbourhoodCreationContainer creationContainer,
            @Context SecurityContext securityContext) {
        service.validate(creationContainer, securityContext);

        return service.createNeighbourhood(creationContainer, securityContext);
    }

}