package com.github.kyriosdata.oes;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BlockTest {

    @Test
    public void bytes() {
        Block b = new Block(new byte[1024]);

        b.setByte(0, (byte)127);
        b.setByte(1023, (byte)-1);
        assertEquals(127, b.getByte(0));
        assertEquals(-1, b.getByte(1023));
    }

    @Test
    public void shorts() {
        Block b = new Block(new byte[1024]);

        b.setShort(0, (short)4096);
        b.setShort(1022, (short)8192);
        assertEquals(4096, b.getShort(0));
        assertEquals(8192, b.getShort(1022));
    }

    @Test
    public void ints() {
        Block b = new Block(new byte[1024]);

        b.setInt(0, 56789);
        b.setInt(1020, 45678);
        assertEquals(56789, b.getInt(0));
        assertEquals(45678, b.getInt(1020));
    }
}

