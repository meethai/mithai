import RPi.GPIO as GPIO
import dht11
import time
import datetime

# initialize GPIO
GPIO.setwarnings(False)
GPIO.setmode(GPIO.BCM)
GPIO.cleanup()

# read data using pin 14
instance = dht11.DHT11(pin=14)

#while True:
result = instance.read()
if result.is_valid():
   print("%d"  % result.temperature)
else:
   print("FAIL")
      
