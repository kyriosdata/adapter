package com.github.kyriosdata.oes;

import org.junit.jupiter.api.Test;
import org.openehr.rm.datatypes.basic.DvBoolean;
import org.openehr.rm.datatypes.basic.DvIdentifier;
import org.openehr.rm.datatypes.text.CodePhrase;
import org.openehr.rm.datatypes.uri.DvEHRURI;
import org.openehr.rm.datatypes.uri.DvURI;
import org.openehr.rm.support.identification.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class AdaptadorTest {

    @Test
    public void accessGroupRef() {

        TemplateID tid = new TemplateID("TemplateID");
        AccessGroupRef agr = new AccessGroupRef(tid);

        byte[] bytes = new Adaptador().set(agr);

        Adaptador a = new Adaptador(bytes);
        AccessGroupRef recuperado = a.getAccessGroupRef();

        assertEquals(agr, recuperado);
    }

    @Test
    public void codePhrase() {
        TerminologyID tid = new TerminologyID("id(v)");
        CodePhrase cp = new CodePhrase(tid, "codigo");

        byte[] bytes = new Adaptador().set(cp);

        Adaptador a = new Adaptador(bytes);

        CodePhrase recuperado = a.getCodePhrase(bytes);

        assertEquals(tid, recuperado.getTerminologyId());
        assertEquals("codigo", recuperado.getCodeString());
    }

    @Test
    public void dvBoolean() {
        // Um objeto a ser adaptado
        DvBoolean dv = new DvBoolean(false);

        // Converte para seed
        byte[] seed = new Adaptador().set(dv);

        // De seed para MR
        Adaptador a = new Adaptador(seed);

        // Define registro de interesse
        a.setInicioRegistro(0);

        // Restaura
        DvBoolean recuperado = a.getDvBoolean(0);

        // Verifica
        assertFalse(recuperado.getValue());
    }

    @Test
    public void dvURI() {
        String uri = "mailto:fabio@inf.ufg.br";

        DvURI duri = new DvURI(uri);
        byte[] bytes = new Adaptador().set(duri);

        Adaptador a = new Adaptador(bytes);

        DvURI recuperado = a.getDvURI(bytes);

        assertEquals(uri, recuperado.getValue());
    }

    @Test
    public void dvEHRURI() {
        String uri = "ehr:fabio@inf.ufg.br";

        DvEHRURI duri = new DvEHRURI(uri);
        byte[] bytes = new Adaptador().set(duri);

        Adaptador a = new Adaptador(bytes);

        DvEHRURI recuperado = a.getDvEHRURI(bytes);

        assertEquals(uri, recuperado.getValue());
    }

    @Test
    public void dvIdentifier() {

        // Um objeto a ser adaptado
        DvIdentifier dv = new DvIdentifier("i", "a", "id", "type");

        // Converte
        byte[] seed = new Adaptador().set(dv);

        // Recuperação
        Adaptador a = new Adaptador(seed);

        // Restaura
        DvIdentifier recuperado = a.getDvIdentifier(0);

        // Verifica
        assertEquals("i", recuperado.getIssuer());
        assertEquals("a", recuperado.getAssigner());
        assertEquals("id", recuperado.getId());
        assertEquals("type", recuperado.getType());
    }

    @Test
    public void genericId() {
        GenericID v = new GenericID("value", "scheme");

        byte[] seed = new Adaptador().set(v);

        Adaptador a = new Adaptador(seed);
        GenericID recuperado = a.getGenericID(seed);

        assertEquals("value", recuperado.getValue());
        assertEquals("scheme", recuperado.getScheme());
    }

    @Test
    public void internetId() {
        // Um objeto a ser adaptado
        InternetID v = new InternetID("id");

        // Converte
        byte[] bytes = new Adaptador().set(v);

        // Restaura
        Adaptador a = new Adaptador(bytes);
        InternetID recuperado = a.getInternetID(bytes);

        // Verifica
        assertEquals("id", recuperado.getValue());
    }

    @Test
    public void isoOid() {

        String linuxLoadOID = "1.3.6.1.4.1.2021.10.1.3.1";
        ISO_OID v = new ISO_OID(linuxLoadOID);

        byte[] bytes = new Adaptador().set(v);

        Adaptador a = new Adaptador(bytes);
        ISO_OID recuperado = a.getISO_OID(bytes);

        assertEquals(linuxLoadOID, recuperado.getValue());
    }

    @Test
    public void templateId() {
        TemplateID v = new TemplateID("templateId");

        byte[] bytes = new Adaptador().set(v);

        Adaptador a = new Adaptador(bytes);

        TemplateID recuperado = a.getTemplateID();

        assertEquals("templateId", recuperado.getValue());
    }

    @Test
    public void terminologyId() {

        TerminologyID v = new TerminologyID("id(v)");

        byte[] bytes = new Adaptador().set(v);

        Adaptador a = new Adaptador(bytes);

        TerminologyID recuperado = a.getTerminologyID(bytes);

        assertEquals("id(v)", recuperado.getValue());
    }

    @Test
    public void uuid() {
        String guid = java.util.UUID.randomUUID().toString();

        UUID v = new UUID(guid);

        byte[] bytes = new Adaptador().set(v);

        Adaptador a = new Adaptador(bytes);

        UUID recuperado = a.getUUID(bytes);

        assertEquals(guid, recuperado.getValue());
    }

    @Test
    public void versionTreeID() {
        VersionTreeID v = new VersionTreeID(1, 2, 3);
        byte[] bytes = new Adaptador().set(v);

        Adaptador a = new Adaptador(bytes);

        VersionTreeID recuperado = a.getVersionTreeID(bytes);

        assertEquals("1.2.3", recuperado.getValue());
    }

    @Test
    public void archetypeID() {

        String archetype = "openEHR-EHR-COMPOSITION.care_plan.v1";
        ArchetypeID v = new ArchetypeID(archetype);
        byte[] bytes = new Adaptador().set(v);

        Adaptador a = new Adaptador(bytes);
        ArchetypeID recuperado = a.getArchetypeID(bytes);

        assertEquals(archetype, recuperado.getValue());
    }

    @Test
    public void objectVersionID() {

        String ovid = "ufg.br::inf.ufg::1";
        ObjectVersionID v = new ObjectVersionID(ovid);

        byte[] bytes = new Adaptador().set(v);

        Adaptador a = new Adaptador(bytes);
        ObjectVersionID recuperado = a.getObjectVersionID(bytes);

        assertEquals(ovid, recuperado.getValue());
    }

    @Test
    public void hierObjectID() {

        String hoid = "inf.ufg";
        HierObjectID v = new HierObjectID(hoid);

        byte[] bytes = new Adaptador().set(v);

        Adaptador a = new Adaptador(bytes);
        HierObjectID recuperado = a.getHierObjectID(bytes);

        assertEquals(hoid, recuperado.getValue());
    }

    @Test
    public void locatableRef() {
        String ovid = "ufg.br::inf.ufg::1";
        ObjectVersionID ov = new ObjectVersionID(ovid);
        LocatableRef lr = new LocatableRef(ov, "ehr", "tipo", "/a");

        byte[] bytes = new Adaptador().set(lr);

        Adaptador a = new Adaptador(bytes);
        LocatableRef recuperado = a.getLocatableRef();

        assertEquals(lr, recuperado);
    }

    @Test
    public void objectRef() {

        TemplateID tid = new TemplateID("TemplateID");
        ObjectRef or = new ObjectRef(tid, "namespace", "type");

        byte[] bytes = new Adaptador().set(or);

        Adaptador a = new Adaptador(bytes);
        ObjectRef recuperado = a.getObjectRef();

        assertEquals(or, recuperado);
    }

    @Test
    public void partyRef() {
        partyRefBase(new TemplateID("TemplateID"));
        partyRefBase(new TerminologyID("TerminologyID"));
        partyRefBase(new GenericID("GenericID", "ehr"));
        partyRefBase(new ArchetypeID("openEHR-EHR-COMPOSITION.adverse_reaction_list.v1"));
        partyRefBase(new ObjectVersionID("ufg.br::inf.ufg::1"));
        partyRefBase(new HierObjectID("inf.ufg"));
    }

    private void partyRefBase(ObjectID objectId) {
        PartyRef pr = new PartyRef(objectId, "tipo");

        // Serializa PartyRef
        byte[] seed = new Adaptador().set(pr);

        // Desserializa
        Adaptador a = new Adaptador(seed);
        PartyRef r = a.getPartyRef();

        assertEquals(pr, r);
    }

}

