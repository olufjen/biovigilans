--
-- Setter rettigheter på databasen "skjemabank_innsiden" for brukeren "innsiden"
--
GRANT
CONNECT
ON DATABASE skjemabank_innsiden
TO innsiden;
--
-- Setter rettigheter på databasen "skjemabank_innsiden" for brukeren "utsiden"
--
GRANT
CONNECT
ON DATABASE skjemabank_innsiden
TO utsiden;
--
-- Setter rettigheter på tabeller i databasen "skjemabank_innsiden" for brukeren "innsiden"
--
GRANT 
SELECT, INSERT, UPDATE, DELETE, REFERENCES, TRIGGER
ON TABLE
tbleier,tblindeks,tblnokkelord,tblinputtype,tblinstitusjon,tblinstitusjonstype,tbllitteraturreferanse,
tblskjemakommentar,tblskjemakonto,tblskjemalinje,tblsporreskjema,tblsporsmal,tblsporsmalinje,tblsvarlinje,tblsvarskala,
tbltema,tbltemalinje,tblundersokelse,tblundersokelseseier,tblforetak,tblregion,tblbruker,tblavdelingkontolinje,
tblkontobrukerlinje,tblnokkelordlinje,tblnokkelordskjemalinje,tblindekslinje
TO innsiden;
--
-- Setter rettigheter på sekvenser i databasen "skjemabank_innsiden" for brukeren "innsiden"
--    The SEQUENCE KEYWORD MUST BE REMOVED FOR LINUX DB 
GRANT
USAGE, SELECT, UPDATE
ON SEQUENCE 
tbleier_eierid_seq,
tblindeks_indeksid_seq,
tblnokkelord_informantid_seq,
tblinputtype_inputtypeid_seq,
tblinstitusjon_institusjonid_seq,
tblinstitusjonstype_institusjonstypeid_seq,
tbllitteraturreferanse_littrefid_seq,
tblskjemakommentar_skjemakommentarid_seq,
tblskjemakonto_kontoid_seq,
tblskjemalinje_skjemalinjeid_seq,
tblsporreskjema_sporreskjemaid_seq,
tblsporsmal_sporsmalid_seq,
tblsvarlinje_svarlinjeid_seq,
tblsvarskala_svarskalaid_seq,
tbltema_temaid_seq,
tblundersokelse_undersokelseid_seq,
tblundersokelseseier_undersokelseseierid_seq,
tblforetak_foretakid_seq,
tblregion_regionid_seq,
tblbruker_brukerid_seq
TO innsiden;
--
-- Setter rettigheter på tabeller i databasen "skjemabank_innsiden" for brukeren "utsiden"
--
--
-- Setter rettigheter på sekvenser i databasen "skjemabank_innsiden" for brukeren "utsiden"
--
GRANT 
SELECT
ON SEQUENCE 
tbleier_eierid_seq,
tblindeks_indeksid_seq,
tblnokkelord_informantid_seq,
tblinputtype_inputtypeid_seq,
tblinstitusjon_institusjonid_seq,
tblinstitusjonstype_institusjonstypeid_seq,
tbllitteraturreferanse_littrefid_seq,
tblskjemakommentar_skjemakommentarid_seq,
tblskjemakonto_kontoid_seq,
tblskjemalinje_skjemalinjeid_seq,
-- tblskjemalinje_sporreskjemaid_seq,
tblsporreskjema_sporreskjemaid_seq,
tblsporsmal_sporsmalid_seq,
tblsvarlinje_svarlinjeid_seq,
tblsvarskala_svarskalaid_seq,
tbltema_temaid_seq,
tblundersokelse_undersokelseid_seq,
tblundersokelseseier_undersokelseseierid_seq
TO utsiden;
