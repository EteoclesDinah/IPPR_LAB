/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package image;


import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import java.util.Arrays;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;


public class Image {
  BufferedImage image;

  //*********************************************************************
  //************** Constructors *****************************************
  public Image(String filename) {   //reads an image from a file specified by given filename
     try{
       this.image = ImageIO.read(new File(filename));
        }catch(IOException e){
          System.out.println("Error: "+e);
      }
  }

  public Image(BufferedImage img) {  //creates 'Image' object from an existing buffered image
    this.image = img;
  }

  public Image(int[][] A) {//gray-scale image from array
    image = new BufferedImage(A.length,A[0].length,
                BufferedImage.TYPE_BYTE_GRAY);
    for(int x=0;x<image.getWidth(); x++)
      for(int y=0;y<image.getHeight();y++) {
        Color newColor = new Color(A[x][y],A[x][y],A[x][y]);
          image.setRGB(x, y,newColor.getRGB());
      }
  }		



  //*******************************************************************
  //************** Basic IO and other functions ***********************

  BufferedImage getImage() {
    return this.image;
  }

  void saveToFile(String filename,String extension) {
     try{
          ImageIO.write(image, "jpg", new File(filename+"."+extension));
        }catch(IOException e){
          System.out.println("Error: "+e);
        }
  }

  public static void saveToFile(int[][] f, String filename, String extension) {
    Image im = new Image(f);
    im.saveToFile(filename, extension);
  }

  void display(String title) {
    ImageIcon icon=new ImageIcon(this.image);
    JFrame frame=new JFrame(title);
        frame.setLayout(new FlowLayout());
        frame.setSize(this.image.getWidth(),this.image.getHeight());
        JLabel lbl=new JLabel();
        lbl.setIcon(icon);
        frame.add(lbl);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

  }

  public static void display(int[][] f,String title) {
    //clip intensities to the range [0,255]
    for(int x=0;x<f.length;x++)
      for(int y=0;y<f[0].length;y++) {
        if(f[x][y]>255)
          f[x][y]=255;
        if(f[x][y]<0)
          f[x][y]=0;
        }

    Image img = new Image(f);
    img.display(title);
  }

  public static void display(double[][] f,String title){
    //clip intensities to the range [0,255]
    int[][] F = new int[f.length][f[0].length];
    for(int x=0;x<f.length;x++)
      for(int y=0;y<f[0].length;y++) {
        F[x][y] = (int) Math.round(f[x][y]);
        if(F[x][y]>255)
              F[x][y]=255;
        if(F[x][y]<0)
              F[x][y]=0;
        }
    Image img = new Image(F);
    img.display(title);

  }


  int[][] getPixelArray(){
    int[][] A = new int[image.getWidth()][image.getHeight()];
    for(int x=0;x<image.getWidth();x++)
      for(int y=0;y<image.getHeight();y++) {
        Color c = new Color(image.getRGB(x, y));
        A[x][y] = (int) (c.getRed()+c.getGreen()+c.getBlue())/3;			}
    return A;	
  }


        //////////////////////////////////////////
  
        public static int[][] threshold (int[][] f, int r) {
          int [][] F = new int [f.length][f[0].length];
            for(int x=0;x<f.length;x++) {
                for(int y=0;y<f[0].length;y++) {
                    if(f[x][y]>r) {
                       F[x][y] = 255;
                    }
                    else {
                       F[x][y] = 0;
                    }
                }
            }
            return F;

        }


        public static int[][] meanIntensityAsThreshold (int[][] f) {
            int [][] F = new int [f.length][f[0].length];
           
           // calculation of mean intensity
            int sum=0;
            for (int x = 0; x < f.length; x++) {
                for (int y = 0; y < f[0].length; y++) {
                   sum += f[x] [y];
                }
            }

            int meanIntensity = sum / (f.length * f[0].length);

           //using mean intensity as threshold

            for(int x=0;x<f.length;x++) {
                for(int y=0;y<f[0].length;y++) {
                    if(f[x][y]>meanIntensity) {
                        F[x][y] = 255;
                    }
                    else {
                        F[x][y] = 0;
                    }
                }
            }

           return F;
        }

        public static int[][] medianIntensityAsThreshold (int[][] f) {
          int [][] F = new int [f.length][f[0].length];

          //1D array to store pixel values

          int[] pixelValues = new int[f.length * f[0].length];

          int index=0;

          //copy pixel va;ues to 1D array

          for (int x = 0; x < f.length; x++) {
              for (int y = 0; y < f[0].length; y++) {
                  pixelValues[index++] = f[x][y];
              }
          }

          // array sort

          Arrays.sort(pixelValues);

          //finding median index

          int medianIndex = pixelValues.length / 2;

          //finding meddian intensity
          int medianIntensity = pixelValues[medianIndex];

          // using median intensity as threshold

          for(int x=0;x<f.length;x++) {
              for(int y=0;y<f[0].length;y++) {
                  if(f[x][y]>medianIntensity) {
                      F[x][y] = 255;
                  }
                  else {
                      F[x][y] = 0;
                  }
              }
          }
          return F;
        }

        ////*////*********************LAB2***********************************************
/*
        public static int[][] threshold (int [][]f, int theta){
            int [][] F = new int [f.length] [f[0].length];

            for(int x=0;x<f.length;x++) {
                    for(int y=0;y<f[0].length;y++) {
                        if(f[x][y]>theta) {
                            F[x][y] = 255;
                        }
                        else {
                            F[x][y] = 0;
                        }

                    }
                }
            return F;

        }
*/
        // log transform *******************************************************************************************************************************

        public static int[][] logTransform (int[][]f) {
            int [][] F= new int [f.length][f[0].length];
            int min = f[0][0];
            int max = f[0][0];

            for(int x=0;x<f.length;x++) {
                for(int y=0;y<f[0].length;y++) {

                    F[x][y] = (int) Math.log(f[x][y] +1);

                    min = Math.min(min, F[x][y]);
                    max = Math.max(max, F[x][y]);
                }
            }

              
            for(int x=0;x<f.length;x++) {
                for(int y=0;y<f[0].length;y++) {

                    F[x][y] = (int)(((F[x][y] - min)/ (double) ( max - min)) * 255);   //normalization of transformed image in [0,255]
                }
            }

            double scale = 255.0 / Math.log(1 + max); // Adjust the scaling factor
            for (int x = 0; x < f.length; x++) {
                for (int y = 0; y < f[0].length; y++) {
                    F[x][y] = (int) (scale * F[x][y]);
                }
            }
            System.out.println("Minimum Pixel Value (After Log Transformation): " + min);
            System.out.println("Maximum Pixel Value (After Log Transformation): " + max);

            return F;


        }

        //histogram*******************************************************************************************************
        /*histogram equalization is a technique used in image processing to emhamce the contrast of an image.
        particularly useful for improving the visibility of details in images with low contrast.
        process:
        1. compute the histogram
        2. calculate cumulative distribution function
        3. normalize the cdf
        4. mapping function
        5. apply the mapping function (to each pixel in the image)
        *****/

        // Calculate histogram
public static int[] calculateHistogram(int[][] image) {
    int[] histogram = new int[256];
    for (int x = 0; x < image.length; x++) {
        for (int y = 0; y < image[0].length; y++) {
            histogram[image[x][y]]++;
        }
    }
    return histogram;
}

// Calculate cumulative distribution function (CDF)
public static int[] calculateCumulativeHistogram(int[] histogram) {
    int[] cumulativeHistogram = new int[256];
    cumulativeHistogram[0] = histogram[0];
    for (int i = 1; i < 256; i++) {
        cumulativeHistogram[i] = cumulativeHistogram[i - 1] + histogram[i];
    }
    return cumulativeHistogram;
}

// Calculate mapping function for histogram equalization
public static int[] calculateMapping(int[] cumulativeHistogram, int totalPixels) {
    int[] mapping = new int[256];
    for (int i = 0; i < 256; i++) {
        mapping[i] = (int) (255.0 * cumulativeHistogram[i] / totalPixels);
    }
    return mapping;
}

// Apply histogram equalization to the image
public static int[][] applyHistogramEqualization(int[][] image, int[] mapping) {
    int[][] equalizedImage = new int[image.length][image[0].length];
    for (int x = 0; x < image.length; x++) {
        for (int y = 0; y < image[0].length; y++) {
            equalizedImage[x][y] = mapping[image[x][y]];
        }
    }
    return equalizedImage;
}


        


        //convolution********************************************************************************************************************************

        public static int [][] convolution (int [][]f) {
            int [][] F = new int [f.length][f[0].length];

            //int [][] w = {{0,1,0},{1,-4,1},{0,1,0}};

            int [][] w = {{0,1,0},{1,-4,1},{0,1,0}};  //kernel for laplacian

            int a = (w.length - 1)/2;
            int b = (w[0].length - 1)/2;

            int[][] f_padded = new int[2*a+f.length][2*b+f[0].length];

            for(int x=0;x<f.length;x++)
                for(int y=0;y<f[0].length;y++)
                    f_padded[a+x][b+y] = f[x][y];

            for (int x=0; x<f.length ; x++) 
                for (int y=0; y<f[0].length; y++)
                    for (int s=-a;s<=a;s++)
                    for(int t=-b;t<b;t++){
                        int v = w[s+a][t+b];
                        F[x][y] = F[x][y] + v*f_padded[(a+x)-s][(b+y)-t];
                    }

            //re-scaling

            int min = f[0][0];
            int max = f[0][0];

            for(int x=0;x<f.length;x++) {
                for(int y=0;y<f[0].length;y++) {

                    F[x][y] = (int) Math.log(f[x][y] +1);

                    min = Math.min(min, F[x][y]);
                    max = Math.max(max, F[x][y]);
                }
            }

            for(int x=0;x<f.length;x++) {
                for(int y=0;y<f[0].length;y++) {

                    F[x][y] = (int)(((F[x][y] - min)/ (double) ( max - min)) * 255);
                }
            }

            double scale = 255.0 / Math.log(1 + max); // Adjust the scaling factor
            for (int x = 0; x < f.length; x++) {
                for (int y = 0; y < f[0].length; y++) {
                    F[x][y] = (int) (scale * F[x][y]);
                }
            }

            return F;
        }



        //averaging*******************************************************************************************************************************

        //function to get filter size from user
        public static int getFilterSizeFromUser() {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter the filter size for blurring (an integer): ");
            return scanner.nextInt();
        }

        //averaging filter
        public static int [][] averaging (int [][]f, int filterSize){
            int [][] F = new int [f.length][f[0].length];

            int filterArea = filterSize * filterSize;

            for (int x=0; x<f.length; x++) {
                for (int y=0; y<f[0].length; y++) {
                    int sum =0;
                    for (int i= -filterSize/2; i<=filterSize/2; i++) {
                        for (int j = -filterSize/2; i<=filterSize/2; i++) {
                            int newX = Math.max(0, Math.min(f.length - 1, x+i));
                            int newY = Math.max(0, Math.min(f[0].length - 1, y+j));
                            sum += f[newX][newY];
                        }
                    }
                    F[x][y] = sum / filterArea;
                }
            }
            return F;
        }

        //image Gradient using Sobel's masks************************************************************************************************************

        public static int [][] imageGradient(int [][] f) {

            int [][] F = new int [f.length][f[0].length];

            int[][] sobelX = {{-1, 0, 1}, {-2, 0, 2}, {-1, 0, 1}};
            int[][] sobelY = {{-1, -2, -1}, {0, 0, 0}, {1, 2, 1}};

            for (int x = 1; x < f.length - 1; x++) {
                for (int y = 1; y < f[0].length - 1; y++) {
                    int gradientX = 0;
                    int gradientY = 0;

                    for (int i = 0; i < 3; i++) {
                        for (int j = 0; j < 3; j++) {
                            gradientX += sobelX[i][j] * f[x - 1 + i][y - 1 + j];
                            gradientY += sobelY[i][j] * f[x - 1 + i][y - 1 + j];
                        }
                    }

                    // Calculate gradient magnitude
                    F[x][y] = (int) Math.sqrt(gradientX * gradientX + gradientY * gradientY);

                    // Clamp values to be in the valid color range
                    F[x][y] = Math.min(255, Math.max(0, F[x][y]));
                }
            }
            return F;
        }



        ////////////////////////LAB3**********************************///////////////////////////////////////////

        public int getFilterWindowFromUser() {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter the filter window (odd integer): ");
            return scanner.nextInt();
        }

        public static int [][] meanFilter (int [][] r, int filterSize) {
            int [][] R = new int [r.length][r[0].length];

            for (int x = 0; x<r.length; x++) {
                for (int y = 0; y<r[0].length; y++) {
                    int sum = 0;
                    int count = 0;

                    //sum of pixels within the filter window
                    for (int i = -filterSize / 2; i <= filterSize / 2; i++) {
                        for (int j = -filterSize / 2; j <= filterSize / 2; j++) {
                            int newX = Math.max(0, Math.min(r.length - 1, x + i));
                            int newY = Math.max(0, Math.min(r[0].length - 1, y + j));
                            sum += getPixelValue(newX, newY, r);
                            count++;
                        }
                    }
                    R[x][y] =  sum / count;    //finding mean
                }
            }
            return R;
        }

        private static int getPixelValue(int x, int y, int[][] r) {
            return r[x][y];
        }


        public static int [][] medianFilter (int [][] r, int filterSize) {
            int [][] R = new int [r.length][r[0].length];

            for (int x = 0; x<r.length; x++) {
               for (int y = 0; y<r[0].length; y++) {

                   //create a neighbourhood array
                   int[] neighbourhood = new int[filterSize * filterSize];
                   int index = 0;

                   //populate the neighbourhood array with pixel values
                   for (int i = -filterSize / 2; i <= filterSize / 2; i++) {
                        for (int j = -filterSize / 2; j <= filterSize / 2; j++) {
                            int newX = Math.max(0, Math.min(r.length - 1, x + i));
                            int newY = Math.max(0, Math.min(r[0].length - 1, y + j));
                            neighbourhood[index++] = getPixelValue(newX, newY, r);
                        }
                    }

                   //finding median 
                   Arrays.sort(neighbourhood);
                   R[x][y] = neighbourhood[neighbourhood.length / 2];
               } 
            }
            return R;
        }


        /////////////////////////////////mse

        public static double calculateMSE(int[][] original, int[][] restored) {
           if (original.length != restored.length || original[0].length != restored[0].length) {
            throw new IllegalArgumentException("Image dimensions do not match.");
            }

            int height = original.length;
            int width = original[0].length;
            double sumSquaredError = 0.0;

            for (int x = 0; x < height; x++) {
                for (int y = 0; y < width; y++) {
                    int error = original[x][y] - restored[x][y];
                    sumSquaredError += error * error;
                }
            }

            return sumSquaredError / (height * width); 
        }



  public static void main(String[] args) {

            Image img = new Image("C:\\Users\\Kamala Rai Danuwar\\Pictures\\Iam grut.jpg");
            int[][] f = img.getPixelArray();

    //////////////********LAB 1******************************************
    //QUESTION 1
    /*
           int [][] F = threshold (f,128);
           Image.display(f, "Original Image");
           Image.display(F, "Threshold Image");
            */
                    
    

    //QUESTION 2
    /*
            int [][] F = meanIntensityAsThreshold(f);
            Image.display(f, "Original Image");
            Image.display(F, "Mean Intensity Threshold Image");

*/

    //QUESTION 3
    /*
            int[][] F = medianIntensityAsThreshold(f);
            Image.display(f,"Original Image"); 
            Image.display(F, "Median Intensity Threshold Image");
    */
    
    
    
           ////////////////////************LAB2***************///////////////////////


           /*
            int [][] F = logTransform(f);
            Image.display(f, "Original Image");
            Image.display(F, "Image After Log Transform");
*/
           
           //histogram equalization'
           
 /*          
            int[] histogram = calculateHistogram(f);
            int[] cumulativeHistogram = calculateCumulativeHistogram(histogram);
            int[] mapping = calculateMapping(cumulativeHistogram, f.length * f[0].length);

            int[][] equalizedImage = applyHistogramEqualization(f, mapping);

            Image.display(f, "Original Image");      
            Image.display(equalizedImage, "Image After Histogram Equalization");
*/
           
           

           //convolution and correlation
           
           /*
            int [][] F = convolution(f);

            Image.display(f, "Original Image");
            Image.display(F, "Laplacian of Image");
            
*/
           //blurring
           /*
           int filterSize = img.getFilterSizeFromUser();
           int [][] F = averaging(f, filterSize);
           Image.display(f,"Original Image");
           Image.display(F,"Blurred Image");
            */

           //image gradient using Sobel's mask
           /*
           int [][] F = imageGradient(f);
           Image.display(f, "Original Image");
           Image.display(F, "Gradient Image");
           */



           ////////////////////***********LAB3*********************************************************************//////////////////
           
           
           /*
           Image messi0 = new Image("C:\\Users\\Kamala Rai Danuwar\\Documents\\NetBeansProjects\\IPPR\\messi.jpg");
           int [][] r0 = messi0.getPixelArray();
           Image messi1 = new Image("C:\\Users\\Kamala Rai Danuwar\\Documents\\NetBeansProjects\\IPPR\\messi_N.jpg");
           int [][] r1 = messi1.getPixelArray();

           int filterWindow = messi1.getFilterWindowFromUser();
           */
           
           
/*
           Image ronaldo0 = new Image("C:\\Users\\Kamala Rai Danuwar\\Documents\\NetBeansProjects\\IPPR\\ronaldo.jpg");
           int[][] r0 = ronaldo0.getPixelArray();
           Image ronaldo1 = new Image("C:\\Users\\Kamala Rai Danuwar\\Documents\\NetBeansProjects\\IPPR\\ronaldo_N.jpg");
           int[][] r1 = ronaldo1.getPixelArray();
           int filterWindow = ronaldo1.getFilterWindowFromUser();
           */


           /*
           Image ronaldo_de_lima0 = new Image("C:\\Users\\Kamala Rai Danuwar\\Documents\\NetBeansProjects\\IPPR\\ronaldo_de_lima.jpg");
           int[][] r0 = ronaldo_de_lima0.getPixelArray();
           Image ronaldo_de_lima1 = new Image("C:\\Users\\Kamala Rai Danuwar\\Documents\\NetBeansProjects\\IPPR\\ronaldo_de_lima_N.jpg");
           int[][] r1 = ronaldo_de_lima1.getPixelArray();

           int filterWindow = ronaldo_de_lima1.getFilterWindowFromUser();
           */
           
           //the below code snippets are same for all three images
  /*         
           int [][] R0 = meanFilter(r1, filterWindow);

           int [][] R1 = medianFilter (r1,filterWindow);

           Image.display(r0, "Original Image");
           Image.display(r1, "Corrupted Image");
           Image.display(R0, "Restored Image using Mean Filter");
           Image.display(R1, "Restored Image using Median Filter");

           //MSE provides quantitative measure of diff betn original and restored images
           //lower MSE values indicate better image restoration
    

           double mseMeanFilter = calculateMSE(r0, R0 );  //passing original image and image after mean filter
           System.out.println("Mean Squared Error (Mean Filter): " + mseMeanFilter);

           double mseMedianFilter = calculateMSE(r0, R1 );  //passing original image and image after median filter
           System.out.println("Mean Squared Error (Median Filter): " + mseMedianFilter);   
           
*/
        }


}