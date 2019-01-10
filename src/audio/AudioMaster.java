package audio;

import org.lwjgl.openal.*;
import org.lwjgl.system.*;

import java.nio.*;

import static org.lwjgl.openal.AL10.*;
import static org.lwjgl.openal.ALC10.*;
import static org.lwjgl.stb.STBVorbis.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.libc.LibCStdlib.*;

public class AudioMaster {
	
	public AudioMaster() {
		
	}
	
	private int bufferPointer;
	private int sourcePointer;
	private long device;
	private long context;

	public void playMusic() {
		String defaultDeviceName = alcGetString(0, ALC_DEFAULT_DEVICE_SPECIFIER);
		device = alcOpenDevice(defaultDeviceName);

		int[] attributes = { 0 };
		context = alcCreateContext(device, attributes);
		alcMakeContextCurrent(context);

		ALCCapabilities alcCapabilities = ALC.createCapabilities(device);
		ALCapabilities alCapabilities = AL.createCapabilities(alcCapabilities);

		ShortBuffer rawAudioBuffer;

		int channels;
		int sampleRate;

		try (MemoryStack stack = stackPush()) {
			// Allocate space to store return information from the function
			IntBuffer channelsBuffer = stack.mallocInt(1);
			IntBuffer sampleRateBuffer = stack.mallocInt(1);

			rawAudioBuffer = stb_vorbis_decode_filename("TetrisMusic.ogg", channelsBuffer, sampleRateBuffer);
			
			// Retreive the extra information that was stored in the buffers by the function
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

		// Request space for the buffer
		bufferPointer = alGenBuffers();

		// Send the data to OpenAL
		alBufferData(bufferPointer, format, rawAudioBuffer, sampleRate);

		// Free the memory allocated by STB
		free(rawAudioBuffer);

		// Request a source
		sourcePointer = alGenSources();

		// Assign the sound we just loaded to the source
		alSourcei(sourcePointer, AL_BUFFER, bufferPointer);

		// Play the sound
		alSourcePlay(sourcePointer);
		
		try {
			// Wait for a second
			Thread.sleep(1000);
		} catch (InterruptedException ignored) {
		}
	}
	
	public boolean isPlaying() {
		int state = alGetSourcei(sourcePointer, AL_SOURCE_STATE);
		if(state == AL_PLAYING) {
			return true;
		}
		else
			return false;
	}
	
	public void destroy() {		
		alDeleteSources(sourcePointer);
		alDeleteBuffers(bufferPointer);
		alcDestroyContext(context);
		alcCloseDevice(device);
	}

}
