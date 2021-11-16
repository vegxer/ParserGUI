package commandHandler;

import java.io.IOException;
import java.util.ArrayList;

public interface Executable {
    void execute(ArrayList<String> commandArgs) throws IOException;
}
