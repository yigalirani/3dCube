import java.awt.*;
import java.applet.*;
class Button{
  Component c;
  String text;
  int pos_x,pos_y;
  int len=10;
  int height=10;
  Button(Component c,String text,int pos_x,int pos_y){
    this.c=c;
    this.text=text;
    this.pos_x=pos_x;
    this.pos_y=pos_y;
  }
  boolean mouseDown(int x,int y){
    if (x>pos_x && x<pos_x+len && y>pos_y && y<pos_y+height)
      return true;
    return false;    
  }
  void paint(Graphics g){
    
    len=g.getFontMetrics().stringWidth(text);
    height=g.getFontMetrics().getHeight();
    height=20;
    g.drawString(text, pos_x, pos_y+height);
    g.drawRect(        pos_x, pos_y, len,height);
  }
  
  
}
class checkbox{
  Component c;
  String text;
  boolean status;
  int pos_x,pos_y;
  checkbox(Component c,String text,boolean init_status,int pos_x,int pos_y){
    this.c=c;
    this.text=text;
    this.status=init_status;
    this.pos_x=pos_x;
    this.pos_y=pos_y;
  }
  boolean mouseDown(int x,int y){
    if (x>pos_x && x<pos_x+10 && y>pos_y && y<pos_y+10){
      status=!status;
      c.repaint();
      return true;
    }
    return false;    
  }
  void mouseMove(int x,int y){
  }
  void paint(Graphics g){
    g.drawRect(pos_x, pos_y, 10, 10);
    g.drawString(text, pos_x+12, pos_y+10);
    if (status){
      g.drawLine(pos_x, pos_y, pos_x+10, pos_y+10);
      g.drawLine(pos_x+10, pos_y, pos_x, pos_y+10);      
    }
  }
}
class point3D{
	point3D(){
	}
	point3D add(point3D p){
	  x+=p.x;
	  y+=p.y;
	  z+=p.z;
	  return this;
	}
	point3D(double x,double y,double z){
	  this.x=x;
	  this.y=y;
	  this.z=z;	    
	}
	point3D(point3D source){
	  x=source.x;
	  y=source.y;
	  z=source.z;
	}
	double x,y,z;
}  
class FlatCube extends Canvas
{
  //point3D points[] = new point3D[8];    
  int rotated_axis=-1;
  double rotated_angle=0.;
  int rotated_col=0;
  int s[][][][] = new int[3][2][10][10]; //axis/side/row/col
  int b[][][][] = new int[3][2][10][10];
  private double inc=1,azim=2.5;
  public double dist=10;
  private double fov=2.;
  int last_x;
  int last_y;
  int orig_x;
  int orig_y;
  boolean drag_overide=false;
  public boolean mouseDown(
                          Event evt,
                          int x,
                          int y){
    if (transparent.mouseDown(x, y)){
      drag_overide=true;
      return true;
    }
    if (reset.mouseDown(x, y)){
      drag_overide=true;
      reset(2,3,1);
      repaint();
      return true;
    }
    
    drag_overide=false;
    last_x=x;
    last_y=y;
    orig_x=x;
    orig_y=y;
    return true;
  }

  void translate_side(int axis,int side,boolean forward){
    switch(axis){
      case face_name.axis_x:
        for (int i=0;i<y_size;i++)
          for (int j=0;j<z_size;j++)
            move(face_name.axis_y,side,j,i,face_name.axis_y,side,i,z_size-1-j,forward);
        return;        
      case face_name.axis_y:
        for (int i=0;i<x_size;i++)
          for (int j=0;j<z_size;j++)
            move(face_name.axis_x,side,j,i,face_name.axis_x,side,i,z_size-1-j,forward);
        return;  
      case face_name.axis_z:
        for (int i=0;i<x_size;i++)
          for (int j=0;j<y_size;j++)
            move(face_name.axis_z,side,i,j,face_name.axis_z,side,j,x_size-1-i,forward);
        return;          
    }
  }
  
  void miror_side(int axis,int side){
    switch(axis){
      case face_name.axis_x:
        for (int i=0;i<y_size;i++)
          for (int j=0;j<z_size;j++)
            s[face_name.axis_y][side][j][i]=b[face_name.axis_y][side][z_size-j-1][y_size-i-1];
        return;        
      case face_name.axis_y:
        for (int i=0;i<x_size;i++)
          for (int j=0;j<z_size;j++)
            s[face_name.axis_x][side][j][i]=b[face_name.axis_x][side][z_size-j-1][x_size-i-1];
        return;  
      case face_name.axis_z:
        for (int i=0;i<x_size;i++)
          for (int j=0;j<y_size;j++)
            s[face_name.axis_z][side][j][i]=b[face_name.axis_z][side][y_size-j-1][x_size-i-1];
        return;          
    }
  }
  int get_size(int axis){
    switch(axis){
      case face_name.axis_x: return x_size;
      case face_name.axis_y: return y_size;
      case face_name.axis_z: return x_size;
      default: return 0;
    }
  }
  void miror_col(int axis,int col){
    int size = get_size(axis);
    //for (int 
  }
  void copytob(){
    for (int side=0;side<=1;side++)
      for (int col=0;col<x_size;col++)
        for (int row=0;row<z_size;row++)
          b[face_name.axis_x][side][row][col]=s[face_name.axis_x][side][row][col];
          
    for (int side=0;side<=1;side++)
      for (int col=0;col<y_size;col++)
        for (int row=0;row<z_size;row++)
          b[face_name.axis_y][side][row][col]=s[face_name.axis_y][side][row][col];
          
    for (int side=0;side<=1;side++)
      for (int col=0;col<x_size;col++)
        for (int row=0;row<y_size;row++)
          b[face_name.axis_z][side][row][col]=s[face_name.axis_z][side][row][col];
     
  }
  void move(int axis,int side,int i,int j,int axis2,int side2,int i2,int j2,boolean forward){
    if (forward)
      s[axis][side][i][j]=b[axis2][side2][i2][j2];    
    else
      s[axis2][side2][i2][j2]=b[axis][side][i][j];    
  }
  private void final_rotate(){
    copytob();
    double a = (rotated_angle*180/Math.PI)%360;
    if (a<0)
      a+=360;
    int normalized_angle=0;
    while(true){
      if (a<45){
        normalized_angle=0;
        return;
      }
      if (a<45+90){
        normalized_angle=1;
        break;
      }
      if (a<45+180){
        normalized_angle=2;
        break;
      }
      if (a<45+270){
        normalized_angle=3;
        break;
      }
      normalized_angle=0;
      return;
    }
    switch(this.rotated_axis){
      case face_name.axis_x:
        if (normalized_angle==1 || normalized_angle==3){      
          if (z_size!=y_size)
            return;
          for (int side=0;side<2;side++)
            for (int i=0;i<z_size;i++){
              move(face_name.axis_x,side,i,rotated_col,face_name.axis_z,(side+1)%2,i,rotated_col,normalized_angle==1);
              move(face_name.axis_z,side,i,rotated_col,face_name.axis_x,side,z_size-i-1,rotated_col,normalized_angle==1);
            }
          if (rotated_col==0)
            translate_side(face_name.axis_x,0,normalized_angle==3);
          if (rotated_col==x_size-1)     
            translate_side(face_name.axis_x,1,normalized_angle==3);
        }
     
        if (normalized_angle==2){
          for (int side=0;side<2;side++){
            for (int i=0;i<z_size;i++)
              s[face_name.axis_x][side][i][rotated_col] = b[face_name.axis_x][(side+1)%2][z_size-i-1][rotated_col];
          }
          for (int side=0;side<2;side++){
            for (int i=0;i<y_size;i++)
              s[face_name.axis_z][side][i][rotated_col] = b[face_name.axis_z][(side+1)%2][y_size-i-1][rotated_col];
          }          
          if (rotated_col==0)
            miror_side(face_name.axis_x,0);
          if (rotated_col==x_size-1)
            miror_side(face_name.axis_x,1);            
        }
        return;        
      case face_name.axis_y:
        if (normalized_angle==1 || normalized_angle==3){      
          if (z_size!=x_size)
            return;
          for (int side=0;side<2;side++)
            for (int i=0;i<z_size;i++){
              move(face_name.axis_y,side,i,rotated_col,face_name.axis_z,(side+1)%2,rotated_col,i,normalized_angle==1);
              move(face_name.axis_z,side,rotated_col,i,face_name.axis_y,side,z_size-i-1,rotated_col,normalized_angle==1);
            }
          if (rotated_col==0)
            translate_side(face_name.axis_y,0,normalized_angle==3);
          if (rotated_col==x_size-1)     
            translate_side(face_name.axis_y,1,normalized_angle==3);
            
        }
        if (normalized_angle==2){
          for (int side=0;side<2;side++){
            for (int i=0;i<z_size;i++)
              s[face_name.axis_y][side][i][rotated_col] = b[face_name.axis_y][(side+1)%2][z_size-i-1][rotated_col];
          }
          for (int side=0;side<2;side++){
            for (int i=0;i<x_size;i++)
              s[face_name.axis_z][side][rotated_col][i] = b[face_name.axis_z][(side+1)%2][rotated_col][x_size-i-1];
          }          
          if (rotated_col==0)
            miror_side(face_name.axis_y,0);
          if (rotated_col==y_size-1)
            miror_side(face_name.axis_y,1);            
        }
        return;      
      case face_name.axis_z:
        if (normalized_angle==1 || normalized_angle==3){      
          if (y_size!=x_size)
            return;
          for (int side=0;side<2;side++)
            for (int i=0;i<x_size;i++){
              move(face_name.axis_x,side,rotated_col,i,face_name.axis_y,(side+1)%2,rotated_col,i,normalized_angle==1);
              move(face_name.axis_y,side,rotated_col,i,face_name.axis_x,side,rotated_col,x_size-i-1,normalized_angle==1);
            }
          if (rotated_col==0)
            translate_side(face_name.axis_z,0,normalized_angle==1);
          if (rotated_col==z_size-1)     
            translate_side(face_name.axis_z,1,normalized_angle==1);
            
        }      
        if (normalized_angle==2){
          for (int side=0;side<2;side++){
            for (int i=0;i<y_size;i++)
              s[face_name.axis_y][side][rotated_col][i] = b[face_name.axis_y][(side+1)%2][rotated_col][y_size-i-1];
          }
          for (int side=0;side<2;side++){
            for (int i=0;i<x_size;i++)
              s[face_name.axis_x][side][rotated_col][i] = b[face_name.axis_x][(side+1)%2][rotated_col][x_size-i-1];
          } 
          if (rotated_col==0)
            miror_side(face_name.axis_z,0);
          if (rotated_col==z_size-1)
            miror_side(face_name.axis_z,1);          
        }      
        return;        
    }
  }
  
  public boolean mouseUp(Event evt,int x,int y){
    max_dx=0;
    max_dy=0;    
    final_rotate();
    rotated_angle=0;
    drag_overide=false;    
    repaint();
    return true;
  }
  int max_dx=0;
  int max_dy=0;
  int dominent=-1;
  int drag_num=0;
  boolean rotate(int x,int y){
    rotated_angle=0;
    int dx=orig_x-x;
    int dy=orig_y-y;
        
    if (selected.axis==face_name.axis_x||selected.axis==face_name.axis_y){
      if (dominent==-1 || drag_num++<3){
        if (Math.abs(dx)>Math.abs(dy))
          dominent=0;
        else
          dominent=1;          
      }
          
      if (dominent==0){
        this.rotated_axis=face_name.axis_z;
        if (z_size==1)
          return false;
        this.rotated_col=selected.row;
        this.rotated_angle = dx*0.02;
        if (transparent.status && !selected.is_transparet)
          rotated_angle=-rotated_angle;
        if (selected. side==1)
          rotated_angle=-rotated_angle;            
        repaint();
        return true;
      }
      //if (Math.abs(dy)>max_dx){        
        this.rotated_axis=selected.axis;
        if (get_size(rotated_axis)==1)
          return false;
        this.rotated_col=selected.col;
        this.rotated_angle = -dy*0.02;
  //      if (transparent.status && !selected.is_transparet)
    //       rotated_angle=-rotated_angle;        
        if (selected.side==1)
          rotated_angle=-rotated_angle;            
            
        repaint();
        return true;        
      //}
    }
    return true;
  }  
  int dd=0;
  synchronized boolean sync_drag(Event evt,
                          int x,
                          int y){
    if (drag_overide)
      return true;    
    if (selected.axis!=-1 && rotate(x,y))
        return true;
    int dx=x-last_x;
    int dy=y-last_y;
    azim+=.01*dx;
    inc-=.01*dy;    
    if (inc>Math.PI*.80)
      inc=Math.PI*.80;
    if (inc<Math.PI*.20)
      inc=Math.PI*.20;
      
    last_x=x;
    last_y=y;  
    dd++;
    //System.out.println("d"+dd +" ");
    repaint();
    return true;
                          }
  
  public boolean mouseDrag(
                          Event evt,
                          int x,
                          int y){
    return sync_drag(evt,x,y);
  }
 
  void world_to_eye(point3D world){
    
    double last_angle;
    double last_size; 

    //rotate by azim..
    last_angle = Math.atan2(world.x,world.y);
    last_size = Math.sqrt(world.x*world.x+world.y*world.y);
    last_angle-=azim;
    double ans_x = last_size*Math.sin(last_angle);
    double ans_y = last_size*Math.cos(last_angle);    
    double ans_z = world.z;
    //rotate by inc..
    last_angle = Math.atan2(ans_y,ans_z);
    last_size = Math.sqrt(ans_y*ans_y+ans_z*ans_z);
    last_angle-=inc;
    ans_y = last_size*Math.sin(last_angle);
    ans_z = last_size*Math.cos(last_angle);    
    // move by dist...
    ans_z-=dist;    
    world.x=ans_x;
    world.y=ans_y;
    world.z=ans_z;
  }
  void eye_to_screen(point3D eye){
    double ans_x = 1.6*eye.x/eye.z;
    double ans_y = -1.6*eye.y/eye.z;    
    ans_x= (ans_x)*d.width/2+d.width/2;
    ans_y= (ans_y)*d.height/2+d.height/2;    
    eye.x=ans_x;
    eye.y=ans_y;
  }
  void world_to_screen(point3D world){
    world_to_eye(world);
    eye_to_screen(world);
  }
  Dimension d = new Dimension(100,100);  
  int size[]={2,3,4};
	int x_size=2;
	int y_size=3;
	int z_size=4;
  void reset(int x_size,int y_size,int z_size){
    this.x_size=x_size;
    this.y_size=y_size;
    this.z_size=z_size;
    for (int side=0;side<=1;side++)
      for (int col=0;col<x_size;col++)
        for (int row=0;row<z_size;row++)
          s[face_name.axis_x][side][row][col]=side;
          
    for (int side=0;side<=1;side++)
      for (int col=0;col<y_size;col++)
        for (int row=0;row<z_size;row++)
          s[face_name.axis_y][side][row][col]=side+2;
          
    for (int side=0;side<=1;side++)
      for (int col=0;col<x_size;col++)
        for (int row=0;row<y_size;row++)
          s[face_name.axis_z][side][row][col]=side+4;
        
  }
  checkbox transparent;
  Button    reset;
  FlatCube(){    
		setSize(426,266);
		transparent = new checkbox(this,"Transparent Mode",false,10,100);
		reset = new Button(this,"reset",10,130);
		reset(3,3,3);
    make_painter();		
	}

	face_name selected = new face_name(-1,-1,-1,-1);
	face_name last_selected = new face_name(-1,-1,-1,-1);

  point3D midpoint(point3D a,point3D b,double m){
    point3D ans = new point3D();
    ans.x=a.x*m+b.x*(1-m);
    ans.y=a.y*m+b.y*(1-m);
    ans.z=a.z*m+b.z*(1-m);
    return ans;    
  }
  void rotate_in_world(point3D point,int rotated_axis,double rotated_angle){
    switch(rotated_axis){
      case face_name.axis_x:        
        double angle = Math.atan2(point.z,point.y);
        angle+=rotated_angle;
        double size = Math.sqrt(point.z*point.z+point.y*point.y);
        point.z = size*Math.sin(angle);
        point.y = size*Math.cos(angle);
        break;      
      case face_name.axis_y:
        angle = Math.atan2(point.z,point.x);
        angle+=rotated_angle;
        size = Math.sqrt(point.z*point.z+point.x*point.x);
        point.z = size*Math.sin(angle);
        point.x = size*Math.cos(angle);
        break;      
      case face_name.axis_z:
        angle = Math.atan2(point.x,point.y);
        angle+=rotated_angle;
        size = Math.sqrt(point.x*point.x+point.y*point.y);
        point.x = size*Math.sin(angle);
        point.y = size*Math.cos(angle);
    }
  }
  void complete_polygon(my_polygon p){

      
  }
  int outlined_i,outlined_j,outlined_side=-1;
  
  boolean is_outlined(int side,int i,int j){
    return (side==outlined_side && i==outlined_i && j==outlined_j);    
  }
  boolean should_rotate(int axis,int side,int row,int col){
    switch(rotated_axis){
      case face_name.axis_x:
        switch(axis){
          case face_name.axis_x:                  
            return (col==this.rotated_col);          
          case face_name.axis_y:        
            if (this.rotated_col==0 && side==0)
              return true;
            if (this.rotated_col==x_size-1 && side==1)
              return true;              
            return false;
          case face_name.axis_z:
            return col==rotated_col;
        }      
      case face_name.axis_y:
        switch(axis){
          case face_name.axis_y:                  
            return (col==this.rotated_col);          
          case face_name.axis_x:        
            if (this.rotated_col==0 && side==0)
              return true;
            if (this.rotated_col==y_size-1 && side==1)
              return true;              
            return false;
          case face_name.axis_z:
            return row==rotated_col;
        }      

      case face_name.axis_z:
        switch(axis){
          case face_name.axis_x:        
          case face_name.axis_y:        
            return (row==this.rotated_col);
          case face_name.axis_z:
            if (this.rotated_col==0 && side==0)
              return true;
            if (this.rotated_col==z_size-1 && side==1)
              return true;                      
        }
    }
    return false;
  }
  void loadij(my_polygon p,int axis,int side,int row,int col){
    p.name = new face_name(axis,side,row,col);
    if (selected!=null && selected.equals(p.name))
	    p.is_outlined=true;

    for (int i=0;i<3;i++)
      p.tpoints[i]=new point3D(p.points[i]);
    p.tpoints[3] = midpoint(p.points[2],p.points[3],.90);
    p.tpoints[4] = midpoint(p.points[1],p.points[3],.70);
    p.tpoints[5] = midpoint(p.points[0],p.points[3],.90);
    if (should_rotate(axis,side,row,col))
      for (int i=0;i<4;i++)
        rotate_in_world(p.points[i],rotated_axis,rotated_angle);
      
    for (int i=0;i<4;i++)
      world_to_screen(p.points[i]);      
      
    if (should_rotate(axis,side,row,col))
      for (int i=0;i<6;i++)
        rotate_in_world(p.tpoints[i],rotated_axis,rotated_angle);
    for (int i=0;i<6;i++)
      world_to_screen(p.tpoints[i]);
	  p.color=s[axis][side][row][col];	  
	  pt.addPolygon(p);	        
	}
	          
  int get_index(int index,int side){
    if (side==0)
      return index;
    int ans = index+2;
    if (ans>=4)
      ans-=4;
    return ans;      
  }
	Painter make_painter(){    
    pt = new Painter(transparent.status);
    my_polygon p;
    int axis;
    axis=face_name.axis_z;
    for (int side=0;side<=1;side++)
	    for (int col=0;col<x_size;col++){
	      for (int row=0;row<y_size;row++){	      
	        p = new my_polygon();	    
	        if (side==1){
	          p.points[0] = new point3D(-1,-1,1);
	          p.points[1] = new point3D(-1,+1,1);
	          p.points[2] = new point3D(+1,+1,1);
	          p.points[3] = new point3D(+1,-1,1);	          
	        }else{
	          p.points[2] = new point3D(-1,-1,-1);
	          p.points[1] = new point3D(-1,+1,-1);
	          p.points[0] = new point3D(+1,+1,-1);
	          p.points[3] = new point3D(+1,-1,-1);          
	        }
	        
	        p.movey(y_size,row);
	        p.movex(x_size,col);
	        p.movez(z_size,calc_move(side,z_size));			      
	        loadij(p,axis,side,row,col);
	      }
	    }
    axis=face_name.axis_y;
    for (int side=0;side<=1;side++)    
	    for (int col=0;col<y_size;col++){
	      for (int row=0;row<z_size;row++){	      
	        p = new my_polygon();	      
	        if (side==1){
	          p.points[3] = new point3D(1,-1,-1);
	          p.points[0] = new point3D(1,-1,+1);
	          p.points[1] = new point3D(1,+1,1);
	          p.points[2] = new point3D(1,+1,-1);        
	        }else{
	          p.points[2] = new point3D(-1,-1,-1);
	          p.points[1] = new point3D(-1,-1,+1);
	          p.points[0] = new point3D(-1,+1,1);
	          p.points[3] = new point3D(-1,+1,-1);	          
	        }
	        p.movex(x_size,calc_move(side,x_size));
	        p.movey(y_size,col);
	        p.movez(z_size,row);	  
	        loadij(p,axis,side,row,col);
	      }
	    }
   axis=face_name.axis_x;
    for (int side=0;side<=1;side++)   
	    for (int col=0;col<x_size;col++){
	      for (int row=0;row<z_size;row++){	      
	        p = new my_polygon();	      
	        if (side==0){
	          p.points[3] = new point3D(-1,-1,-1);
	          p.points[0] = new point3D(-1,-1,+1);
	          p.points[1] = new point3D(+1,-1,1);
	          p.points[2] = new point3D(+1,-1,-1);      
	        }else{
	          p.points[2] = new point3D(-1,1,-1);
	          p.points[1] = new point3D(-1,1,+1);
	          p.points[0] = new point3D(1,+1,1);
	          p.points[3] = new point3D(1,+1,-1);          
	        }
	        p.movey(y_size,calc_move(side,y_size));
	        p.movex(x_size,col);
	        p.movez(z_size,row);	 	      	      
	        loadij(p,axis,side,row,col);
	      }
	    }
	  pt.end();
	  return pt;	  
	}
	int calc_move(int side,int size){
	  if (side==0)
	    return 0;
	  return size-1;
	}
	void repaint_if_selected_changed(){	  
	  if (!last_selected.equals(selected))
	    repaint();
	  last_selected.copy(selected);  
	}
	public boolean mouseMove(Event evt,int x,int y){
	  dominent=-1;
	  drag_num=0;
	  if (pt==null)
	    return true;
	  my_polygon p= pt.getpoint(x, y);
	  if (p==null)
	    selected.axis=-1;
	  else
	    selected.copy(p.name);  
	  repaint_if_selected_changed();	    
	  return true;
	}
	
	void draw_axis(Graphics g){
	  //g.setColor(Color.white);
	  double size=3;
	  point3D p0=new point3D(0,0,0);
	  point3D px=new point3D(size,0,0);
	  point3D py=new point3D(0,size,0);
	  point3D pz=new point3D(0,0,size);
	  world_to_screen(p0);
	  world_to_screen(px);
	  world_to_screen(py);
	  world_to_screen(pz);
	  p0.x-=200;
	  px.x-=200;
	  py.x-=200;
	  pz.x-=200;	  
	  g.drawLine((int)p0.x,(int)p0.y,(int)py.x,(int)py.y);
	  g.drawLine((int)p0.x,(int)p0.y,(int)pz.x,(int)pz.y);
	  g.drawLine((int)p0.x,(int)p0.y,(int)px.x,(int)px.y);
	  g.drawString("x",(int) px.x,(int) px.y);
	  g.drawString("y",(int) py.x,(int) py.y);
	  g.drawString("z",(int) pz.x,(int) pz.y);	  
	}
	
	Painter pt;
	void paint2(Graphics g){
	  //g.setFont(new Font("Serif",Font.PLAIN,10));
	  int text_y=0;
	  long t = System.currentTimeMillis();
	  make_painter();
	  long d1=System.currentTimeMillis()-t;
	  t = System.currentTimeMillis();
	  pt.paint(g);	  
	  long d2=System.currentTimeMillis()-t;
	  //g.drawString("make_time="+d1+"paint_time="+d2, 10, 10);
	  
	  g.setColor(Color.black);
	  draw_axis(g);
	  g.drawString("num_paint="+num_paint++, 10, text_y+=12);	  
	  g.drawString("selected="+selected,10,text_y+=12);
    g.drawString("azim="+azim+",inc="+inc,10,text_y+=12);	 
	  g.drawString("x_side="+x_size+",y_side="+y_size+",z_side="+z_size,10,text_y+=12);	
    g.drawString("rotated_axis="+face_name.getAxisName(rotated_axis),10,text_y+=12);	 
    g.drawString("rotated_angle="+rotated_angle, 10, text_y+=12);
    g.drawString("rotated_col="+rotated_col, 10, text_y+=12);	  
	  transparent.paint(g);
	  reset.paint(g);
	}
	int num_paint=0;
	public void draw(Graphics g){	 
	  d = getSize();
	  g.setColor(Color.lightGray);
	  g.fillRect(0,0,d.width,d.height);
	  g.setColor(Color.black);

	  paint2(g);
	}
	//{{DECLARE_CONTROLS

	//}}
    private Image bufferImage;
    Graphics buffer_graphics;
    protected Graphics getBufferGraphics(){
        if (bufferImage == null){
          bufferImage= createImage(getSize().width,getSize().height);         
          buffer_graphics = bufferImage.getGraphics();
        }
        return buffer_graphics;
    }
    
    class paint_thread extends Thread{
      Graphics g;
      paint_thread(Graphics g){
        this.g=g;
      }
      public void run(){
        while(true){
          if (!in_paint){
            repaint2();
          }
          try{
            Thread.sleep(50);
          }catch(Throwable e){
          }
        }
      }
    }
    synchronized void sync_paint(){
      draw(getBufferGraphics());      
      getGraphics().drawImage(bufferImage, 0, 0,null);
    }

    public void update(Graphics g){
        paint(g);
    }
    paint_thread the_thread=null;
    boolean in_paint = true;
    public void paint(Graphics g){  
      if (the_thread==null){
        the_thread= new paint_thread(g);      
        the_thread.start();
      }
      in_paint = true;
      //draw(g);
      draw(getBufferGraphics());      
      g.drawImage(bufferImage, 0, 0,null);
      in_paint = false;      
    }
    
    public void repaint2(){
      super.repaint();
    }
    
    public void repaint(){
      //super.repaint();
    }
}
