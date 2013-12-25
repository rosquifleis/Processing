import processing.core.*; 
import processing.xml.*; 

import javax.media.opengl.*; 
import processing.opengl.*; 
import hypermedia.pinguino.*; 

import java.applet.*; 
import java.awt.Dimension; 
import java.awt.Frame; 
import java.awt.event.MouseEvent; 
import java.awt.event.KeyEvent; 
import java.awt.event.FocusEvent; 
import java.awt.Image; 
import java.io.*; 
import java.net.*; 
import java.text.*; 
import java.util.*; 
import java.util.zip.*; 
import java.util.regex.*; 

public class osciloscopio2pinguino_usb extends PApplet {

// Osciloscopio de 6 canales
// Walii   pinguino.walii.es 2010


 // Libreria de pinguino
int valA,valB,valC,valD,valE,valF,valG,valH;
Pinguino board; // objeto pinguino

int[] valuesA;
int[] valuesB;
int[] valuesC;
int[] valuesD;
int[] valuesE;
int[] valuesF;

PFont fontA;
PFont fontB;
PFont fontC;
public void setup()
{

fontA = loadFont("LucidaBright-30.vlw");
fontB = loadFont("LucidaBright-14.vlw");
fontC = loadFont("LucidaBright-20.vlw");
size(800, 600);
board = new Pinguino( this ); // identificamos el PINGUINO

valuesA = new int[width-150];
valuesB = new int[width-150];
valuesC = new int[width-150];
valuesD = new int[width-150];
valuesE = new int[width-150];
valuesF = new int[width-150];

}

public int getY(int val) {

return (int)(val / 1023.0f * (height-40)) - 1;
}
public void draw()
{

valA=board.analogRead(13);
valB=board.analogRead(14);
valC=board.analogRead(15);
valD=board.analogRead(16);
valE=board.analogRead(17);
valF=board.analogRead(19);
valG=board.analogRead(19);
valH=board.analogRead(20);

for (int i=0; i<width-151; i++) {
valuesA[i] = valuesA[i+1];
valuesB[i] = valuesB[i+1];
valuesC[i] = valuesC[i+1];
valuesD[i] = valuesD[i+1];
valuesE[i] = valuesE[i+1];
valuesF[i] = valuesF[i+1];
}

valuesA[width-151] = valA;
valuesB[width-151] = valB;
valuesC[width-151] = valC;
valuesD[width-151] = valD;
valuesE[width-151] = valE;
valuesF[width-151] = valF;

background(0xff212121);

textFont(fontA);

text(valA + 1, (width-140), 108-5);
text(valB + 1, (width-140), 206-5);
text(valC + 1, (width-140), 304-5);
text(valD + 1, (width-140), 402-5);
text(valE + 1, (width-140), 500-5);
text(valF + 1, (width-140), 598-5);
textFont(fontC);
text("Walii  Pinguino.walii.es",10,20);

textFont(fontB);
text("Osciloscopio de 6 canales",270,20);
drawdata("0", width-90, 30, valuesA);
drawdata("1", width-90, 128, valuesB);
drawdata("2", width-90, 226, valuesC);
drawdata("3", width-90, 324, valuesD);
drawdata("4", width-90, 422, valuesE);
drawdata("5", width-90, 520, valuesF);

for (int x=150; x<width-1; x++) {
check(x,255,0,0);
line((width)-x,
6+((height/6)*0)+((height-1-getY(valuesA[x-150]))/6), (width)-1-x,
6+((height/6)*0)+((height-1-getY(valuesA[x-149]))/6));
check(x,0,255,0);
line((width)-x,
4+((height/6)*1)+((height-1-getY(valuesB[x-150]))/6), (width)-1-x,
4+((height/6)*1)+((height-1-getY(valuesB[x-149]))/6));
check(x,0,0,255);
line((width)-x,
2+((height/6)*2)+((height-1-getY(valuesC[x-150]))/6), (width)-1-x,
2+((height/6)*2)+((height-1-getY(valuesC[x-149]))/6));
check(x,255,255,0);
line((width)-x,
0+((height/6)*3)+((height-1-getY(valuesD[x-150]))/6), (width)-1-x,
0+((height/6)*3)+((height-1-getY(valuesD[x-149]))/6));
check(x,0,255,255);
line((width)-x,
-2+((height/6)*4)+((height-1-getY(valuesE[x-150]))/6), (width)-1-x,
-2+((height/6)*4)+((height-1-getY(valuesE[x-149]))/6));
check(x,255,0,255);
line((width)-x,
-4+((height/6)*5)+((height-1-getY(valuesF[x-150]))/6), (width)-1-x,
-4+((height/6)*5)+((height-1-getY(valuesF[x-149]))/6));
}

//colores de las lineas y cajas
stroke(250);
// lineas para dividir las lecturas
line(0,108,width-1,108);
line(0,206,width-1,206);
line(0,304,width-1,304);
line(0,402,width-1,402);
line(0,500,width-1,500);
line( 0, 0, width-1, 0); 
line(width-1, 0, width-1, height-1);
line(width-1, height-1, 0, height-1);
line( 0, height-1, 0, 0); 
}
public void drawdata(String pin, int w, int h, int[] values)
{
fill(0xff7E8AFF);
text("Puerta RA: " + pin, w, h);
text("min: " + str(min(values) + 1), w, h + 14);
text("max: " + str(max(values) + 1), w, h + 28);
}
public void check(int xx, int rr, int gg, int bb)
{

if (xx<=170)
{
float kick = (parseFloat(170-xx)/20)*255;
stroke(rr,gg,bb,255-kick);
}
else
{
stroke(rr,gg,bb);
}
}

/*
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
*/

  static public void main(String args[]) {
    PApplet.main(new String[] { "--present", "--bgcolor=#666666", "--stop-color=#cccccc", "osciloscopio2pinguino_usb" });
  }
}
