package tr.org.liderahenk.lider.messaging.messages;

import java.util.Date;
import java.util.Map;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import tr.org.liderahenk.lider.core.api.messaging.enums.AgentMessageType;
import tr.org.liderahenk.lider.core.api.messaging.enums.StatusCode;
import tr.org.liderahenk.lider.core.api.messaging.messages.IPolicyStatusMessage;
import tr.org.liderahenk.lider.core.api.persistence.enums.ContentType;

/**
 * Default implementation for {@link IPolicyStatusMessage}. This message is sent
 * <b>from agent to Lider</b> in order to notify Lider about policy
 * result/status.
 * 
 * @author <a href="mailto:emre.akkaya@agem.com.tr">Emre Akkaya</a>
 * @see tr.org.liderahenk.lider.messaging.messages.ExecutePoliciesMessageImpl
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class PolicyStatusMessageImpl implements IPolicyStatusMessage {

	private static final long serialVersionUID = 8345802637309377501L;

	private AgentMessageType type;

	private String policyVersion;

	private Long commandExecutionId;

	private StatusCode responseCode;

	private String responseMessage;

	private Map<String, Object> responseData;

	private ContentType contentType;

	private String from;

	private Date timestamp;

	@Override
	public AgentMessageType getType() {
		return type;
	}

	public void setType(AgentMessageType type) {
		this.type = type;
	}

	@Override
	public String getPolicyVersion() {
		return policyVersion;
	}

	public void setPolicyVersion(String policyVersion) {
		this.policyVersion = policyVersion;
	}

	@Override
	public Long getCommandExecutionId() {
		return commandExecutionId;
	}

	public void setCommandExecutionId(Long commandExecutionId) {
		this.commandExecutionId = commandExecutionId;
	}

	@Override
	public StatusCode getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(StatusCode responseCode) {
		this.responseCode = responseCode;
	}

	@Override
	public String getResponseMessage() {
		return responseMessage;
	}

	public void setResponseMessage(String responseMessage) {
		this.responseMessage = responseMessage;
	}

	@Override
	public Map<String, Object> getResponseData() {
		return responseData;
	}

	public void setResponseData(Map<String, Object> responseData) {
		this.responseData = responseData;
	}

	@Override
	public ContentType getContentType() {
		return contentType;
	}

	public void setContentType(ContentType contentType) {
		this.contentType = contentType;
	}

	@Override
	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	@Override
	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

}
