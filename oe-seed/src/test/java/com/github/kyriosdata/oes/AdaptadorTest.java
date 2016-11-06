package com.github.kyriosdata.oes;

import org.junit.jupiter.api.Test;
import org.openehr.rm.datatypes.basic.DvBoolean;
import org.openehr.rm.datatypes.basic.DvIdentifier;

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
        DvBoolean recuperado = a.dvBoolean(seed);

        // Verifica
        assertFalse(recuperado.getValue());
    }

}

