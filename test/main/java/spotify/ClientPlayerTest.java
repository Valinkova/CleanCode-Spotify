package spotify;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.SourceDataLine;

import java.io.IOException;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ClientPlayerTest {
    @Mock
    private SourceDataLine sourceDataLine;
    @Mock
    private AudioInputStream audioInputStream;
    private ClientPlayer clientPlayer;
    private byte[] bytesBuffer;

    @Before
    public void setUp() {
        clientPlayer = new ClientPlayer();
        bytesBuffer = new byte[4096];
    }

    @Test
    public void testPlaySongWhenThrowException() throws IOException {
        doNothing().when(sourceDataLine).start();
        when(audioInputStream.read(bytesBuffer)).thenThrow(new IOException());
        when(sourceDataLine.write(bytesBuffer, 0, bytesBuffer.length)).thenReturn(1);

        clientPlayer.playSelectedSong(sourceDataLine, audioInputStream);
    }

    @Test
    public void testPlaySongSuccessful() throws IOException {
        doNothing().when(sourceDataLine).start();
        when(audioInputStream.read(bytesBuffer)).thenReturn(-1);

        clientPlayer.playSelectedSong(sourceDataLine, audioInputStream);
    }
}