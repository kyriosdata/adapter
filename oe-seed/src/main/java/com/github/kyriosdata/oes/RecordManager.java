package com.github.kyriosdata.oes;

/**
 * Define os tipos de registros correspondentes aos objetos do
 * Modelo de Referência do openEHR e oferece serviços para a
 * criação e consulta de registros.
 * <p>
 * <p>Os campos de um registro estão dispostos em uma sequência.
 * Primeiro seguem os campos de tamanho fixo e só então aqueles
 * de tamanho variável.
 * <p>
 * <p>Os campos de tamanho variável, se presentes, devem ser
 * analisados e aquele identificado como de maior frequência de
 * uso deve ser o primeiro deles.
 */
public class RecordManager {

    /**
     * Identificação dos tipos dos campos de cada um dos
     * tipos de registro.
     */
    public static byte[][] types = {
      /* 0 */ {1, 2, 3}
    };

    /**
     * Deslocamentos dos campos (primeiros bytes)
     * para cada um dos tipos de registro.
     * <p>
     * <p>Observe que o deslocamento do primeiro campo
     * sempre é 0.
     * <p>
     * <p>Os campos de tamanho variável não estão
     * incluídos, exceto o primeiro deles. Observe que
     * para os demais campos de tamanho variável não
     * é possível previamente estabelecer a posição
     * de início de cada um deles, pois essa
     * informação depende do tamanho dos campos do
     * registro em questão.
     */
    public static byte[][] offsets = {
        /* 00 */ {0, 1, 3},
        /* 01 */ {0, 1, 3},
        /* 02 */ {0, 1, 3},
        /* 03 */ {0, 1, 3},
        /* 04 */ {0, 1, 3},
        /* 05 */ {0, 1, 3},
        /* 06 */ {0, 1, 3},
        /* 07 */ {0, 1, 3},
        /* 08 */ {0, 1, 3},
        /* 09 */ {0, 1, 3},
        /* 10 */ {0, 1, 3},
        /* 11 */ {0, 1, 3},
        /* 12 */ {0, 1, 3},
        /* 13 */ {0, 1, 3},
        /* 14 */ {0, 1, 3},
        /* 15 */ {0, 1, 3}
    };
}
