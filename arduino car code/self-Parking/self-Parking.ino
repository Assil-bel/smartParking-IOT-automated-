//imported libraries




//--------------------------------------------------------------------------------VARLlABLE DECLARATlON---------------------------------------------------------------------------------------

//for bluetooth comunicaton
char inputByte;

//DC motor 1&2
int motor1pin1 = 6;
int motor1pin2 = 7;
int speed1 = A4;
int motor2pin1 = 4;
int motor2pin2 = 5;
int speed2 = A5;


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
  analogWrite(speed1, 200); //ENA pin MIN IS 128
  analogWrite(speed2, 200); //ENB pin
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
  
  if (inputByte=='1'){
  digitalWrite(13,HIGH);
   digitalWrite(motor1pin1, LOW);
  digitalWrite(motor1pin2, HIGH);

  digitalWrite(motor2pin1, LOW);
  digitalWrite(motor2pin2, HIGH);
  }
  else if (inputByte=='2'){
  digitalWrite(13,LOW);
   digitalWrite(motor1pin1, HIGH);
  digitalWrite(motor1pin2, LOW);

  digitalWrite(motor2pin1, HIGH);
  digitalWrite(motor2pin2, LOW);
  }
   else if (inputByte=='3'){
  digitalWrite(13,LOW);
    digitalWrite(motor1pin1, HIGH);
  digitalWrite(motor1pin2, LOW);

  digitalWrite(motor2pin1, LOW);
  digitalWrite(motor2pin2, HIGH);
  }
    if (inputByte=='4'){
  digitalWrite(13,HIGH);
    digitalWrite(motor1pin1, LOW);
  digitalWrite(motor1pin2, HIGH);

  digitalWrite(motor2pin1, HIGH);
  digitalWrite(motor2pin2, LOW);
  }
  if (inputByte=='s'){
  digitalWrite(13,HIGH);
   digitalWrite(motor1pin1, LOW);
  digitalWrite(motor1pin2, LOW);

  digitalWrite(motor2pin1, LOW);
  digitalWrite(motor2pin2, LOW);
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
void move_backward(int distance)
{  
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
int move_right(int distance)
{  
  digitalWrite(motor1pin1, LOW);
  digitalWrite(motor1pin2, HIGH);

  digitalWrite(motor2pin1, LOW);
  digitalWrite(motor2pin2, LOW);
    delay(distance);
  digitalWrite(motor1pin1, LOW);
  digitalWrite(motor1pin2, LOW);
  digitalWrite(motor2pin1, LOW);
  digitalWrite(motor2pin2, LOW);
  }
int move_left(int distance)
{ 
  digitalWrite(motor1pin1, LOW);
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
void stop_car(int distance){
  digitalWrite(motor1pin1, LOW);
  digitalWrite(motor1pin2, LOW);
  digitalWrite(motor2pin1, LOW);
  digitalWrite(motor2pin2, LOW);
  delay(distance);
}


//PARKNG FUNCTlONS
void park_horizontally(){
  move_left(1500);
  stop_car(400);
  move_backward(1000);
  stop_car(400);
  }

 void park_vertically(){
  move_right(1500);
  stop_car(400);
  move_backward(1000);
  }   


 //SPOT FUNCTlONS
 void go_to_spot_1(){
        move_forward(1000);
        stop_car(400);
        move_left(500);
        move_forward(2000);
        stop_car(400);
        park_horizontally();
      }   

  void go_to_spot_2(){
        move_forward(1500);
        move_right(500);
        move_forward(2000);
        park_vertically();
      }   
 
  void go_to_spot_3(){
        move_forward(2000);
        move_left(500);
        move_forward(2000);
        park_horizontally();
      }
  void go_to_spot_4(){
        move_forward(3000);
        move_left(500);
        move_forward(2000);
        park_horizontally();
      }
