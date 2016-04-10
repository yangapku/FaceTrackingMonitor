package monitor;

import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Enumeration;
public class Arduinoconnector {

    private SerialPort serialPort;
    private InputStream in;
    private OutputStream out;
    public Arduinoconnector(String targetname) throws IOException {
    	try {
            System.setProperty("gnu.io.rxtx.SerialPorts", targetname);
            Enumeration portList = CommPortIdentifier.getPortIdentifiers();
            CommPortIdentifier portId = null;
            while (portList.hasMoreElements()) {
                CommPortIdentifier tmpPortId = (CommPortIdentifier) portList.nextElement();
                System.out.println(tmpPortId.getName());
                // ����˿������Ǵ������ж�����
                if (tmpPortId.getPortType() == CommPortIdentifier.PORT_SERIAL) {
                    if (tmpPortId.getName().startsWith("COM")) {
                        portId = tmpPortId;
                    }
                }
            }
            if (portId == null) {
                System.out.println("not detected");
            }
            System.out.println("detected");
            SerialPort serialPortusing = (SerialPort) portId.open("ArduinoSerial", 10000);// �򿪴��ڵĳ�ʱʱ��Ϊ10000ms
            // ���ô�������Ϊ9600������λ8λ��ֹͣλ1�ǣ���żУ����
            
            serialPortusing.setSerialPortParams(9600, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
            this.serialPort=serialPortusing;
      
    	} catch (Exception e) {
            e.printStackTrace();

        }      
        this.in = serialPort.getInputStream();// �õ�������
        this.out = serialPort.getOutputStream();// �õ������
    }
    /*
    public void read() {
        try {
            // ���������������
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String line = null;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/
    public void write(String commandin) throws InterruptedException {
        try {
            
            System.out.println("connected!");
            // ���������������
            OutputStreamWriter writer = new OutputStreamWriter(out);
            BufferedWriter bw = new BufferedWriter(writer);
            bw.write(commandin);//д������
            System.out.println(commandin);
            bw.flush();
            bw.close();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    int steppermove(int direction,int step,Arduinoconnector testObj){
    	try{
    	String commandinto ="1,"+String.valueOf(direction)+","+String.valueOf(step)+",0";
    	testObj.write(commandinto);}
    	catch(Exception e){e.printStackTrace();}
    	return 1;
    }
    public void close() throws IOException {
        in.close();
        out.close();
        this.serialPort.close();
        System.out.println("closed");
    }

    
    public static void main1(String[] args) {
    try{
    	String targetportname= "COM7";//������ҵĵ����ϽӵĴ��ڣ�û���ҵ������Զ�ȷ�����ڵķ�����ֻ���ֶ�����
    	
    	Arduinoconnector testObj = new Arduinoconnector(targetportname);//�Դ��ڽ������Ӳ���
    	Thread.sleep(2000);// ���Ӻ���ͣ2���ټ���ִ��,ִ�������������ͣ
    	System.out.println("start to write");
        //testObj.write("aaa");//��һ�������ַ�����Ϊ�����ʽΪ��[��������],[����1],[����2]����
        					//�������ǵ�һ����1�������ǲ�������������ڶ���������1����1��2������ʱ�����˳ʱ�룻
        					//���������������Ϊ����
        //��������ӿ�
        int direction=1,step =5;
        testObj.steppermove(direction,step,testObj);
        testObj.close();
    }
	catch(Exception e){e.printStackTrace();}
	
        
    }
}
