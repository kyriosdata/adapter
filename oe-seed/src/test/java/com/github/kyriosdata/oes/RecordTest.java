package com.github.kyriosdata.oes;

import org.junit.jupiter.api.Test;

public class RecordTest {

    @Test
    public void criaRegistroTamanhoFixo() {

        // Bloco e a posição do registro nele
        byte[] block = new byte[1024];
        int offset = 0;

        // Vamos "embrulhar" o bloco
        Record r = new Record();
        r.setRecord(block, offset);

        r.setTipo((byte)0);
        r.setTamanho(10);
    }
}

