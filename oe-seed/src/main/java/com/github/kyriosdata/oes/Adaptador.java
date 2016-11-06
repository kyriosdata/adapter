/*
 * Copyright (c) 2016 Fábio Nogueira de Lucena
 *
 * Fábrica de Software - Instituto de Informática (UFG)
 * Creative Commons Attribution 4.0 International License.
 */

package com.github.kyriosdata.oes;

import com.github.kyriosdata.seed.Seed;
import org.openehr.rm.datatypes.basic.DvBoolean;
import org.openehr.rm.datatypes.basic.DvIdentifier;

/**
 * Classe que adapta MR do openEHR para Seed e vice-versa.
 */
public class Adaptador {

    public static final int DV_BOOLEAN = 0;
    public static final int DV_IDENTIFIER = 1;

    byte[][] meta = new byte[][] {
            { DV_BOOLEAN, 1, Seed.BOOLEAN },
            { DV_IDENTIFIER, 4, Seed.STRING, Seed.STRING, Seed.STRING, Seed.STRING }
    };

    /**
     * Cria um objeto em conformidade com o tipo definido
     * pelo Modelo de Referência do openEHR.
     *
     */
    public Adaptador() {
    }

    /**
     * Converte objeto em sequência de bytes correspondente.
     *
     * @param rm O objeto a ser serializado.
     *
     * @return Objeto serializado em sequência de bytes.
     *
     * @see #dvBoolean(byte[])
     */
    public byte[] adapta(DvBoolean rm) {
        Seed seed = Seed.serializa(meta[DV_BOOLEAN]);
        seed.defineBoolean(0, rm.getValue());
        return seed.array();
    }

    /**
     * Obtém objeto a partir da serialização correspondente.
     *
     * @param dados Objeto serializado em uma sequência de bytes.
     *
     * @return Objeto obtido da sequência de bytes.
     *
     * @see #adapta(DvBoolean)
     */
    public DvBoolean dvBoolean(byte[] dados) {
        Seed s = Seed.desserializa(dados);
        return new DvBoolean(s.obtemBoolean(0));
    }

    /**
     * Converte objeto em sequência de bytes correspondente.
     *
     * @param rm O objeto a ser serializado.
     *
     * @return Objeto serializado em sequência de bytes.
     *
     * @see #dvIdentifier(byte[])
     */
    public byte[] adapta(DvIdentifier rm) {
        Seed seed = Seed.serializa(meta[DV_IDENTIFIER]);
        seed.defineString(0, rm.getIssuer());
        seed.defineString(1, rm.getAssigner());
        seed.defineString(2, rm.getId());
        seed.defineString(3, rm.getType());
        return seed.array();
    }

    /**
     * Obtém objeto a partir da serialização correspondente.
     *
     * @param dados Objeto serializado em uma sequência de bytes.
     *
     * @return Objeto obtido da sequência de bytes.
     *
     * @see #adapta(DvIdentifier)
     */
    public DvIdentifier dvIdentifier(byte[] dados) {
        Seed s = Seed.desserializa(dados);
        return new DvIdentifier(
                s.obtemString(0),
                s.obtemString(1),
                s.obtemString(2),
                s.obtemString(3));
    }
}