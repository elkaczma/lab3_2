package edu.iis.mto.staticmock;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.internal.util.reflection.Whitebox;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import edu.iis.mto.staticmock.reader.NewsReader;

import static org.powermock.api.mockito.PowerMockito.*;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;


@RunWith(PowerMockRunner.class)
@PrepareForTest({ConfigurationLoader.class, NewsReaderFactory.class})
public class NewsLoaderTest {
	
	private IncomingNews incomingNews = new IncomingNews();
	private final IncomingInfo incomingInfo0 = new IncomingInfo("incomingInfo0",SubsciptionType.A);
	private final IncomingInfo incomingInfo1 = new IncomingInfo("incomingInfo1",SubsciptionType.B);
	private final IncomingInfo incomingInfo2 = new IncomingInfo("incomingInfo2",SubsciptionType.C);
	private final IncomingInfo incomingInfo3 = new IncomingInfo("incomingInfo3",SubsciptionType.NONE);
	private final IncomingInfo incomingInfo4 = new IncomingInfo("incomingInfo4",SubsciptionType.NONE);
	
	private Configuration configuration;
	private ConfigurationLoader mockedConfigurationLoader;
	
	private NewsReader mockedNewsReader;
	private String readerTypeValue = "testedReader";
	
	private NewsLoader newsLoader;
	
	@Before
	public void setUp() throws Exception {
		
		configuration = new Configuration();
		
		mockStatic(ConfigurationLoader.class);
		mockedConfigurationLoader = mock(ConfigurationLoader.class);
		when(mockedConfigurationLoader.getInstance()).thenReturn(mockedConfigurationLoader);
		
		Whitebox.setInternalState(configuration, "readerType", readerTypeValue);
		when(mockedConfigurationLoader.loadConfiguration()).thenReturn(configuration);
		
		incomingNews.add(incomingInfo0);
		incomingNews.add(incomingInfo1);
		incomingNews.add(incomingInfo2);
		incomingNews.add(incomingInfo3);
		incomingNews.add(incomingInfo4);
		
		mockedNewsReader = mock(NewsReader.class);
		when(mockedNewsReader.read()).thenReturn(incomingNews);
		
		mockStatic(NewsReaderFactory.class);
		when(NewsReaderFactory.getReader(readerTypeValue)).thenReturn(mockedNewsReader);
		
		newsLoader = new NewsLoader();
	}

	@Test
	public void testLoadNewsShouldHaveTwoPublicInfoAndThreeInfoForSubscription() {
		
		PublishableNews publishableNews = newsLoader.loadNews();
		
		List<String> publicContent = (List<String>) Whitebox.getInternalState(publishableNews, "publicContent");
		Assert.assertThat(publicContent.size(), is(equalTo(2)));
		
		List<String> subscribentContent = (List<String>) Whitebox.getInternalState(publishableNews, "subscribentContent");
		Assert.assertThat(subscribentContent.size(), is(equalTo(3)));
	}
	
	@Test
	public void testReadMethodShouldBeInvokedOnce() {
		
		PublishableNews publishableNews = newsLoader.loadNews();
		
		verify(mockedNewsReader, times(1)).read();
	}
	
}
