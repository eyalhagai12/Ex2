package api;

public class Edge implements EdgeData{
    private int src;
    private int dst;
    private double weight;

    /**
     * Create a new edge given a source, a destination and a weight
     *
     * @param src The source node index
     * @param dst The destination node index
     * @param weight The weight of the edge
     */
    public Edge(int src, int dst, double weight){
        this.src = src;
        this.dst = dst;
        this.weight = weight;
    }

    @Override
    public int getSrc() {
        return src;
    }

    @Override
    public int getDest() {
        return dst;
    }

    @Override
    public double getWeight() {
        return weight;
    }

    @Override
    public String getInfo() {
        return null;
    }

    @Override
    public void setInfo(String s) {

    }

    @Override
    public int getTag() {
        return 0;
    }

    @Override
    public void setTag(int t) {

    }
}
