import java.util.*;
import java.awt.*;
class face_name{
  int row;
  int col;
  int side;
  int axis;
  boolean is_transparet; //used by painter only  
  static String getAxisName(int axis){
    switch (axis){
      case axis_x: return "x";
      case axis_y: return "y";
      case axis_z: return "z";
      default: return "unknwon";
    }    
  }
  public boolean equals(Object obj){
    face_name r = (face_name)obj;
    return (row==r.row && col==r.col && side==r.side &&axis==r.axis);
  }
  public String toString(){
    return getAxisName(axis)+",side="+side+",row="+row+",col="+col;
  }
  face_name(int axis,int side,int row,int col){
    this.axis=axis;
    this.side=side;
    this.row=row;
    this.col=col;
  }
  void copy(face_name right){
    this.axis=right.axis;
    this.side=right.side;
    this.row=right.row;
    this.col=right.col;
    this.is_transparet=right.is_transparet;
  }
  public static final int axis_x=0;
  public static final int axis_y=1;
  public static final int axis_z=2;  
  face_name name;
}

class my_polygon{
  /*rules for namings square:
  axis is x|y|z 
  side 0 refers to -1 1 to 1
  row/col refers to row when the cube is facing the positve direction  
  
  */
  face_name name;
  point3D points[] = new point3D[4];
  point3D tpoints[] = new point3D[6];
  double z;
  int color;
  boolean is_outlined;
  boolean is_painted;
  boolean is_selectable;
  int x[]=new int[6];
  int y[]=new int[6];
  void movex(int scale,int move){
	  for (int i=0;i<4;i++)
	    points[i].x = points[i].x -scale+1+move*2;
  }
  void movey(int scale,int move){
	  for (int i=0;i<4;i++)
	    points[i].y = points[i].y -scale+1+move*2;
  }
  void movez(int scale,int move){
	  for (int i=0;i<4;i++)
	    points[i].z = points[i].z  -scale+1+move*2;
  }
  
  void calc(){
    z=0.;
    for (int i=0;i<points.length;i++)
      z+=points[i].z;
    z/=points.length;
	  double angle1=-Math.atan2(points[0].y-points[1].y,points[0].x-points[1].x)*180/Math.PI;
	  double angle2=-Math.atan2(points[2].y-points[1].y,points[2].x-points[1].x)*180/Math.PI;
	  double diff = angle2 - angle1;
	  if (diff<0)
	    diff+=360;
	  //is_transparet = false;//diff<180;
	  name.is_transparet =diff<180;
  }  
  void tloadxy(){
    for (int j=0;j<6;j++){
      x[j]=(int)tpoints[j].x;
      y[j]=(int)tpoints[j].y;        
    }    
  }
  
  void loadxy(){
    for (int j=0;j<4;j++){
      x[j]=(int)points[j].x;
      y[j]=(int)points[j].y;        
    }    
  }
  void paint(Graphics g,boolean transparent_mode){
    int n=4;
    //if (!name.is_transparet)
    //  return;
    if (name.is_transparet && transparent_mode){
      tloadxy();
      n+=2;
    }else
      loadxy();
    Color c=null;
	  switch(color){
	    case 0:c=Color.blue;break;
	    case 1:c=Color.cyan ;break;
	    case 2:c=Color.white ;break;
	    case 3:c=Color.green;break;
	    case 4:c=Color.red;break;
	    case 5:c=Color.orange;break;
	  }
	  g.setColor(c);
    g.fillPolygon(x,y,n);
  }  
  void paint_outline(Graphics g,boolean transparent_mode){
    int n=4;
    //if (!name.is_transparet)
    //  return;    
    if (name.is_transparet &&transparent_mode){
      tloadxy();
      n+=2;
    }else
      loadxy();
    if (is_outlined)      
      g.setColor(Color.white);
    else
      g.setColor(Color.black);
    g.drawPolygon(x,y,n);  
  }
}

class Painter{
  double getz(Object obj){
    my_polygon p = (my_polygon)obj;
    return p.z-(p.name.is_transparet &&transparent_mode?0:1000);
  }
  public static Vector concat(Vector a,Object pivot,Vector b){
      Vector ans = new Vector(a.size()+1+b.size()); //prealoc for speed
      for (int i=0;i<a.size();i++)
          ans.addElement(a.elementAt(i));
      ans.addElement(pivot);
      for (int i=0;i<b.size();i++)
          ans.addElement(b.elementAt(i));
      return ans;
  }  
  public  Vector sortVector(Vector v){
      if (v==null)
          return v;
      if (v.size()<=1)
          return v;
      Object pivot = v.elementAt(0);
      Vector smaller = new Vector();
      Vector bigger = new Vector();
      for (int i=1;i<v.size();i++){
          Object cur = v.elementAt(i);
          if (getz(pivot)>getz(cur))
              smaller.addElement(cur);
          else
              bigger.addElement(cur);
      }
      return concat(sortVector(smaller),pivot,sortVector(bigger));
  }

  Vector polygons;
  boolean transparent_mode=false;
  Painter(boolean transparent_mode){
    this.transparent_mode=transparent_mode;
    reset();
  }
  
  void reset(){
    polygons = new Vector(100);
  }

  void addPolygon(my_polygon p){
    p.calc();
    polygons.addElement(p);
  }
  
  void end(){
    polygons = sortVector(polygons);
    //sorts the polygons according to depth.
  }
  
  void paint (Graphics g){
    int n = polygons.size();    
    int outline_pos=-1;
    boolean outline_transparent;
    my_polygon last_p=null;
    for (int i=0;i<n;i++){
      my_polygon p = (my_polygon)polygons.elementAt(i);
      p.paint(g,transparent_mode);
      if (last_p!=null&&(last_p.name.is_transparet!=p.name.is_transparet || i==n-1) && outline_pos!=-1){
        last_p.paint_outline(g,transparent_mode);
        outline_pos=-1;
      }      
      p.paint_outline(g,transparent_mode);
      if (p.is_outlined){
        outline_pos=i;    
        last_p = p;
        outline_transparent = p.name.is_transparet;
      }      
    }
/*    for (int i=0;i<n;i++){
      my_polygon p = (my_polygon)polygons.elementAt(i);
      p.paint_outline(g);
    }*/
    
  }

  my_polygon getpoint(int x,int y){
    int n = polygons.size(); 
    for (int i=n-1;i>=0;i--){
      my_polygon p = (my_polygon)polygons.elementAt(i);
      Polygon pol;
      if (p.name.is_transparet&& transparent_mode){        
        p.tloadxy();
        pol = new Polygon(p.x,p.y,6);
      }else{
        p.loadxy();
        pol = new Polygon(p.x,p.y,4);        
      }
      if (pol.inside(x,y)){
        return p;
      }
    }
    return null;
  }
  boolean transparet_mode=false;
    
  int bool_to_int(boolean bool){
    if (bool)
      return 1;
    return 0;  
  }
}





