public class Newton_zoom extends Newton 
{
    final Cmplx one = new Cmplx(1.0, 0.0);

    public int pixelColour(Cmplx pixel)
    {
        Cmplx z; 
        Cmplx z1 = new Cmplx(); 
        Cmplx z2 = new Cmplx();
        Cmplx test = new Cmplx();
        int it = 0;
    
        z = new Cmplx(pixel.real(), pixel.imag());
        do 
        {
            it++;
            z1.sqr(z); z1.mult(3);
            test.cube(z);
            z2.set(test.real(), test.imag()); z2.mult(2); z2.add(one);
            z.divid(z2, z1);
            test.subtr(one);
        } while ( (test.modsq() >= 0.005) && (it <= maxt) );
  
        return it;
    }

}
