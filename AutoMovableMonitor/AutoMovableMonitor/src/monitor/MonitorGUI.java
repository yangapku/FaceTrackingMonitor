package monitor;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import com.github.sarxos.webcam.WebcamResolution;

import javax.swing.JButton;
import java.awt.Color;

public class MonitorGUI extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private WebcamPanel panel;
	JButton beginbtn;
	JButton stopbtn;
	MonitorController monitcontrol;
	JPanel lastframe;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MonitorGUI frame = new MonitorGUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MonitorGUI() {
		setTitle("MovableMonitor");
		monitcontrol=new MonitorController();
		
		addWindowListener(new WindowAdapter(){  
			public void windowClosing(WindowEvent e){ 
				monitcontrol.webcam.close();
				super.windowClosing(e);
			}
		}); 
		setBounds(100, 100, 708, 481);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		panel = new WebcamPanel(monitcontrol.webcam);
		panel.setBounds(33, 34, 390, 362);
		panel.setFPSDisplayed(true);
		panel.setDisplayDebugInfo(true);
		panel.setImageSizeDisplayed(true);
		panel.setMirrored(true);
		contentPane.add(panel);
		
		beginbtn = new JButton("Begin Monitor");
		beginbtn.setBounds(495, 157, 130, 29);
		contentPane.add(beginbtn);
		
		stopbtn = new JButton("Stop Monitor");
		stopbtn.setBounds(495, 257, 130, 29);
		contentPane.add(stopbtn);
		
		/*lastframe = new JPanel();
		lastframe.setBackground(Color.WHITE);
		lastframe.setBounds(476, 34, 161, 147);
		contentPane.add(lastframe);*/
		
		beginbtn.addActionListener(e->{
			monitcontrol.beginMonit(lastframe);
		});
		
		stopbtn.addActionListener(e->{
			monitcontrol.timer.cancel();
		});
	}
}
