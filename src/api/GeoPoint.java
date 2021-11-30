package api;

public class GeoPoint implements GeoLocation {
    private final int x;
    private final int y;
    private final int z;

    public GeoPoint(int x, int y, int z){
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
