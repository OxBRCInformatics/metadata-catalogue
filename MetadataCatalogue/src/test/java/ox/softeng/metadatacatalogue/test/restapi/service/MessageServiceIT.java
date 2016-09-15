package ox.softeng.metadatacatalogue.test.restapi.service;

import ox.softeng.metadatacatalogue.api.MessageApi;
import ox.softeng.metadatacatalogue.domain.core.Message;

public class MessageServiceIT extends DataModelServiceIT<Message> {

	@Override
	protected Message getInstance() throws Exception
	{
		return MessageApi.createMessage(apiCtx, "My test Message", "Message description", "Test Author", "Test Organization");
	}
	
	@Override
	protected String getServicePath()
	{
		return "/message";
	}

	@Override
	protected Class<? extends Message> getClazz()
	{
		return Message.class;
	}	
}
