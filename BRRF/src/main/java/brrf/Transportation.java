package brrf;

/*
 * Methods used by train, bus, plane, etc... classes
 */
public interface Transportation {
    String getArrTime(String stationParam);
    String getDeptTime(String stationParam);
    String getName();
    String getCarrierName();
    String getDistanceFromStart(String stationParam);
}
