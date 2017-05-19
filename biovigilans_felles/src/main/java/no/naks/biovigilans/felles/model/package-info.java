/**
 * Denne pakken inneholde klasser brukeren web modell objekter.
 * Web modell objekter inneholder til enhver tid brukerens data til og fra databasen. 
 * Forretningslaget (og databasen) har egne modell objekter, som reprenesterer strukturen i databasen.
 * Web modell objektene reprensenterer i størst mulig grad strukturen i skjermbildene.
 * Dette skjer gjennom bruk av rammeverket freemarker og .properties filer som peker til navn i skjermbildefelter.
 * @author olj
 */
@javax.xml.bind.annotation.XmlSchema(namespace = "http://www.webserviceX.NET", elementFormDefault = javax.xml.bind.annotation.XmlNsForm.QUALIFIED)
package no.naks.biovigilans.felles.model;