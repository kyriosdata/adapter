package com.github.kyriosdata.oes;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FieldManagerTest {

    @Test
    public void bytes() {
        byte[] buffer = new byte[1024];

        FieldManager.setByte(buffer, 0, (byte)127);
        FieldManager.setByte(buffer, 1023, (byte)-1);
        assertEquals(127, FieldManager.getByte(buffer, 0));
        assertEquals(-1, FieldManager.getByte(buffer, 1023));
    }

    @Test
    public void shorts() {
        byte[] buffer = new byte[1024];
        FieldManager.setShort(buffer, 0, (short)4096);
        FieldManager.setShort(buffer, 1022, (short)8192);
        assertEquals(4096, FieldManager.getShort(buffer, 0));
        assertEquals(8192, FieldManager.getShort(buffer, 1022));
    }

    @Test
    public void ints() {
        byte[] buffer = new byte[1024];
        FieldManager.setInt(buffer, 0, 56789);
        FieldManager.setInt(buffer, 1020, 45678);
        assertEquals(56789, FieldManager.getInt(buffer, 0));
        assertEquals(45678, FieldManager.getInt(buffer, 1020));
    }
}

