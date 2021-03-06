package tr.org.liderahenk.lider.core.api.service;

import java.util.List;
import java.util.Map;

import tr.org.liderahenk.lider.core.api.plugin.ICommand;
import tr.org.liderahenk.lider.core.api.service.enums.CommandResultStatus;

/**
 * 
 * Factory to create {@link ICommandResult}
 *
 * @author <a href="mailto:birkan.duman@gmail.com">Birkan Duman</a>
 * @author <a href="mailto:emre.akkaya@agem.com.tr">Emre Kağan Akkaya</a>
 * 
 */
public interface ICommandResultFactory {

	/**
	 * 
	 * @param status
	 *            of command result
	 * @param messages
	 *            in command result
	 * @param command
	 *            creating command result
	 * @return new command result
	 */
	ICommandResult create(CommandResultStatus status, List<String> messages, ICommand command);

	/**
	 * @param status
	 *            of command result
	 * @param messages
	 *            in command result
	 * @param command
	 *            creating this command result
	 * @param resultMap
	 *            containing command execution results
	 * @return new command result
	 */
	ICommandResult create(CommandResultStatus status, List<String> messages, ICommand command,
			Map<String, Object> resultMap);

}
