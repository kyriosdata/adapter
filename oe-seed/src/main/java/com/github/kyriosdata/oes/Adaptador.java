/*
 * Copyright (c) 2016 Fábio Nogueira de Lucena
 * Fábrica de Software - Instituto de Informática (UFG)
 * Creative Commons Attribution 4.0 International License.
 */

package com.github.kyriosdata.oes;

import com.github.kyriosdata.seed.Seed;
import org.openehr.rm.datatypes.basic.DvBoolean;
import org.openehr.rm.ehr.EHR;
import org.openehr.rm.support.measurement.MeasurementService;
import org.openehr.terminology.TerminologySource;

/**
 * Classe que adapta MR do openEHR para Seed e vice-versa.
 */
public class Adaptador {

    public static final int DV_BOOLEAN = 0;

    private MeasurementService ms;
    private TerminologySource ts;

    // Referência para a raiz.
    private int raiz = -1;

    // Serviço de serialização
    private Seed seed;

    /**
     * Cria um objeto em conformidade com o tipo definido
     * pelo Modelo de Referência do openEHR.
     *
     * @param tipo Tipo do objeto (MR do openEHR).
     *
     * @throws IllegalArgumentException Se tipo for inválido.
     */
    public Adaptador(int tipo) {
        byte[] metaInformacao = null;
        switch (tipo) {
            case DV_BOOLEAN:
                metaInformacao = new byte[] { 1, Seed.BOOLEAN };
                break;
            default:
                throw new IllegalArgumentException("tipo");
        }

        EHR erh;
        DvBoolean dvb;
        seed = Seed.serializa(metaInformacao);
    }

    /**
     * Cria dvboolean.
     *
     * @param valor Valor lógico.
     */
    public void dvBoolean(boolean valor) {
        seed.defineBoolean(0, valor);
    }
}