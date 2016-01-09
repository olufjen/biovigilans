package no.naks.biovigilans_admin.web.client;

import org.restlet.resource.ClientResource;
import org.restlet.data.MediaType;

import no.naks.biovigilans_admin.web.client.resource.InnmeldingResource;
import no.naks.biovigilans_admin.web.model.Innmelding;

public class InnmeldingClientResource implements InnmeldingResource {
	private InnmeldingResource delegate;
	private  ClientResource innmeldingClient;
	
	public InnmeldingClientResource() {
		super();
        innmeldingClient = new ClientResource(
                "http://localhost:8080/fagprosedyrer_web/restapi/innmelding/chunkylover53/mails/123");
//        innmeldingClient.accept(MediaType.APPLICATION_JSON);
//        innmeldingClient.accept(MediaType.APPLICATION_JAVA_OBJECT);
        innmeldingClient.accept(MediaType.APPLICATION_JAVA_OBJECT_XML);
        this.delegate = innmeldingClient.wrap(InnmeldingResource.class);
        this.delegate.toString();
	}

	@Override
	public Innmelding represent() throws Exception {
		// TODO Auto-generated method stub
		 return delegate.represent();
	}

	@Override
	public Innmelding store(Innmelding bean) throws Exception {
		// TODO Auto-generated method stub
		 return delegate.store(bean);
	}

	@Override
	public void remove() throws Exception {
		// TODO Auto-generated method stub
		 delegate.remove();
	}

	public InnmeldingResource getDelegate() {
		return delegate;
	}

	public void setDelegate(InnmeldingResource delegate) {
		this.delegate = delegate;
	}

	public ClientResource getInnmeldingClient() {
		return innmeldingClient;
	}

	public void setInnmeldingClient(ClientResource innmeldingClient) {
		this.innmeldingClient = innmeldingClient;
	}

}
