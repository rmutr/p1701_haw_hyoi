char a;  
int led1 = 13;
int led2 = 52;
int led3 = 53;

int buttonPin = 23;
int btn_val = 0;
int btn_change = 0;

int counter = 0;
void setup()
{
  pinMode(buttonPin, INPUT_PULLUP);
  pinMode(led1,OUTPUT);
  pinMode(led2,OUTPUT);
  pinMode(led3,OUTPUT);
  Serial.begin(115200);
}



void loop()
{
  
  if ((!digitalRead(buttonPin)) != btn_val)
  {
    btn_val = (!digitalRead(buttonPin));
    
    if (btn_val == 1) {
      Serial.println("9");
    }
  }
  delay(50);
  led();
/*
            if(buttonState = HIGH){
              delay(10000);
              Serial.print ("9");
              Serial.println();
              Serial.end();  
           }
             if(buttonState = LOW)
            {
             Serial.print ("1");
             Serial.println();
             }
               Serial.end();         
*/
}

void led ()
{
  if(Serial.available()>0)
    {
      a=Serial.read();
      Serial.print ("> ");
      Serial.write (a);
      Serial.println();
      if(a=='1') 
      {
        digitalWrite(led1,HIGH);
        digitalWrite(led2,LOW);
        digitalWrite(led3,LOW);
      }
      else if(a=='2')
      {
        digitalWrite(led1,LOW);
        digitalWrite(led2,HIGH);
        digitalWrite(led3,LOW);
      }
      else if(a=='3')
      {
        digitalWrite(led1,LOW);
        digitalWrite(led2,LOW);
        digitalWrite(led3,HIGH);
      }
      else
      {
        digitalWrite(led1,LOW);
        digitalWrite(led2,LOW);
        digitalWrite(led3,LOW);
      }
    }
    delay(1000);
}
