package no.naks.biovigilans_admin.web.client;

import no.naks.biovigilans_admin.web.model.Innmelding;

import org.restlet.data.MediaType;
import org.restlet.representation.ObjectRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.ResourceException;

public class InnmeldingClient {
	  public static void main(String[] args) throws Exception {
		  Representation mr = null;
		  Innmelding innmelding = null;
		  InnmeldingClientResource innmeldingResource = new InnmeldingClientResource();
		  try
		  {
			  mr = innmeldingResource.getInnmeldingClient().get();
		  }catch(ResourceException re){
	        	System.out.println(re.getMessage());
	      }
		  if (mr != null){
//			  mr.setMediaType(MediaType.APPLICATION_JAVA_OBJECT_XML);
			  innmelding = new ObjectRepresentation<Innmelding>(mr,innmeldingResource.getInnmeldingClient().getClass().getClassLoader()).getObject();
		  }
		  if (innmelding != null){
			     System.out.println("Text: " + innmelding.getTittel());
		  }
	  }
}
