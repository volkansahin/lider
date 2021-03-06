package tr.org.liderahenk.lider.core.api.messaging.subscribers;

import tr.org.liderahenk.lider.core.api.messaging.messages.ILiderMessage;
import tr.org.liderahenk.lider.core.api.messaging.messages.IRegistrationMessage;

/**
 * Agent registration interface, any bundle - exposing an implementation of this
 * interface as a service - will be notified of messages received by underlying
 * messaging system.
 * 
 * @author <a href="mailto:emre.akkaya@agem.com.tr">Emre Akkaya</a>
 *
 */
public interface IRegistrationSubscriber {

	/**
	 * Handle agent registration (and unregistration) according to underlying
	 * system.
	 * 
	 * @param message
	 * @return
	 * @throws Exception
	 * 
	 */
	ILiderMessage messageReceived(IRegistrationMessage message) throws Exception;

	/**
	 * Optional method which can be used to send another message to agent
	 * immediately after the registration process completes.
	 * 
	 * @return
	 * @throws Exception
	 */
	ILiderMessage postRegistration() throws Exception;

}
