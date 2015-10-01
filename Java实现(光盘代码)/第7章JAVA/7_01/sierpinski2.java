//Ԫ���Զ������ɵ�Sierpinski������(����Ƶ����fractal.cn)2004

import java.io.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

//һάԪ���Զ�������
public class sierpinski2 extends JApplet implements ActionListener
{

     private int SIZE = 800;
     private int cellarr1[] = new int[SIZE];
     private int cellarr2[] = new int[SIZE];

     private JButton resetcells;
     private JButton drawcells;


     public void init()
     {
          Container container = getContentPane();
          container.setLayout( new FlowLayout() );

          drawcells = new JButton("Draw Cell Pattern");
          drawcells.addActionListener(this);
          container.add(drawcells);

          resetcells = new JButton("Reset Cell Pattern");
          resetcells.addActionListener(this);
          container.add(resetcells);

          initialize_cells();

     }

     public void initialize_cells()
     {
          //��ʼ��Ԫ��
          for(int i = 0;i < SIZE; i++)
          {
               cellarr1[i] = 0;
               cellarr2[i] = 0;
          }

          //��������Ԫ��
          cellarr1[SIZE / 2] = 1;

     }


     public void actionPerformed( ActionEvent actionEvent)
     {
          if(actionEvent.getSource() == resetcells)
          {
               initialize_cells();
          }
          if(actionEvent.getSource() == drawcells)
          {
               repaint();
          }
     }

     public void paint(Graphics g)
     {
          super.paint( g );
        
          //Ԫ���Զ�����ʼ����
          for(int i = 0;i<SIZE - 2;i++)
          {
               for(int j = 0;j<SIZE - 2;j++)
               {

                    //��ʾ��ǰԪ������
                    if (cellarr1[j + 1]==1)
                    {
                         g.setColor(new Color(127,213,13));
                    }
                    else
                    {
                         g.setColor(new Color(0,0,0));
                    }

                    g.drawLine(j,i,j,i);

                    int nbr = cellarr1[1 + (j - 1) % (SIZE - 2)] + cellarr1[1 + (j + 1) % (SIZE - 2)];

                    if (cellarr1[j + 1] == 1)
                    {
                         if (nbr == 0)
                         {
                              cellarr2[1 + (j - 1) % (SIZE - 2)] = 1;
                              cellarr2[1 + (j) % (SIZE - 2)] = 1;
                              cellarr2[1 + (j + 1) % (SIZE - 2)] = 1;
                         }

                         if (nbr == 1)
                         {
                              if (cellarr1[1 + (j - 1) % (SIZE - 2)] == 0) cellarr2[1 + (j - 1) % (SIZE - 2)] = 1;
                              cellarr2[1 + j % (SIZE - 2)] = 1;
                              if (cellarr1[1 + (j + 1) % (SIZE - 2)] == 0) cellarr2[1 + (j + 1) % (SIZE - 2)] = 1;
                         }

                         if (nbr == 2)
                         {
                              cellarr2[1 + j % (SIZE - 1)] = 0;
                         }

                    }

               }

               for(int h = 0; h< SIZE - 2; h++)
               {
                    cellarr1[h] = cellarr2[h];
               }

          }
     }
}
