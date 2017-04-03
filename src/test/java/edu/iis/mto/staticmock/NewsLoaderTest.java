package edu.iis.mto.staticmock;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.internal.util.reflection.Whitebox;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import edu.iis.mto.staticmock.reader.NewsReader;

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
		
		String readerTypeValue = "testedReader";
		Whitebox.setInternalState(configuration, "readerType", readerTypeValue);
		when(mockedConfigurationLoader.loadConfiguration()).thenReturn(configuration);
		
		IncomingNews incomingNews = new IncomingNews();
		IncomingInfo incomingInfo0 = new IncomingInfo("incomingInfo0",SubsciptionType.A);
		IncomingInfo incomingInfo1 = new IncomingInfo("incomingInfo1",SubsciptionType.B);
		IncomingInfo incomingInfo2 = new IncomingInfo("incomingInfo2",SubsciptionType.C);
		IncomingInfo incomingInfo3 = new IncomingInfo("incomingInfo3",SubsciptionType.NONE);
		IncomingInfo incomingInfo4 = new IncomingInfo("incomingInfo4",SubsciptionType.NONE);
		incomingNews.add(incomingInfo0);
		incomingNews.add(incomingInfo1);
		incomingNews.add(incomingInfo2);
		incomingNews.add(incomingInfo3);
		incomingNews.add(incomingInfo4);
		
		NewsReader mockedNewsReader = mock(NewsReader.class);
		when(mockedNewsReader.read()).thenReturn(incomingNews);
	}

	@Test
	public void test() {
		fail("Not yet implemented");
	}

}
