package com.github.kyriosdata.oes;

import java.nio.ByteBuffer;

/**
 * Oferece operações básicas sobre um vetor de bytes.
 */
public class FieldManager {

    // TODO definir constantes para cada um dos tipos possíveis.
    // TODO fazer inline em vez de usar bytebuffer, mais eficiente?
    // TODO inclui conhecimento sobre o tamanho de cada um dos tipos em bytes.
    // TODO não deveria incluir list e map?

    public static byte getByte(byte[] buffer, int pos) {
        return ByteBuffer.wrap(buffer).get(pos);
    }

    public static void setByte(byte[] buffer, int pos, byte valor) {

        ByteBuffer.wrap(buffer).put(pos, valor);
    }

    public static int getShort(byte[] buffer, int pos) {
        return ByteBuffer.wrap(buffer).getShort(pos);
    }

    public static void setShort(byte[] buffer, int pos, short valor) {
        ByteBuffer.wrap(buffer).putShort(pos, valor);
    }

    public static int getInt(byte[] buffer, int pos) {

        return ByteBuffer.wrap(buffer).getInt(pos);
    }

    public static void setInt(byte[] buffer, int pos, int valor) {

        ByteBuffer.wrap(buffer).putInt(pos, valor);
    }
}
