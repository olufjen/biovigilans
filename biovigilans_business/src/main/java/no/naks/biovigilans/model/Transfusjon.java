package no.naks.biovigilans.model;

import java.sql.Time;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;

public interface Transfusjon {

		
	public DateTime getTransfusjondato();

	public void setTransfusjondato(DateTime transfusjondato);

	public Date getTransfusionDate();

	public void setTransfusionDate(Date transfusionDate);
	public String getTransfusjonsklokkeslett();

	public void setTransfusjonsklokkeslett(String transfusjonsklokkeslett);

	public String getHastegrad();

	public void setHastegrad(String hastegrad);

	public String getFeiltranfudert();

	public void setFeiltranfudert(String feiltranfudert);

	public String getIndikasjon();

	public void setIndikasjon(String indikasjon);

	public int getAntalenheter();

	public void setAntalenheter(int antalenheter);

	public Map<String, Pasientkomplikasjon> getPasientKomplikasjoner();

	public void setPasientKomplikasjoner(
			Map<String, Pasientkomplikasjon> pasientKomplikasjoner);

	public Map<String, String> getTransfusjonsFields();

	public void setTransfusjonsFields(Map<String, String> transfusjonsFields);

	public String[] getKeys();

	public void setKeys(String[] keys);
	public void settransfusjonsFieldsMaps(String[]userFields);
	

	public Map<String, Blodprodukt> getBlodProdukter();

	public void setBlodProdukter(Map<String, Blodprodukt> blodProdukter);
	public void saveField(String userField,String userValue);

	public String getTildigerKomplikasjon();

	public void setTildigerKomplikasjon(String tildigerKomplikasjon);
	public String getTransDato();
	public void produceProduktegenskaper(Produktegenskap produktegenskap);
	public void setTransDato(String transDato);

	public Time getTransklokke();
	public void savetoTransfusjon();
	public void setTransklokke(Time transklokke);
	public Long getPasient_Id();
	public Long getTransfusjonsId();
	public void setTransfusjonsId(Long transfusjonsId);
	public void setPasient_Id(Long pasient_Id);
	public void produceBlodprodukt(Blodprodukt blodprodukt);
	public void setParams();
	public int[] getTypes();
	public Object[] getParams();
	public int[] getUtypes();
		
	public List<Blodprodukt> getProduktListe();

	public void setProduktListe(List<Blodprodukt> produktListe);
}
