package api;

public class Edge implements EdgeData{
    private int src;
    private int dest;
    private double w;

    /**
     * Create a new edge given a source, a destination and a weight
     *
     * @param src The source node index
     * @param dst The destination node index
     * @param weight The weight of the edge
     */
    public Edge(int src, int dst, double weight){
        this.src = src;
        this.dest = dst;
        this.w = weight;
    }

    @Override
    public int getSrc() {
        return src;
    }

    @Override
    public int getDest() {
        return dest;
    }

    @Override
    public double getWeight() {
        return w;
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
