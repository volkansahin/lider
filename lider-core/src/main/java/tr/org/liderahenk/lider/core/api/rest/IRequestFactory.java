package tr.org.liderahenk.lider.core.api.rest;

import tr.org.liderahenk.lider.core.api.rest.requests.IPolicyRequest;
import tr.org.liderahenk.lider.core.api.rest.requests.IProfileRequest;
import tr.org.liderahenk.lider.core.api.rest.requests.ITaskCommandRequest;

public interface IRequestFactory {

	IProfileRequest createProfileRequest(String json) throws Exception;

	IPolicyRequest createPolicyRequest(String json) throws Exception;

	ITaskCommandRequest createTaskCommandRequest(String json) throws Exception;

}
