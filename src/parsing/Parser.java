package parsing;


import org.jsoup.nodes.Element;

import java.io.IOException;

public interface Parser<T> {
    T parse(Element document) throws IOException;
}
