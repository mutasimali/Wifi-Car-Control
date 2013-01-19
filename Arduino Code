#include <Servo.h> //servo library for arduino

String readstring; //string to save the income strings
Servo MotorA; //right - left motor
Servo MotorB;// forward - backward motor
int light1=6; // light pin
int light2=7; // light pin
int buzzer=5; // buzzer pin

void setup() {
  
Serial.begin(115200); // to initialize the serial port for communications 

MotorA.attach(9); // right - left motor pin
MotorB.attach(10);  // forward - backward motor pin

for(int p=5;p<8;p++)
{pinMode(p, OUTPUT);} // make 5,6,7 pins as Output for (I/O)

Serial.println("Ready !"); // print in serial monitor "Ready !"

}


void loop() {

  while (Serial.available()) { //if serial port is used and active

  delayMicroseconds(65); // delay 65 microseconds
  if (Serial.available() >0) { // if there is income
      char c =Serial.read(); // char to get all the income 
      readstring += c;}} // save all the income letters in string 

if (readstring.length() >0) { // if the string for all the income has value   

    Serial.println(readstring); // print in serial monitor the string value
    char carray[readstring.length() + 1]; // char array to change the string to char array
    int space= readstring.indexOf(' '); // the space position in the string
    
  if (readstring.charAt(space + 1) == 'A') // to find the letter after the space position if A to work with the right - left motor 
    { readstring.toCharArray(carray, sizeof(carray)); // convert the saved value in the string to char array
      int n = atoi(carray); // change char array to integer 
      MotorA.write(n);} // move the motor to the integer value 
    
  if (readstring.charAt(space + 1) == 'B')  // to find the letter after the space position if B to work with the forward - backward motor 
    { readstring.toCharArray(carray, sizeof(carray)); // convert the saved value in the string to char array
      int n = atoi(carray); // change char array to integer 
      if(n<=83){digitalWrite(5, HIGH);} // if the motor value is backward Turn the buzzer On
      else{digitalWrite(5, LOW);}// if the motor value is forward Turn the buzzer Off
      MotorB.write(n);} // move the motor to the integer value 
    
  if (readstring.charAt(space + 1) == 'L')// to find the letter after the space position if L to turn th lights On
    {digitalWrite(light1, HIGH);  //turn the lights on
      digitalWrite(light2, HIGH);} //turn the lights on
  
  if (readstring.charAt(space + 1) == 'l') // to find the letter after the space position if l to turn th lights Off
    {digitalWrite(light1, LOW);  //turn the lights off
      digitalWrite(light2, LOW);} //turn the lights off
} 

readstring=""; // empty the string to get a new value

} 
