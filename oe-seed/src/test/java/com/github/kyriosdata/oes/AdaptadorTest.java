package com.github.kyriosdata.oes;

import org.junit.jupiter.api.Test;
import org.openehr.rm.datatypes.basic.DvBoolean;
import org.openehr.rm.datatypes.basic.DvIdentifier;
import org.openehr.rm.support.identification.ISO_OID;
import org.openehr.rm.support.identification.InternetID;
import org.openehr.rm.support.identification.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class AdaptadorTest {

    @Test
    public void dvBoolean() {
        Adaptador a = new Adaptador();

        // Um objeto a ser adaptado
        DvBoolean dv = new DvBoolean(false);

        // Converte
        byte[] seed = a.adapta(dv);

        // Restaura
        DvBoolean recuperado = a.dvBoolean(seed);

        // Verifica
        assertFalse(recuperado.getValue());
    }

    @Test
    public void dvIdentifier() {
        Adaptador a = new Adaptador();

        // Um objeto a ser adaptado
        DvIdentifier dv = new DvIdentifier("i", "a", "id", "type");

        // Converte
        byte[] seed = a.adapta(dv);

        // Restaura
        DvIdentifier recuperado = a.oeDvIdentifier(seed);

        // Verifica
        assertEquals("i", recuperado.getIssuer());
        assertEquals("a", recuperado.getAssigner());
        assertEquals("id", recuperado.getId());
        assertEquals("type", recuperado.getType());
    }

    @Test
    public void internetId() {
        Adaptador a = new Adaptador();

        // Um objeto a ser adaptado
        InternetID v = new InternetID("id");

        // Converte
        byte[] bytes = a.adapta(v);

        // Restaura
        InternetID recuperado = a.oeInternetID(bytes);

        // Verifica
        assertEquals("id", recuperado.getValue());
    }

    @Test
    public void isoOid() {
        Adaptador a = new Adaptador();

        String linuxLoadOID = "1.3.6.1.4.1.2021.10.1.3.1";
        ISO_OID v = new ISO_OID(linuxLoadOID);

        byte[] bytes = a.adapta(v);

        ISO_OID recuperado = a.oeISO_OID(bytes);

        assertEquals(linuxLoadOID, recuperado.getValue());
    }

    @Test
    public void uuid() {
        Adaptador a = new Adaptador();

        String guid = java.util.UUID.randomUUID().toString();

        UUID v = new UUID(guid);

        byte[] bytes = a.adapta(v);

        UUID recuperado = a.oeUUID(bytes);

        assertEquals(guid, recuperado.getValue());
    }

}

