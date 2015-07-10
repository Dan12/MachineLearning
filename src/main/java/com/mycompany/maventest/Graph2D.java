package com.mycompany.maventest;

//Simple 2D Graph

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import javax.swing.JFrame;
import javax.swing.JPanel;
import no.uib.cipr.matrix.Matrix;

public class Graph2D {

    private final int panelWidth = 400;
    private final int panelHeight = 400;
    private final int padding = 35;
    private final int graphWidth = panelWidth-padding*2;
    private final int graphHeight = panelHeight-padding*2;
    private int[] xPoints;
    private int[] yPoints;
    private String title = "";
    private boolean scatter;
    private final int scatterRad = 3;
    private double[] xExt;
    private double[] yExt;
    private final int divisions = 10;
    private final int bufferSpace = 5;
    private int equationRes = 10;
    private Equation equation = new Equation();
    Panel p;
    
    public Graph2D(Matrix a){
        double[][] tempa = GenFunc.getMatrixArray(a);
        singleArr(tempa);
    }
    
    public Graph2D(Matrix a, Matrix b){
        double[][] tempa = GenFunc.getMatrixArray(a);
        double[][] tempb = GenFunc.getMatrixArray(b);
        doubleArr(tempa, tempb);
    }
    
    public Graph2D(double[][] a){
        singleArr(a);
    }
    
    public Graph2D(double[][] a, double[][] b){
        doubleArr(a, b);
    }
    
    //side: 1-a is x, 2-a is y
    public Graph2D(double[] a, int side){
        double[] vals = new double[a.length];
        for(int i = 0; i < a.length; i++)
            vals[i] = i;
        if(side == 1)
            setLines(a, vals);
        else
            setLines(vals, a);
    }
    
    private void singleArr(double[][] a){
        double[] senda = new double[a.length];
        double[] sendb = new double[a.length];
        for(int i = 0; i < 2; i++){
            for(int r = 0; r < a.length; r++){
                if(i == 0)
                    senda[r] = a[r][0];
                else
                    sendb[r] = a[r][a[0].length-1];
            }
        }
        setLines(senda, sendb);
    }
    
    private void doubleArr(double[][] a, double[][] b){
        double[] senda = new double[a.length];
        double[] sendb = new double[a.length];
        for(int i = 0; i < 2; i++){
            for(int r = 0; r < a.length; r++){
                if(i == 0)
                    senda[r] = a[r][0];
                else
                    sendb[r] = b[r][0];
            }
        }
        setLines(senda, sendb);
    }
    
    private void setLines(double[] a, double[] b){
        xExt = minMax(a);
        yExt = minMax(b);
        xPoints = mappedPoints(xExt, a, graphWidth);
        yPoints = mappedPoints(yExt, b, graphHeight);
    }
    
    private int[] mappedPoints(double[] ext, double[] a, int dim){
        int[] ret = new int[a.length];
        for(int i = 0; i < a.length; i++){
            ret[i] = (int) GenFunc.map(a[i], ext[0], ext[1], 0, dim);
        }
        return ret;
    }
    
    private double[] minMax(double[] a){
        double[] ret = new double[]{a[0],a[0]};
        for(int i = 0; i < a.length; i++){
            if(a[i] < ret[0])
                ret[0] = a[i];
            if(a[i] > ret[1])
                ret[1] = a[i];
        }
        return ret;
    }
    
    public void setTitle(String t){
        title = t;
    }
    
    //1-connected, 2-scatter
    public void setMode(int m){
        switch(m){
            case 1:
                scatter = false;
                break;
            case 2:
                scatter = true;
                break;
        }
    }
    
    public void setEquation(Equation e){
        equation = e;
    }
    
    public void update(){
        p.repaint();
    }
    
    public void showGraph(){
        JFrame.setDefaultLookAndFeelDecorated(false);
        JFrame fr = new JFrame("Graph");
        p = new Panel(panelWidth, panelHeight);
        fr.setContentPane(p);
        fr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        fr.setLocation(10, 10);
        fr.setResizable(false);
        fr.pack();
        fr.setVisible(true);
    }
    
    private class Panel extends JPanel{
        
        public Panel(int w, int h){
            super.setPreferredSize(new Dimension(w, h));
            super.setBackground(new Color(240,240,240));
        }
        
        @Override
        public void paintComponent(Graphics g){
            super.paintComponent(g);
            
            g.setColor(Color.BLACK);
            g.setFont(new Font("Arial", Font.BOLD, (int) (padding*0.3)));
            
            g.drawLine(padding-bufferSpace, padding-bufferSpace, padding+graphWidth+bufferSpace, padding-bufferSpace);
            g.drawLine(padding-bufferSpace, padding+graphHeight+bufferSpace, padding+graphWidth+bufferSpace, padding+graphHeight+bufferSpace);
            g.drawLine(padding-bufferSpace, padding-bufferSpace, padding-bufferSpace, padding+graphHeight+bufferSpace);
            g.drawLine(padding+graphWidth+bufferSpace, padding-bufferSpace, padding+graphWidth+bufferSpace, padding+graphHeight+bufferSpace);
            
            for(int i = padding; i <= padding+graphWidth; i+=graphWidth/divisions){
                g.drawLine(i, padding+graphHeight+bufferSpace, i, (int) (padding+graphHeight+padding*0.2+bufferSpace));
                g.drawString(String.format("%.1f",GenFunc.map(i,padding,padding+graphWidth,xExt[0],xExt[1])), i, panelHeight-4);
            }
            
            for(int i = padding; i <= padding+graphHeight; i+=graphWidth/divisions){
                g.drawLine((int) (padding-padding*0.2-bufferSpace), panelHeight-i, padding-bufferSpace, panelHeight-i);
                g.drawString(String.format("%.1f",GenFunc.map(i,padding,padding+graphHeight,yExt[0],yExt[1])), 4, panelHeight-i);
            }
            
            if(scatter){
                for(int i = 0; i < xPoints.length; i++)
                    g.fillOval(padding+xPoints[i]-scatterRad, padding+graphHeight-yPoints[i]-scatterRad, scatterRad*2, scatterRad*2);
            }
                
            else{
                for(int i = 0; i < xPoints.length-1; i++)
                    g.drawLine(padding+xPoints[i], padding+graphHeight-yPoints[i], padding+xPoints[i+1], padding+graphHeight-yPoints[i+1]);     
            }
            
            drawEquation(g);
            
            g.setColor(Color.BLACK);
            g.setFont(new Font("Arial", Font.BOLD, (int) (padding*0.8)));
            g.drawString(title, padding, (int) (padding-padding*0.2));
        }
        
        private void drawEquation(Graphics g){
            g.setColor(Color.RED);
            int prevY = (int) GenFunc.map(equation.getYValue(GenFunc.map(padding, padding, padding+graphWidth, xExt[0], xExt[1])), yExt[0], yExt[1], 0, graphHeight);
            for(int i = padding+equationRes; i < graphWidth+padding; i+=equationRes){
                int curY = (int) GenFunc.map(equation.getYValue(GenFunc.map(i, padding, padding+graphWidth, xExt[0], xExt[1])), yExt[0], yExt[1], 0, graphHeight);
                if(prevY >= 0 && prevY <= graphHeight && curY >= 0 && curY <= graphHeight)
                    g.drawLine(i-equationRes, padding+graphHeight-prevY, i, padding+graphHeight-curY);
                prevY = curY;
            }
        }
        
    }

}
