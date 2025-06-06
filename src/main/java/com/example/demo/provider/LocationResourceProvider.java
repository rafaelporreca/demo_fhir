package com.example.demo.provider;

import ca.uhn.fhir.jpa.api.dao.IFhirResourceDao;
import ca.uhn.fhir.rest.annotation.Create;
import ca.uhn.fhir.rest.annotation.ResourceParam;
import ca.uhn.fhir.rest.api.MethodOutcome;
import ca.uhn.fhir.rest.server.IResourceProvider;
import org.hl7.fhir.instance.model.api.IBaseResource;
import org.hl7.fhir.r5.model.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


public class LocationResourceProvider implements IResourceProvider {

    private final IFhirResourceDao<Location> locationDao;

    // O DAO agora é passado via construtor
    public LocationResourceProvider(IFhirResourceDao<Location> locationDao) {
        this.locationDao = locationDao;
    }

    @Override
    public Class<? extends IBaseResource> getResourceType() {
        return Location.class;
    }

    @Create
    public MethodOutcome create(@ResourceParam Location theLocation) {
        return locationDao.create(theLocation);
    }
}
