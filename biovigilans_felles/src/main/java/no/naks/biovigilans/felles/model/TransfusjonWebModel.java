package no.naks.biovigilans.felles.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import no.naks.biovigilans.model.Blodprodukt;
import no.naks.biovigilans.model.BlodproduktImpl;
import no.naks.biovigilans.model.Hemolyse;
import no.naks.biovigilans.model.HemolyseImpl;
import no.naks.biovigilans.model.Komplikasjonsklassifikasjon;
import no.naks.biovigilans.model.KomplikasjonsklassifikasjonImpl;
import no.naks.biovigilans.model.Pasient;
import no.naks.biovigilans.model.PasientImpl;
import no.naks.biovigilans.model.Pasientkomplikasjon;
import no.naks.biovigilans.model.PasientkomplikasjonImpl;
import no.naks.biovigilans.model.Produktegenskap;
import no.naks.biovigilans.model.ProduktegenskapImpl;
import no.naks.biovigilans.model.Sykdom;
import no.naks.biovigilans.model.SykdomImpl;
import no.naks.biovigilans.model.Symptomer;
import no.naks.biovigilans.model.SymptomerImpl;
import no.naks.biovigilans.model.Tiltak;
import no.naks.biovigilans.model.Transfusjon;
import no.naks.biovigilans.model.TransfusjonImpl;
import no.naks.biovigilans.model.Utredning;
import no.naks.biovigilans.model.UtredningImpl;
import no.naks.biovigilans.model.Vigilansmelding;

/**
 * Denne klassen representerer Transfusjonopplysninger gitt av bruker.
 * Det omfatter Transfusjon, Blodprodukt, og Pasientkomplikasjon som definert i modellen
 * @author olj
 */
public class TransfusjonWebModel extends VigilansModel {


	private Blodprodukt blodProdukt;
	/**
	 * Database modellobjekter
	 */
	private Blodprodukt annenBlodprodukt;
	private Transfusjon transfusjon;
	private Pasientkomplikasjon pasientKomplikasjon;
	private Symptomer symptomer;
	private Komplikasjonsklassifikasjon komplikasjonsklassifikasjon;
	private Utredning utredning;
	private Hemolyse hemoLyse;
	private Produktegenskap produktEgenskap;
	private Produktegenskap tromboEgenskap;
	private Produktegenskap plasmaEgenskap;
	private Pasient pasient;
	private Vigilansmelding vigilansmelding;
	
	private List<String>hemolyseParametre; // Nedtrekk Hemolyseparametre når hemelyseparametre er positive
	private String[] hemolysparams;
	private Map<String,Blodprodukt> blodprodukter;
	private String[] plasmaEgenskaper;
	private List<Sykdom> sykdommer;
	
	public TransfusjonWebModel() {
		super();
	
		
	}

	public Pasient getPasient() {
		return pasient;
	}

	public void setPasient(Pasient pasient) {
		this.pasient = pasient;
	}

	/**
	 * createModelobjects
	 * Denne rutinen lager database modellobjekter.
	 * Den kalles dersom disse objektene ikke finnes fra  før (som i en oppfølgingsmelding)
	 */
	public void createModelobjects(){
		blodprodukter = new HashMap();
		blodProdukt = new BlodproduktImpl();
		
		annenBlodprodukt = new BlodproduktImpl();
		transfusjon = new TransfusjonImpl();
		pasientKomplikasjon = new PasientkomplikasjonImpl();
		vigilansmelding = (Vigilansmelding) pasientKomplikasjon;
		vigilansmelding.setKladd("nei");
		vigilansmelding.setSupplerendeopplysninger("ukjent");
		symptomer = new SymptomerImpl();
		komplikasjonsklassifikasjon = new KomplikasjonsklassifikasjonImpl();
		utredning = new UtredningImpl();
		hemolyseParametre = new ArrayList<String>();
		hemoLyse = new HemolyseImpl();
		produktEgenskap = new ProduktegenskapImpl("erytrocytt");
		tromboEgenskap = new ProduktegenskapImpl("trombocytt");

		
	}

	public List<Sykdom> getSykdommer() {
		return sykdommer;
	}



	public void setSykdommer(List<Sykdom> sykdommer) {
		this.sykdommer = sykdommer;
	}



	public Produktegenskap getProduktEgenskap() {
		return produktEgenskap;
	}



	public void setProduktEgenskap(Produktegenskap produktEgenskap) {
		this.produktEgenskap = produktEgenskap;
	}


	public Vigilansmelding getVigilansmelding() {
		return vigilansmelding;
	}



	public void setVigilansmelding(Vigilansmelding vigilansmelding) {
		this.vigilansmelding = vigilansmelding;
	}



	/**
	 * distributeTerms
	 * Denne rutinene fordeler felter fra skjermbildet til modellobjekter
	 * Feltene fra skjermbildet er bestemt fra name attributten i .html og definert i filen tables.properties.
	 * Rekkefølgen i formFields er bestemt fra plasseringen i applicationContext.xml under RapporterHendelseServerResourceHtml
	 */
	public void distributeTerms(){
		String[] formFields = getFormNames();
//		makeBlodprodukt();
		String[] blodProduktFields = {formFields[27],formFields[28],formFields[29],"pm",formFields[31],formFields[32],
				formFields[57],formFields[58],formFields[59],formFields[60],formFields[61],formFields[62]};  // formFields[30] (blod-plasma)  fjernet !!
		String[] egenskaperFields = {formFields[50],formFields[51],formFields[52],formFields[53],formFields[54],formFields[55],formFields[56],formFields[208],formFields[209]};
		String[] antallFields = {formFields[33],formFields[34],formFields[35],formFields[36],"a4",formFields[37],formFields[184],formFields[185],formFields[186]};
		String[] tromboEgenskaper = {formFields[236],formFields[237],formFields[238],formFields[239],formFields[240],formFields[241],formFields[242]};
		String[] plasmaEgenskaper = {formFields[243],formFields[244],formFields[245]};
		blodProdukt.setBlodProduktfieldMaps(blodProduktFields);
		blodProdukt.setEgenskaperfieldMaps(egenskaperFields);
		blodProdukt.setAntallfieldMaps(antallFields);
		blodProdukt.setKeyvalues();
	
		produktEgenskap.setEgenskaperfieldMaps(egenskaperFields);
		tromboEgenskap.setEgenskaperfieldMaps(tromboEgenskaper);
		plasmaEgenskap.setEgenskaperfieldMaps(plasmaEgenskaper);
		String[] transfusjonsFields = {formFields[18],formFields[19],formFields[20],formFields[21],formFields[22],formFields[23],formFields[24],formFields[25],formFields[26],
				formFields[148],formFields[149],formFields[150],formFields[151],formFields[152],formFields[153],formFields[154],formFields[155],formFields[156],formFields[157],
				formFields[158],formFields[159],formFields[160],formFields[161],formFields[162],formFields[163],formFields[164],formFields[165],formFields[166],
				formFields[167],formFields[168],formFields[169],formFields[170],formFields[171],formFields[172],formFields[173],formFields[174]};
		
		transfusjon.settransfusjonsFieldsMaps(transfusjonsFields);
		
		String[] annenblodProduktfields = {formFields[38],formFields[39],formFields[40],formFields[41],formFields[42],formFields[43],
				"p1","p2","p3","p4","p5","p6"};
		String[] annenEgenskaper = {"e1","e2","e3","e4","e5","e6","e7","e8","e9"};
		String[] annenblodAntallFields = {formFields[44],formFields[45],formFields[46],formFields[47],formFields[48],formFields[49],formFields[175],formFields[176],formFields[177]};
		annenBlodprodukt.setBlodProduktfieldMaps(annenblodProduktfields);
		annenBlodprodukt.setEgenskaperfieldMaps(annenEgenskaper);
		annenBlodprodukt.setAntallfieldMaps(annenblodAntallFields);
		annenBlodprodukt.setKeyvalues();
		String[] pasientKomplikasjonsfields = {formFields[129],formFields[130],formFields[131],formFields[132],formFields[133],formFields[134],
				formFields[135],formFields[136],formFields[137],formFields[138],formFields[139],formFields[140],formFields[141],formFields[142],
				formFields[91],formFields[92],formFields[93],formFields[94],formFields[95],formFields[96],
				formFields[97],formFields[98],formFields[99],formFields[100],formFields[101],formFields[102],formFields[103],formFields[104],
				formFields[105],formFields[106],formFields[107],formFields[108],formFields[109],formFields[110],formFields[111],formFields[112],formFields[113],
				formFields[180],formFields[181],formFields[182],formFields[183],formFields[347],formFields[348]};
		pasientKomplikasjon.setPatientkomplicationfieldMaps(pasientKomplikasjonsfields);
		String[] symptomerFields = {formFields[65],formFields[66],formFields[67],formFields[68],formFields[69],formFields[70],formFields[71],
				formFields[72],formFields[73],formFields[74],formFields[75],formFields[76],formFields[77],formFields[78],formFields[79],formFields[80],
				formFields[81],formFields[82],formFields[83],formFields[84],formFields[85],formFields[86],formFields[87],formFields[88],
				formFields[89],formFields[90],formFields[178],formFields[179]};
		symptomer.setsymptomerfieldMaps(symptomerFields);
		String[] klassifikasjonsFields = {formFields[210],formFields[211],formFields[212],formFields[213],formFields[214],formFields[215],
				formFields[216],formFields[217],formFields[218],formFields[219],formFields[220],formFields[221],formFields[222],formFields[223],
				formFields[224],formFields[225],formFields[226],formFields[227],formFields[228],formFields[229],formFields[230],formFields[231],formFields[232],
				formFields[233],formFields[234],formFields[235]};
		komplikasjonsklassifikasjon.setkomplikasjonklassifikasjonFieldsMaps(klassifikasjonsFields);
	/*	
		String[] utredningFields = {formFields[114],formFields[115],formFields[116],formFields[117],formFields[118],formFields[119],formFields[120],
				formFields[121],formFields[122],formFields[123],formFields[124],formFields[125],formFields[126],formFields[127],formFields[128]};
	*/	
		String[] utredningFields = {formFields[246],formFields[247],formFields[248],formFields[249],formFields[250],formFields[251],formFields[252],
				formFields[253],formFields[254],formFields[255],formFields[256],formFields[257],formFields[258],formFields[259],formFields[260],formFields[261],
				formFields[262],formFields[263],formFields[264],formFields[265],formFields[266],formFields[267],formFields[268],formFields[269],formFields[270],
				formFields[271],formFields[272],formFields[273],formFields[274],formFields[275],formFields[276],formFields[277],formFields[278],formFields[279],
				formFields[280],formFields[281],formFields[282],formFields[283],formFields[284],formFields[285],formFields[286],formFields[287],formFields[288],
				formFields[289],formFields[290],formFields[291],formFields[292],formFields[293],formFields[294],formFields[295],formFields[296],formFields[297],
				formFields[298],formFields[299],formFields[300],formFields[301],formFields[302],formFields[303],formFields[304],formFields[305],formFields[306],
				formFields[307],formFields[308],formFields[309],formFields[310],formFields[311],formFields[312],formFields[313],formFields[314],formFields[315],
				formFields[316],formFields[317],formFields[318],formFields[319],formFields[320],formFields[321],formFields[322],formFields[323],formFields[324],
				formFields[325],formFields[326],formFields[327],formFields[328],formFields[329],formFields[330],formFields[331],formFields[332],formFields[333],
				formFields[334],formFields[335],formFields[336],formFields[337],formFields[338],formFields[339],formFields[340],formFields[341],formFields[342],
				formFields[343],formFields[344],formFields[345],formFields[346],formFields[349]};
		utredning.setutredningfieldMaps(utredningFields);
		String[] hemoLysefields = {formFields[187],formFields[188],formFields[189],formFields[190],formFields[191]};
//		utredning.addFields(hemoLysefields);
		hemoLyse.setHemlysefieldMaps(hemoLysefields);
	}

	/**
	 * saveValues
	 * Denne rutinen lagrer feltverdier for transfusjons(pasient)komplikasjoner som er angitt av bruker 
	 */
	public void saveValues() {
		String[] formFields = getFormNames(); // Inneholder navn på input felt i skjermbildet
		Map<String,String> userEntries = getFormMap(); // formMap inneholder verdier angitt av bruker
		pasientKomplikasjon.setKomplikasjonsFields(userEntries);
		vigilansmelding.setVigilansFields(userEntries); // OBS !! sjekk OLJ 26.05.15
		for (String field : formFields){
			String userEntry = userEntries.get(field);
		//	System.out.println("Key: "+ field);
			if (userEntry != null && !userEntry.equals(""))
				System.out.println("Key: "+ field + " Innhold = " + userEntry);
			
			annenBlodprodukt.saveField(field, userEntry);
			blodProdukt.saveField(field, userEntry);
			transfusjon.saveField(field, userEntry);
			pasientKomplikasjon.saveField(field, userEntry);
			symptomer.saveField(field, userEntry);
			komplikasjonsklassifikasjon.savetoField(field, userEntry);
			utredning.saveField(field, userEntry);
			hemoLyse.saveField(field, userEntry);
			produktEgenskap.saveField(field, userEntry);
			tromboEgenskap.saveField(field, userEntry);
			plasmaEgenskap.saveField(field, userEntry);
		}

//		annenBlodprodukt.saveToBlodprodukt();
//		blodProdukt.produceProduktegenskaper(produktEgenskap);
		transfusjon.savetoTransfusjon();
/*		
		transfusjon.setHastegrad(null);
		transfusjon.setIndikasjon(null);
		transfusjon.setTransDato(null);
//		transfusjon.setTransfusjonsklokkeslett(null); Fjernet fra skjema olj 10.02.15
		transfusjon.setTildigerKomplikasjon(null);
*/
/*	
		if (annenBlodprodukt.getBlodprodukt() != null && !annenBlodprodukt.getBlodprodukt().equals(""))
			transfusjon.getBlodProdukter().put(annenBlodprodukt.getBlodprodukt(),annenBlodprodukt );
*/		
		transfusjon.produceBlodprodukt(blodProdukt);
		transfusjon.produceProduktegenskaper(produktEgenskap);
		transfusjon.produceProduktegenskaper(tromboEgenskap);
		transfusjon.produceProduktegenskaper(plasmaEgenskap);
		transfusjon.produceBlodprodukt(annenBlodprodukt);
		vigilansmelding.saveToVigilansmelding(); // Lagt til 23.09.16 - supplerende opplysninger mangler ved oppfølging
		pasientKomplikasjon.savetoPasientkomplikasjon();
		Vigilansmelding melding = (Vigilansmelding)pasientKomplikasjon;
		melding.setSupplerendeopplysninger(vigilansmelding.getSupplerendeopplysninger()); //// Lagt til 23.09.16 - supplerende opplysninger mangler ved oppfølging
/*		
		pasientKomplikasjon.setAlvorlighetsgrad(null);
		pasientKomplikasjon.setKliniskresultat(null);
		pasientKomplikasjon.setArsakssammenheng(null);
		pasientKomplikasjon.setKlassifikasjon(null);
		pasientKomplikasjon.setKladd(null);
*/		
		pasientKomplikasjon.produceSymptoms(symptomer);
		pasientKomplikasjon.getKomplikasjonsKlassifikasjoner().put("klassifikasjon",komplikasjonsklassifikasjon);
		pasientKomplikasjon.produceClassification(komplikasjonsklassifikasjon);
	//	utredning.saveUtredning();			Bruk av utredning er endret etter endring i transfusjonsskjema olj 22.01.15
	//	utredning.produceHemolyse(hemoLyse);
		pasientKomplikasjon.setUtredning(utredning);
		transfusjon.getPasientKomplikasjoner().put(pasientKomplikasjon.getAlvorlighetsgrad(), pasientKomplikasjon);
		
	}


	public String[] getPlasmaEgenskaper() {
		return plasmaEgenskaper;
	}



	public void setPlasmaEgenskaper(String[] plasmaEgenskaper) {
		this.plasmaEgenskaper = plasmaEgenskaper;
		plasmaEgenskap = new ProduktegenskapImpl(plasmaEgenskaper);
	}



	public List<String> getHemolyseParametre() {
		return hemolyseParametre;
	}


	public void setHemolyseParametre(List<String> hemolyseParametre) {
		this.hemolyseParametre = hemolyseParametre;
	}
	
	public String[] getHemolysparams() {
		return hemolysparams;
	}


	public void setHemolysparams(String[] hemolysparams) {
		this.hemolysparams = hemolysparams;
	}


	/**
	 * setHemolyseparams
	 * Denne rutnen setter opp nedtrekk for hemolyseparametre
	 * @param hemoparams - En rekke strengvariable som inneholder hemolyseparametre
	 * 
	 */
	public void setHemolyseparams(String[] hemoparams){
		this.hemolysparams = hemoparams;
		for (String hemo : hemoparams){
			this.hemolyseParametre.add(hemo);
		
		}
	}	

	public Transfusjon getTransfusjon() {
		return transfusjon;
	}


	public void setTransfusjon(Transfusjon transfusjon) {
		this.transfusjon = transfusjon;
	}


	public Blodprodukt getBlodProdukt() {
		return blodProdukt;
	}


	public void setBlodProdukt(Blodprodukt blodProdukt) {
		this.blodProdukt = blodProdukt;
	}


	public Blodprodukt getAnnenBlodprodukt() {
		return annenBlodprodukt;
	}


	public void setAnnenBlodprodukt(Blodprodukt annenBlodprodukt) {
		this.annenBlodprodukt = annenBlodprodukt;
	}


	public Pasientkomplikasjon getPasientKomplikasjon() {
		return pasientKomplikasjon;
	}


	public void setPasientKomplikasjon(Pasientkomplikasjon pasientKomplikasjon) {
		this.pasientKomplikasjon = pasientKomplikasjon;
	}


	public Symptomer getSymptomer() {
		return symptomer;
	}


	public void setSymptomer(Symptomer symptomer) {
		this.symptomer = symptomer;
	}


	public Komplikasjonsklassifikasjon getKomplikasjonsklassifikasjon() {
		return komplikasjonsklassifikasjon;
	}


	public void setKomplikasjonsklassifikasjon(
			Komplikasjonsklassifikasjon komplikasjonsklassifikasjon) {
		this.komplikasjonsklassifikasjon = komplikasjonsklassifikasjon;
	}


	public Utredning getUtredning() {
		return utredning;
	}


	public void setUtredning(Utredning utredning) {
		this.utredning = utredning;
	}



		
	/**
	 * setTransfusjonsFlag
	 * Denne rutinen setter opp ulike feltverdier for TransfusjonKvitteringWebModel.
	 * Dette er blodproduktverdier, symptomer, og hemolyseverdier
	 * @param kvittering
	 */
	public void setTransfusjonsFlag(TransfusjonKvitteringWebModel kvittering){
		String blodProd = blodProdukt.getBlodprodukt();
		if (blodProd != null && blodProd.equals("p-blod-trauma")){
			kvittering.setDisplayPakker("block");
		}
		String annenBlod = annenBlodprodukt.getBlodprodukt();
		if (annenBlod != null && annenBlod.equals("p-annenblod-trauma")){
			kvittering.setDisplayandrePakker("block");
		}
		Iterator symptomIterator = pasientKomplikasjon.getSymptomer().keySet().iterator();
		while (symptomIterator.hasNext()){
			String key = (String)symptomIterator.next();
			Symptomer symptomer  = (Symptomer)pasientKomplikasjon.getSymptomer().get(key);
			if (symptomer.getTempFor() > 0 && symptomer.getTempetter() > 0){
				kvittering.setDisplayTemperatur("block");
			}
			
		}
	
		Iterator utredIterator = utredning.getHemolyseAnalyser().keySet().iterator();
		
		while (utredIterator.hasNext()){
			String key = (String)utredIterator.next();
			Hemolyse hemolyseParam = (Hemolyse)utredning.getHemolyseAnalyser().get(key);
			String hemo = hemolyseParam.getHemolyseParameter();
			kvittering.getHemolyseParams().add(hemo);
			
		}
		kvittering.convertHemolyse();
	}
}
