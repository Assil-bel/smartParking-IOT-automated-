//imported libraries




//--------------------------------------------------------------------------------VARLlABLE DECLARATlON---------------------------------------------------------------------------------------

//for bluetooth comunicaton
char inputByte;

//DC motor 1&2
int motor1pin1 = 6;
int motor1pin2 = 7;
int speed1 = 11;
int motor2pin1 = 4;
int motor2pin2 = 5;
int speed2 = 9;



// ------------------------------------------------------------------------------SET UP----------------------------------------------------------------------------------------------------

void setup() {
 // initialize serial communication at 9600 bits per second:
 Serial.begin(9600);
 pinMode(13,OUTPUT);
 
  // sets the motor pins as outputs:
  pinMode(motor1pin1, OUTPUT);
  pinMode(motor1pin2, OUTPUT);
  pinMode(motor2pin1, OUTPUT);
  pinMode(motor2pin2, OUTPUT);
  pinMode(speed1, OUTPUT); 
  pinMode(speed2, OUTPUT);
  
  

  //Controlling speed (0 = off and 255 = max speed):
  analogWrite(speed1, 130); //ENA pin MIN IS 128
  analogWrite(speed2, 130); //ENB pin
}

//-------------------------------------------------------------------------------------------LOOP-------------------------------------------------------------------------------------------
void loop() {
  //if some date is sent va bluetooth, reads it
while(Serial.available()>0){
  inputByte= Serial.read();
  Serial.println(inputByte);
if (inputByte=='Z'){
do{
  inputByte= Serial.read();
  Serial.println(inputByte);
  //-----------------------------------------------parking_functs------------------------
  if (inputByte=='1'){
  digitalWrite(13,HIGH);
  go_to_spot_1();
  }
  else if (inputByte=='2'){
  digitalWrite(13,LOW);
   go_to_spot_2();
  }
   else if (inputByte=='3'){
  digitalWrite(13,LOW);
  go_to_spot_3();
  }
    if (inputByte=='4'){
  digitalWrite(13,HIGH);
  go_to_spot_4();
  }
  //-------------------------------------stop_car------------------
  if (inputByte=='s'){
  digitalWrite(13,HIGH);
  stop_car();
  }
  //---------------------------------------------------------manual mode-------------------------------------------
  if (inputByte=='5'){
  move_forward(380);
  }
   if (inputByte=='6'){
  move_backward(380);
  }
  if (inputByte=='7'){
  move_right(380);
  }
  if (inputByte=='8'){
  move_left(380);
  }
  //---------------------------------------------------break------------------------------------------
  if (inputByte=='C'){
  digitalWrite(13,HIGH);
  stop_car();
  break;
  }

}
while(1);
}
  }
}
//--------------------------------------------------------------------------------------FUNCTlONS-------------------------------------------------------------------------------------------




// moving functions
void move_forward(int distance)
{
  analogWrite(speed1, 130); //ENA pin MIN IS 128
  analogWrite(speed2, 190);
  digitalWrite(motor1pin1, HIGH);
  digitalWrite(motor1pin2, LOW);

  digitalWrite(motor2pin1, HIGH);
  digitalWrite(motor2pin2, LOW);
    delay(distance);
  digitalWrite(motor1pin1, LOW);
  digitalWrite(motor1pin2, LOW);
  digitalWrite(motor2pin1, LOW);
  digitalWrite(motor2pin2, LOW);
}
void move_backward(int distance)
{ 
  analogWrite(speed1, 130); //ENA pin MIN IS 128
  analogWrite(speed2, 200);
  digitalWrite(motor1pin1, LOW);
  digitalWrite(motor1pin2, HIGH);

  digitalWrite(motor2pin1, LOW);
  digitalWrite(motor2pin2, HIGH);
    delay(distance);
  digitalWrite(motor1pin1, LOW);
  digitalWrite(motor1pin2, LOW);
  digitalWrite(motor2pin1, LOW);
  digitalWrite(motor2pin2, LOW);
}
int move_right(int distance)
{  
  analogWrite(speed1, 255); //ENA pin MIN IS 128
  analogWrite(speed2, 255);
  digitalWrite(motor1pin1, LOW);
  digitalWrite(motor1pin2, HIGH);

  digitalWrite(motor2pin1, HIGH);
  digitalWrite(motor2pin2, LOW);
    delay(distance);
  digitalWrite(motor1pin1, LOW);
  digitalWrite(motor1pin2, LOW);
  digitalWrite(motor2pin1, LOW);
  digitalWrite(motor2pin2, LOW);
  }
int move_left(int distance)
{ 
  digitalWrite(motor1pin1, HIGH);
  digitalWrite(motor1pin2, LOW);

  digitalWrite(motor2pin1, LOW);
  digitalWrite(motor2pin2, HIGH);
    delay(distance);
  digitalWrite(motor1pin1, LOW);
  digitalWrite(motor1pin2, LOW);
  digitalWrite(motor2pin1, LOW);
  digitalWrite(motor2pin2, LOW);
  }
 //stopping
void stop_car(){
  digitalWrite(motor1pin1, LOW);
  digitalWrite(motor1pin2, LOW);
  digitalWrite(motor2pin1, LOW);
  digitalWrite(motor2pin2, LOW);
}


//PARKNG FUNCTlONS
void park_horizontally(){
  move_left(1000);
  stop_car();
  delay(300);
  move_backward(600);
  stop_car();
  }

 void park_vertically(){
  move_left(1000);
  stop_car();
  delay(300);
  move_backward(600);
  stop_car();
  }   


 //SPOT FUNCTlONS
 void go_to_spot_1(){
        move_forward(520);
        delay(500);
        move_right(645);
        delay(500);
        move_forward(340);

      }   

  void go_to_spot_2(){
        move_forward(930);
        delay(500);
        move_right(650);
        delay(500);
        move_forward(350);
        
      }   
 
  void go_to_spot_3(){
        move_forward(1250);
        delay(500);
        move_right(655);
        delay(500);
        move_forward(380);
      }
  void go_to_spot_4(){
        move_forward(1760);
        delay(500);
        move_right(660);
        delay(500);
        move_forward(390);
      }
