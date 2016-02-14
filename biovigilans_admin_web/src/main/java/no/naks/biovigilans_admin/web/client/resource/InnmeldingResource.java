package no.naks.biovigilans_admin.web.client.resource;

import no.naks.biovigilans_admin.web.model.Innmelding;

import org.restlet.resource.Delete;
import org.restlet.resource.Get;
import org.restlet.resource.Put;

/**
 * @author olj
 * Dette Interface fungerer som et grensesnitt for en delegate som pakkes (wrap)
 * til en Restlet ClientResource.
 * Se klassen InnmeldingClientResource
 */
public interface InnmeldingResource {

    @Get
    public Innmelding represent() throws Exception;
    @Put
    public Innmelding store(Innmelding bean) throws Exception;
    @Delete
    public void remove() throws Exception;

}
