package com.github.kyriosdata.oes;

import java.nio.ByteBuffer;

/**
 * Oferece operações básicas sobre um vetor de bytes.
 */
public class Block {
    private ByteBuffer buffer;

    public Block(byte[] buffer) {
        this.buffer = ByteBuffer.wrap(buffer);
    }

    public byte getByte(int pos) {
        return buffer.get(pos);
    }

    public void setByte(int pos, byte valor) {
        buffer.put(pos, valor);
    }

    public int getShort(int pos) {
        return buffer.getShort(pos);
    }

    public void setShort(int pos, short valor) {
        buffer.putShort(pos, valor);
    }

    public int getInt(int pos) {
        return buffer.getInt(pos);
    }

    public void setInt(int pos, int valor) {
        buffer.putInt(pos, valor);
    }
}
