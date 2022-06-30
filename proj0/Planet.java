public class Planet{
    public double xxPos;                            
    public double yyPos;                            
    public double xxVel;
    public double yyVel;
    public double mass;
    public String imgFileName;
    private static String path = "images/";

    public Planet(double xP, double yP, double xV,
                  double yV, double m, String img){
         this.xxPos = xP;
         this.yyPos = yP;
         this.xxVel = xV;
         this.yyVel = yV;
         this.mass = m;
         this.imgFileName = img;
     }

    public Planet(Planet b){
        this(b.xxPos, b.yyPos, b.xxVel, b.yyVel, b.mass, b.imgFileName);
         }
    public double calcDistance(Planet b){
             double dist;
             dist =Math.sqrt(Math.pow((this.xxPos - b.xxPos),2) +Math.pow((this.yyPos - b.yyPos), 2));
             return dist;
            }

    public double calcForceExertedBy(Planet b){
            double gConstant = 6.67e-11;
            double dist = this.calcDistance(b);
            double force = gConstant * this.mass * b.mass / Math.pow(dist, 2);
            return force;
        }

    public double calcForceExertedByX(Planet b){
        double force = this.calcForceExertedBy(b);
        double dist = this.calcDistance(b);
        double xForce = force * (b.xxPos - this.xxPos) / dist;
        return xForce;
        }

    public double calcForceExertedByY(Planet b){
        double force = this.calcForceExertedBy(b);
        double dist = this.calcDistance(b);
        double yForce = force * (b.yyPos - this.yyPos) / dist;
        return yForce;
        }
    public double calcNetForceExertedByX(Planet[] bs){
        double xNetFor = 0;
        int N = bs.length;
        for(int i =0; i < N; i++){
            if(this.equals(bs[i])){
                continue;
            }
                xNetFor += this.calcForceExertedByX(bs[i]);
                
            }
        return xNetFor;
        }    

    public double calcNetForceExertedByY(Planet[] bs){
        double yNetFor = 0;
        int N = bs.length;      
        for(int i =0; i < N; i++){                            
            if(this.equals(bs[i])){
                continue;
            }
                yNetFor += this.calcForceExertedByY(bs[i]);
                                                                    
             }                                                      
        return yNetFor;  
     }
    public void update(double dTime, double xForce, double yForce){
        double xAcce = xForce / this.mass;
        double yAcce = yForce / this.mass;
        this.xxVel = this.xxVel + dTime * xAcce;
        this.yyVel = this.yyVel + dTime * yAcce;
        this.xxPos = this.xxPos + dTime * this.xxVel;
        this.yyPos = this.yyPos + dTime * this.yyVel;
        }

    public void draw(){
        StdDraw.picture(this.xxPos, this.yyPos, path + this.imgFileName);
        }
     }
