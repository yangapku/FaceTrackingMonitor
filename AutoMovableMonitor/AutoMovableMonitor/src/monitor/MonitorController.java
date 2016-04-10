package monitor;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamResolution;

public class MonitorController {
	public int frameCount=0;
	public Webcam webcam;
	Timer timer;
	MonitorController(){
		webcam = Webcam.getDefault();
		webcam.setViewSize(WebcamResolution.VGA.getSize());
	}
	public void beginMonit(JPanel panel){
		timer=new Timer();
		timer.schedule(new TimerTask(){
            @Override
            public void run() {
                try {
					ControlIteration(panel);
				} catch (IOException e) {
					e.printStackTrace();
				}
            }
		},0,5000);
	}
	public void ControlIteration(JPanel panel) throws IOException{
		try {
		//Graphics g=panel.getGraphics();
		Image lastpic=Toolkit.getDefaultToolkit().createImage("frames/frame"+(frameCount)+".png");
	//Image lastpic=Toolkit.getDefaultToolkit().createImage("C:/Users/sunwt/Desktop/pic/SAM_2317.JPG");
		//g.drawImage(lastpic,0,0,(int)panel.getWidth(),(int)panel.getHeight(),null);
		System.out.println("here");
		BufferedImage thisFrame=webcam.getImage();
		File file=new File("frames/frame"+(++frameCount)+".png");
	//File file=new File("C:/Users/sunwt/Desktop/pic/SAM_2317.JPG");	
		ImageIO.write(thisFrame,"PNG",file);
		FrameAnalyzer fa=new FrameAnalyzer(file);		
		System.out.println("there");
		//String analyzeResult=fa.faceDetect();
		fa.answer=fa.faceDetect();		
		int PARA=20;                        /////////////////////////////////////////////
		int distance=1;
		System.out.println("tthere");
		int step=distance*fa.decidestep()/PARA;
		
		String targetportname= "COM9";	
    	Arduinoconnector testObj = new Arduinoconnector(targetportname);
    	Thread.sleep(2000);
    	System.out.println("start to write");
        //int direction=1,steperstep =step;
        if (step>=0){
        	        testObj.steppermove(2,step,testObj);
        }else{
	        testObj.steppermove(1,-step,testObj);        	
        }

        testObj.close();
		
		System.out.println(fa.answer);
		} catch (Exception e) { 
			e.printStackTrace(); 
		}
		
	}
}
