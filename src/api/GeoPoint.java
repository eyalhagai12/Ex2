package api;

public class GeoPoint implements GeoLocation {
    private final double x;
    private final double y;
    private final double z;

    public GeoPoint(double x, double y, double z){
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public double x() {
        return x;
    }

    @Override
    public double y() {
        return y;
    }

    @Override
    public double z() {
        return z;
    }

    @Override
    public double distance(GeoLocation g) {
        double dist = Math.pow(x-g.x(), 2) + Math.pow(y-g.y(), 2) + Math.pow(z-g.z(), 2);
        dist = Math.sqrt(dist);
        return dist;
    }
}
