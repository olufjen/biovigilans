package no.naks.biovigilans.felles.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author olj
 *
 * Denne klassen benyttes til åvise en kvitteringsside for hva melder har registrert fra en transfusjonskomplikasjon
 * 
 */
public class TransfusjonKvitteringWebModel extends VigilansModel {

	private String sykdomBehandling;
	private String sykdomBetydning;
	private String avdeling;
	private String tidTransfusjon;
	private String datoTransfusjon;
	private String indikasjonTransfusjon;
	private String kjonn;
	private String mann = "unchecked";
	private String kvinne = "x";
	
	private String antallPakker = "";
	private String blodprodukt = "";
	private String annetBlodprodukt = "";
	private String annetblodAntall = "";
	
	private String annenAntallerytrocyt = "";
	private String annenAntalltrombo = "";
	private String annenAntallplasma = "";
	
	private String hastegrad = "";
	private String pasientType = "";
	private String tidligere = "";
	private String displaytidligereTrans = "block";
	private String tapping = "Ikke angitt";
	private String suspensjon = "Ikke angitt";
	private String plasma = "Ikke angitt";
	private String erytrocytipakke = "";
	private String tromboipakke = "";
	private String plasmaipakke = "";
	private String traumetransfusjon = "";
	private String egenskapKriseblod = "";
	private String egenskapVasket = "";
	private String egenskapBestralt = "";
	private String egenskapHla = "";
	private String egenskapPatogen = "";
	private String egenskapblodvarmer = "";
	private String displayPlasma = "none";
	private String displayPakker = "none";
	private String displayandrePakker = "none";
	private String displayTemperatur = "none";
	private String klassifikasjon = "";
	private String temperaturFor = "";
	private String temperaturEtter = "";
	private String tempendring = "";
	private String[] antistoff = {"","","","","","","","",""};
	private String[] hemolyse;
	private List<String>hemolyseParams;
	private String poseDyrkning = "";
	private String alvorlighetsGrad = "";
	private String kliniskRes = "";
	private String arsak = "";

	private String[]  symptomer = {"","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","",""};
	
	
	private String[] formFields;
	
	
	
	public TransfusjonKvitteringWebModel(String[] sessionParams) {
		super();
		setFormNames(sessionParams);
		formFields = getFormNames();
		hemolyseParams = new ArrayList<String>();

	}


	public String getDisplayandrePakker() {
		return displayandrePakker;
	}


	public void setDisplayandrePakker(String displayandrePakker) {
		this.displayandrePakker = displayandrePakker;
	}


	public String getDisplayTemperatur() {
		return displayTemperatur;
	}


	public void setDisplayTemperatur(String displayTemperatur) {
		this.displayTemperatur = displayTemperatur;
	}


	public String getDisplayPlasma() {
		return displayPlasma;
	}


	public void setDisplayPlasma(String displayPlasma) {
		this.displayPlasma = displayPlasma;
	}

	

	public String getDisplayPakker() {
		return displayPakker;
	}


	public void setDisplayPakker(String displayPakker) {
		this.displayPakker = displayPakker;
	}

	public void convertHemolyse(){
		hemolyse = new String[hemolyseParams.size()];
		hemolyse = hemolyseParams.toArray(hemolyse);
	}
	public String[] getHemolyse() {
		return hemolyse;
	}


	public void setHemolyse(String[] hemolyse) {
		this.hemolyse = hemolyse;
	}


	public List<String> getHemolyseParams() {
		return hemolyseParams;
	}


	public void setHemolyseParams(List<String> hemolyseParams) {
		this.hemolyseParams = hemolyseParams;
	}


	public String getAlvorlighetsGrad() {
		Map<String,String> userEntries = getFormMap();
		String[] alvorFields = {formFields[129],formFields[130],formFields[131],formFields[132],formFields[133]};
		for (String field : alvorFields){
			String value =  userEntries.get(field);
			if (value != null && !value.equals("")){
				alvorlighetsGrad = value;
				break;
			}
		}
		return alvorlighetsGrad;
	}


	public void setAlvorlighetsGrad(String alvorlighetsGrad) {
			this.alvorlighetsGrad = alvorlighetsGrad;
	}


	public String getKliniskRes() {
		Map<String,String> userEntries = getFormMap();
		String[] kliniskFields = {formFields[134],
				formFields[135],formFields[136],formFields[137],formFields[138]};
		for (String field : kliniskFields){
			String value =  userEntries.get(field);
			if (value != null && !value.equals("")){
				kliniskRes = value;
				break;
			}
		}
		
		return kliniskRes;
	}


	public void setKliniskRes(String kliniskRes) {
		this.kliniskRes = kliniskRes;
	}


	public String getArsak() {
		Map<String,String> userEntries = getFormMap();
		String[] arsakFields = {formFields[139],formFields[140],formFields[141],formFields[142]};
		for (String field : arsakFields){
			String value =  userEntries.get(field);
			if (value != null && !value.equals("")){
				arsak = value;
				break;
			}
		}
		
		return arsak;
	}


	public void setArsak(String arsak) {
		this.arsak = arsak;
	}


	public String getPoseDyrkning() {
		Map<String,String> userEntries = getFormMap();
		String[] poseFields = {formFields[127],formFields[128]};
		for (String field : poseFields){
			String value =  userEntries.get(field);
			if (value != null && !value.equals("")){
				poseDyrkning = poseDyrkning + " "+ value;
			}
		}
		return poseDyrkning;
	}


	public void setPoseDyrkning(String poseDyrkning) {
		this.poseDyrkning = poseDyrkning;
	}


	public String[] getAntistoff() {
		Map<String,String> userEntries = getFormMap();
		String[] antistoffFields = {formFields[5],formFields[6],formFields[7],formFields[8],formFields[143],formFields[144],formFields[145]};
		int ct = 0;
		for (String field : antistoffFields){
			String value =  userEntries.get(field);
			if (value != null && !value.equals("")){
				antistoff[ct] = value + " ";
				ct++;
				
			}
		}
		return antistoff;
	}


	public void setAntistoff(String[] antistoff) {
		this.antistoff = antistoff;
	}


	public String getTemperaturFor() {
		Map<String,String> userEntries = getFormMap();
		String field = formFields[178];
		if (userEntries.get(field) != null){
			temperaturFor = userEntries.get(field);
			String tempE = getTemperaturEtter();
			if (!tempE.equals("") && !temperaturFor.equals("")){
				double tfx = Double.parseDouble(temperaturFor);
				double tex = Double.parseDouble(tempE);
				double diff = tex - tfx;
				tempendring = String.valueOf(diff);
			}
			
		}
		
		return temperaturFor;
	}


	public void setTemperaturFor(String temperaturFor) {
		this.temperaturFor = temperaturFor;
	}


	public String getTemperaturEtter() {
		Map<String,String> userEntries = getFormMap();
		String field = formFields[179];
		if (userEntries.get(field) != null){
			temperaturEtter = userEntries.get(field);
		}
	
		return temperaturEtter;
	}


	public void setTemperaturEtter(String temperaturEtter) {
		this.temperaturEtter = temperaturEtter;
	}


	public String getTempendring() {
		return tempendring;
	}


	public void setTempendring(String tempendring) {
		this.tempendring = tempendring;
	}


	public String getKlassifikasjon() {
		Map<String,String> userEntries = getFormMap();
		String[] klassifikasjonFields = {formFields[91],formFields[92],formFields[93],formFields[94],formFields[95],formFields[96],
			formFields[97],formFields[98],formFields[99],formFields[100],formFields[101],formFields[102],formFields[103],formFields[104],
			formFields[105],formFields[106],formFields[107],formFields[108],formFields[109],formFields[110],formFields[111],formFields[112],formFields[113]};
		for (String field : klassifikasjonFields){
			String value =  userEntries.get(field);
			if (value != null && !value.equals("")){
				klassifikasjon = value;
				break;
			}
		}
		return klassifikasjon;
	}


	public void setKlassifikasjon(String klassifikasjon) {
		this.klassifikasjon = klassifikasjon;
	}


	public String[] getSymptomer() {
		Map<String,String> userEntries = getFormMap();
		String[] symptomerFields = {formFields[65],formFields[66],formFields[67],formFields[68],formFields[69],formFields[70],formFields[71],
				formFields[72],formFields[73],formFields[74],formFields[75],formFields[76],formFields[77],formFields[78],formFields[79],formFields[80],
				formFields[81],formFields[82],formFields[83],formFields[84],formFields[85],formFields[86],formFields[87],formFields[88],
				formFields[89],formFields[90],formFields[178],formFields[179]};
		int ct = 0;
		for (String field : symptomerFields){
			String value =  userEntries.get(field);
			if (value != null && !value.equals("")){
				symptomer[ct] = value + " ";
				ct++;
				
			}
		}
		return symptomer;
	}


	public void setSymptomer(String[] symptomer) {
		this.symptomer = symptomer;
	}


	public String getAnnenAntallerytrocyt() {
		Map<String,String> userEntries = getFormMap();
		String field = formFields[175];
		annenAntallerytrocyt = userEntries.get(field);
		if (annenAntallerytrocyt == null)
			annenAntallerytrocyt = "";
		return annenAntallerytrocyt;
	}


	public void setAnnenAntallerytrocyt(String annenAntallerytrocyt) {
		this.annenAntallerytrocyt = annenAntallerytrocyt;
	}


	public String getAnnenAntalltrombo() {
		Map<String,String> userEntries = getFormMap();
		String field = formFields[176];
		annenAntalltrombo = userEntries.get(field);
		if (annenAntalltrombo == null)
			annenAntalltrombo = "";
		return annenAntalltrombo;
	}


	public void setAnnenAntalltrombo(String annenAntalltrombo) {
		this.annenAntalltrombo = annenAntalltrombo;
	}


	public String getAnnenAntallplasma() {
		Map<String,String> userEntries = getFormMap();
		String field = formFields[177];
		annenAntallplasma = userEntries.get(field);
		if (annenAntallplasma == null)
			annenAntallplasma  = "";
		return annenAntallplasma;
	}


	public void setAnnenAntallplasma(String annenAntallplasma) {
		this.annenAntallplasma = annenAntallplasma;
	}


	public String getAnnetBlodprodukt() {
		Map<String,String> userEntries = getFormMap();
		String[] annetblodFields = {formFields[38],formFields[39],formFields[40],formFields[41],formFields[42],formFields[43]};
		
		for (String field : annetblodFields){
			String value =  userEntries.get(field);
			if (value != null && !value.equals("")){
				annetBlodprodukt = value;
				break;
			}
		}
		return annetBlodprodukt;
	}


	public void setAnnetBlodprodukt(String annetBlodprodukt) {
		this.annetBlodprodukt = annetBlodprodukt;
	}


	public String getAnnetblodAntall() {
		Map<String,String> userEntries = getFormMap();
		String[] antallFields = {formFields[44],formFields[45],formFields[46],formFields[47],formFields[48],formFields[49],formFields[175],formFields[176],formFields[177]};
		
		for (String field : antallFields){
			String value =  userEntries.get(field);
			if (value != null && !value.equals("")){
				annetblodAntall = value;
				break;
			}
		}
		return annetblodAntall;
	}


	public void setAnnetblodAntall(String annetblodAntall) {
		this.annetblodAntall = annetblodAntall;
	}


	public String getEgenskapKriseblod() {
		Map<String,String> userEntries = getFormMap();
		String field = formFields[50];
		egenskapKriseblod = userEntries.get(field);
		if (egenskapKriseblod == null)
			egenskapKriseblod = "";
		return egenskapKriseblod;
	}


	public void setEgenskapKriseblod(String egenskapKriseblod) {
		this.egenskapKriseblod = egenskapKriseblod;
	}


	public String getEgenskapVasket() {
		Map<String,String> userEntries = getFormMap();
		String field = formFields[51];
		egenskapVasket = userEntries.get(field);
		if (egenskapVasket == null)
			egenskapVasket = "";
		return egenskapVasket;
	}


	public void setEgenskapVasket(String egenskapVasket) {
		this.egenskapVasket = egenskapVasket;
	}


	public String getEgenskapBestralt() {
		Map<String,String> userEntries = getFormMap();
		String field = formFields[52];
		egenskapBestralt = userEntries.get(field);
		if (egenskapBestralt == null)
			egenskapBestralt = "";
		return egenskapBestralt;
	}


	public void setEgenskapBestralt(String egenskapBestralt) {
		this.egenskapBestralt = egenskapBestralt;
	}


	public String getEgenskapHla() {
		Map<String,String> userEntries = getFormMap();
		String field = formFields[53];
		egenskapHla = userEntries.get(field);
		if (egenskapHla == null)
			egenskapHla = "";
		return egenskapHla;
	}


	public void setEgenskapHla(String egenskapHla) {
		this.egenskapHla = egenskapHla;
	}


	public String getEgenskapPatogen() {
		Map<String,String> userEntries = getFormMap();
		String field = formFields[54];
		egenskapPatogen = userEntries.get(field);
		if (egenskapPatogen == null)
			egenskapPatogen = "";
		return egenskapPatogen;
	}


	public void setEgenskapPatogen(String egenskapPatogen) {
		this.egenskapPatogen = egenskapPatogen;
	}


	public String getEgenskapblodvarmer() {
		Map<String,String> userEntries = getFormMap();
		String field = formFields[55];
		egenskapblodvarmer = userEntries.get(field);
		if (egenskapblodvarmer == null)
			egenskapblodvarmer = "";
		return egenskapblodvarmer;
	}


	public void setEgenskapblodvarmer(String egenskapblodvarmer) {
		this.egenskapblodvarmer = egenskapblodvarmer;
	}


	public String getErytrocytipakke() {
		Map<String,String> userEntries = getFormMap();
		String field = formFields[184];
		erytrocytipakke = userEntries.get(field);
		if (erytrocytipakke == null)
			erytrocytipakke = "";
		return erytrocytipakke;
	}


	public void setErytrocytipakke(String erytrocytipakke) {
		this.erytrocytipakke = erytrocytipakke;
	}


	public String getTromboipakke() {
		Map<String,String> userEntries = getFormMap();
		String field = formFields[185];
		tromboipakke = userEntries.get(field);
		if (tromboipakke == null) 
			tromboipakke = "";
		return tromboipakke;
	}


	public void setTromboipakke(String tromboipakke) {
		this.tromboipakke = tromboipakke;
	}


	public String getPlasmaipakke() {
		Map<String,String> userEntries = getFormMap();
		String field = formFields[186];
		plasmaipakke = userEntries.get(field);
		if (plasmaipakke == null)
			plasmaipakke = "";
		return plasmaipakke;
	}


	public void setPlasmaipakke(String plasmaipakke) {
		this.plasmaipakke = plasmaipakke;
	}


	public String getTraumetransfusjon() {
		return traumetransfusjon;
	}


	public void setTraumetransfusjon(String traumetransfusjon) {
		this.traumetransfusjon = traumetransfusjon;
	}


	public String getPlasma() {
		Map<String,String> userEntries = getFormMap();
		String field = formFields[31];
		plasma = userEntries.get(field);
		if (plasma == null)
			plasma = "";
		return plasma;
	}


	public void setPlasma(String plasma) {
		this.plasma = plasma;
	}


	public String getTapping() {
		Map<String,String> userEntries = getFormMap();
		String[] tappingFields = {formFields[57],formFields[58],formFields[59]};
		
		for (String field : tappingFields){
			String value =  userEntries.get(field);
			if (value != null && !value.equals("")){
				tapping = value;
				break;
			}
		}
		return tapping;
	}


	public void setTapping(String tapping) {
		this.tapping = tapping;
	}


	public String getSuspensjon() {
		Map<String,String> userEntries = getFormMap();
		String[] susFields = {formFields[60],formFields[61],formFields[62]};
		
		for (String field : susFields){
			String value =  userEntries.get(field);
			if (value != null && !value.equals("")){
				suspensjon = value;
				break;
			}
		}
		return suspensjon;
	}


	public void setSuspensjon(String suspensjon) {
		this.suspensjon = suspensjon;
	}


	public String getAntallPakker() {
		Map<String,String> userEntries = getFormMap();
		String[] antallFields = {formFields[33],formFields[34],formFields[35],formFields[36],formFields[37]};
		
		for (String field : antallFields){
			String value =  userEntries.get(field);
			if (value != null && !value.equals("")){
				antallPakker = value;
				break;
			}
		}
		return antallPakker;
	}


	public void setAntallPakker(String antallPakker) {
		this.antallPakker = antallPakker;
	}


	public String getBlodprodukt() {
		Map<String,String> userEntries = getFormMap();
		String[] produktFields = {formFields[27],formFields[28],formFields[29],formFields[30],formFields[31],formFields[32]};
		for (String field : produktFields){
			String value =  userEntries.get(field);
			if (value != null && !value.equals("")){
				blodprodukt = value;
				break;
			}
		}
	
		return blodprodukt;
	}


	public void setBlodprodukt(String blodprodukt) {
		this.blodprodukt = blodprodukt;
	}


	public String getHastegrad() {
		Map<String,String> userEntries = getFormMap();
		String[] hasteFields = {formFields[23],formFields[24],formFields[25]};
		for (String field : hasteFields){
			String value =  userEntries.get(field);
			if (value != null && !value.equals("")){
				hastegrad = value;
				break;
			}
		}
		return hastegrad;
	}


	public void setHastegrad(String hastegrad) {
		this.hastegrad = hastegrad;
	}


	public String getPasientType() {
		Map<String,String> userEntries = getFormMap();
		String[] typeFields = {formFields[18],formFields[19],formFields[20]};
		for (String field : typeFields){
			String value =  userEntries.get(field);
			if (value != null && !value.equals("")){
				pasientType = value;
				break;
			}
		}
		return pasientType;
	}


	public void setPasientType(String pasientType) {
		this.pasientType = pasientType;
	}


	public String getTidligere() {
		Map<String,String> userEntries = getFormMap();
		String[] typeFields = {formFields[148],formFields[149],formFields[150],formFields[151],formFields[152],formFields[153],formFields[154],formFields[155],formFields[156],formFields[157],
				formFields[158],formFields[159],formFields[160],formFields[161],formFields[162],formFields[163],formFields[164],formFields[165],formFields[166],
				formFields[167],formFields[168],formFields[169],formFields[170],formFields[171],formFields[172],formFields[173],formFields[174]};
		for (String field : typeFields){
			String value =  userEntries.get(field);
			if (value != null && !value.equals("")){
				tidligere = value;
				break;
			}
		}
		if (tidligere == null || tidligere.equals("")){
			displaytidligereTrans = "none";
			tidligere = "";
		}
		return tidligere;
	}


	public String getDisplaytidligereTrans() {
		return displaytidligereTrans;
	}


	public void setDisplaytidligereTrans(String displaytidligereTrans) {
		this.displaytidligereTrans = displaytidligereTrans;
	}


	public void setTidligere(String tidligere) {
		this.tidligere = tidligere;
	}


	public String getKjonn() {
		Map<String,String> userEntries = getFormMap();
		String field = "pas-kjonn";
		kjonn = userEntries.get(field);
		if (kjonn.equals("M")){
			kjonn = "Mann";
		}
		if (kjonn.equals("K")){
			kjonn = "Kvinne";
		}
		return kjonn;
	}


	public void setKjonn(String kjonn) {
		this.kjonn = kjonn;
	}


	public String getMann() {
		Map<String,String> userEntries = getFormMap();
		String field = "pas-kjonn";
		boolean checked = false;
		kjonn = userEntries.get(field);
		if (kjonn.equals("M")){
			mann = "M";
			checked = true;
		}
		return mann;
	}


	public void setMann(String mann) {
		this.mann = mann;
	}


	public String getKvinne() {
		Map<String,String> userEntries = getFormMap();
		String field = "pas-kjonn";
		boolean checked = false;
		kjonn = userEntries.get(field);
		if (kjonn.equals("K")){
			kvinne = "K";
			checked = true;
		}
		return kvinne;
	}


	public void setKvinne(String kvinne) {
		this.kvinne = kvinne;
	}


	public String getSykdomBehandling() {
		Map<String,String> userEntries = getFormMap();
		String field = "p-antistoff-behandling";
		sykdomBehandling = userEntries.get(field);
		if (sykdomBehandling == null)
			sykdomBehandling = "-";
		return sykdomBehandling;
	}


	public void setSykdomBehandling(String sykdomBehandling) {
		this.sykdomBehandling = sykdomBehandling;
	}


	public String getSykdomBetydning() {
		Map<String,String> userEntries = getFormMap();
		String field = "p-annen-sykdom";
		sykdomBetydning = userEntries.get(field);
		if (sykdomBetydning == null)
			sykdomBetydning = "-";
		return sykdomBetydning;
	}


	public void setSykdomBetydning(String sykdomBetydning) {
		this.sykdomBetydning = sykdomBetydning;
	}


	public String getAvdeling() {
		Map<String,String> userEntries = getFormMap();
		String field = "p-avdeling-list";
		avdeling = userEntries.get(field);
		if (avdeling == null)
			avdeling = "-";
		return avdeling;
	}


	public void setAvdeling(String avdeling) {
		this.avdeling = avdeling;
	}


	public String getTidTransfusjon() {
		Map<String,String> userEntries = getFormMap();
		String field = "transfusjon-time";
		tidTransfusjon = userEntries.get(field);
		if (tidTransfusjon == null)
			tidTransfusjon = "-";
		return tidTransfusjon;
	}


	public void setTidTransfusjon(String tidTransfusjon) {
		this.tidTransfusjon = tidTransfusjon;
	}


	public String getDatoTransfusjon() {
		Map<String,String> userEntries = getFormMap();
		String field = "transfusjon-date";
		datoTransfusjon = userEntries.get(field);
		if (datoTransfusjon == null)
			datoTransfusjon = "-";
		return datoTransfusjon;
	}


	public void setDatoTransfusjon(String datoTransfusjon) {
		this.datoTransfusjon = datoTransfusjon;
	}


	public String getIndikasjonTransfusjon() {
		Map<String,String> userEntries = getFormMap();
		String field = "p-antistoff-transfusjon";
		indikasjonTransfusjon = userEntries.get(field);
		if (indikasjonTransfusjon == null)
			indikasjonTransfusjon = "-";
		return indikasjonTransfusjon;
	}


	public void setIndikasjonTransfusjon(String indikasjonTransfusjon) {
		this.indikasjonTransfusjon = indikasjonTransfusjon;
	}

	
}
