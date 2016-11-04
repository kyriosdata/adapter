/*
 * Copyright (c) 2016 Fábio Nogueira de Lucena
 *
 * Fábrica de Software - Instituto de Informática (UFG)
 * Creative Commons Attribution 4.0 International License.
 */

package com.github.kyriosdata.oes;

import com.github.kyriosdata.seed.Seed;
import org.openehr.rm.datatypes.basic.DvBoolean;
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

    byte[][] meta = new byte[][] {
            { DV_BOOLEAN, 1, Seed.BOOLEAN }
    };

    /**
     * Cria um objeto em conformidade com o tipo definido
     * pelo Modelo de Referência do openEHR.
     *
     */
    public Adaptador() {
    }

    public byte[] adapta(DvBoolean rm) {
        Seed seed = Seed.serializa(meta[DV_BOOLEAN]);
        seed.defineBoolean(0, rm.getValue());
        return seed.array();
    }

    public DvBoolean dvBoolean(byte[] dados) {
        Seed s = Seed.desserializa(dados);
        return new DvBoolean(s.obtemBoolean(0));
    }
}