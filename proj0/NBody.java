public class NBody{

    public static double readRadius(String name){
        In in = new In(name);
        in.readLine();
        double radius = in.readDouble();
        return radius;
    }

    public static Planet[] readPlanets(String name){
        In in = new In(name);
        in.readLine();
        in.readLine();
        Planet[] planets = new Planet[5];
        for(int i = 0; i < 5; i++){
            planets[i] = new Planet(in.readDouble(), in.readDouble(),in.readDouble(),
                                    in.readDouble(), in.readDouble(),in.readString());
            }
            return planets;
        }

    public static void main(String[] args){
        String imageToDraw = "images/starfield.jpg";
        double t = 0;
        double T = Double.parseDouble(args[0]);
        double dt = Double.parseDouble(args[1]);
        String filename = args[2];
        double radius = readRadius(filename);
        StdDraw.enableDoubleBuffering();
        Planet[] bodies = readPlanets(filename);
        int N = bodies.length;
        StdDraw.setScale(-radius, radius); 

        for(t = 0; t<T; t = t+dt){
            double[] xForces = new double[N];
            double[] yForces = new double[N];
            for(int i = 0; i < N; i++){
                xForces[i] = bodies[i].calcNetForceExertedByX(bodies);
                yForces[i] = bodies[i].calcNetForceExertedByY(bodies);
                }
            for(int i = 0; i < N; i++){
                bodies[i].update(dt, xForces[i], yForces[i]);
                }
            StdDraw.picture(0, 0, imageToDraw);
            for(int i = 0; i < N; i++){
                bodies[i].draw();
                }

            StdDraw.show();
            StdDraw.pause(10);
         }
         StdOut.printf("%d\n", bodies.length);
         StdOut.printf("%.2e\n", radius);
        for (int i = 0; i < bodies.length; i++) {
            StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
            bodies[i].xxPos, bodies[i].yyPos, bodies[i].xxVel,
            bodies[i].yyVel, bodies[i].mass, bodies[i].imgFileName);
        }
     }
}
    
