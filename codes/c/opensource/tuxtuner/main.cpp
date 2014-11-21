#include "RtAudio.h"
#include "include/kiss_fft.h"
#include "include/kiss_fftr.h"
#include <iostream>
#include <string>
//#include <cstdlib>
//#include <cstring>
#include <complex>
#include <cmath>
#include <fstream>
//#include<stdio.h>
#include <unistd.h>
#if defined(__WINDOWS_DS__)
#define sleep(n) Sleep(1000*n)
#endif
#include "RtError.h"
#define PI 3.14592653589

using namespace std;

typedef signed short BUFF;

struct data{
	signed short* buffer;		//location of data
	unsigned long bufferBytes;	//#of bytes of data
	unsigned long totalFrames;	//number of frames per sample (t * fs)
	unsigned long frameCounter;	//number of current frames used
	unsigned int channels;		//number of channels to record
};


struct note {
	static const float f[];
	static const string p[];
	static const int total;
} notes;


const float note::f[] = {
	82.407, 110.00, 146.83, 196.00, 246.94,329.63, 440.00, 466.16, 493.88
};

const string note::p[] = {
	"E2","A2","D3","G3","B3","E4","A4"
};
const int note::total = 7;

void clean(RtAudio adc, data d)
{
	if ( adc.isStreamOpen() ) adc.closeStream();
	if (d.buffer) free(d.buffer);
	exit(0);
}

string findNote(float fund)
{
	for (int i = 0; i < notes.total; i++) {
		if (abs(fund - notes.f[i] )< 10 ) {
			return notes.p[i];
		}
	}
	return "0"; 
}

float findFund(float fund)
{
	for (int i = 0; i < notes.total; i++) {
		if (abs(fund - notes.f[i] )< 10 ) {
			return notes.f[i];
		}
	}
	return 0; 
}

complex<float>* FFT( complex<float>* x, int NFFT )
{
	complex<float>* X = new complex<float>[NFFT];

	if ( NFFT == 1 )
	{
		X[0] = x[0];
		return X;
	}

	else
	{
		complex<float>* even = new complex<float>[NFFT/2];
		complex<float>* odd  = new complex<float>[NFFT/2];
		int n;

		for(n = 0; n < NFFT/2; n++)
		{
			even[n] = x[2*n];
			odd[n]  = x[2*n+1];
		}

		complex<float>* EVEN = FFT( even, NFFT/2 );
		complex<float>* ODD = FFT( odd, NFFT/2 );
		complex<float> twiddle;

		delete [] even;
		delete [] odd;

		for(n = 0; n < NFFT/2; n++)
		{
			twiddle= polar( 1.0, -(2*PI*n)/NFFT );
			ODD[n] = ODD[n] * twiddle;
			X[n]   = EVEN[n] + ODD[n];
			X[n+NFFT/2] = EVEN[n] - ODD[n];
		}

		delete [] EVEN;
		delete [] ODD;

		return X;
	}
}



int record( void *outputBuffer, void *inputBuffer, unsigned int nBufferFrames,
		double streamTime, RtAudioStreamStatus status, void *userData )
{
	data* d = (data*) userData;

	if ( status )
		std::cout << "Stream overflow detected!" << std::endl;

	unsigned int frames = nBufferFrames;		//number of new frames

	//If # new frames is more than it can hold,
	//Only copy in as much as buffer size will hold
	if( d->frameCounter + frames > d->totalFrames ) {
		frames = d->totalFrames - d->frameCounter;
		d->bufferBytes = frames * d->channels * sizeof( BUFF );
	}

	//Copy the new data at the starting position
	unsigned long start = d->frameCounter * d->channels;
	memcpy( d->buffer+start, inputBuffer, d->bufferBytes);

	/*To hear the input:*/
	//memcpy( outputBuffer, inputBuffer, d->bufferBytes);

	//Increment frame counter
	d->frameCounter += frames;

	//Check boundary once more
	if (d->frameCounter >= d->totalFrames ) return 2;

	return 0;
}

float run()
{

	RtAudio adc;
	if ( adc.getDeviceCount() < 1 ) {
		std::cout << "\nNo audio devices found!\n";
		exit( 0 );
	}


	adc.showWarnings( false );
	//double t = 1;
	int i = 0;
	//Initialize ADC parameters
	RtAudio::StreamParameters parameters;
	parameters.deviceId = adc.getDefaultInputDevice();
	parameters.nChannels = 1;
	parameters.firstChannel = 0;
	unsigned int fs = 44100;
	unsigned int bufferFrames = 256;
	unsigned long space;
	data d;

	//RtAudio::StreamParameters oParams;
	//oParams.deviceId = adc.getDefaultOutputDevice();
	//oParams.nChannels = 1;
	//oParams.firstChannel = 0;


	//Initialize storage space
	d.buffer = 0;
	d.channels = parameters.nChannels;
	d.bufferBytes = bufferFrames * d.channels * sizeof( BUFF );
	d.totalFrames = (unsigned long) fs/2;
	d.frameCounter = 0;
	space = d.totalFrames * d.channels * sizeof(BUFF);
	d.buffer = (BUFF*)malloc(space);

	//Open the stream and start recording

	try {
		adc.openStream( NULL, &parameters, RTAUDIO_SINT16,
				fs, &bufferFrames, &record, (void *)&d );
		adc.startStream();
	}

	catch ( RtError& e ) {
		//e.printMessage();
		exit( 0 );
	}

	while( adc.isStreamRunning() ) {
		sleep( 0 );
	}


	//Stop the stream

	try {

		adc.stopStream();
	}
	catch (RtError& e) {
		//e.printMessage();
		clean(adc, d);
	}

	//compute the FFT
	int	NFFT = d.totalFrames-200;


	kiss_fftr_cfg	cfg = NULL;
	kiss_fft_scalar	rin[NFFT];
	kiss_fft_cpx	sout[NFFT];
	float		freq[NFFT];
	float		fft[NFFT];
	float fres = (float) fs / NFFT;
	float temp;

	cfg = kiss_fftr_alloc(NFFT, 0, NULL, NULL);

	for(i=0; i < NFFT; i++){
		rin[i] = (float)d.buffer[i+200]; 

	}

	kiss_fftr(cfg, rin, sout);

	//complex<float>* ctins = new complex<float>[NFFT];
	//for (i = 0; i < NFFT; i++) {
		//ctins[i].real(float(d.buffer[i+200]));
	//}

	//complex<float>* ctfft = FFT(ctins, NFFT); 


	//delete [] ctins;

	//Analyze the frequency
	for (i = 0; i < NFFT; i++) {
		freq[i] = ((float)i)*fres;
		temp = (float) sout[i].r;
		//temp = (float) abs(ctfft[i]);
		fft[i] = temp;
	}


	float		fund = 0;
	int		fundi = 0;
	for (i=0; i< NFFT; i++) {
		if( abs(sout[i].r) > fund )
		{
			fund = fft[i];
			fundi = i;
		}

	}

	//free(cfg);
	//clean(adc, d);
	//delete [] ctfft;



	//Close stream and free memory
	if ( adc.isStreamOpen() ) adc.closeStream();
	if (d.buffer) free(d.buffer);

	
	return freq[fundi];

}

int main(int argc, const char *argv[])
{
	double freq = 0;

	for (int i = 0; i < 1000; i++) {
		freq = run();
		
		string NOTE = findNote( freq );
		float FUND = findFund( freq );
		if(NOTE != "0") 
		{
			system("cmd.exe /c cls");
			
			cout << NOTE << endl;
			cout << freq << endl; 

			cout << "|####|" << endl;


			if( freq - FUND > 8 ) 
				cout << "|****|" << " +8 Hz" << endl;
			else
				cout << "|    |" << endl;
			if( ( freq - FUND > 6 ) && ( freq - FUND < 8) )
				cout << "|****|" << " +6 Hz" << endl;
			else
				cout << "|    |" << endl;
			if( ( freq - FUND > 4 ) && ( freq - FUND < 6) )
				cout << "|****|" << " +4 Hz" << endl;
			else
				cout << "|    |" << endl;
			if( ( freq - FUND > 2 ) && ( freq - FUND < 4) )
				cout << "|****|" << " +2 Hz" << endl;
			else
				cout << "|    |" << endl;
			if( ( freq - FUND > 1 ) && ( freq - FUND < 2) )
				cout << "|****|" << " +1 Hz" << endl;
			else
				cout << "|    |" << endl;

			cout << "|====|" << " " << FUND << endl;

			if( ( freq - FUND < -1 ) && ( freq - FUND > -2) )
				cout << "|****|" << " -1 Hz" << endl;
			else
				cout << "|    |" << endl;
			if( ( freq - FUND < -2 ) && ( freq - FUND > -4) )
				cout << "|****|" << " -2 Hz" << endl;
			else
				cout << "|    |" << endl;
			if( ( freq - FUND < -4 ) && ( freq - FUND > -6) )
				cout << "|****|" << " -4 Hz" << endl;
			else
				cout << "|    |" << endl;
			if( ( freq - FUND < -6 ) && ( freq - FUND > -8) )
				cout << "|****|" << " -6 Hz" << endl;
			else
				cout << "|    |" << endl;
			if( freq - FUND < -8 ) 
				cout << "|****|" << " -8 Hz" << endl;
			else
				cout << "|    |" << endl;


			cout << "|####|" << endl;
		}

	}

	return 0;
}

