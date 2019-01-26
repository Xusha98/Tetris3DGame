package audio;

import org.lwjgl.openal.*;
import org.lwjgl.system.*;

import java.nio.*;

import static org.lwjgl.openal.AL10.*;
import static org.lwjgl.openal.ALC10.*;
import static org.lwjgl.stb.STBVorbis.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.libc.LibCStdlib.*;



import java.util.ArrayList;


public class AudioMaster {
	
	
	private static ArrayList<Integer> allBuffers = new ArrayList<>();
	private static int bufferPointer;
	private static int sourceID;
	private static long device;
	private static long context;
	private static String defaultDeviceName;
	private static ALCCapabilities alcCapabilities;
	private static ALCapabilities alCapabilities;
	
	public AudioMaster() {
		
	}
	
	
	
	public static void init() {
		
		defaultDeviceName = alcGetString(0, ALC_DEFAULT_DEVICE_SPECIFIER);
		device = alcOpenDevice(defaultDeviceName);

		int[] attributes = { 0 };
		context = alcCreateContext(device, attributes);
		alcMakeContextCurrent(context);

		alcCapabilities = ALC.createCapabilities(device);
		alCapabilities = AL.createCapabilities(alcCapabilities);
		
	}
	

	
	
	public static void play(String file) {
		

		ShortBuffer rawAudioBuffer;

		int channels;
		int sampleRate;

		try (MemoryStack stack = stackPush()) {
			
			IntBuffer channelsBuffer = stack.mallocInt(1);
			IntBuffer sampleRateBuffer = stack.mallocInt(1);

			rawAudioBuffer = stb_vorbis_decode_filename("resources/music/" + file +".ogg", channelsBuffer, sampleRateBuffer);
			channels = channelsBuffer.get(0);
			sampleRate = sampleRateBuffer.get(0);
	
		}

		// Find the correct OpenAL format
		int format = -1;
		if (channels == 1) {
			format = AL_FORMAT_MONO16;

		} else if (channels == 2) {
			format = AL_FORMAT_STEREO16;
			
		}

		bufferPointer = alGenBuffers();
		
		
		alBufferData(bufferPointer, format, rawAudioBuffer, sampleRate);
		
		free(rawAudioBuffer);

		sourceID = alGenSources();
	
		alSourcei(sourceID, AL_BUFFER, bufferPointer);
		
		alSourcePlay(sourceID);

		try {
			// Wait for a second
			Thread.sleep(1000);
		} catch (InterruptedException ignored) {
		}
	}
	
	public static boolean isPlaying() {
		int state = alGetSourcei(sourceID, AL_SOURCE_STATE);
		if(state == AL_PLAYING) {
			return true;
		}
		else
			return false;
	}
	
	public static void destroy() {		
		alDeleteSources(sourceID);
		alDeleteBuffers(bufferPointer);
		alcDestroyContext(context);
		alcCloseDevice(device);
	}
	
}
