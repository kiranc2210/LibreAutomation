import logging
import serial
import time
import sys
import serial.tools.list_ports as port_list
ports = list(port_list.comports())
connected = []
for element in ports:
    connected.append(element.device)
print("Connected COM ports: " + str(connected[-1]))
baudrate= 115200
waiting_time = 10
count = 0


def configure_serialport(portnumber, baudrate):

        print(f"Configuring Serial port {portnumber} with Baudrate {baudrate}")
        serialport = serial.Serial()
        serialport.baudrate = baudrate
        serialport.port = portnumber
        serialport.bytesize = serial.EIGHTBITS
        serialport.parity = serial.PARITY_NONE
        serialport.stopbits = serial.STOPBITS_ONE
        # serialport.timeout = 1
        serialport.rtscts = False
        serialport.dsrdtr = False
        serialport.xonxoff = False
        return serialport

try:
    serial_obj = configure_serialport(str(connected[-1]), baudrate)
    serial_obj.open()
    logging.debug(f'Device ReadLog Thread Started.')
    logging.info(f"Configuring Serial port done")
    print(f"Configuring Serial port done")
    logging.debug(f"Configuring Serial port done")
except Exception as e:
    print(f'{e}\n error in creating the serialport\n')
    logging.error(f'Error in creating the serialport\n')
    logging.error(e)
    logging.info(f'\t\t\t\t -------- END --------')
    exit(1)


def SendSerialData(message):
    if serial_obj.isOpen:
        try:
            # Ensure that the end of the message has both \r and \n, not just one or the other
            newmessage = message.strip()
            newmessage += '\r\n'
            serial_obj.write(newmessage.encode('utf-8'))
        except:
            print("Error in sending message: ", sys.exc_info()[0])
        else:
            return True
    else:
        return False


def readSerialLine():
    data = serial_obj.readline().decode('utf-8')
    print(data)

def main():
    global count
    while True:
        SendSerialData("getenv ssid")
        # using ser.readline() assumes each line contains a single reading
        readSerialLine()
        count += 1
        print(count)


if __name__ == "__main__":
    main()