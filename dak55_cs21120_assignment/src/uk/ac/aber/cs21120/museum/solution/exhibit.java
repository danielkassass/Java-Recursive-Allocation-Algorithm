package uk.ac.aber.cs21120.museum.solution;

public class exhibit {
    //This is the class i will be storing the information of exhibits
    private String name;
    private String exhibitionTheme;
    private int exhibitionSize;
    private int visitors;

    //constructor
    public exhibit(String name, String exhibitionTheme, int exhibitionSize, int visitors) {
        this.name = name;
        this.exhibitionTheme = exhibitionTheme;
        this.exhibitionSize = exhibitionSize;
        this.visitors = visitors;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setExhibitionTheme(String exhibitionTheme) {
        this.exhibitionTheme = exhibitionTheme;
    }

    public void setExhibitionSize(int exhibitionSize) {
        this.exhibitionSize = exhibitionSize;
    }

    public void setVisitors(int visitors) {
        this.visitors = visitors;
    }

    public String getName() {
        return name;
    }
    public String getExhibitionTheme() {
        return exhibitionTheme;
    }

    public int getExhibitionSize() {
        return exhibitionSize;
    }

    public int getVisitors() {
        return visitors;
    }

    @Override
    public String toString() {
        String toString = "exhibit{" +
                "name='" + name + '\'' +
                ", exhibitionTheme='" + exhibitionTheme + '\'' +
                ", exhibitionSize=" + exhibitionSize +
                ", visitors=" + visitors +
                '}';
        return toString;
    }
}
