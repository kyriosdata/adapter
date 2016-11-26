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

    public static final int OE_ACCESSGROUPREF = 18;
    public static final int OE_ARCHETYPEID = 13;
    public static final int OE_CODEPHRASE = 8;
    public static final int OE_DVBOOLEAN = 0;
    public static final int OE_DVIDENTIFIER = 1;
    public static final int OE_DVEHRURI = 10;
    public static final int OE_DVURI = 9;
    public static final int OE_GENERICID = 6;
    public static final int OE_HIEROBJECTID = 15;
    public static final int OE_INTERNETID = 2;
    public static final int OE_ISO_OID = 3;
    public static final int OE_LOCATABLEREF= 17;
    public static final int OE_OBJECTREF= 16;
    public static final int OE_OBJECTVERSIONID= 14;
    public static final int OE_PARTYREF = 12;
    public static final int OE_TEMPLATEID = 7;
    public static final int OE_TERMINOLOGYID = 5;
    public static final int OE_UUID = 4;
    public static final int OE_VERSIONTREEID = 11;

    /**
     * Uma entrada em meta-informação para cada classe do MR.
     *
     * Meta-informação para todos os tipos considerados pelo Adaptador.
     * Primeiro byte não é empregado.
     * Segundo byte é a quantidade de campos.
     * Terceiro byte em diante indica os tipos de cada um dos campos.
     */
    private byte[][] meta;

    private byte[] buffer;
    private Seed s;

    /**
     * Cria adaptador para recuperar MR a partir de
     * vetor de bytes.
     *
     * @param bytes Vetor contendo serialização de objetos MR.
     *              O vetor deve ter sido produzido por um dos
     *              métodos set.
     */
    public Adaptador(byte[] bytes) {
        super();
        buffer = bytes;
        s = Seed.desserializa(buffer);
    }

    /**
     * Cria adaptador para conversão do MR para
     * vetor de bytes.
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
        meta[OE_PARTYREF] = new byte[] {OE_PARTYREF, 1, Seed.VETOR};
        meta[OE_ARCHETYPEID] = new byte[] {OE_ARCHETYPEID, 1, Seed.STRING};
        meta[OE_OBJECTVERSIONID] = new byte[] {OE_OBJECTVERSIONID, 1, Seed.STRING};
        meta[OE_HIEROBJECTID] = new byte[] {OE_HIEROBJECTID, 1, Seed.STRING};
        meta[OE_OBJECTREF] = new byte[] {OE_OBJECTREF, 3, Seed.VETOR, Seed.STRING, Seed.STRING};
        meta[OE_LOCATABLEREF] = new byte[] {OE_LOCATABLEREF, 3, Seed.VETOR, Seed.STRING};
        meta[OE_ACCESSGROUPREF] = new byte[] {OE_ACCESSGROUPREF, 1, Seed.VETOR};
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
     * @see #getDvBoolean(int)
     */
    public byte[] set(DvBoolean rm) {
        Seed seed = Seed.serializa(meta[OE_DVBOOLEAN]);
        seed.defineBoolean(0, rm.getValue());
        return seed.array();
    }

    /**
     * Obtém objeto a partir da serialização correspondente.
     *
     * @param ordem Ordem do objeto no registro.
     * @return Objeto obtido da sequência de bytes.
     * @see #set(DvBoolean)
     */
    public DvBoolean getDvBoolean(int ordem) {
        return new DvBoolean(s.obtemBoolean(0));
    }

    /**
     * Converte objeto em sequência de bytes correspondente.
     *
     * @param rm O objeto a ser serializado.
     * @return Objeto serializado em sequência de bytes.
     * @see #getDvIdentifier(int)
     */
    public byte[] set(DvIdentifier rm) {
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
     * @see #set(DvIdentifier)
     */
    public DvIdentifier getDvIdentifier(int posicao) {
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
     * @see #getGenericID(byte[])
     */
    public byte[] set(GenericID rm) {
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
     * @see #set(GenericID)
     */
    public GenericID getGenericID(byte[] dados) {
        return new GenericID(s.obtemString(0), s.obtemString(1));
    }

    /**
     * Converte objeto em sequência de bytes correspondente.
     *
     * @param rm O objeto a ser serializado.
     * @return Objeto serializado em sequência de bytes.
     * @see #getInternetID(byte[])
     */
    public byte[] set(InternetID rm) {
        Seed seed = Seed.serializa(meta[OE_INTERNETID]);
        seed.defineString(0, rm.getValue());
        return seed.array();
    }

    /**
     * Obtém objeto a partir da serialização correspondente.
     *
     * @param dados Objeto serializado em uma sequência de bytes.
     * @return Objeto obtido da sequência de bytes.
     * @see #set(InternetID)
     */
    public InternetID getInternetID(byte[] dados) {
        return new InternetID(s.obtemString(0));
    }

    /**
     * Converte objeto em sequência de bytes correspondente.
     *
     * @param rm O objeto a ser serializado.
     * @return Objeto serializado em sequência de bytes.
     * @see #getISO_OID(byte[])
     */
    public byte[] set(ISO_OID rm) {
        Seed seed = Seed.serializa(meta[OE_ISO_OID]);
        seed.defineString(0, rm.getValue());
        return seed.array();
    }

    /**
     * Obtém objeto a partir da serialização correspondente.
     *
     * @param dados Objeto serializado em uma sequência de bytes.
     * @return Objeto obtido da sequência de bytes.
     * @see #set(ISO_OID)
     */
    public ISO_OID getISO_OID(byte[] dados) {
        return new ISO_OID(s.obtemString(0));
    }

    /**
     * Converte objeto em sequência de bytes correspondente.
     *
     * @param rm O objeto a ser serializado.
     * @return Objeto serializado em sequência de bytes.
     * @see #getUUID(byte[])
     */
    public byte[] set(UUID rm) {
        Seed seed = Seed.serializa(meta[OE_UUID]);
        seed.defineString(0, rm.getValue());
        return seed.array();
    }

    /**
     * Obtém objeto a partir da serialização correspondente.
     *
     * @param dados Objeto serializado em uma sequência de bytes.
     * @return Objeto obtido da sequência de bytes.
     * @see #set(ISO_OID)
     */
    public UUID getUUID(byte[] dados) {
        return new UUID(s.obtemString(0));
    }

    /**
     * Converte objeto em sequência de bytes correspondente.
     *
     * @param rm O objeto a ser serializado.
     * @return Objeto serializado em sequência de bytes.
     * @see #getTemplateID()
     */
    public byte[] set(TemplateID rm) {
        Seed seed = Seed.serializa(meta[OE_TEMPLATEID]);
        seed.defineString(0, rm.getValue());
        return seed.array();
    }

    /**
     * Obtém objeto a partir da serialização correspondente.
     *
     * @return Objeto obtido da sequência de bytes.
     * @see #set(TemplateID)
     */
    public TemplateID getTemplateID() {
        return new TemplateID(s.obtemString(0));
    }

    /**
     * Converte objeto em sequência de bytes correspondente.
     *
     * @param rm O objeto a ser serializado.
     * @return Objeto serializado em sequência de bytes.
     * @see #getTerminologyID(byte[])
     */
    public byte[] set(TerminologyID rm) {
        Seed seed = Seed.serializa(meta[OE_TERMINOLOGYID]);
        seed.defineString(0, rm.getValue());
        return seed.array();
    }

    /**
     * Obtém objeto a partir da serialização correspondente.
     *
     * @param dados Objeto serializado em uma sequência de bytes.
     * @return Objeto obtido da sequência de bytes.
     * @see #set(TerminologyID)
     */
    public TerminologyID getTerminologyID(byte[] dados) {
        return new TerminologyID(s.obtemString(0));
    }

    /**
     * Converte objeto em sequência de bytes correspondente.
     *
     * @param rm O objeto a ser serializado.
     * @return Objeto serializado em sequência de bytes.
     * @see #getCodePhrase(byte[])
     */
    public byte[] set(CodePhrase rm) {
        Seed seed = Seed.serializa(meta[OE_CODEPHRASE]);
        seed.defineString(0, rm.getCodeString());

        byte[] terminologyId = set(rm.getTerminologyId());
        seed.defineByteArray(1, terminologyId);

        return seed.array();
    }

    /**
     * Obtém objeto a partir da serialização correspondente.
     *
     * @param dados Objeto serializado em uma sequência de bytes.
     * @return Objeto obtido da sequência de bytes.
     * @see #set(CodePhrase)
     */
    public CodePhrase getCodePhrase(byte[] dados) {
        String codeString = s.obtemString(0);

        s.setOffsetInicio(s.offset(1) + 4);

        TerminologyID tid = getTerminologyID(null);

        return new CodePhrase(tid, codeString);
    }

    /**
     * Converte objeto em sequência de bytes correspondente.
     *
     * @param rm O objeto a ser serializado.
     * @return Objeto serializado em sequência de bytes.
     * @see #getDvURI(byte[])
     */
    public byte[] set(DvURI rm) {
        Seed seed = Seed.serializa(meta[OE_DVURI]);
        seed.defineString(0, rm.getValue());

        return seed.array();
    }

    /**
     * Obtém objeto a partir da serialização correspondente.
     *
     * @param dados Objeto serializado em uma sequência de bytes.
     * @return Objeto obtido da sequência de bytes.
     * @see #set(CodePhrase)
     */
    public DvURI getDvURI(byte[] dados) {
        return new DvURI(s.obtemString(0));
    }

    /**
     * Converte objeto em sequência de bytes correspondente.
     *
     * @param rm O objeto a ser serializado.
     * @return Objeto serializado em sequência de bytes.
     * @see #getDvEHRURI(byte[])
     */
    public byte[] set(DvEHRURI rm) {
        Seed seed = Seed.serializa(meta[OE_DVEHRURI]);
        seed.defineString(0, rm.getValue());

        return seed.array();
    }

    /**
     * Obtém objeto a partir da serialização correspondente.
     *
     * @param dados Objeto serializado em uma sequência de bytes.
     * @return Objeto obtido da sequência de bytes.
     * @see #set(CodePhrase)
     */
    public DvEHRURI getDvEHRURI(byte[] dados) {
        return new DvEHRURI(s.obtemString(0));
    }

    /**
     * Converte objeto em sequência de bytes correspondente.
     *
     * @param rm O objeto a ser serializado.
     * @return Objeto serializado em sequência de bytes.
     * @see #getVersionTreeID(byte[])
     */
    public byte[] set(VersionTreeID rm) {
        Seed seed = Seed.serializa(meta[OE_VERSIONTREEID]);
        seed.defineString(0, rm.getValue());

        return seed.array();
    }

    /**
     * Obtém objeto a partir da serialização correspondente.
     *
     * @param dados Objeto serializado em uma sequência de bytes.
     * @return Objeto obtido da sequência de bytes.
     * @see #set(VersionTreeID)
     */
    public VersionTreeID getVersionTreeID(byte[] dados) {
        return new VersionTreeID(s.obtemString(0));
    }

    /**
     * Converte objeto em sequência de bytes correspondente.
     *
     * @param rm O objeto a ser serializado.
     * @return Objeto serializado em sequência de bytes.
     * @see #getVersionTreeID(byte[])
     */
    public byte[] set(ArchetypeID rm) {
        Seed seed = Seed.serializa(meta[OE_ARCHETYPEID]);
        seed.defineString(0, rm.getValue());

        return seed.array();
    }

    /**
     * Obtém objeto a partir da serialização correspondente.
     *
     * @param dados Objeto serializado em uma sequência de bytes.
     * @return Objeto obtido da sequência de bytes.
     * @see #set(VersionTreeID)
     */
    public ArchetypeID getArchetypeID(byte[] dados) {
        return new ArchetypeID(s.obtemString(0));
    }

    /**
     * Converte objeto em sequência de bytes correspondente.
     *
     * @param rm O objeto a ser serializado.
     * @return Objeto serializado em sequência de bytes.
     * @see #getObjectVersionID(byte[]) (byte[])
     */
    public byte[] set(ObjectVersionID rm) {
        Seed seed = Seed.serializa(meta[OE_OBJECTVERSIONID]);
        seed.defineString(0, rm.getValue());

        return seed.array();
    }

    /**
     * Obtém objeto a partir da serialização correspondente.
     *
     * @param dados Objeto serializado em uma sequência de bytes.
     * @return Objeto obtido da sequência de bytes.
     * @see #set(ObjectVersionID)
     */
    public ObjectVersionID getObjectVersionID(byte[] dados) {
        return new ObjectVersionID(s.obtemString(0));
    }

    /**
     * Converte objeto em sequência de bytes correspondente.
     *
     * @param rm O objeto a ser serializado.
     * @return Objeto serializado em sequência de bytes.
     * @see #getHierObjectID(byte[])
     */
    public byte[] set(HierObjectID rm) {
        Seed seed = Seed.serializa(meta[OE_HIEROBJECTID]);
        seed.defineString(0, rm.getValue());

        return seed.array();
    }

    /**
     * Obtém objeto a partir da serialização correspondente.
     *
     * @param dados Objeto serializado em uma sequência de bytes.
     * @return Objeto obtido da sequência de bytes.
     * @see #set(HierObjectID)
     */
    public HierObjectID getHierObjectID(byte[] dados) {
        return new HierObjectID(s.obtemString(0));
    }

    /**
     * Converte objeto em sequência de bytes correspondente.
     *
     * @param rm O objeto a ser serializado.
     * @return Objeto serializado em sequência de bytes.
     * @see #getPartyRef()
     */
    public byte[] set(PartyRef rm) {
        Seed seed = Seed.serializa(meta[OE_PARTYREF]);

        // ObjectRef (bytes)
        byte[] orBytes = adaptaBase((ObjectRef)rm);

        // Posição 0 (ObjectRef)
        seed.defineByteArray(0, orBytes);

        return seed.array();
    }

    /**
     * Converte objeto em sequência de bytes correspondente.
     *
     * @param rm O objeto a ser serializado.
     * @return Objeto serializado em sequência de bytes.
     * @see #getLocatableRef()
     */
    public byte[] set(LocatableRef rm) {
        Seed seed = Seed.serializa(meta[OE_LOCATABLEREF]);

        // ObjectRef (bytes)
        byte[] orBytes = adaptaBase(rm);

        // Posição 0 (ObjectRef)
        seed.defineByteArray(0, orBytes);

        // Posição 1 (String)
        seed.defineString(1, rm.getPath());

        return seed.array();
    }

    /**
     * Converte objeto em sequência de bytes correspondente.
     *
     * @param rm O objeto a ser serializado.
     * @return Objeto serializado em sequência de bytes.
     * @see #getPartyRef()
     */
    public byte[] set(AccessGroupRef rm) {
        Seed seed = Seed.serializa(meta[OE_ACCESSGROUPREF]);

        // ObjectRef (bytes)
        byte[] orBytes = adaptaBase((ObjectRef)rm);

        // Posição 0 (ObjectRef)
        seed.defineByteArray(0, orBytes);

        return seed.array();
    }

    /**
     * Converte objeto em sequência de bytes correspondente.
     *
     * @param rm O objeto a ser serializado.
     * @return Objeto serializado em sequência de bytes.
     * @see #getPartyRef()
     */
    public byte[] set(ObjectRef rm) {
        if (rm instanceof PartyRef) {
            return set((PartyRef)rm);
        } else if (rm instanceof LocatableRef) {
            return set((LocatableRef)rm);
        }  else if (rm instanceof AccessGroupRef) {
            return set((AccessGroupRef)rm);
        }

        return adaptaBase(rm);
    }

    private byte[] adaptaBase(ObjectRef rm) {
        Seed seed = Seed.serializa(meta[OE_OBJECTREF]);

        byte[] oidBytes = set(rm.getId());

        // Posição 0 (ObjectID)
        seed.defineByteArray(0, oidBytes);

        // Posição 1 (String)
        seed.defineString(1, rm.getNamespace());

        // Posição 2 (String)
        seed.defineString(2, rm.getType());

        return seed.array();
    }

    public ObjectRef getObjectRef() {

        // Posição do ObjectRef
        int orInicio = s.getOffsetInicio();

        // Posição inicial do ObjectID (metainformação)
        // (consultar processo de "empacotamento")
        int inicio = orInicio + 5 + 4;

        // Tipo do objeto armazenado (subclasse de ObjectID)
        byte tipo = s.getTipo(inicio);

        ObjectID oid = getObjectID(inicio, tipo);

        s.setOffsetInicio(orInicio);

        String namespace = s.obtemString(1);
        String type = s.obtemString(2);
        return new ObjectRef(oid, namespace, type);
    }

    /**
     * Obtém objeto a partir da serialização correspondente.
     *
     * @return Objeto obtido da sequência de bytes.
     * @see #set(LocatableRef)
     */
    public LocatableRef getLocatableRef() {

        // Posição do LocatableRef
        int lrInicio = s.getOffsetInicio();

        // Posição inicial do ObjectRef (metainformação)
        // Metainformação:  3
        // Tamanho do vetor de bytes: 4
        int inicio = lrInicio + 3 + 4;
        s.setOffsetInicio(inicio);

        // Primeiro campo (0): ObjectRef
        ObjectRef or = getObjectRef();
        ObjectVersionID ovid = (ObjectVersionID)or.getId();
        String namespace = or.getNamespace();
        String type = or.getType();

        // Segundo campo (1): String
        String path = s.obtemString(1);

        s.setOffsetInicio(lrInicio);
        return new LocatableRef(ovid, namespace, type, path);
    }

    /**
     * Obtém objeto a partir da serialização correspondente.
     *
     * @return Objeto obtido da sequência de bytes.
     * @see #set(VersionTreeID)
     */
    public PartyRef getPartyRef() {

        // Posição do objeto PartyRef
        int prInicio = s.getOffsetInicio();

        // Posição inicial do ObjectID (metainformação)
        // Metainformação: 3
        // Tamanho do vetor de bytes do objeto: 4
        int inicio = prInicio + 3 + 4;

        s.setOffsetInicio(inicio);

        // Recupera ObjectRef
        ObjectRef oid = getObjectRef();

        s.setOffsetInicio(prInicio);
        return new PartyRef(oid.getId(), oid.getType());
    }

    /**
     * Obtém objeto a partir da serialização correspondente.
     *
     * @return Objeto obtido da sequência de bytes.
     * @see #set(VersionTreeID)
     */
    public AccessGroupRef getAccessGroupRef() {

        // Posição do objeto PartyRef
        int prInicio = s.getOffsetInicio();

        // Posição inicial do ObjectID (metainformação)
        // Metainformação: 3
        // Tamanho do vetor de bytes do objeto: 4
        int inicio = prInicio + 3 + 4;

        // Define posição inicial do ObjectRef
        s.setOffsetInicio(inicio);

        // Recupera ObjectRef
        ObjectRef oid = getObjectRef();

        s.setOffsetInicio(prInicio);
        return new AccessGroupRef(oid.getId());
    }

    private byte[] set(ObjectID oid) {
        byte[] oidBytes = null;
        if (oid instanceof TemplateID) {
            oidBytes = set((TemplateID)oid);
        } else if (oid instanceof TerminologyID) {
            oidBytes = set((TerminologyID)oid);
        } else if (oid instanceof GenericID) {
            oidBytes = set((GenericID)oid);
        } else if (oid instanceof ArchetypeID) {
            oidBytes = set((ArchetypeID)oid);
        } else if (oid instanceof ObjectVersionID) {
            oidBytes = set((ObjectVersionID) oid);
        } else if (oid instanceof HierObjectID) {
            oidBytes = set((HierObjectID) oid);
        }

        return oidBytes;
    }

    private ObjectID getObjectID(int inicio, byte tipo) {
        ObjectID oid = null;
        s.setOffsetInicio(inicio);
        if (tipo == OE_TEMPLATEID) {
            oid = getTemplateID();
        } else if (tipo == OE_TERMINOLOGYID) {
            oid = getTerminologyID(null);
        } else if (tipo == OE_GENERICID) {
            oid = getGenericID(null);
        } else if (tipo == OE_ARCHETYPEID) {
            oid = getArchetypeID(null);
        } else if (tipo == OE_OBJECTVERSIONID) {
            oid = getObjectVersionID(null);
        } else if (tipo == OE_HIEROBJECTID) {
            oid = getHierObjectID(null);
        }

        return oid;
    }
}