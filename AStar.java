
import java.awt.Point;
import java.io.Serializable;
import java.util.*;


public class AStar implements AIModule {

  public class points{
    public Point pnt;
    public double f, g, h;
    public AStar.points prev;
    public int height;
   //  public boolean visited;
    public points(final TerrainMap map, Point point, double g){
      this.pnt = point;
      this.prev = prev;  //Parent
      this.g = g;   //Cost from start
      this.f = g + getHeuristics(map, this.pnt, map.getEndPoint());  // total cost
    //  this.visited = false;
  }
 }


  public List<Point> createPath(final TerrainMap map){

    
        ArrayList<Point> path = new ArrayList<Point>();   // Final list to turn in
        Point CurrentPoint = map.getStartPoint();  // The point that holds the current point
        Point LastPoint = map.getEndPoint();    // Point that holds the end point
        ArrayList<points> openl = new ArrayList<points>();   // Open List   --------->   this is a queue
        ArrayList<points> closel = new ArrayList<points>();    // Final list of points that we will extract x and y out of and turn in.
        path.add(new Point(CurrentPoint));   //
        //openl.add(new points(map, CurrentPoint, 0.0));  // List that will contain all the possible elements/points.
        //closel.add(new points(map, CurrentPoint, 0.0));   // Start point in the closed list. 
        int flag = 0;
        AStar.points start = new AStar.points(map, CurrentPoint, 0.0);
        AStar.points end = new AStar.points(map, map.getEndPoint(), 0.0);
        openl.add(start);
        int index = 0;    // To store the index of the minimum f in openl

        while(openl.size() > 0) {
          //System.out.println(" 1. " + openl.size());
          //System.out.println(" 2 " + CurrentPoint.x + "   " + CurrentPoint.y);
          ///////////////////////Trying to find the element in openl with minimumf/////////////////////
          double minfcost = openl.get(0).f;           
          System.out.println(" 1 openl size " + openl.size());


          for (int j = 1; j < openl.size(); j++){

            if(minfcost > openl.get(j).f){
              minfcost = openl.get(j).f;
              CurrentPoint = openl.get(j).pnt;
              index = j;
            }
            else{
              ;
            }
          }

          ////////////////////////////////////////////----------------------------/////////////////////////////
          AStar.points poppingelement = openl.remove(index);  // removing the element with minimum f from the open list.
          //System.out.println(" 3 " + openl.size());
          //System.out.println(" 4 " + poppingelement.pnt.x + "   " + poppingelement.pnt.y);
          Point[] neighbor = map.getNeighbors(CurrentPoint);   //  Point array to hold neigbors of the currentpoint
          //points neighbor = new points(map, neighbor, map.getCost(CurrentPoint, )
          ArrayList<points> successor = new ArrayList<points>(); // List to hold successor of CurrentPoint
          for (int i = 0; i < neighbor.length; i++){
            //openl.add(new points(map, neighbor[i], map.getCost(CurrentPoint, neighbor[i])));
            //points neighbor = new points(map, neighbor, map.getCost(CurrentPoint, )/////??????????????????
            AStar.points the_neighbor = new AStar.points(map, neighbor[i], map.getCost(poppingelement.pnt, neighbor[i]));
            successor.add(the_neighbor);
            successor.get(i).prev = poppingelement;

            //System.out.println(" For neighbor " + i + " " + successor.get(i).f);
            //System.out.println(" For neighbor " + i + " " + successor.get(i).pnt.x + " " + successor.get(i).pnt.y);
          }

          System.out.println(" 2 - successor size " + successor.size());



          for (int i = 0; i < successor.size(); i++){
              //System.out.println(" successor " + i);
              if(successor.get(i).pnt.x == end.pnt.x && successor.get(i).pnt.y == end.pnt.x){  //  This is where we return the path
                  for( int k = 0; k < closel.size(); k++){
                      path.add(closel.get(k).pnt);
                  }
                  return path;
              }  // We have reached the goal, stop and return the closed list. 
              else{

               //    System.out.println(" 1 " + openl.size());
                successor.get(i).g = poppingelement.g + map.getCost(poppingelement.pnt, successor.get(i).pnt);  // Calgulate successor g
                successor.get(i).h = getHeuristics(map, successor.get(i).pnt, map.getEndPoint());  // Calculate successor h
                successor.get(i).f = successor.get(i).g + successor.get(i).h;  // Calculate successor f = g + h
               // System.out.println(" 2. " + successor.get(i).f + " " + successor.get(i).g + "       " + successor.get(i).h);
                  for( int k = 0; k <= openl.size(); k++){
                    //System.out.println("Shello");
                    //System.out.println(" 1 " + openl.size());
						if(openl.contains(successor.get(k))){
							if(successor.get(k).g <= successor.get(i).g) {
							
							} else if(closed.contains(succesor.get(k))) {
								if(succesor.get(k))
							}
						}
                  }
                  
              }
              if(flag == 0){
                    flag = 1;    
                    openl.add(successor.get(i));
                    System.out.println(" 2 openl size " + openl.size());
                    
              }
          }
          
          //System.out.println(" 1 " + openl.size());
          closel.add(poppingelement); 
          //flag = 1;
                   
        }






      return null;
  }  
  public double getHeuristics(final TerrainMap map, final Point pt1, final Point pt2){
    double VT = map.getTile(pt2) - map.getTile(pt1); // Height difference between 2 points
    int yd = pt2.y - pt1.y;  // y2 -y1
    int xd = pt2.x - pt1.x;  // x2- x1
    int HT = (Math.abs(yd)==0)?((Math.abs(xd)==0)?0:(Math.abs(xd)-1)):(Math.abs(yd)-1);
    double dist = Math.pow((Math.pow(yd,2) + Math.pow(xd,2)), 0.5 );
    return dist;
  }
}


/*
// A*
initialize the open list
initialize the closed list
put the starting node on the open list (you can leave its f at zero)

while the open list is not empty
    find the node with the least f on the open list, call it "q"
    pop q off the open list
    generate q's 8 successors and set their parents to q
    for each successor
      if successor is the goal, stop the search
        successor.g = q.g + distance between successor and q
        successor.h = distance from goal to successor
        successor.f = successor.g + successor.h

        if a node with the same position as successor is in the OPEN list \
            which has a lower f than successor, skip this successor
        if a node with the same position as successor is in the CLOSED list \ 
            which has a lower f than successor, skip this successor
        otherwise, add the node to the open list
    end
    push q on the closed list
end
*/