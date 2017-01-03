package com.github.kyriosdata.oes;

/**
 * Manipulação de operações sobre o header de um registro.
 */
public class Record {

    private Block bloco;
    private int posicao;

    public void setRecord(Block b, int pos) {
        bloco = b;
        posicao = pos;
    }

    /**
     * Obtém o tipo do registro.
     * @return O tipo do registro.
     */
    public int getTipo() {
        return bloco.getByte(posicao);
    }

    public void setTipo(byte tipo) {
        bloco.setByte(posicao, tipo);
    }

    public int getTamanho() {
        return bloco.getInt(posicao + 1);
    }

    public void setTamanho(int tamanho) {
        bloco.setInt(posicao + 1, tamanho);
    }

    /**
     * Recupera a posição inicial do campo do registro.
     *
     * @param campo A ordem do campo no registro.
     * @return A posição do primeiro byte do campo.
     */
    public static int getPosicaoCampo(int campo) {
        return 0;
    }
}
