package Trailer;

/**
 * Created by Manish Menaria on 12-Jun-16.
 */
public class Trailer {
    String name,size,source,type;

    public Trailer(String name,String size,String source,String type) {
        this.name = name;
        this.size = size;
        this.source =source;
        this.type=type;
    }

    public String getName() {
        return name;
    }

    public String getSize() {
        return size;
    }

    public String getSource() {
        return source;
    }

    public String getType() {
        return type;
    }
}
