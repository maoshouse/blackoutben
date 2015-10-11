package com.example.maoshouse.myapplication.backend;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.response.CollectionResponse;
import com.google.api.server.spi.response.NotFoundException;
import com.google.appengine.api.datastore.Cursor;
import com.google.appengine.api.datastore.QueryResultIterator;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.cmd.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.Nullable;
import javax.inject.Named;

import static com.googlecode.objectify.ObjectifyService.ofy;

/**
 * WARNING: This generated code is intended as a sample or starting point for using a
 * Google Cloud Endpoints RESTful API with an Objectify entity. It provides no data access
 * restrictions and no data validation.
 * <p/>
 * DO NOT deploy this code unchanged as part of a real application to real users.
 */
@Api(
        name = "locApi",
        version = "v1",
        resource = "loc",
        namespace = @ApiNamespace(
                ownerDomain = "backend.myapplication.maoshouse.example.com",
                ownerName = "backend.myapplication.maoshouse.example.com",
                packagePath = ""
        )
)
public class LocEndpoint {

    private static final Logger logger = Logger.getLogger(LocEndpoint.class.getName());

    private static final int DEFAULT_LIST_LIMIT = 20;

    static {
        // Typically you would register this inside an OfyServive wrapper. See: https://code.google.com/p/objectify-appengine/wiki/BestPractices
        ObjectifyService.register(Loc.class);
    }

    /**
     * Returns the {@link Loc} with the corresponding ID.
     *
     * @param id the ID of the entity to be retrieved
     * @return the entity with the corresponding ID
     * @throws NotFoundException if there is no {@code Loc} with the provided ID.
     */
    @ApiMethod(
            name = "get",
            path = "loc/{id}",
            httpMethod = ApiMethod.HttpMethod.GET)
    public Loc get(@Named("id") Long id) throws NotFoundException {
        logger.info("Getting Loc with ID: " + id);
        Loc loc = ofy().load().type(Loc.class).id(id).now();
        if (loc == null) {
            throw new NotFoundException("Could not find Loc with ID: " + id);
        }
        return loc;
    }

    /**
     * Inserts a new {@code Loc}.
     */
    @ApiMethod(
            name = "insert",
            path = "loc",
            httpMethod = ApiMethod.HttpMethod.POST)
    public Loc insert(Loc loc) {
        // Typically in a RESTful API a POST does not have a known ID (assuming the ID is used in the resource path).
        // You should validate that loc.id has not been set. If the ID type is not supported by the
        // Objectify ID generator, e.g. long or String, then you should generate the unique ID yourself prior to saving.
        //
        // If your client provides the ID then you should probably use PUT instead.
        ofy().save().entity(loc).now();
        logger.info("Created Loc with ID: " + loc.getId());

        return ofy().load().entity(loc).now();
    }

    /**
     * Updates an existing {@code Loc}.
     *
     * @param id  the ID of the entity to be updated
     * @param loc the desired state of the entity
     * @return the updated version of the entity
     * @throws NotFoundException if the {@code id} does not correspond to an existing
     *                           {@code Loc}
     */
    @ApiMethod(
            name = "update",
            path = "loc/{id}",
            httpMethod = ApiMethod.HttpMethod.PUT)
    public Loc update(@Named("id") Long id, Loc loc) throws NotFoundException {
        // TODO: You should validate your ID parameter against your resource's ID here.
        checkExists(id);
        ofy().save().entity(loc).now();
        logger.info("Updated Loc: " + loc);
        return ofy().load().entity(loc).now();
    }

    /**
     * Deletes the specified {@code Loc}.
     *
     * @param id the ID of the entity to delete
     * @throws NotFoundException if the {@code id} does not correspond to an existing
     *                           {@code Loc}
     */
    @ApiMethod(
            name = "remove",
            path = "loc/{id}",
            httpMethod = ApiMethod.HttpMethod.DELETE)
    public void remove(@Named("id") Long id) throws NotFoundException {
        checkExists(id);
        ofy().delete().type(Loc.class).id(id).now();
        logger.info("Deleted Loc with ID: " + id);
    }

    /**
     * List all entities.
     *
     * @param cursor used for pagination to determine which page to return
     * @param limit  the maximum number of entries to return
     * @return a response that encapsulates the result list and the next page token/cursor
     */
    @ApiMethod(
            name = "list",
            path = "loc",
            httpMethod = ApiMethod.HttpMethod.GET)
    public CollectionResponse<Loc> list(@Nullable @Named("cursor") String cursor, @Nullable @Named("limit") Integer limit) {
        limit = limit == null ? DEFAULT_LIST_LIMIT : limit;
        Query<Loc> query = ofy().load().type(Loc.class).limit(limit);
        if (cursor != null) {
            query = query.startAt(Cursor.fromWebSafeString(cursor));
        }
        QueryResultIterator<Loc> queryIterator = query.iterator();
        List<Loc> locList = new ArrayList<Loc>(limit);
        while (queryIterator.hasNext()) {
            locList.add(queryIterator.next());
        }
        return CollectionResponse.<Loc>builder().setItems(locList).setNextPageToken(queryIterator.getCursor().toWebSafeString()).build();
    }

    private void checkExists(Long id) throws NotFoundException {
        try {
            ofy().load().type(Loc.class).id(id).safe();
        } catch (com.googlecode.objectify.NotFoundException e) {
            throw new NotFoundException("Could not find Loc with ID: " + id);
        }
    }
}