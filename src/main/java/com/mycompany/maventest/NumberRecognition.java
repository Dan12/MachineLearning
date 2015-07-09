package com.mycompany.maventest;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import javax.swing.JFrame;
import javax.swing.JPanel;
import no.uib.cipr.matrix.DenseMatrix;
import no.uib.cipr.matrix.Matrix;

public class NumberRecognition {
    
    private int scale = 16;
    private int imgDim = 20;
    private int weight = 60;
    NeuralNetwork nn;
    private int fontSize = scale*2;
    private int predictedVal = 0;

    public NumberRecognition(){
        Matrix tempY = MatFileInt.readFile("ex3data1.mat", "y");
        Matrix tempX = MatFileInt.readFile("ex3data1.mat", "X");
        nn = new NeuralNetwork(tempX, tempY, 10, 1, new int[]{25}, 1);
        nn.loadWeights("nnweights.mat");
    }
    
    public void startApplication(){
        JFrame.setDefaultLookAndFeelDecorated(false);
        JFrame fr = new JFrame("Graph");
        Panel p = new Panel(scale*imgDim);
        fr.setContentPane(p);
        fr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        fr.setLocation(10, 10);
        fr.setResizable(false);
        fr.pack();
        fr.setVisible(true);
    }
    
    private class Panel extends JPanel implements MouseMotionListener, KeyListener{
        
        private int[][] pixels;
        private int height;
        
        public Panel(int d){
            height = (int) (d+fontSize*1.3);
            super.setPreferredSize(new Dimension(d, height));
            super.setBackground(new Color(240,240,240));
            super.setFocusable(true);
            addMouseMotionListener(this);
            addKeyListener(this);
            pixels = new int[imgDim][imgDim];
            clearPixels();
        }
        
        private void clearPixels(){
            for(int r = 0; r < imgDim; r++){
                for(int c = 0; c < imgDim; c++)
                    pixels[r][c] = 125;
            }
        }
        
        @Override
        public void paintComponent(Graphics g){
            super.paintComponent(g);
            
            g.setFont(new Font("Arial",Font.BOLD, fontSize));
            
            for(int r = 0; r < imgDim; r++){
                for(int c = 0; c < imgDim; c++){
                    g.setColor(new Color(pixels[r][c],pixels[r][c],pixels[r][c]));
                    g.fillRect(r*scale, c*scale, scale, scale);
                }
            }
            
            g.setColor(Color.BLACK);
            g.drawString("Predicted Value: "+predictedVal, 10, height-4);
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            int r = e.getX()/scale;
            int c = e.getY()/scale;
            if(r >= 1 && r < imgDim-1 && c >= 1 && c < imgDim-1){
                addWeight(r, c, weight);
                addWeight(r+1, c, weight/2);
                addWeight(r, c+1, weight/2);
                addWeight(r-1, c, weight/2);
                addWeight(r, c-1, weight/2);
                //System.out.println(r+","+c);
                guessNumber();
                repaint();
            }
        }
        
        public void guessNumber(){
            Matrix data = new DenseMatrix(1,imgDim*imgDim);
            int indAt = 0;
            for(int r = 0; r < imgDim; r++){
                for(int c = 0; c < imgDim; c++){
                    data.set(0, indAt, GenFunc.map(pixels[r][c], 0, 255, -1, 1));
                    indAt++;
                }
            }
            Matrix pred = nn.predict(data);
            predictedVal = ((int)pred.get(0, 0)+1)%10;
        }
        
        public void addWeight(int r, int c, int w){
            pixels[r][c]+=w;
            if(pixels[r][c] > 255)
                pixels[r][c] = 255;
        }

        @Override
        public void mouseMoved(MouseEvent e) {}

        @Override
        public void keyTyped(KeyEvent e) {}

        @Override
        public void keyPressed(KeyEvent e) {
            if(e.getKeyCode() == 10){
                clearPixels();
                repaint();
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {}
    }

}
