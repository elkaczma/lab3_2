package edu.iis.mto.staticmock;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.powermock.api.mockito.PowerMockito.*;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;


@RunWith(PowerMockRunner.class)
@PrepareForTest(ConfigurationLoader.class)
public class NewsLoaderTest {

	@Before
	public void setUp() throws Exception {
		
		Configuration configuration = new Configuration();
		
		mockStatic(ConfigurationLoader.class);
		ConfigurationLoader mockedConfigurationLoader = mock(ConfigurationLoader.class);
		when(mockedConfigurationLoader.getInstance()).thenReturn(mockedConfigurationLoader);
		when(mockedConfigurationLoader.loadConfiguration()).thenReturn(configuration);
		
	}

	@Test
	public void test() {
		fail("Not yet implemented");
	}

}
