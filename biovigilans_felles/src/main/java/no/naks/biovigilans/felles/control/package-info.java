/**
 * Denne pakken inneholde klasser for singleton web tjenester.
 * Web tjenester knytter brukerhandlinger og valg til forretningslaget og databasetjenester
 * I tillegg finnes en tjeneste for administrasjon av sesjonen til en bruker. 
 * Denne tjenesten administrerer alle brukerens web modell objekter, samt valg av database.
 * Knytningen skjer ved bruk av Spring Dependency Injection. (applicationContext.xml og applicationContext-ejb.xml)
 * @author olj
 */
@javax.xml.bind.annotation.XmlSchema(namespace = "http://www.webserviceX.NET", elementFormDefault = javax.xml.bind.annotation.XmlNsForm.QUALIFIED)
package no.naks.biovigilans.felles.control;