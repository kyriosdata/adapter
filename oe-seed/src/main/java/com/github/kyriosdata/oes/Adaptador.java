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
import org.openehr.rm.support.identification.*;

/**
 * Classe que adapta MR do openEHR para Seed e vice-versa.
 */
public class Adaptador {

    public static final int OE_DVBOOLEAN = 0;
    public static final int OE_DVIDENTIFIER = 1;
    public static final int OE_INTERNETID = 2;
    public static final int OE_ISO_OID = 3;
    public static final int OE_UUID = 4;
    public static final int OE_TERMINOLOGYID = 5;
    public static final int OE_GENERICID = 6;
    public static final int OE_TEMPLATEID = 7;

    byte[][] meta = new byte[][]{
            {OE_DVBOOLEAN, 1, Seed.BOOLEAN},
            {OE_DVIDENTIFIER, 4, Seed.STRING, Seed.STRING, Seed.STRING, Seed.STRING},
            {OE_INTERNETID, 1, Seed.STRING},
            {OE_ISO_OID, 1, Seed.STRING},
            {OE_UUID, 1, Seed.STRING},
            {OE_TERMINOLOGYID, 1, Seed.STRING},
            {OE_GENERICID, 2, Seed.STRING, Seed.STRING},
            {OE_TEMPLATEID, 1, Seed.STRING}
    };

    /**
     * Cria um objeto em conformidade com o tipo definido
     * pelo Modelo de Referência do openEHR.
     */
    public Adaptador() {
    }

    /**
     * Converte objeto em sequência de bytes correspondente.
     *
     * @param rm O objeto a ser serializado.
     * @return Objeto serializado em sequência de bytes.
     * @see #dvBoolean(byte[])
     */
    public byte[] adapta(DvBoolean rm) {
        Seed seed = Seed.serializa(meta[OE_DVBOOLEAN]);
        seed.defineBoolean(0, rm.getValue());
        return seed.array();
    }

    /**
     * Obtém objeto a partir da serialização correspondente.
     *
     * @param dados Objeto serializado em uma sequência de bytes.
     * @return Objeto obtido da sequência de bytes.
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
     * @return Objeto serializado em sequência de bytes.
     * @see #oeDvIdentifier(byte[])
     */
    public byte[] adapta(DvIdentifier rm) {
        Seed seed = Seed.serializa(meta[OE_DVIDENTIFIER]);
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
     * @return Objeto obtido da sequência de bytes.
     * @see #adapta(DvIdentifier)
     */
    public DvIdentifier oeDvIdentifier(byte[] dados) {
        Seed s = Seed.desserializa(dados);
        return new DvIdentifier(
                s.obtemString(0),
                s.obtemString(1),
                s.obtemString(2),
                s.obtemString(3));
    }

    /**
     * Converte objeto em sequência de bytes correspondente.
     *
     * @param rm O objeto a ser serializado.
     * @return Objeto serializado em sequência de bytes.
     * @see #oeGenericID(byte[])
     */
    public byte[] adapta(GenericID rm) {
        Seed seed = Seed.serializa(meta[OE_GENERICID]);
        seed.defineString(0, rm.getValue());
        seed.defineString(1, rm.getScheme());
        return seed.array();
    }

    /**
     * Obtém objeto a partir da serialização correspondente.
     *
     * @param dados Objeto serializado em uma sequência de bytes.
     * @return Objeto obtido da sequência de bytes.
     * @see #adapta(GenericID)
     */
    public GenericID oeGenericID(byte[] dados) {
        Seed s = Seed.desserializa(dados);
        return new GenericID(s.obtemString(0), s.obtemString(1));
    }

    /**
     * Converte objeto em sequência de bytes correspondente.
     *
     * @param rm O objeto a ser serializado.
     * @return Objeto serializado em sequência de bytes.
     * @see #oeInternetID(byte[])
     */
    public byte[] adapta(InternetID rm) {
        Seed seed = Seed.serializa(meta[OE_INTERNETID]);
        seed.defineString(0, rm.getValue());
        return seed.array();
    }

    /**
     * Obtém objeto a partir da serialização correspondente.
     *
     * @param dados Objeto serializado em uma sequência de bytes.
     * @return Objeto obtido da sequência de bytes.
     * @see #adapta(InternetID)
     */
    public InternetID oeInternetID(byte[] dados) {
        Seed s = Seed.desserializa(dados);
        return new InternetID(s.obtemString(0));
    }

    /**
     * Converte objeto em sequência de bytes correspondente.
     *
     * @param rm O objeto a ser serializado.
     * @return Objeto serializado em sequência de bytes.
     * @see #oeISO_OID(byte[])
     */
    public byte[] adapta(ISO_OID rm) {
        Seed seed = Seed.serializa(meta[OE_ISO_OID]);
        seed.defineString(0, rm.getValue());
        return seed.array();
    }

    /**
     * Obtém objeto a partir da serialização correspondente.
     *
     * @param dados Objeto serializado em uma sequência de bytes.
     * @return Objeto obtido da sequência de bytes.
     * @see #adapta(ISO_OID)
     */
    public ISO_OID oeISO_OID(byte[] dados) {
        Seed s = Seed.desserializa(dados);
        return new ISO_OID(s.obtemString(0));
    }

    /**
     * Converte objeto em sequência de bytes correspondente.
     *
     * @param rm O objeto a ser serializado.
     * @return Objeto serializado em sequência de bytes.
     * @see #oeUUID(byte[])
     */
    public byte[] adapta(UUID rm) {
        Seed seed = Seed.serializa(meta[OE_UUID]);
        seed.defineString(0, rm.getValue());
        return seed.array();
    }

    /**
     * Obtém objeto a partir da serialização correspondente.
     *
     * @param dados Objeto serializado em uma sequência de bytes.
     * @return Objeto obtido da sequência de bytes.
     * @see #adapta(ISO_OID)
     */
    public UUID oeUUID(byte[] dados) {
        Seed s = Seed.desserializa(dados);
        return new UUID(s.obtemString(0));
    }

    /**
     * Converte objeto em sequência de bytes correspondente.
     *
     * @param rm O objeto a ser serializado.
     * @return Objeto serializado em sequência de bytes.
     * @see #oeTemplateID(byte[])
     */
    public byte[] adapta(TemplateID rm) {
        Seed seed = Seed.serializa(meta[OE_TERMINOLOGYID]);
        seed.defineString(0, rm.getValue());
        return seed.array();
    }

    /**
     * Obtém objeto a partir da serialização correspondente.
     *
     * @param dados Objeto serializado em uma sequência de bytes.
     * @return Objeto obtido da sequência de bytes.
     * @see #adapta(TemplateID)
     */
    public TemplateID oeTemplateID(byte[] dados) {
        Seed s = Seed.desserializa(dados);
        return new TemplateID(s.obtemString(0));
    }

    /**
     * Converte objeto em sequência de bytes correspondente.
     *
     * @param rm O objeto a ser serializado.
     * @return Objeto serializado em sequência de bytes.
     * @see #oeTerminologyID(byte[])
     */
    public byte[] adapta(TerminologyID rm) {
        Seed seed = Seed.serializa(meta[OE_TERMINOLOGYID]);
        seed.defineString(0, rm.getValue());
        return seed.array();
    }

    /**
     * Obtém objeto a partir da serialização correspondente.
     *
     * @param dados Objeto serializado em uma sequência de bytes.
     * @return Objeto obtido da sequência de bytes.
     * @see #adapta(TerminologyID)
     */
    public TerminologyID oeTerminologyID(byte[] dados) {
        Seed s = Seed.desserializa(dados);
        return new TerminologyID(s.obtemString(0));
    }
}