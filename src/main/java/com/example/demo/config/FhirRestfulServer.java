package com.example.demo.config;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.jpa.api.config.JpaStorageSettings;
import ca.uhn.fhir.jpa.api.dao.DaoRegistry;
import ca.uhn.fhir.jpa.api.dao.IFhirResourceDao;
import ca.uhn.fhir.jpa.search.DatabaseBackedPagingProvider;
import ca.uhn.fhir.rest.server.ETagSupportEnum;
import ca.uhn.fhir.rest.server.IResourceProvider;
import ca.uhn.fhir.rest.server.RestfulServer;
import ca.uhn.fhir.rest.server.interceptor.ResponseHighlighterInterceptor;
import com.example.demo.provider.LocationResourceProvider;
import jakarta.servlet.annotation.WebServlet;
import org.hl7.fhir.r5.model.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@WebServlet(urlPatterns = "/*", displayName = "Fhir Restful Server")
@Configuration
public class FhirRestfulServer extends RestfulServer {

    @Autowired
    private DaoRegistry daoRegistry;

    @Autowired
    private JpaStorageSettings jpaStorageSettings;

    @Autowired
    private DatabaseBackedPagingProvider databaseBackedPagingProvider;

    @Override
    protected void initialize() {
        setFhirContext(FhirContext.forR5());

        List<IResourceProvider> providers = new ArrayList<>();

        // Obtenha o DAO de Location do DaoRegistry
        IFhirResourceDao<Location> locationDao = daoRegistry.getResourceDao(Location.class);
        // Crie e adicione seu LocationResourceProvider, passando o DAO
        providers.add(new LocationResourceProvider(locationDao));

        // Remova a linha abaixo:
        // providers.add(new JpaSystemProviderR5(this, daoRegistry, jpaStorageSettings, databaseBackedPagingProvider));

        setResourceProviders(providers);

        // O próprio RestfulServer, quando configurado com DaoRegistry,
        // gerencia automaticamente as operações de sistema (ex: /metadata).
        // Não é necessário um provedor de sistema explícito aqui.

        // Configurações opcionais do servidor
        setETagSupport(ETagSupportEnum.ENABLED);
        registerInterceptor(new ResponseHighlighterInterceptor()); // Útil para desenvolvimento
    }
}
