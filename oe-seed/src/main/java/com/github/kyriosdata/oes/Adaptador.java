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
import org.openehr.rm.datatypes.text.CodePhrase;
import org.openehr.rm.datatypes.uri.DvEHRURI;
import org.openehr.rm.datatypes.uri.DvURI;
import org.openehr.rm.support.identification.*;

/**
 * Classe que "converte" objeto do MR (openEHR) em formato
 * empregado pelo Seed e vice-versa.
 */
public class Adaptador {

    /**
     * Identificador único para cada classe do MR.
     */

    public static final int OE_DVBOOLEAN = 0;
    public static final int OE_DVIDENTIFIER = 1;
    public static final int OE_INTERNETID = 2;
    public static final int OE_ISO_OID = 3;
    public static final int OE_UUID = 4;
    public static final int OE_TERMINOLOGYID = 5;
    public static final int OE_GENERICID = 6;
    public static final int OE_TEMPLATEID = 7;
    public static final int OE_CODEPHRASE = 8;
    public static final int OE_DVURI = 9;
    public static final int OE_DVEHRURI = 10;
    public static final int OE_VERSIONTREEID = 11;
    public static final int OE_PARTYREF = 12;
    public static final int OE_ARCHETYPEID = 13;
    public static final int OE_OBJECTVERSIONID= 14;
    public static final int OE_HIEROBJECTID = 15;

    /**
     * Uma entrada em meta-informação para cada classe do MR.
     *
     * Meta-informação para todos os tipos considerados pelo Adaptador.
     * Primeiro byte não é empregado.
     * Segundo byte é a quantidade de campos.
     * Terceiro byte em diante indica os tipos de cada um dos campos.
     */
    byte[][] meta;

    private byte[] buffer;
    private Seed s;

    /**
     * Cria adaptador para conversão Seed -> MR.
     *
     * @param bytes Vetor contendo serialização de objetos MR.
     */
    public Adaptador(byte[] bytes) {
        super();
        buffer = bytes;
        s = Seed.desserializa(buffer);
    }

    /**
     * Cria adaptador para conversão MR -> Seed.
     */
    public Adaptador() {
        buffer = null;

        meta = new byte[50][];
        meta[OE_DVBOOLEAN] = new byte[] {OE_DVBOOLEAN, 1, Seed.BOOLEAN};
        meta[OE_DVIDENTIFIER] = new byte[] {OE_DVIDENTIFIER, 4, Seed.STRING, Seed.STRING, Seed.STRING, Seed.STRING};
        meta[OE_INTERNETID] = new byte[] {OE_INTERNETID, 1, Seed.STRING};
        meta[OE_ISO_OID] = new byte[] {OE_ISO_OID, 1, Seed.STRING};
        meta[OE_UUID] = new byte[] {OE_UUID, 1, Seed.STRING};
        meta[OE_TERMINOLOGYID] = new byte[] {OE_TERMINOLOGYID, 1, Seed.STRING};
        meta[OE_GENERICID] = new byte[] {OE_GENERICID, 2, Seed.STRING, Seed.STRING};
        meta[OE_TEMPLATEID] = new byte[] {OE_TEMPLATEID, 1, Seed.STRING};
        meta[OE_CODEPHRASE] = new byte[] {OE_CODEPHRASE, 2, Seed.STRING, Seed.VETOR};
        meta[OE_DVURI] = new byte[] {OE_DVURI, 1, Seed.STRING};
        meta[OE_DVEHRURI] = new byte[] {OE_DVEHRURI, 1, Seed.STRING};
        meta[OE_VERSIONTREEID] = new byte[] {OE_VERSIONTREEID, 1, Seed.STRING};
        meta[OE_PARTYREF] = new byte[] {OE_PARTYREF, 3, Seed.VETOR, Seed.STRING, Seed.STRING};
        meta[OE_ARCHETYPEID] = new byte[] {OE_ARCHETYPEID, 1, Seed.STRING};
        meta[OE_OBJECTVERSIONID] = new byte[] {OE_OBJECTVERSIONID, 1, Seed.STRING};
        meta[OE_HIEROBJECTID] = new byte[] {OE_HIEROBJECTID, 1, Seed.STRING};
    }

    /**
     * Define o registro corrente por meio da posição
     * do primeiro byte.
     *
     * @param inicio Posição do primeiro byte do registro
     *               a ser considerado o corrente.
     */
    public void setInicioRegistro(int inicio) {
        s.setOffsetInicio(inicio);
    }

    /**
     * Converte objeto em sequência de bytes correspondente.
     *
     * @param rm O objeto a ser serializado.
     * @return Objeto serializado em sequência de bytes.
     * @see #oeDvBoolean(int)
     */
    public byte[] adapta(DvBoolean rm) {
        Seed seed = Seed.serializa(meta[OE_DVBOOLEAN]);
        seed.defineBoolean(0, rm.getValue());
        return seed.array();
    }

    /**
     * Obtém objeto a partir da serialização correspondente.
     *
     * @param ordem Ordem do objeto no registro.
     * @return Objeto obtido da sequência de bytes.
     * @see #adapta(DvBoolean)
     */
    public DvBoolean oeDvBoolean(int ordem) {
        return new DvBoolean(s.obtemBoolean(0));
    }

    /**
     * Converte objeto em sequência de bytes correspondente.
     *
     * @param rm O objeto a ser serializado.
     * @return Objeto serializado em sequência de bytes.
     * @see #oeDvIdentifier(int)
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
     * @param posicao Posição inicial do objeto no vetor.
     * @return Objeto obtido da sequência de bytes.
     *
     * @see #adapta(DvIdentifier)
     */
    public DvIdentifier oeDvIdentifier(int posicao) {
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
     * @see #oeTemplateID()
     */
    public byte[] adapta(TemplateID rm) {
        Seed seed = Seed.serializa(meta[OE_TEMPLATEID]);
        seed.defineString(0, rm.getValue());
        return seed.array();
    }

    /**
     * Obtém objeto a partir da serialização correspondente.
     *
     * @return Objeto obtido da sequência de bytes.
     * @see #adapta(TemplateID)
     */
    public TemplateID oeTemplateID() {
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
        return new TerminologyID(s.obtemString(0));
    }

    /**
     * Converte objeto em sequência de bytes correspondente.
     *
     * @param rm O objeto a ser serializado.
     * @return Objeto serializado em sequência de bytes.
     * @see #oeCodePhrase(byte[])
     */
    public byte[] adapta(CodePhrase rm) {
        Seed seed = Seed.serializa(meta[OE_CODEPHRASE]);
        seed.defineString(0, rm.getCodeString());

        byte[] terminologyId = adapta(rm.getTerminologyId());
        seed.defineByteArray(1, terminologyId);

        return seed.array();
    }

    /**
     * Obtém objeto a partir da serialização correspondente.
     *
     * @param dados Objeto serializado em uma sequência de bytes.
     * @return Objeto obtido da sequência de bytes.
     * @see #adapta(CodePhrase)
     */
    public CodePhrase oeCodePhrase(byte[] dados) {
        String codeString = s.obtemString(0);

        s.setOffsetInicio(s.offset(1) + 4);

        TerminologyID tid = oeTerminologyID(null);

        return new CodePhrase(tid, codeString);
    }

    /**
     * Converte objeto em sequência de bytes correspondente.
     *
     * @param rm O objeto a ser serializado.
     * @return Objeto serializado em sequência de bytes.
     * @see #oeDvURI(byte[])
     */
    public byte[] adapta(DvURI rm) {
        Seed seed = Seed.serializa(meta[OE_DVURI]);
        seed.defineString(0, rm.getValue());

        return seed.array();
    }

    /**
     * Obtém objeto a partir da serialização correspondente.
     *
     * @param dados Objeto serializado em uma sequência de bytes.
     * @return Objeto obtido da sequência de bytes.
     * @see #adapta(CodePhrase)
     */
    public DvURI oeDvURI(byte[] dados) {
        Seed s = Seed.desserializa(dados);

        return new DvURI(s.obtemString(0));
    }

    /**
     * Converte objeto em sequência de bytes correspondente.
     *
     * @param rm O objeto a ser serializado.
     * @return Objeto serializado em sequência de bytes.
     * @see #oeDvEHRURI(byte[])
     */
    public byte[] adapta(DvEHRURI rm) {
        Seed seed = Seed.serializa(meta[OE_DVEHRURI]);
        seed.defineString(0, rm.getValue());

        return seed.array();
    }

    /**
     * Obtém objeto a partir da serialização correspondente.
     *
     * @param dados Objeto serializado em uma sequência de bytes.
     * @return Objeto obtido da sequência de bytes.
     * @see #adapta(CodePhrase)
     */
    public DvEHRURI oeDvEHRURI(byte[] dados) {
        Seed s = Seed.desserializa(dados);

        return new DvEHRURI(s.obtemString(0));
    }

    /**
     * Converte objeto em sequência de bytes correspondente.
     *
     * @param rm O objeto a ser serializado.
     * @return Objeto serializado em sequência de bytes.
     * @see #oeVersionTreeID(byte[])
     */
    public byte[] adapta(VersionTreeID rm) {
        Seed seed = Seed.serializa(meta[OE_VERSIONTREEID]);
        seed.defineString(0, rm.getValue());

        return seed.array();
    }

    /**
     * Obtém objeto a partir da serialização correspondente.
     *
     * @param dados Objeto serializado em uma sequência de bytes.
     * @return Objeto obtido da sequência de bytes.
     * @see #adapta(VersionTreeID)
     */
    public VersionTreeID oeVersionTreeID(byte[] dados) {
        Seed s = Seed.desserializa(dados);

        return new VersionTreeID(s.obtemString(0));
    }

    /**
     * Converte objeto em sequência de bytes correspondente.
     *
     * @param rm O objeto a ser serializado.
     * @return Objeto serializado em sequência de bytes.
     * @see #oeVersionTreeID(byte[])
     */
    public byte[] adapta(ArchetypeID rm) {
        Seed seed = Seed.serializa(meta[OE_ARCHETYPEID]);
        seed.defineString(0, rm.getValue());

        return seed.array();
    }

    /**
     * Obtém objeto a partir da serialização correspondente.
     *
     * @param dados Objeto serializado em uma sequência de bytes.
     * @return Objeto obtido da sequência de bytes.
     * @see #adapta(VersionTreeID)
     */
    public ArchetypeID oeArchetypeID(byte[] dados) {

        return new ArchetypeID(s.obtemString(0));
    }

    /**
     * Converte objeto em sequência de bytes correspondente.
     *
     * @param rm O objeto a ser serializado.
     * @return Objeto serializado em sequência de bytes.
     * @see #oeObjectVersionID(byte[]) (byte[])
     */
    public byte[] adapta(ObjectVersionID rm) {
        Seed seed = Seed.serializa(meta[OE_OBJECTVERSIONID]);
        seed.defineString(0, rm.getValue());

        return seed.array();
    }

    /**
     * Obtém objeto a partir da serialização correspondente.
     *
     * @param dados Objeto serializado em uma sequência de bytes.
     * @return Objeto obtido da sequência de bytes.
     * @see #adapta(ObjectVersionID)
     */
    public ObjectVersionID oeObjectVersionID(byte[] dados) {

        return new ObjectVersionID(s.obtemString(0));
    }


    /**
     * Converte objeto em sequência de bytes correspondente.
     *
     * @param rm O objeto a ser serializado.
     * @return Objeto serializado em sequência de bytes.
     * @see #oeHierObjectID(byte[])
     */
    public byte[] adapta(HierObjectID rm) {
        Seed seed = Seed.serializa(meta[OE_HIEROBJECTID]);
        seed.defineString(0, rm.getValue());

        return seed.array();
    }

    /**
     * Obtém objeto a partir da serialização correspondente.
     *
     * @param dados Objeto serializado em uma sequência de bytes.
     * @return Objeto obtido da sequência de bytes.
     * @see #adapta(HierObjectID)
     */
    public HierObjectID oeHierObjectID(byte[] dados) {

        return new HierObjectID(s.obtemString(0));
    }

    /**
     * Converte objeto em sequência de bytes correspondente.
     *
     * @param rm O objeto a ser serializado.
     * @return Objeto serializado em sequência de bytes.
     * @see #oePartyRef()
     */
    public byte[] adapta(PartyRef rm) {
        Seed seed = Seed.serializa(meta[OE_PARTYREF]);

        byte[] oidBytes = null;
        ObjectID oid = rm.getId();
        if (oid instanceof TemplateID) {
            oidBytes = adapta((TemplateID)oid);
        } else if (oid instanceof TerminologyID) {
            oidBytes = adapta((TerminologyID)oid);
        } else if (oid instanceof GenericID) {
            oidBytes = adapta((GenericID)oid);
        } else if (oid instanceof ArchetypeID) {
            oidBytes = adapta((ArchetypeID)oid);
        } else if (oid instanceof ObjectVersionID) {
            oidBytes = adapta((ObjectVersionID) oid);
        } else if (oid instanceof HierObjectID) {
            oidBytes = adapta((HierObjectID) oid);
        }

        // Posição 0 (ObjectID)
        seed.defineByteArray(0, oidBytes);

        // Posição 1 (String)
        seed.defineString(1, rm.getNamespace());

        // Posição 2 (String)
        seed.defineString(2, rm.getType());

        return seed.array();
    }

    /**
     * Obtém objeto a partir da serialização correspondente.
     *
     * @return Objeto obtido da sequência de bytes.
     * @see #adapta(VersionTreeID)
     */
    public PartyRef oePartyRef() {

        // Posição do objeto PartyRef
        int prInicio = s.getOffsetInicio();

        // Posição inicial do ObjectID (metainformação)
        // (consultar processo de "empacotamento")
        int inicio = prInicio + 5 + 4;

        // Tipo do objeto armazenado (subclasse de ObjectID)
        byte tipo = s.getTipo(inicio);

        ObjectID oid = null;
        s.setOffsetInicio(inicio);
        if (tipo == OE_TEMPLATEID) {
            oid = oeTemplateID();
        } else if (tipo == OE_TERMINOLOGYID) {
            oid = oeTerminologyID(null);
        } else if (tipo == OE_GENERICID) {
            oid = oeGenericID(null);
        } else if (tipo == OE_ARCHETYPEID) {
            oid = oeArchetypeID(null);
        } else if (tipo == OE_OBJECTVERSIONID) {
            oid = oeObjectVersionID(null);
        } else if (tipo == OE_HIEROBJECTID) {
            oid = oeHierObjectID(null);
        }

        s.setOffsetInicio(prInicio);
        return new PartyRef(oid, s.obtemString(2));
    }
}