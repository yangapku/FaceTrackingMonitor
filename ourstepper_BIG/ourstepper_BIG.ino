#include <Stepper.h>
const int speed=100;
const int stepsPerRevolution = 200;  // change this to fit the number of steps per revolution
                                     // for your motor
int numdata[6] = {0},stepCount = 0, mark=0;
String comdata = "";

// initialize the stepper library on pins 8 through 11:
Stepper myStepper(stepsPerRevolution, 5,6,7,8);            

void setup() {
  // set the speed at 60 rpm:
  pinMode(13,OUTPUT);
  myStepper.setSpeed(speed);
  // initialize the serial port:
  Serial.begin(9600);
}

void readcommand(){
  Serial.println("recommand");
  int j = 0;
if(mark == 1)
  {
    //Serial.println(comdata);
    //Serial.println(comdata.length());
    for(int i = 0; i < comdata.length() ; i++)
    {       
      if(comdata[i] == ',')
      {
        j++;
        Serial.println(numdata[j-1]);
      }
      else
      {
        numdata[j] = numdata[j] * 10 + (comdata[i] - '0');
      }
    }
}
}

void turn(){
  if(numdata[1]==1)
      myStepper.step(stepsPerRevolution*numdata[2]);
  else 
      myStepper.step(-stepsPerRevolution*numdata[2]);     
    delay(500);
    mark = 0;
    for(int i=0; i < 6 ; i++) numdata[i]=0;
}

void loop() {  
  while (Serial.available() > 0)  
    {
        comdata += char(Serial.read());
        delay(2);
        mark=1;
    }  
  if (comdata.length() > 0)
    {  Serial.println(comdata) ;
        readcommand();
        turn();
        comdata = "";
    }

}

