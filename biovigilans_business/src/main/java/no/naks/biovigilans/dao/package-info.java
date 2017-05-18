/**
 * Denne pakken inneholde klasser for singleton DAO tjenester
 * DAO tjenester gjør oppslag og lagring til databasen
 * Til hver DAO tjeneste finnes det et -Select-klasse, som instansierer nødvendige modellobjekter
 * Knytningen skjer ved bruk av Spring Dependency Injection. (applicationContext.xml og applicationContext-ejb.xml)
 * @author olj
 */
@javax.xml.bind.annotation.XmlSchema(namespace = "http://www.webserviceX.NET", elementFormDefault = javax.xml.bind.annotation.XmlNsForm.QUALIFIED)
package no.naks.biovigilans.dao;