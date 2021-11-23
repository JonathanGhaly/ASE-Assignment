public class Area {
    String areaName;

    Area() {
        areaName = "";
    }

    Area(String areaName) {
        this.areaName = areaName;
    }

    @Override
    public String toString(){
        return areaName;
    }

}
