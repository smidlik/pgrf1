package drawables;

public class Edge {

    int x1, y1, x2, y2;
    float k, q;

    public Edge(Point p1, Point p2){
        this.x1 = p1.getX();
        this.y1 = p1.getY();
        this.x2 = p2.getX();
        this.y2 = p2.getY();
    }

    public boolean isHorizontal(){
        return false; //TODO
    }

    public void order(){
        // TODO seřadit dle y1 < y2
    }

    public void cut(){
        // todo oříznout poslední pixel
    }

    public void compute(){
        // todo vypočítat k a q
    }

    public int findX(int y){
        return 0; // TODO vypočítat X dle y,k,q
    }

    public boolean isIntersection(int y){
        return false; //TODO - true když y > y1 && y < y2
    }

    public int yMin(int yMin){
        return 0; //todo dle y1, y2 a yMin rozhodnout které vracíme
    }
    public int yMax(int yMax){
        return 0; //todo dle y1,y2,yMax navrátit maxHodnotu
    }

    public boolean inside(Point p){
        // pozor na orientaci !!
        Point v1 = new Point(x2 - x1, y2 - y1);
        Point n1 = new Point(v1.getY(), -v1.getX());
        Point v2 = new Point(p.getX() - x1, p.getY() - y1);
        return (n1.getX() * v2.getX() + n1.getY() * v2.getY())<0;
    }

    public Point intersection(Point v1, Point v2) {
        float px = ((v1.getX() * v2.getY() - v1.getY() * v2.getX()) * (x1 - x2) - (x1 * y2 - y1 * x2) * (v1.getX() - v2.getX())) / (float) ((v1.getX() - v2.getX()) * (y1 - y2) - (x1 - x2) * (v1.getY() - v2.getY()));
        float py = ((v1.getX() * v2.getY() - v1.getY() * v2.getX()) * (y1 - y2) - (x1 * y2 - y1 * x2) * (v1.getY() - v2.getY())) / (float) ((v1.getX() - v2.getX()) * (y1 - y2) - (x1 - x2) * (v1.getY() - v2.getY()));
        return new Point(Math.round(px), Math.round(py));
    }
}
