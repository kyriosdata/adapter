package com.github.kyriosdata.oes;

/**
 * Esquema de um registro.
 *
 * <p>Os campos de um registro estão dispostos em uma sequência.
 * Primeiro seguem os campos de tamanho fixo e só então aqueles
 * de tamanho variável.
 *
 * <p>Os campos de tamanho variável, se presentes, devem ser
 * analisados e aquele identificado como de maior frequência de
 * uso deve ser o primeiro deles.
 */
public class RecordTypes {

    /**
     * Identificação dos tipos dos campos de cada um dos
     * tipos de registro.
     */
    public static byte[][] types = {
      /* 0 */ { 1, 2, 3 }
    };

    /**
     * Deslocamentos dos campos (primeiros bytes)
     * para cada um dos tipos de registro.
     *
     * <p>Observe que o deslocamento do primeiro campo
     * sempre é 0 e, portanto, o deslocamento do
     * primeiro campo não é armazenado.
     *
     * <p>Os campos de tamanho variável não estão
     * incluídos, exceto o primeiro deles. Observe que
     * para os demais campos de tamanho variável não
     * é possível previamente estabelecer a posição
     * de início de cada um deles, pois essa
     * informação depende do tamanho dos campos do
     * registro em questão.
     */
    public static byte[][] offsets = {
      /* 0 */ { }
    };
}
