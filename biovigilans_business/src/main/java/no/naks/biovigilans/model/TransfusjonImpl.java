package no.naks.biovigilans.model;

import java.sql.Time;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.ibm.icu.util.Calendar;


public class TransfusjonImpl extends AbstractTransfusjon implements Transfusjon {
	private List<Blodprodukt>  produktListe=null;
	
	public TransfusjonImpl() {
		super();
		types = new int[] {Types.DATE,Types.TIME,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.INTEGER,Types.VARCHAR,Types.INTEGER};
		utypes = new int[] {Types.DATE,Types.TIME,Types.VARCHAR,Types.VARCHAR,Types.VARCHAR,Types.INTEGER,Types.VARCHAR,Types.INTEGER,Types.INTEGER};
		
		transfusjonsFields = new HashMap();
		pasientKomplikasjoner = new HashMap();
		blodProdukter = new HashMap();
		produktListe = new ArrayList();
	}
	
	public void setParams(){
		Long id = getTransfusjonsId();
		if (id == null){
			params = new Object[]{getTransfusionDate(),getTransklokke(),getHastegrad(),getFeiltranfudert(),getIndikasjon(),getAntalenheter(),getTildigerKomplikasjon(),getPasient_Id()};
		}else
			params = new Object[]{getTransfusionDate(),getTransklokke(),getHastegrad(),getFeiltranfudert(),getIndikasjon(),getAntalenheter(),getTildigerKomplikasjon(),getPasient_Id(),getTransfusjonsId()};
	

	}	
	/**
	 * settransfusjonsFieldsMaps
	 * Denne rutinen setter opp hvilke skjermbildefelter som hører til hvilke databasefelter
	 * @param userFields En liste over skjermbildefelter
	 */
	
	public void settransfusjonsFieldsMaps(String[]userFields){

		keys = userFields;
		for (int i = 0;i<36;i++){
			transfusjonsFields.put(userFields[i],null);
		}
		
	

	}	
	
	public List<Blodprodukt> getProduktListe() {
		return produktListe;
	}

	public void setProduktListe(List<Blodprodukt> produktListe) {
		this.produktListe = produktListe;
	}

	/**
	 * saveField
	 * Denne rutinen lagrer skjermbildefelter til riktig databasefelt
	 * @param userField
	 * @param userValue
	 */
	
	public void saveField(String userField,String userValue){
		
		if (transfusjonsFields.containsKey(userField) && userValue != null && !userValue.equals("")){
			transfusjonsFields.put(userField,userValue);	
	
		}
	}

	/**
	 * savetoTransfusjon()
	 * Denne rutinen lagrer verdier fra skjermbildet til riktig felt
	 * @since 05.04.16
	 * Lagrer også verdier i feltet feiltranfudert
	 * For hemovigilans : Tidligere reaksjoner blir også satt i feiltranfudert
	 */
	public void savetoTransfusjon(){
		if (getTransfusjonsId() == null){
			setHastegrad(null);
			setIndikasjon(null);
			setTransDato(null);
//			transfusjon.setTransfusjonsklokkeslett(null); Fjernet fra skjema olj 10.02.15
			setTildigerKomplikasjon(null);
			setFeiltranfudert(null); // OBS Rekkefølgen på disse to kall er viktig !! 
		}
		if (getTransfusjonsId() != null){
			String hastegrad = new String(""); // Hastegrad fjernet fra organer
			if (getHastegrad() != null)
				hastegrad = new String (getHastegrad());
			String indikasjon = new String(getIndikasjon());
			String tidligere = new String(getTildigerKomplikasjon());
			Date transDato = getTransfusionDate();
			String feiltrans = getFeiltranfudert();
			if (transDato != null){
				SimpleDateFormat df = new SimpleDateFormat( "yyyy-MM-dd" );
				setTransDato(df.format(transDato));
			}
			String transdato = null;
			if (getTransDato() == null || getTransDato().isEmpty()){
				transdato = new String(getTransDato()); 
			}
			
			setHastegrad(null);
			setIndikasjon(null);
			setTransDato(null);
//			transfusjon.setTransfusjonsklokkeslett(null); Fjernet fra skjema olj 10.02.15
			setTildigerKomplikasjon(null);
			if (getHastegrad() == null || getHastegrad().isEmpty())
				setHastegrad(hastegrad);
			if (getIndikasjon() == null || getIndikasjon().isEmpty())
				setIndikasjon(indikasjon);
			if (getTildigerKomplikasjon() == null || getTildigerKomplikasjon().isEmpty())
				setTildigerKomplikasjon(tidligere);
			if (getTransDato() == null ||  getTransDato().isEmpty()) 
				setTransDato(transdato);
			if (getFeiltranfudert() == null || getFeiltranfudert().isEmpty())
				setFeiltranfudert(feiltrans);
		}
	}
	/**
	 * produceBlodprodukt
	 * Denne rutinen lager korrekt antall blodprodukter etter hvor mange bruker har oppgitt
	 * @param hemolyse
	 */
	public void produceBlodprodukt(Blodprodukt blodprodukt) {
		Blodprodukt lokalBlodprodukt = null;
		
		
		for (String produkt : blodprodukt.getBlodproduktFields().values() ){
			
			if (produkt != null && !produkt.equals("")){
				for (String produktnavn : blodprodukt.getProdukter()){
					if (produkt.equals(produktnavn)){
						lokalBlodprodukt = new BlodproduktImpl();
						lokalBlodprodukt.setBlodprodukt(produkt);
					
						break;
					}
				}
				if (lokalBlodprodukt == null){
					for (String produktnavn : blodprodukt.getPlasmaProdukter()){
						if (produkt.equals(produktnavn)){
							lokalBlodprodukt = new BlodproduktImpl();
							lokalBlodprodukt.setBlodprodukt(produkt);
							break;
						}
					}
				}
				if (lokalBlodprodukt == null){
					String produktvalue = (String) blodprodukt.getBlodproduktFields().get(blodprodukt.getUserFields().get(5)); // Userfield index 5 er annet blodprodukt !!
					if (produktvalue != null && !produktvalue.equals("")){
						lokalBlodprodukt = new BlodproduktImpl();
						lokalBlodprodukt.setBlodprodukt(produktvalue);
						
					}
				}
//				lokalBlodprodukt.saveToBlodprodukt();
				if (lokalBlodprodukt != null)
					getBlodProdukter().put(lokalBlodprodukt.getBlodprodukt(), lokalBlodprodukt);
			
				lokalBlodprodukt = null;
				
			}
			
			
		}
		SetblodProductValues(blodprodukt);
		
	
	}
	/* 
	 * produceProduktegenskaper
	 * Denne rutinen setter alle produktegenskapene til riktig blodprodukt
	 */
	public void produceProduktegenskaper(Produktegenskap egenskap){
		Produktegenskap lokalEgenskap = null;
		boolean noTemp = false;
		// Så lenge det finnes produktegenskaper:
		for (String produkt : egenskap.getProduktegenskapFields().values() ){
			if (produkt != null && !produkt.equals("")){
				lokalEgenskap = new ProduktegenskapImpl();
				lokalEgenskap.distributeValues(produkt);
	//			Plasser egenskapene til riktig blodprodukt:
				Iterator blodIterator = getBlodProdukter().keySet().iterator();
				while (blodIterator.hasNext()){
					String key = (String) blodIterator.next();
					boolean plasma = true;
					Blodprodukt lokalBlodprodukt = (Blodprodukt)getBlodProdukter().get(key);
					if (lokalBlodprodukt.getBlodprodukt().equals(egenskap.getEgenskapType())){
						plasma = false;
						lokalBlodprodukt.getProduktEgenskaper().put(produkt,lokalEgenskap);
					}
					if (plasma){
						if (egenskap.getEgenskapTyper() != null){
							for (String egenskapType : egenskap.getEgenskapTyper()){
								if (lokalBlodprodukt.getBlodprodukt().equals(egenskapType)){
									lokalBlodprodukt.getProduktEgenskaper().put(produkt,lokalEgenskap);
								}
							}
						}
					}
				}
			}
		}
	}

	/**
	 * SetblodProductValues
	 * @param blodprodukt
	 * Denne rutinen setter alle verdiene til riktig blodprodukt
	 */
	private void SetblodProductValues(Blodprodukt blodprodukt){
		Iterator blodIterator = getBlodProdukter().keySet().iterator();
		while (blodIterator.hasNext()){
			String key = (String) blodIterator.next();
			Blodprodukt lokalBlodprodukt = (Blodprodukt)getBlodProdukter().get(key);
			lokalBlodprodukt.setAntallFields(blodprodukt.getAntallFields());
			lokalBlodprodukt.setUserFields(blodprodukt.getUserFields());
			lokalBlodprodukt.setKeyvalues();
			if (lokalBlodprodukt.getBlodprodukt().equals("trombocytt") || lokalBlodprodukt.getBlodprodukt().equals("erytrocytt") ){  // OLJ 26.11.16 -> OLJ 23.03.15
				for (String produkt : blodprodukt.getBlodproduktFields().values() ){
					if (produkt != null && !produkt.equals("")){
						chooseTapping(lokalBlodprodukt, produkt);
						chooseSuspensjon(lokalBlodprodukt, produkt);
					}
				}
			}

			lokalBlodprodukt.setAntallkeyProdukt();
			if (lokalBlodprodukt.getAntallEnheter() < 0)
				lokalBlodprodukt.setAntallEnheter(-1);
			produktListe.add(lokalBlodprodukt);
		}
	}
	private void chooseTapping(Blodprodukt blodprodukt,String egenskap){
		for (String tapping : blodprodukt.getTapping()){
			if (tapping.equals(egenskap)){
				blodprodukt.setTappetype(egenskap);
			}
		}
	}
	private void chooseSuspensjon(Blodprodukt blodprodukt,String suspensjon){
		for (String susp : blodprodukt.getSuspensjonsValg()){
			if (susp.equals(suspensjon)){
				blodprodukt.setSuspensjon(suspensjon);
			}
		}
	}
	
}
