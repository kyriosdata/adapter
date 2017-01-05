package com.github.kyriosdata.oes;

/**
 * Manipulação de operações sobre o header de um registro.
 */
public class Record {

    private byte[] bloco;
    private int posicao;
    private byte[] offsets;
    private int sizeOfHeader;

    public void setRecord(byte[] block, int offset) {
        bloco = block;
        posicao = offset;
        offsets = RecordManager.offsets[getTipo()];
    }

    /**
     * Obtém o tipo do registro.
     * @return O tipo do registro.
     */
    public int getTipo() {
        return FieldManager.getByte(bloco, posicao);
    }

    public void setTipo(byte tipo) {
        FieldManager.setByte(bloco, posicao, tipo);
    }

    public int getTamanho() {
        return FieldManager.getInt(bloco, posicao + 1);
    }

    public void setTamanho(int tamanho) {
        FieldManager.setInt(bloco, posicao + 1, tamanho);
    }

    /**
     * Recupera a posição inicial do campo do registro.
     *
     * @param campo A ordem do campo no registro. A primeira
     *              é zero. A última ordem válida é aquela do
     *              último campo, caso todos sejam de tamanho
     *              fixo ou a ordem do primeiro campo de
     *              tamanho variável.
     * @return A posição do primeiro byte do campo.
     */
    public int getPosicaoCampo(int campo) {
        return sizeOfHeader + offsets[campo];
    }
}
