//PINGUINO 6 canales analogicos
// ejemplo general escucha processing 
// walii pinguino.walii.es
int i;
uchar todo,mode,pin,value;
unsigned char buffer[2];
int temp;
int endstring;

void clear();



void setup() {
	for( i=0; i<8; i++ ) pinMode( i, OUTPUT );
	clear();
}

void loop() {

	// select action to perform...
	if ( USB.available() ) {
		if (USB.read()=='+')
			{
			todo =USB.read();
			if ( todo=='C' ) clear();	// clear all
			if ( todo=='W' ) {		// switch on/off the specified pin
				mode = USB.read();
				pin = USB.read();
				value  = USB.read();
				if ( mode=='D' ) digitalWrite( pin, value );
				if ( mode=='A' ) {
					temp=value+(USB.read()*256);
					analogWrite( pin, temp );
					}
				}		
			if ( todo=='R' ) {
					mode = USB.read();
					pin = USB.read();
					if ( mode=='D' ) {
						buffer[0] = digitalRead( pin );
						USB.send(  buffer, 1 );
						}
					if ( mode=='A' ) {
						temp = analogRead( pin  );
						buffer[0]=temp;
						buffer[1]=temp/256;
						USB.send(  buffer, 2 );
						}
					}
		endstring=USB.read();	// read end string byte ( 0 )
		}
	
	}
}

void clear() {
	for( i=0; i<8; i++ ) {
		digitalWrite( i, LOW );
	}
}


