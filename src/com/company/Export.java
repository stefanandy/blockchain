package com.company;

import java.io.IOException;

public interface Export {

    public void writeBlock(Block block) throws IOException;
    public void closeFile();
}
